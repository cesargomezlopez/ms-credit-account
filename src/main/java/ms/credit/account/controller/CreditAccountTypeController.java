package ms.credit.account.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import ms.credit.account.model.CreditAccountType;
import ms.credit.account.service.ICreditAccountTypeService;
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
@RequestMapping(value = "/creditAccountType")
@Api(value = "creditAccountType")
public class CreditAccountTypeController {

  @Autowired
  private ICreditAccountTypeService creditAccountTypeService;

  @GetMapping(value = "/findAllCreditAccountTypes", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Find all Credit Account Types",
      notes = "Find all Credit Account Types registered")
  public Mono<ResponseEntity<Flux<CreditAccountType>>> findAllCreditAccountTypes() {
    return Mono.just(ResponseEntity
      .ok()
      .contentType(MediaType.APPLICATION_JSON)
      .body(creditAccountTypeService.findAll()))
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }
  
  @GetMapping(value = "/findCreditAccountTypeById", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Find a Credit Account Type by Id",
      notes = "Find a Credit Account Type registered")
  public Mono<ResponseEntity<CreditAccountType>> 
      findCreditAccountTypeById(@RequestParam("id")String id) {
    return creditAccountTypeService.findById(id).flatMap(cat -> {
      return Mono.just(ResponseEntity
        .ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(cat));
    }).defaultIfEmpty(ResponseEntity.notFound().build());
  }
  
  @PostMapping(value = "/createCreditAccountType", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Creates a Credit Account Type",
      notes = "Register a Credit Account Type")
  public Mono<ResponseEntity<CreditAccountType>>
      createCreditAccountType(@Valid @RequestBody CreditAccountType creditAccountType) {
    return creditAccountTypeService.create(creditAccountType).flatMap(cat -> {
      return Mono.just(ResponseEntity
        .ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(cat));
    }).defaultIfEmpty(ResponseEntity.notFound().build());
  }
  
  @PutMapping(value = "/updateCreditAccountType", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Updates a Credit Account Type",
      notes = "Updates a Credit Account Type registered")
  public Mono<ResponseEntity<CreditAccountType>>
      updateCreditAccountType(@Valid @RequestBody CreditAccountType creditAccountType) {
    return creditAccountTypeService.update(creditAccountType).flatMap(cat -> {
      return Mono.just(ResponseEntity
        .ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(cat));
    }).defaultIfEmpty(ResponseEntity.notFound().build());
  }
  
  @DeleteMapping(value = "/deleteCreditAccountTypeById", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Deletes a Credit Account Type by Id",
      notes = "Removes a Credit Account Type registered")
  public Mono<ResponseEntity<Void>> deleteCreditAccountTypeById(@RequestParam("id")String id) {
    return creditAccountTypeService.deleteById(id)
      .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }
}
