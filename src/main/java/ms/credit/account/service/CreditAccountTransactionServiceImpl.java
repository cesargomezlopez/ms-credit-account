package ms.credit.account.service;

import ms.credit.account.model.CreditAccountTransaction;
import ms.credit.account.repository.ICreditAccountTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CreditAccountTransactionServiceImpl implements ICreditAccountTransactionService {

  @Autowired
  private ICreditAccountTransactionRepository creditAccountTransactionRepository;

  @Override
  public Flux<CreditAccountTransaction> findAll() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Mono<CreditAccountTransaction> findById(String id) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Mono<CreditAccountTransaction> create(CreditAccountTransaction entity) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Mono<CreditAccountTransaction> update(CreditAccountTransaction entity) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Mono<Void> deleteById(String id) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Flux<CreditAccountTransaction> findByClientId(String clientId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Flux<CreditAccountTransaction> findByCreditAccountId(String creditAccountId) {
    // TODO Auto-generated method stub
    return null;
  }

}
