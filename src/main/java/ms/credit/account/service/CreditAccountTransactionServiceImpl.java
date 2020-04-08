package ms.credit.account.service;

import java.util.Date;
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
        if (entity.getAmount() > ca.getConsume()) {
          ca.setBalance(ca.getCreditAmount() + entity.getAmount() - ca.getConsume());
          ca.setConsume(0.0);
        } else if (-entity.getAmount() > ca.getCreditAmount()
            && ca.getBalance() > ca.getCreditAmount()) {
          System.out.println("asdasdasdasdasd1");
          ca.setBalance(0.0);
          ca.setConsume(ca.getCreditAmount());
        } else {
          ca.setBalance(ca.getBalance() + entity.getAmount());
          ca.setConsume(ca.getConsume() - entity.getAmount());
        }
        
        if (ca.getBalance() >= 0 && ca.getConsume() <= ca.getCreditAmount()) {
          creditAccountRepository.save(ca).subscribe();
          entity.setRegisterDate(new Date());
          return creditAccountTransactionRepository.save(entity);
        } else {
          return Mono.error(
             new Exception("Consume can not exceed the limit of the credit account"));
        }
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
          cat.setBalance(cat.getBalance() + entity.getAmount());
          cat.setConsume(cat.getConsume() - entity.getAmount());
          if (cat.getBalance() >= 0 && cat.getConsume() <= cat.getCreditAmount()) {
            creditAccountRepository.save(cat).subscribe();
            return creditAccountTransactionRepository.save(entity);
          } else {
            return Mono.error(
                 new Exception("Consume can not exceed the limit of the credit account"));
          }
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
  public Mono<Integer> payDebt(String creditAccountId, Double amount) {
    try {
      return creditAccountRepository.findById(creditAccountId).flatMap(ca -> {
        CreditAccountTransaction cat = new CreditAccountTransaction();
        cat.setCreditAccountId(creditAccountId);
        cat.setAmount(amount);
        cat.setRegisterDate(new Date());
        this.create(cat).subscribe();
        return Mono.just(1);
      }).switchIfEmpty(Mono.just(-1));
    } catch (Exception e) {
      return Mono.error(e);
    }
  }
  
  public Mono<Boolean> existsBankByIdFromApi(String idBank) {
    try {
      String url = "http://localhost:8004/bank/existsById?id=" + idBank;
      return WebClient.create()
          .get()
          .uri(url)
          .retrieve()
          .bodyToMono(Boolean.class);
    } catch (Exception e) {
      return Mono.error(e);
    }
  }

}
