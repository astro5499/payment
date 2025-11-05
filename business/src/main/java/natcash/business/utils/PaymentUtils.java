package natcash.business.utils;

import natcash.business.dto.request.PaymentRequestDTO;
import natcash.business.dto.response.PaymentResponseDTO;
import natcash.business.dto.response.RequestResponseDTO;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

public class PaymentUtils {

    public static String getSignature(PaymentRequestDTO requestDTO, String key) throws Exception {
        String concatenationString = requestDTO.getUsername() + requestDTO.getPassword() + requestDTO.getPartnerCode() + requestDTO.getAmount() + requestDTO.getOrderNumber() + requestDTO.getRequestId();
        String concatenationBase64 = Base64.getEncoder().encodeToString(concatenationString.getBytes(StandardCharsets.UTF_8));

        return calculateHMac(key, concatenationBase64);
    }

    public static String getUrl(String baseUrl, UUID paymentId) {
        try{
            return String.format(baseUrl,URLEncoder.encode(paymentId.toString(), StandardCharsets.UTF_8.toString()));
    } catch (UnsupportedEncodingException e) {
        throw new RuntimeException("Error encoding callback URL", e);
    }
    }

    private static String calculateHMac(String key, String data) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        return bytesToHex(sha256_HMAC.doFinal(data.getBytes("UTF-8")));
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static PaymentResponseDTO buildPaymentResponse(String status, String code, String message, String url, Long expiredAt) {
        PaymentResponseDTO responseDTO = new PaymentResponseDTO();
        responseDTO.setStatus(status);
        responseDTO.setCode(code);
        responseDTO.setMessage(message);
        responseDTO.setUrl(url);
        responseDTO.setExpiredAt(expiredAt);

        return responseDTO;
    }

    public static RequestResponseDTO buildPaymentResponse(ErrorCode errorCode, String message) {
        RequestResponseDTO responseDTO = new RequestResponseDTO();
        responseDTO.setCode(errorCode.code());
        responseDTO.setStatus(String.valueOf(errorCode.status()));
        responseDTO.setMessage(message);
        return responseDTO;
    }
    public static String generateTransCode(Long nextVal) {
        String prefix = calculatePrefix(nextVal / 9999);
        return prefix + String.format("%04d", nextVal % 9999 + 1);
    }
    public static String calculatePrefix(long index) {
        StringBuilder prefix = new StringBuilder();
        index++;

        while (index > 0) {
            index--;
            char ch = (char) ('A' + (index % 26));
            prefix.insert(0, ch);
            index /= 26;
        }

        return prefix.toString();
    }

}
