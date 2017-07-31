package info.chian.springbootjwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtTokenUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Override
    public User register(User user) {
        PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        return new User(1,"admin", passwordEncoder.encode("123456"),"ROLE_ADMIN");
    }

    @Override
    public String login(String username, String password) {
        System.out.println("userName>"+username+"password:>>"+password);
        UsernamePasswordAuthenticationToken authenticationToken=
                new UsernamePasswordAuthenticationToken(username,password);

        final Authentication authentication=authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final UserDetails userDetails=userDetailsService.loadUserByUsername(username);
        return jwtTokenUtil.generateToken(userDetails);
    }

    @Override
    public String login(String username, String password, HttpServletRequest httpServletRequest) {
        System.out.println("userName>"+username+"password:>>"+password);
        UsernamePasswordAuthenticationToken authenticationToken=
                new UsernamePasswordAuthenticationToken(username,password);
        authenticationToken.setDetails(new WebAuthenticationDetails(httpServletRequest));
        final Authentication authentication=authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final UserDetails userDetails=userDetailsService.loadUserByUsername(username);
        return jwtTokenUtil.generateToken(userDetails);
    }

    @Override
    public String refresh(String oldToken) {
        final String token=oldToken.substring(tokenHead.length());
        String username=jwtTokenUtil.getUsernameFromToken(token);

//        JwtUser user= (JwtUser) userDetailsService.loadUserByUsername(username);
        if(jwtTokenUtil.canTokenbeRefreshed(token)){
            return jwtTokenUtil.refreshToken(token);
        }
        return null;
    }
}
