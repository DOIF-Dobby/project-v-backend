package org.doif.projectv.common.security.provider;

import lombok.extern.slf4j.Slf4j;
import org.doif.projectv.common.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;


/**
 * <pre>
 *     실제 인증이 일어나는 클래스이다.
 *     AuthenticationManager(ProviderManager)는 실제로 많은 AuthenticationProvider 구현체들을 가질 수 있다
 *     ProviderManager는 AuthenticationProvider를 가지고 인증 로직을 태우게된다.
 *     AuthenticationProvider를 구현한 CustomAuthenticationProvider를 사용한다.
 *
 *     해당 클래스는 입력받은 사용자 아이디를 UserDetailsService에게 넘겨 DB에서 사용자 인증 정보를 받고
 *     암호화된 패스워드를 비교하기 위하여 PasswordEncoder에게 사용자가 입력한 평문 패스워드를 전달해 암호화된 형태로 받아서
 *     암호화<->암호화 형태로 비밀번호를 비교한다(평문<->평문 아님).
 *     만약 인증이 완료되면 Authentication객체를 구현한 UsernamePasswordAuthenticationToken객체를 반환한다.
 * </pre>
 * @date : 2020-10-20
 * @author : 김명진
 * @version : 1.0.0
**/
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("[:: CustomAuthenticationProvider > authenticate ::]");

        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        String userId = token.getName();
        String password = (String) token.getCredentials();

        UserDetails userDetails  = userDetailsService.loadUserByUsername(userId);

        if(StringUtils.isEmpty(userId)) {
            throw new AuthenticationServiceException("Empty userId");
        }
        if(StringUtils.isEmpty(password)) {
            throw new AuthenticationServiceException("Empty password");
        }

        // 패스워드 불일치
        if(!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException(userId + ": Invalid password");
        }

        // TODO: 만료, 잠금등 체크를 해야한다.

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        log.info("[:: CustomAuthenticationProvider > supports ::]");

        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }
}
