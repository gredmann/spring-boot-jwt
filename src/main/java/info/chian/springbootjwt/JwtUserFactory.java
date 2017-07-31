package info.chian.springbootjwt;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {
    private JwtUserFactory() {
    }
    public static JwtUser create(User user){
        return new JwtUser(user.getUsername(),user.getPassword(),collectToGrantedAuthorites(user.getRols()));

    }

    private static List<GrantedAuthority> collectToGrantedAuthorites(List<String> authorties){
        return authorties.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

    }
}
