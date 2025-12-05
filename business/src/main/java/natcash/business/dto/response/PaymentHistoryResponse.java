package natcash.business.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class PaymentHistoryResponse {

    private String resultCode;

    private String resultMessage;

    private List<PaymentLogResponseDTO> results;
}
