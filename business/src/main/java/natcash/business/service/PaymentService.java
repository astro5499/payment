package natcash.business.service;

import natcash.business.dto.request.PaymentRequestDTO;
import natcash.business.dto.response.PaymentDetailResponse;
import natcash.business.dto.response.PaymentQueryDTO;
import natcash.business.entity.Payment;

import java.util.Set;
import java.util.UUID;

public interface PaymentService {

    Payment createPayment(PaymentRequestDTO requestDTO, String finAccount, String transCode);

    void updateTransaction(String orderId, String status);

    String checkPaymentStatus(String orderId);

    Payment findPaymentByTransCodeAndStatus(String transCode, String status);
    Payment findById(UUID paymentId);

    PaymentDetailResponse findPaymentById(UUID id);
    PaymentDetailResponse findPaymentByPartnerCodeAndOrderId(String partnerCode, String orderId);

    void updatePaymentStatus(Payment payment);
    void expiredPaymentStatus(UUID id);

    Set<PaymentQueryDTO> findAllPaymentByStatus(String status);

    Set<PaymentQueryDTO> findByPartnerCodeAndOrderIdAndStatus(String partnerId, String orderId, String status);


    void confirmPayments(UUID ids);
}
