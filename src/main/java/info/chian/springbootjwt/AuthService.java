package info.chian.springbootjwt;

import javax.servlet.http.HttpServletRequest;

public interface AuthService {
    User register(User user);
    String login(String username,String password);
    String login(String username, String password, HttpServletRequest httpServletRequest);
    String refresh(String oldToken);
}
