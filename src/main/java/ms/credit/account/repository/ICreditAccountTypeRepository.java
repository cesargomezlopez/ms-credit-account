package ms.credit.account.repository;

import ms.credit.account.model.CreditAccountType;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICreditAccountTypeRepository
      extends ReactiveMongoRepository<CreditAccountType, String> {

}
