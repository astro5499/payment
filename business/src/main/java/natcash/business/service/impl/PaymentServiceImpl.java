package natcash.business.service.impl;

import lombok.RequiredArgsConstructor;
import natcash.business.dto.request.PaymentRequestDTO;
import natcash.business.dto.response.PaymentDetailResponse;
import natcash.business.entity.FinAccount;
import natcash.business.entity.Payment;
import natcash.business.repository.PaymentRepository;
import natcash.business.service.FinAccountService;
import natcash.business.service.PaymentService;
import natcash.business.utils.PaymentStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    @Value("${payment.expired_time}")
    private Long timeToLive;

    private final PaymentRepository repository;
    private final FinAccountService finAccountService;

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

    @Override
    public Payment findPaymentByOrderId(String orderId) {
        return repository.findPaymentByOrderId(orderId);
    }

    @Override
    public Payment findById(UUID paymentId) {
        return repository.findById(paymentId).orElse(null);
    }

    @Override
    public PaymentDetailResponse findPaymentById(UUID id) {
        PaymentDetailResponse paymentDetailResponse = new PaymentDetailResponse();
        Payment payment = repository.findById(id).orElse(null);
        if (Objects.nonNull(payment)) {
            FinAccount finAccount = finAccountService.findFinAccountById(payment.getToAccount());

            paymentDetailResponse.setPaymentId(payment.getId());
            paymentDetailResponse.setAmount(payment.getAmount());
            paymentDetailResponse.setAccountId(payment.getToAccount());
            paymentDetailResponse.setOrderId(payment.getOrderId());
            paymentDetailResponse.setQrCode(finAccount.getQrCode());
            paymentDetailResponse.setCreatedAt(payment.getCreatedAt());
            paymentDetailResponse.setExpiredTime(timeToLive);
        }
        return paymentDetailResponse;
    }

    @Override
    public void updatePaymentStatus(Payment payment) {
        repository.save(payment);
    }

    @Override
    public void expiredPaymentStatus(UUID id) {
        Payment payment = repository.findById(id).orElse(null);
        if (Objects.nonNull(payment)) {
            payment.setStatus(PaymentStatus.EXPIRED.getValue());
        }
    }
}