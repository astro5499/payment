package natcash.business.service;

import natcash.business.dto.response.PaymentLogResponseDTO;

import java.util.List;

public interface AgentApiClient {

    List<PaymentLogResponseDTO> getPayments(String phoneNumber);
}
