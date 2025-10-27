package natcash.business.service;

import natcash.business.dto.request.PaymentRequestDTO;
import natcash.business.dto.response.PaymentResponseDTO;
import natcash.business.entity.Partner;

public interface PartnerService {
    Partner findByUsername(String username);

    PaymentResponseDTO initPayment(PaymentRequestDTO requestDTO, String clientIp) throws Exception;
}
