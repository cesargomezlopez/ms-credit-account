package ms.credit.account.util;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICrud<T,S> {

  public Flux<T> findAll();

  public Mono<T> findById(S id);

  public Mono<T> create(T entity);
  
  public Mono<T> update(T entity);

  public Mono<Void> deleteById(S id);
  
}
