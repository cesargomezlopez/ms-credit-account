package ms.credit.account.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import javax.validation.Valid;
import javax.validation.constraints.Min;
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

  @NotEmpty(message = "Credit Account Bank Id can not be empty")
  private String bankId;

  @Min(value = 0L, message = "Credit Amount can not be negative")
  private Double creditAmount;

  @Min(value = 0L, message = "Credit Account Balance can not be negative")
  private Double balance;

  @Min(value = 0L, message = "Credit Account Consume can not be negative")
  private Double consume;

  @Min(value = 0L, message = "Credit Account Interest can not be negative")
  private Double interest;

  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date startDate;

  @DBRef
  @Valid
  private CreditAccountType creditAccountType;

}
