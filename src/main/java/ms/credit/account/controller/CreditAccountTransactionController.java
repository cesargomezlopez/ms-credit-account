package ms.credit.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import ms.credit.account.service.ICreditAccountTransactionService;

@RestController
@RequestMapping(value = "/creditAccountTransaction")
@Api(value = "creditAccountTransaction")
public class CreditAccountTransactionController {

  @Autowired
  private ICreditAccountTransactionService creditAccountTransactionService;

}
