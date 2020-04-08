package ms.credit.account.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import ms.credit.account.model.CreditAccount;
import ms.credit.account.service.ICreditAccountService;
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
@RequestMapping(value = "/creditAccount")
@Api(value = "creditAccount")
public class CreditAccountController {

  @Autowired
  private ICreditAccountService creditAccountService;

  @GetMapping(value = "/findAllCreditAccounts", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Find all Credit Accounts",
      notes = "Find all Credit Accounts registered")
  public Mono<ResponseEntity<Flux<CreditAccount>>> findAllCreditAccounts() {
    return Mono.just(ResponseEntity
      .ok()
      .contentType(MediaType.APPLICATION_JSON)
      .body(creditAccountService.findAll()))
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }
  
  @GetMapping(value = "/findCreditAccountById", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Find a Credit Account by Id",
      notes = "Find a Credit Account registered")
  public Mono<ResponseEntity<CreditAccount>> 
      findCreditAccountById(@RequestParam("id")String id) {
    return creditAccountService.findById(id).flatMap(ca -> {
      return Mono.just(ResponseEntity
        .ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(ca));
    }).defaultIfEmpty(ResponseEntity.notFound().build());
  }
  
  @GetMapping(value = "/findCreditAccountsByClientId", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Find a Credit Accounts by Client Id",
      notes = "Find a Credit Accounts registered By Client Id")
  public Mono<ResponseEntity<Flux<CreditAccount>>> 
      findCreditAccountsByClientId(@RequestParam("clientId")String clientId) {
    return Mono.just(ResponseEntity
      .ok()
      .contentType(MediaType.APPLICATION_JSON)
      .body(creditAccountService.findByClientId(clientId)))
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }
  
  @PostMapping(value = "/createCreditAccount", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Creates a Credit Account",
      notes = "Register a Credit Account")
  public Mono<ResponseEntity<CreditAccount>>
      createCreditAccount(@Valid @RequestBody CreditAccount creditAccount) {
    return creditAccountService.create(creditAccount).flatMap(ca -> {
      return Mono.just(ResponseEntity
        .ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(ca));
    }).defaultIfEmpty(ResponseEntity.notFound().build());
  }
  
  @PutMapping(value = "/updateCreditAccount", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Updates a Credit Account",
      notes = "Updates a Credit Account registered")
  public Mono<ResponseEntity<CreditAccount>>
      updateCreditAccount(@Valid @RequestBody CreditAccount creditAccount) {
    return creditAccountService.update(creditAccount).flatMap(ca -> {
      return Mono.just(ResponseEntity
        .ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(ca));
    }).defaultIfEmpty(ResponseEntity.notFound().build());
  }
  
  @DeleteMapping(value = "/deleteCreditAccountById", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Deletes a Credit Account by Id",
      notes = "Removes a Credit Account registered")
  public Mono<ResponseEntity<Void>> deleteCreditAccountById(@RequestParam("id")String id) {
    return creditAccountService.deleteById(id)
      .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }
  
  @GetMapping(value = "/getDebt", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Get Credit Account Debt", notes = "Required credit account id")
  public Mono<ResponseEntity<Double>> getDebt(
      @RequestParam("creditAccountId")String creditAccountId){
    return creditAccountService.getDebt(creditAccountId).flatMap(debt -> {
      return Mono.just(ResponseEntity
        .ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(debt));
    }).defaultIfEmpty(ResponseEntity.notFound().build());
  }

}
