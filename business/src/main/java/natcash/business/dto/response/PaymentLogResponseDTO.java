package natcash.business.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class PaymentLogResponseDTO {
    private String transactionName;
    private String transactionId;
    private String accountNumber;
    private String fullName;
    private String toAccountNumber;
    private String toFullName;
    private String amount;
    private String fee;
    private String discount;
    private String totalAmount;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private OffsetDateTime createDate;
    private String content;
    private String transType;
    private String transStatus;
    private String direction;
}
