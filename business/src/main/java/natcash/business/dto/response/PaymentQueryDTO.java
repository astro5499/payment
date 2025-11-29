package natcash.business.dto.response;

import java.util.UUID;

public interface PaymentQueryDTO {

    UUID getId();

    String getToAccount();

    Long getTransactionId();
}
