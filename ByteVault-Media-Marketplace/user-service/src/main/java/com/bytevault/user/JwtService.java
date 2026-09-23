package com.bytevault.user;
import java.time.Instant; import java.time.temporal.ChronoUnit; import javax.crypto.SecretKey;
import org.springframework.security.oauth2.jose.jws.JwsHeader; import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*; import org.springframework.stereotype.Service;
@Service
public class JwtService {
 private final JwtEncoder encoder; public JwtService(SecretKey key){ this.encoder=new NimbusJwtEncoder(com.nimbusds.jose.jwk.source.ImmutableSecret.of(key)); }
 public String generate(User user){
  Instant now=Instant.now();
  JwtClaimsSet claims=JwtClaimsSet.builder().issuer("bytevault-user-service").subject(user.getUsername()).issuedAt(now).expiresAt(now.plus(2,ChronoUnit.HOURS)).claim("userId",user.getId()).claim("roles",java.util.List.of(user.getRole())).build();
  JwsHeader header=JwsHeader.with(MacAlgorithm.HS256).build();
  return encoder.encode(JwtEncoderParameters.from(header,claims)).getTokenValue();
 }
}
