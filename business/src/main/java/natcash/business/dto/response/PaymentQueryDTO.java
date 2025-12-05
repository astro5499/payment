package natcash.business.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public interface PaymentQueryDTO {

    String getId();

    String getToAccount();

    Long getTransactionId();
}
