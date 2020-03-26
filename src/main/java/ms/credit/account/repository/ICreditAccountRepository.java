package ms.credit.account.repository;

import ms.credit.account.model.CreditAccount;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ICreditAccountRepository extends ReactiveMongoRepository<CreditAccount, String> {

  Mono<CreditAccount> findByCode(String code);

  Flux<CreditAccount> findByClientId(String clientId);

}
