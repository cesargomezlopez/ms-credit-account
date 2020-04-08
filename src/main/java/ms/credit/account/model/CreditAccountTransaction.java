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
  
  private double amount;
  
  @NotEmpty(message = "Credit Account Id can not be empty")
  private String creditAccountId;

  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date registerDate;
}
