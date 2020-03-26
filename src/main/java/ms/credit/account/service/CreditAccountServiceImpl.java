package ms.credit.account.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Date;
import ms.credit.account.model.CreditAccount;
import ms.credit.account.repository.ICreditAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CreditAccountServiceImpl implements ICreditAccountService {

  @Autowired
  private ICreditAccountRepository creditAccountRepository;

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
          entity.setStartDate(new Date());
          return creditAccountRepository.save(entity);
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
            System.out.println(cl);

            JsonParser parser = new JsonParser();
            JsonObject client = parser.parse(cl).getAsJsonObject();

            System.out.println(client);
              
            String id = client.get("id").getAsString();

            System.out.println(id);
              
            if (id != null) {
              return creditAccountRepository.save(entity);
            } else {
              return Mono.error(new Exception("Client not found"));
            }
              
          });
        } catch (Exception e) {
          return Mono.error(e);
        }
        
        
      }).switchIfEmpty(Mono.error(new Exception("Bank Account not found")));
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
    return creditAccountRepository.findByClientId(clientId);
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
