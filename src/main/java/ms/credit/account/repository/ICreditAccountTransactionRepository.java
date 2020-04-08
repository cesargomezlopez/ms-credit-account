package ms.credit.account.repository;

import ms.credit.account.model.CreditAccountTransaction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ICreditAccountTransactionRepository
    extends ReactiveMongoRepository<CreditAccountTransaction, String> {

  Flux<CreditAccountTransaction> findByCreditAccountId(String creditAccountId);

}
