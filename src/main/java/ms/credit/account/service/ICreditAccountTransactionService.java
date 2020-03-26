package ms.credit.account.service;

import ms.credit.account.model.CreditAccountTransaction;
import ms.credit.account.util.ICrud;
import reactor.core.publisher.Flux;

public interface ICreditAccountTransactionService extends ICrud<CreditAccountTransaction, String> {

  Flux<CreditAccountTransaction> findByClientId(String clientId);

  Flux<CreditAccountTransaction> findByCreditAccountId(String creditAccountId);

}
