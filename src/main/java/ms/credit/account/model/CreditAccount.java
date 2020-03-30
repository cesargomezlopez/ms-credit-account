package ms.credit.account.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "creditAccount")
public class CreditAccount {

  @Id
  @Generated
  private String id;

  @NotEmpty(message = "Credit Account Code can not be empty")
  private String code;

  @NotEmpty(message = "Credit Account Client Id can not be empty")
  private String clientId;

  private Double creditAmount;
  
  private Double balance;
  
  private Double consume;

  private Double interest;

  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date startDate;

  @DBRef
  @Valid
  private CreditAccountType creditAccountType;

}
