package ms.credit.account.model;

import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "creditAccountType")
public class CreditAccountType {

  @Id
  @Generated
  private String id;

  @NotEmpty(message = "Credit Account Code can not be empty")
  private String code;

  @NotEmpty(message = "Credit Account Description can not be empty")
  private String description;

  private Double minCreditAmount;

  private Double maxCreditAmount;

  private Double minInterest;

  private Double maxInterest;

}
