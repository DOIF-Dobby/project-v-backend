package org.doif.projectv.common.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doif.projectv.common.security.vo.UserDetailsInfo;
import org.doif.projectv.common.user.entity.User;
import org.doif.projectv.common.user.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <pre>
 *     AuthenticationProvider 구현체에서 인증에 사용할 사용자 인증정보를 DB에서 가져오는 역할을 하는 클래스이다.
 *     UserDetailsService를 구현한 UserDetailsServiceImpl를 사용한다.
 * </pre>
 * @date : 2020-10-20
 * @author : 김명진
 * @version : 1.0.0
**/
@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        log.info("[:: UserDetailsServiceImpl > loadUserByUsername :::: userId: {}]", userId);

        // userId로 DB에서 유저 조회
        Optional<User> optional = userRepository.searchUserFetchRole(userId);
        User user = optional.orElseThrow(() -> new UsernameNotFoundException("Invalid UserId"));

        // user가 가지고 있는 role들 조회
        List<SimpleGrantedAuthority> authorities = user.getUserRoles().stream()
                .map(userRole -> new SimpleGrantedAuthority(String.valueOf(userRole.getId())))
                .collect(Collectors.toList());

        return new UserDetailsInfo(user, authorities);
    }
}
