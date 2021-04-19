package org.doif.projectv.common.security.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;

/**
 * <pre>
 *     Form Login시 걸리는 Filter이다.
 *     UsernamePasswordAuthenticationFilter를 상속한 CustomUsernamePasswordAuthenticationFilter를 등록하였다.
 *     이 필터는 HttpServletRequest에서 사용자가 Form으로 입력한 로그인 정보를 인터셉트해서 AuthenticationManager에게 Authentication 객체를 넘겨준다.
 * </pre>
 * @date : 2020-10-19
 * @author : 김명진
 * @version : 1.0.0
**/
@Slf4j
public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final boolean postOnly = true;

    public CustomUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    /**
     * 인증 프로세스 이전에,
     * Request 객체에서 사용자 정보를 가져와서
     * Authentication 객체를 인증 프로세스 객체에게 전달하는 역할
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("[:: CustomUsernamePasswordAuthenticationFilter > attemptAuthentication ::]");

        // 요청이 POST로 넘어왔는지 체크
        if(postOnly && !request.getMethod().equals(HttpMethod.POST.name())) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        // Authorization 헤더에 담긴 username:password로 인증
        UsernamePasswordAuthenticationToken authRequest;
        String authorization = request.getHeader("Authorization");

        if(authorization == null) {
            throw new AuthenticationServiceException("Invalid Request");
        }

        authorization = authorization.substring("Basic ".length());

        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decode = decoder.decode(authorization);
        String usernameAndPassword = new String(decode);

        String[] strings = StringUtils.delimitedListToStringArray(usernameAndPassword, ":");
        String username = strings[0];
        String password = strings[1];

        authRequest = new UsernamePasswordAuthenticationToken(username, password);

        // jackson ObjectMapper로 request body로 넘어온 데이터를 UserDto 객체로 변환
//        try{
//            UserDto.Auth userDto = new ObjectMapper().readValue(request.getInputStream(), UserDto.Auth.class);
//            authRequest = new UsernamePasswordAuthenticationToken(userDto.getId(), userDto.getPassword());
//        } catch (IOException exception){
//            throw new AuthenticationServiceException("Invalid Request");
//        }

        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
