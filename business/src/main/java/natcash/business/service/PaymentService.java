package natcash.business.service;

import natcash.business.dto.request.PaymentRequestDTO;
import natcash.business.dto.response.PaymentDetailResponse;
import natcash.business.dto.response.PaymentResponseDTO;
import natcash.business.entity.Payment;

import java.util.UUID;

public interface PaymentService {

    Payment createPayment(PaymentRequestDTO requestDTO, String finAccount);

    void updateTransaction(String orderId, String status);

    String checkPaymentStatus(String orderId);

    Payment findPaymentByOrderId(String orderId);
    Payment findById(UUID paymentId);

    PaymentDetailResponse findPaymentById(UUID id);

    void updatePaymentStatus(Payment payment);
    void expiredPaymentStatus(UUID id);

}
