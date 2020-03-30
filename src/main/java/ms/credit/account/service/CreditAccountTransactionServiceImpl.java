package ms.credit.account.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Date;
import ms.credit.account.model.CreditAccount;
import ms.credit.account.model.CreditAccountTransaction;
import ms.credit.account.repository.ICreditAccountRepository;
import ms.credit.account.repository.ICreditAccountTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CreditAccountTransactionServiceImpl implements ICreditAccountTransactionService {

  @Autowired
  private ICreditAccountTransactionRepository creditAccountTransactionRepository;

  @Autowired
  private ICreditAccountRepository creditAccountRepository;

  @Override
  public Flux<CreditAccountTransaction> findAll() {
    return creditAccountTransactionRepository.findAll();
  }

  @Override
  public Mono<CreditAccountTransaction> findById(String id) {
    return creditAccountTransactionRepository.findById(id)
        .switchIfEmpty(Mono.empty());
  }

  @Override
  public Mono<CreditAccountTransaction> create(CreditAccountTransaction entity) {
    try {
      return creditAccountRepository.findById(entity.getCreditAccountId()).flatMap(ca -> {
        return getClientByIdFromApi(entity.getClientId()).flatMap(cl -> {
          JsonParser parser = new JsonParser();
          JsonObject client = parser.parse(cl).getAsJsonObject();
          String idClient = client.get("id").getAsString();

          if (idClient != null) {
            ca.setBalance(ca.getBalance() + entity.getAmount());
            ca.setConsume(ca.getConsume() - entity.getAmount());
            if (ca.getBalance() >= 0 && ca.getConsume() <= ca.getCreditAmount()) {
              creditAccountRepository.save(ca).subscribe();
              entity.setRegisterDate(new Date());
              return creditAccountTransactionRepository.save(entity);
            } else {
              return Mono.error(
                 new Exception("Consume can not exceed the limit of the credit account"));
            }
          } else {
            return Mono.error(new Exception("Client not found"));
          }
        });
      }).switchIfEmpty(Mono.error(new Exception("Credit Account not found")));
    } catch (Exception e) {
      return Mono.error(e);
    }
  }

  @Override
  public Mono<CreditAccountTransaction> update(CreditAccountTransaction entity) {
    try {
      return creditAccountTransactionRepository.findById(entity.getId()).flatMap(catr -> {
        return creditAccountRepository.findById(entity.getCreditAccountId()).flatMap(cat -> {
          return getClientByIdFromApi(entity.getClientId()).flatMap(cl -> {
            JsonParser parser = new JsonParser();
            JsonObject client = parser.parse(cl).getAsJsonObject();
            String idClient = client.get("id").getAsString();

            if (idClient != null) {
              cat.setBalance(cat.getBalance() + entity.getAmount());
              cat.setConsume(cat.getConsume() - entity.getAmount());
              if (cat.getBalance() >= 0 && cat.getConsume() <= cat.getCreditAmount()) {
                creditAccountRepository.save(cat).subscribe();
                entity.setRegisterDate(new Date());
                return creditAccountTransactionRepository.save(entity);
              } else {
                return Mono.error(
                     new Exception("Consume can not exceed the limit of the credit account"));
              }
            } else {
              return Mono.error(new Exception("Client not found"));
            }
          });
        }).switchIfEmpty(Mono.error(new Exception("Credit Account not found")));
      }).switchIfEmpty(Mono.error(new Exception("Credit Account Transaction not found")));
    } catch (Exception e) {
      return Mono.error(e);
    }
  }

  @Override
  public Mono<Void> deleteById(String id) {
    try {
      return creditAccountTransactionRepository.findById(id).flatMap(cat -> {
        return creditAccountTransactionRepository.delete(cat);
      });
    } catch (Exception e) {
      return Mono.error(e);
    }
  }

  @Override
  public Flux<CreditAccountTransaction> findByClientId(String clientId) {
    try {
      return getClientByIdFromApi(clientId).flatMapMany(cl -> {
        JsonParser parser = new JsonParser();
        JsonObject client = parser.parse(cl).getAsJsonObject();
        String id = client.get("id").getAsString();

        if (id != null) {
          return creditAccountTransactionRepository.findByClientId(clientId); 
        } else {
          return Flux.error(new Exception("Client not found"));
        }
      });
    } catch (Exception e) {
      return Flux.error(e);
    }
  }

  @Override
  public Flux<CreditAccountTransaction> findByCreditAccountId(String creditAccountId) {
    try {
      return creditAccountRepository.findById(creditAccountId).flatMapMany(ca -> {
        return creditAccountTransactionRepository.findByCreditAccountId(creditAccountId);
      }).switchIfEmpty(Flux.error(new Exception("Credit Account not found")));
    } catch (Exception e) {
      return Flux.error(e);
    }
  }
  
  @Override
  public Flux<CreditAccountTransaction>
      findByCreditAccountIdAndClientId(String creditAccountId, String clientId) {
    try {
      return getClientByIdFromApi(clientId).flatMapMany(cl -> {
        JsonParser parser = new JsonParser();
        JsonObject client = parser.parse(cl).getAsJsonObject();
        String id = client.get("id").getAsString();

        if (id != null) {
          return creditAccountRepository.findById(creditAccountId).flatMapMany(ca -> {
            return creditAccountTransactionRepository
                  .findByCreditAccountIdAndClientId(creditAccountId, clientId);
          }).switchIfEmpty(Flux.error(new Exception("Credit Account not found")));
        } else {
          return Flux.error(new Exception("Client not found"));
        }
      });
    } catch (Exception e) {
      return Flux.error(e);
    }
  }
  
  public Mono<String> getClientByIdFromApi(String id) {
    try {
      String url = "http://localhost:8001/client/findClientById?id=" + id;
      return WebClient.create()
          .get()
          .uri(url)
          .retrieve()
          .bodyToMono(String.class);
    } catch (Exception e) {
      return Mono.error(e);
    }
  }

}
