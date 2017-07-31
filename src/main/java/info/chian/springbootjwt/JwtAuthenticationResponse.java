package info.chian.springbootjwt;

import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationResponse {

    public JwtAuthenticationResponse() {
    }

    private String token;

    public String getToken() {
        return token;
    }

    public JwtAuthenticationResponse(String token) {

        this.token = token;
    }
}
