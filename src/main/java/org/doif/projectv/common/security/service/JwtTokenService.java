package org.doif.projectv.common.security.service;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doif.projectv.common.role.entity.Role;
import org.doif.projectv.common.user.entity.User;
import org.doif.projectv.common.user.entity.UserRole;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <pre>
 *     Jwt 토큰을 생성하고, 유효한 토큰인지 판별하는 클래스이다.
 * </pre>
 * @date : 2020-10-21
 * @author : 김명진
 * @version : 1.0.0
**/
@RequiredArgsConstructor
@PropertySource("classpath:encrypt.properties")
@Slf4j
public class JwtTokenService {

    @Value("${jwt.key}")
    private String key;

    // Request header 토큰 key 명
    private static final String AUTH_KEY = "Authorization";

    // 토큰 유효시간 30분
    private static final long tokenValidTime = 30 * 60 * 1000L;

    public String generateJwtToken(User user) {
        Date now = new Date();

        String compact = Jwts.builder()
                .setHeader(createHeader())
                .setClaims(createClaims(user))                              // 정보 저장
                .setIssuedAt(now)                                           // 발행일
                .setExpiration(new Date(now.getTime() + tokenValidTime))    // 만료일
                .signWith(SignatureAlgorithm.HS256, createSigningKey())
                .compact();
        return "Bearer " + compact;
    }

    public String getAuthKey() {
        return AUTH_KEY;
    }

    private Map<String, Object> createClaims(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());

        return claims;
    }

    private Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();

        header.put("typ", "JWT");
        header.put("alg", "HS256");

        return header;
    }

    private Key createSigningKey() {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(key);
        return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean isValidToken(String jwtToken) {
        if(StringUtils.isEmpty(jwtToken)) {
            log.error("[:: JwtTokenService > isValidToken Token invalid Reason: Token empty ::]");
            return false;
        }

        String token = getTokenFromHeader(jwtToken);

        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(key)).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException exception) {
            log.error("[:: JwtTokenService > isValidToken Token invalid Reason: Token expired ::]");
            return false;
        } catch (JwtException exception) {
            log.error("[:: JwtTokenService > isValidToken Token invalid Reason: Token tampered ::]");
            return false;
        } catch (NullPointerException exception) {
            log.error("[:: JwtTokenService > isValidToken Token invalid Reason: Token is null ::]");
            return false;
        }
    }

    private String getTokenFromHeader(String jwtToken) {
        return jwtToken.substring("Bearer ".length());
    }

    public String getId(String jwtToken) {
        String token = getTokenFromHeader(jwtToken);
        Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(key))
                .parseClaimsJws(token).getBody();

        return claims.get("id", String.class);

    }

}
