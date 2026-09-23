package com.bytevault.user;
import jakarta.validation.Valid; import jakarta.validation.constraints.NotBlank; import org.springframework.http.HttpStatus; import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/auth")
public class AuthController {
 private final UserRepository repo; private final JwtService jwt; private final BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
 public AuthController(UserRepository repo,JwtService jwt){this.repo=repo;this.jwt=jwt;}
 record Credentials(@NotBlank String username,@NotBlank String password){}
 record AuthResponse(String token,String username,String role){}
 @PostMapping("/register") @ResponseStatus(HttpStatus.CREATED)
 public AuthResponse register(@Valid @RequestBody Credentials c){ if(repo.findByUsername(c.username()).isPresent()) throw new IllegalArgumentException("Username already exists"); User u=new User();u.setUsername(c.username());u.setPasswordHash(encoder.encode(c.password()));u.setRole("USER");repo.save(u);return new AuthResponse(jwt.generate(u),u.getUsername(),u.getRole()); }
 @PostMapping("/login")
 public AuthResponse login(@Valid @RequestBody Credentials c){ User u=repo.findByUsername(c.username()).orElseThrow(()->new IllegalArgumentException("Invalid username or password")); if(!encoder.matches(c.password(),u.getPasswordHash())) throw new IllegalArgumentException("Invalid username or password"); return new AuthResponse(jwt.generate(u),u.getUsername(),u.getRole()); }
}
