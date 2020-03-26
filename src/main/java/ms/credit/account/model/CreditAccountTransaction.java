package ms.credit.account.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "creditAccountTransaction")
public class CreditAccountTransaction {

  @Id
  private String id;
  
  @NotEmpty(message = "Credit Account Transaction Code can not be empty")
  private String code;
  
  private double amount;
  
  @NotEmpty(message = "Credit Account Id can not be empty")
  private String creditAccountId;

  @NotEmpty(message = "Credit Account Client Id can not be empty")
  private String clientId;

  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date registerDate;
}
