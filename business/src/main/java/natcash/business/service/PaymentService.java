package natcash.business.service;

import natcash.business.dto.request.PaymentRequestDTO;
import natcash.business.dto.response.PaymentResponseDTO;
import natcash.business.entity.Payment;

public interface PaymentService {

    Payment createPayment(PaymentRequestDTO requestDTO, String finAccount);

    void updateTransaction(String orderId, String status);

    String checkPaymentStatus(String orderId);
}
