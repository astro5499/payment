package natcash.business.utils;

import natcash.business.dto.request.PaymentRequestDTO;
import natcash.business.entity.Payment;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class PaymentUtils {

    public static String getSignature(PaymentRequestDTO requestDTO, String key) throws Exception {
        String concatenationString = requestDTO.getUsername() + requestDTO.getPassword() + requestDTO.getPartnerCode() + requestDTO.getAmount() + requestDTO.getOrderNumber() + requestDTO.getRequestId();
        String concatenationBase64 = Base64.getEncoder().encodeToString(concatenationString.getBytes(StandardCharsets.UTF_8));

        return calculateHMac(key, concatenationBase64);
    }

    public static String getUrl(Payment payment, String baseUrl) {
        try{
        String url = String.format(baseUrl,
                URLEncoder.encode(payment.getToAccount(), StandardCharsets.UTF_8.toString()),
                URLEncoder.encode(payment.getAmount().toString(), StandardCharsets.UTF_8.toString()),
                URLEncoder.encode(payment.getOrderId(), StandardCharsets.UTF_8.toString())
        );
        return url;
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
}
