package natcash.business.dto.request;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class WalletTransactionRequest {
    private String transactionId;
    private BigDecimal  amount;
    private String transactionContent;
    private String fromPhone;
    private String toPhone;
}
