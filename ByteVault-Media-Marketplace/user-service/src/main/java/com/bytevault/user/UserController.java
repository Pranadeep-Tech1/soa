package com.bytevault.user;
import org.springframework.security.core.annotation.AuthenticationPrincipal; import org.springframework.security.oauth2.jwt.Jwt; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/users")
public class UserController {
 @GetMapping("/me") public Object me(@AuthenticationPrincipal Jwt jwt){return java.util.Map.of("username",jwt.getSubject(),"userId",jwt.getClaim("userId"),"roles",jwt.getClaim("roles"));}
}
