package natcash.business.service.impl;

import lombok.RequiredArgsConstructor;
import natcash.business.dto.request.PaymentRequestDTO;
import natcash.business.entity.Payment;
import natcash.business.repository.PaymentRepository;
import natcash.business.service.PaymentService;
import natcash.business.utils.PaymentStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository repository;

    private final Map<String, String> orderStatus = new ConcurrentHashMap<>();

    @Override
    public Payment createPayment(PaymentRequestDTO requestDTO, String finAccount) {
        Payment payment = new Payment();
        payment.setId(UUID.randomUUID());
        payment.setOrderId(requestDTO.getOrderNumber());
        payment.setAmount(requestDTO.getAmount());
        payment.setFromPartner(requestDTO.getUsername());
        payment.setToAccount(finAccount);
        payment.setStatus(PaymentStatus.PENDING.getValue());
        payment.setCreatedAt(LocalDateTime.now());
        payment.setUpdatedAt(LocalDateTime.now());

        return repository.save(payment);
    }

    public void updateTransaction(String orderId, String status) {
        orderStatus.put(orderId, status);
    }

    public String checkPaymentStatus(String orderId) {
        return orderStatus.getOrDefault(orderId, "NOT_FOUND");
    }
}