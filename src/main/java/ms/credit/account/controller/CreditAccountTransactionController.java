package ms.credit.account.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import ms.credit.account.model.CreditAccountTransaction;
import ms.credit.account.service.ICreditAccountTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/creditAccountTransaction")
@Api(value = "creditAccountTransaction")
public class CreditAccountTransactionController {

  @Autowired
  private ICreditAccountTransactionService creditAccountTransactionService;

  @GetMapping(value = "/findAllCreditAccountTransactions",
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Find All Credit Account Transactions",
      notes = "Find All Credit Account Transactions registered")
  public Mono<ResponseEntity<Flux<CreditAccountTransaction>>> findAllCreditAccountTransactions() {
    return Mono.just(ResponseEntity.ok()
      .contentType(MediaType.APPLICATION_JSON)
      .body(creditAccountTransactionService.findAll()))
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }
  
  @GetMapping(value = "/findCreditAccountTransactionById", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Find a Credit Account Transaction by Id",
      notes = "Find a Credit Account Transaction registered")
  public Mono<ResponseEntity<CreditAccountTransaction>> 
      findCreditAccountTransactionById(@RequestParam("id")String id) {
    return creditAccountTransactionService.findById(id).flatMap(ca -> {
      return Mono.just(ResponseEntity
        .ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(ca));
    }).defaultIfEmpty(ResponseEntity.notFound().build());
  }
  
  @PostMapping(value = "/createCreditAccountTransaction", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Creates a Credit Account Transaction",
      notes = "Register a Credit Account Transaction")
  public Mono<ResponseEntity<CreditAccountTransaction>>
      createCreditAccountTransaction(
        @Valid @RequestBody CreditAccountTransaction creditAccountTransaction) {
    return creditAccountTransactionService.create(creditAccountTransaction).flatMap(ca -> {
      return Mono.just(ResponseEntity
        .ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(ca));
    }).defaultIfEmpty(ResponseEntity.notFound().build());
  }
  
  @PutMapping(value = "/updateCreditAccountTransaction", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Updates a Credit Account Transaction",
      notes = "Updates a Credit Account Transaction registered")
  public Mono<ResponseEntity<CreditAccountTransaction>>
      updateCreditAccountTransaction(
        @Valid @RequestBody CreditAccountTransaction creditAccountTransaction) {
    return creditAccountTransactionService.update(creditAccountTransaction).flatMap(ca -> {
      return Mono.just(ResponseEntity
        .ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(ca));
    }).defaultIfEmpty(ResponseEntity.notFound().build());
  }
  
  @DeleteMapping(value = "/deleteCreditAccountTransactionById", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Deletes a Credit Account Transaction by Id",
      notes = "Removes a Credit Account Transaction registered")
  public Mono<ResponseEntity<Void>>
      deleteCreditAccountTransactionById(@RequestParam("id")String id) {
    return creditAccountTransactionService.deleteById(id)
      .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }
  
  @GetMapping(value = "/findAllCreditAccountTransactionsByCreditAccountId",
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Find All Credit Account Transactions by Credit Account Id",
      notes = "Find All Credit Account Transactions registered by Credit Account Id")
  public Mono<ResponseEntity<Flux<CreditAccountTransaction>>>
          findAllCreditAccountTransactionsByCreditAccountId(
            @RequestParam("creditAccountId")String creditAccountId) {
    return Mono.just(ResponseEntity.ok()
      .contentType(MediaType.APPLICATION_JSON)
      .body(creditAccountTransactionService.findByCreditAccountId(creditAccountId)))
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }
  
  @PostMapping(value = "/payDebt", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Pay Credit Account Debt", notes = "Required credit account id")
  public Mono<ResponseEntity<Integer>> payDebt(
      @RequestParam("creditAccountId")String creditAccountId,
      @RequestParam("amount")Double amount){
    return creditAccountTransactionService.payDebt(creditAccountId, amount).flatMap(rs -> {
      return Mono.just(ResponseEntity
        .ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(rs));
    }).defaultIfEmpty(ResponseEntity.notFound().build());
  }

}
