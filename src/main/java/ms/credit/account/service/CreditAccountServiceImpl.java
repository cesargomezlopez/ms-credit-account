package ms.credit.account.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Date;
import ms.credit.account.model.CreditAccount;
import ms.credit.account.repository.ICreditAccountRepository;
import ms.credit.account.repository.ICreditAccountTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CreditAccountServiceImpl implements ICreditAccountService {

  @Autowired
  private ICreditAccountRepository creditAccountRepository;
  
  @Autowired
  private ICreditAccountTypeRepository creditAccountTypeRepository;

  @Override
  public Flux<CreditAccount> findAll() {
    return creditAccountRepository.findAll();
  }

  @Override
  public Mono<CreditAccount> findById(String id) {
    return creditAccountRepository.findById(id)
      .switchIfEmpty(Mono.empty());
  }

  @Override
  public Mono<CreditAccount> create(CreditAccount entity) {
    try {
      return getClientByIdFromApi(entity.getClientId()).flatMap(cl -> {
        JsonParser parser = new JsonParser();
        JsonObject client = parser.parse(cl).getAsJsonObject();
        String id = client.get("id").getAsString();
        
        if (id != null) {
          return creditAccountTypeRepository
              .findById(entity.getCreditAccountType().getId()).flatMap(cat -> {
                entity.setStartDate(new Date());
                return creditAccountRepository.save(entity);
              }).switchIfEmpty(Mono.error(new Exception("Credit Account Type not found")));
        } else {
          return Mono.error(new Exception("Client not found"));
        }
      });
    } catch (Exception e) {
      return Mono.error(e);
    }
  }

  @Override
  public Mono<CreditAccount> update(CreditAccount entity) {
    try {
      return creditAccountRepository.findById(entity.getId()).flatMap(ca -> {
        try {
          return getClientByIdFromApi(entity.getClientId()).flatMap(cl -> {
            JsonParser parser = new JsonParser();
            JsonObject client = parser.parse(cl).getAsJsonObject();
            String id = client.get("id").getAsString();
              
            if (id != null) {
              return creditAccountTypeRepository
                  .findById(entity.getCreditAccountType().getId()).flatMap(cat -> {
                    entity.setStartDate(new Date());
                    return creditAccountRepository.save(entity);
                  }).switchIfEmpty(Mono.error(new Exception("Credit Account Type not found")));
            } else {
              return Mono.error(new Exception("Client not found"));
            }
          });
        } catch (Exception e) {
          return Mono.error(e);
        }
      }).switchIfEmpty(Mono.error(new Exception("Credit Account not found")));
    } catch (Exception e) {
      return Mono.error(e);
    }
  }

  @Override
  public Mono<Void> deleteById(String id) {
    try {
      return creditAccountRepository.findById(id).flatMap(ca -> {
        return creditAccountRepository.delete(ca);
      });
    } catch (Exception e) {
      return Mono.error(e);
    }
  }

  @Override
  public Mono<CreditAccount> findByCode(String code) {
    return creditAccountRepository.findByCode(code)
      .switchIfEmpty(Mono.empty());
  }

  @Override
  public Flux<CreditAccount> findByClientId(String clientId) {
    return creditAccountRepository.findByClientId(clientId)
      .switchIfEmpty(Flux.empty());
  }
  
  @Override
  public Mono<Double> getDebt(String creditAccountId) {
    try {
      return creditAccountRepository.findById(creditAccountId).flatMap(ca -> {
        return Mono.just(ca.getConsume());
      }).switchIfEmpty(Mono.empty());
    } catch (Exception e) {
      return Mono.error(e);
    }
  }
  
  @Override
  public Mono<Integer> payDebt(String creditAccountId) {
    try {
      return creditAccountRepository.findById(creditAccountId).flatMap(ca -> {
        ca.setBalance(ca.getCreditAmount());
        ca.setConsume(0.0);
        creditAccountRepository.save(ca).subscribe();
        return Mono.just(1);
      }).switchIfEmpty(Mono.just(-1));
    } catch (Exception e) {
      return Mono.error(e);
    }
  }

  private Mono<String> getClientByIdFromApi(String id) {
    String url = "http://localhost:8001/client/findClientById?id=" + id;
    return WebClient.create()
      .get()
      .uri(url)
      .retrieve()
      .bodyToMono(String.class);
  }
}
