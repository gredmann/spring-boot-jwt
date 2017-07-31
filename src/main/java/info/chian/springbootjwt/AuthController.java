package info.chian.springbootjwt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class AuthController {
    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private AuthService authService;

    @RequestMapping(value = "/auth")
//    @JsonIgnoreProperties(ignoreUnknown = true)
    @ResponseBody
    public ResponseEntity<?> createAuthenticationToken(
            JwtAuthenticationRequest jwtAuthenticationRequest
            ,HttpServletRequest httpServletRequest
    ){
            jwtAuthenticationRequest.setPassword("123456");
            jwtAuthenticationRequest.setUsername("admin");

        final String token=authService.login(jwtAuthenticationRequest.getUsername(),jwtAuthenticationRequest.getPassword(),httpServletRequest);
        System.out.println("token>>>"+token);
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));

    }

    @RequestMapping(value = "/auth/refresh",method = RequestMethod.POST)
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request){
                String token=request.getHeader(tokenHeader);
                String refreshedToken=authService.refresh(token);
                if(refreshedToken==null){
                    return ResponseEntity.badRequest().body(null);

                }else {
                    return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
                }
    }

    @RequestMapping(value = "/auth/register",method = RequestMethod.POST)
    public User register(@RequestBody User user){
        return authService.register(user);
    }

}
