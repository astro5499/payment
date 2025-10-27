package natcash.business.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import natcash.business.dto.request.PaymentRequestDTO;
import natcash.business.dto.response.PaymentResponseDTO;
import natcash.business.entity.FinAccount;
import natcash.business.entity.Partner;
import natcash.business.entity.Payment;
import natcash.business.repository.PartnerRepository;
import natcash.business.service.FinAccountService;
import natcash.business.service.PartnerService;
import natcash.business.service.PaymentService;
import natcash.business.service.TransactionLogService;
import natcash.business.utils.ErrorCode;
import natcash.business.utils.PaymentUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PartnerServiceImpl implements PartnerService {

    @Value("${payment.secret_key}")
    private String privateKey;

    @Value("${payment.expired_time}")
    private Integer expiredAt;

    @Value("${payment.url}")
    private String url;


    private final PartnerRepository repository;

    private final TransactionLogService transactionLogService;

    private final FinAccountService finAccountService;

    private final PaymentService paymentService;

    @Override
    public Partner findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public PaymentResponseDTO initPayment(PaymentRequestDTO requestDTO, String clientIp) throws Exception {
        String generatedSignature = PaymentUtils.getSignature(requestDTO, privateKey);

        if (!generatedSignature.equals(requestDTO.getSignature())) {
            return handleError(requestDTO, ErrorCode.ERR_PARAMETERS_INVALID);
        }

        Partner partner = this.findByUsername(requestDTO.getUsername());
        if (Objects.isNull(partner)) {
            return handleError(requestDTO, ErrorCode.ERR_ACCOUNT_EXISTS);
        }

        List<String> partnerIps = Arrays.asList(partner.getIp().split(";"));
        if (!partnerIps.contains(clientIp)) {
            return handleError(requestDTO, ErrorCode.ERR_PARTNER_IP_NOT_VALID);
        }

        if (transactionLogService.isExistsByRequestIdOrOrderId(requestDTO.getRequestId(), requestDTO.getOrderNumber())) {
            return handleError(requestDTO, ErrorCode.ERR_PARTNER_REQUEST_ORDER_ALREADY_EXIST);
        }

        FinAccount availableFinAccount = finAccountService.findAvailableFinAccount(requestDTO.getAmount());
        if (Objects.isNull(availableFinAccount)) {
            return handleError(requestDTO, ErrorCode.ERR_SYSTEM_BUSY);
        }

        Payment payment = paymentService.createPayment(requestDTO, availableFinAccount.getAccountId());
        PaymentResponseDTO responseDTO = buildPaymentResponse(String.valueOf(ErrorCode.SUCCESS.status()), ErrorCode.SUCCESS.code(), ErrorCode.SUCCESS.message(), PaymentUtils.getUrl(payment, url), expiredAt);
        transactionLogService.saveTransactionLog(requestDTO, responseDTO, payment.getId());

        return responseDTO;
    }

    private static PaymentResponseDTO buildPaymentResponse(String status, String code, String message, String url, Integer expiredAt) {
        PaymentResponseDTO responseDTO = new PaymentResponseDTO();
        responseDTO.setStatus(status);
        responseDTO.setCode(code);
        responseDTO.setMessage(message);
        responseDTO.setUrl(url);
        responseDTO.setExpiredAt(expiredAt);

        return responseDTO;
    }

    private PaymentResponseDTO handleError(PaymentRequestDTO requestDTO, ErrorCode errorCode) throws JsonProcessingException {
        PaymentResponseDTO responseDTO = buildPaymentResponse(String.valueOf(errorCode.status()), errorCode.code(), errorCode.message(), null, null);
        transactionLogService.saveTransactionLog(requestDTO, responseDTO, null);
        return responseDTO;
    }
}
