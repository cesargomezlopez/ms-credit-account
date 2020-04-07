package ms.credit.account.service;

import ms.credit.account.model.CreditAccount;
import ms.credit.account.util.ICrud;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICreditAccountService extends ICrud<CreditAccount, String> {

  Mono<CreditAccount> findByCode(String code);

  Flux<CreditAccount> findByClientId(String clientId);
  
  Mono<Double> getDebt(String creditAccountId);
  
  Mono<Integer> payDebt(String creditAccount);

}
