package com.bytevault.order;

import static org.junit.jupiter.api.Assertions.*;
import java.time.Instant;
import org.junit.jupiter.api.Test;

class DownloadTokenTest {
    @Test void validTokenIsAccepted() {
        DownloadTokenService s = new DownloadTokenService("test-secret-123");
        String token = s.create(10L, 20L, Instant.now().plusSeconds(60));
        assertTrue(s.verify(token, 10L, 20L));
    }
    @Test void tamperedTokenIsRejected() {
        DownloadTokenService s = new DownloadTokenService("test-secret-123");
        String token = s.create(10L, 20L, Instant.now().plusSeconds(60));
        String tampered = token.substring(0, token.length()-1) + "x";
        assertFalse(s.verify(tampered, 10L, 20L));
    }
    @Test void expiredTokenIsRejected() {
        DownloadTokenService s = new DownloadTokenService("test-secret-123");
        String token = s.create(10L, 20L, Instant.now().minusSeconds(1));
        assertFalse(s.verify(token, 10L, 20L));
    }
}
