package info.chian.springbootjwt;

import com.sun.org.apache.xpath.internal.operations.Bool;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil {
    private static final String CLAIM_KEY_NAME="sub";
    private static final String CLAIM_KEY_CREATED="created";
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;

    public String getUsernameFromToken(String token){
        String username;
        final Claims claims=getClaimsFromToken(token);
        username=claims.getSubject();
        return username;
    }

    public String generateToken(UserDetails userDetails){
        Map<String,Object> claims=new HashMap<>();
        claims.put(CLAIM_KEY_NAME,new Date());
        claims.put(CLAIM_KEY_CREATED,userDetails.getUsername());
        return generateToken(claims);

    }

    public Boolean canTokenbeRefreshed(String token){
        return true;
    }

    private String generateToken(Map<String, Object> claims) {
       return Jwts.builder().setClaims(claims).setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512,secret).compact();
    }


    public Date getCreatedDateFromToken(String token){

        return new Date();

    }

    private Claims getClaimsFromToken(String token){
        Claims claims;
        claims= Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return claims;
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        JwtUser user= (JwtUser) userDetails;
        final String username=getUsernameFromToken(token);
//        final Date created=getCreatedDateFromToken(token);
       return username.equals(user.getUsername())&&!isTokenExpired(token);

    }

    private Boolean isTokenExpired(String token){
        final Date expiration=getExpirationDateFromToken(token);
        return expiration.before(new Date());

    }

    private Date getExpirationDateFromToken(String token) {
        Date expiration;
        final Claims claims=getClaimsFromToken(token);
        expiration=claims.getExpiration();
        return expiration;
    }

    public String refreshToken(String token){
        String refreshToken;
        final Claims claims=getClaimsFromToken(token);
        claims.put(CLAIM_KEY_CREATED,new Date());
        refreshToken=geerateToken(claims);
        return refreshToken;

    }

    private String geerateToken(Claims claims) {
        return Jwts.builder().setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512,secret).compact();
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis()+expiration*1000);
    }
}
