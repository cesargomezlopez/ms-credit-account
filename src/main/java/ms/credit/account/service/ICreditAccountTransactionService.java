package ms.credit.account.service;

import ms.credit.account.model.CreditAccountTransaction;
import ms.credit.account.util.ICrud;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICreditAccountTransactionService extends ICrud<CreditAccountTransaction, String> {

  Flux<CreditAccountTransaction> findByCreditAccountId(String creditAccountId);

  Mono<Integer> payDebt(String creditAccount, Double amount);

}
