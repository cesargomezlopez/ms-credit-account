package ms.credit.account.service;

import ms.credit.account.model.CreditAccountType;
import ms.credit.account.repository.ICreditAccountTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CreditAccountTypeServiceImpl implements ICreditAccountTypeService {

  @Autowired
  private ICreditAccountTypeRepository creditAccountTypeRepository;

  @Override
  public Flux<CreditAccountType> findAll() {
    return creditAccountTypeRepository.findAll();
  }

  @Override
  public Mono<CreditAccountType> findById(String id) {
    return creditAccountTypeRepository.findById(id)
        .switchIfEmpty(Mono.empty());
  }

  @Override
  public Mono<CreditAccountType> create(CreditAccountType entity) {
    try {
      return creditAccountTypeRepository.save(entity);
    } catch (Exception e) {
      return Mono.error(e);
    }
  }

  @Override
  public Mono<CreditAccountType> update(CreditAccountType entity) {
    try {
      return creditAccountTypeRepository.findById(entity.getId()).flatMap(cat -> {
        return creditAccountTypeRepository.save(entity);
      }).switchIfEmpty(Mono.error(new Exception("Credit Account Type not found")));
    } catch (Exception e) {
      return Mono.error(e);
    }
  }

  @Override
  public Mono<Void> deleteById(String id) {
    try {
      return creditAccountTypeRepository.findById(id).flatMap(cat -> {
        return creditAccountTypeRepository.delete(cat);
      });
    } catch (Exception e) {
      return Mono.error(e);
    }
  }

}
