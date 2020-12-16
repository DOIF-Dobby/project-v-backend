package org.doif.projectv.common.user.service;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.response.CommonResponse;
import org.doif.projectv.common.response.ResponseUtil;
import org.doif.projectv.common.user.dto.UserDto;
import org.doif.projectv.common.user.entity.User;
import org.doif.projectv.common.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final BytesEncryptor bytesEncryptor;

    @Override
    public Page<UserDto.Result> selectByCondition(UserDto.Search search, Pageable pageable) {
        return userRepository.selectByCondition(search, pageable)
                .map(user -> {
                    String decryptSvnId = new String( bytesEncryptor.decrypt(user.getSvnId().getBytes()) );
                    String decryptSvnPassword = new String( bytesEncryptor.decrypt(user.getSvnPassword().getBytes()) );

                    return new UserDto.Result(user.getId(), user.getName(), user.getStatus(), decryptSvnId, decryptSvnPassword);
                });
    }

    @Override
    public CommonResponse insert(UserDto.Insert dto) {
        Optional<User> optionalUser = userRepository.findById(dto.getId());

        if(optionalUser.isPresent()) {
            throw new IllegalArgumentException("이미 사용중인 ID 입니다.");
        }

        String encryptPassword = passwordEncoder.encode(dto.getPassword());
        String encryptSvnId = new String(bytesEncryptor.encrypt(dto.getSvnId().getBytes()));
        String encryptSvnPassword = new String(bytesEncryptor.encrypt(dto.getSvnPassword().getBytes()));

        User user = new User(dto.getId(), encryptPassword, dto.getName(), dto.getStatus(), encryptSvnId, encryptSvnPassword);
        userRepository.save(user);

        return ResponseUtil.ok();
    }

    @Override
    public CommonResponse update(String id, UserDto.Update dto) {
        Optional<User> optionalUser = userRepository.findById(id);
        User user = optionalUser.orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없음"));

        String encryptSvnId = new String(bytesEncryptor.encrypt(dto.getSvnId().getBytes()));
        String encryptSvnPassword = new String(bytesEncryptor.encrypt(dto.getSvnPassword().getBytes()));

        user.changeUser(dto.getName(), dto.getStatus(), encryptSvnId, encryptSvnPassword);

        return ResponseUtil.ok();
    }

    @Override
    public CommonResponse delete(String id) {
        Optional<User> optionalUser = userRepository.findById(id);
        User user = optionalUser.orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없음"));
        userRepository.delete(user);

        return ResponseUtil.ok();
    }
}
