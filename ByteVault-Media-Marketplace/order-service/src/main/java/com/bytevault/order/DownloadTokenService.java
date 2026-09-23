package com.bytevault.order;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Base64;

@Service
public class DownloadTokenService {
    private final byte[] secret;
    public DownloadTokenService(@Value("${security.download.secret}") String secret) {
        this.secret = secret.getBytes(StandardCharsets.UTF_8);
    }
    public String create(Long orderId, Long productId, Instant expiry) {
        try {
            String payload = orderId + ":" + productId + ":" + expiry.getEpochSecond();
            return encode(payload) + "." + signature(payload);
        } catch (Exception e) { throw new IllegalStateException(e); }
    }
    public boolean verify(String token, Long orderId, Long productId) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 2) return false;
            String payload = new String(Base64.getUrlDecoder().decode(parts[0]), StandardCharsets.UTF_8);
            String[] values = payload.split(":");
            if (values.length != 3) return false;
            if (!values[0].equals(orderId.toString()) || !values[1].equals(productId.toString())) return false;
            if (Long.parseLong(values[2]) < Instant.now().getEpochSecond()) return false;
            return MessageDigest.isEqual(signature(payload).getBytes(StandardCharsets.UTF_8), parts[1].getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) { return false; }
    }
    private String encode(String value) { return Base64.getUrlEncoder().withoutPadding().encodeToString(value.getBytes(StandardCharsets.UTF_8)); }
    private String signature(String payload) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secret, "HmacSHA256"));
        return Base64.getUrlEncoder().withoutPadding().encodeToString(mac.doFinal(payload.getBytes(StandardCharsets.UTF_8)));
    }
}
