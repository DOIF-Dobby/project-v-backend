package org.doif.projectv.common.security.config;

import org.doif.projectv.common.security.encryptor.CustomAesBytesEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@PropertySource("classpath:encrypt.properties")
public class CryptConfig {

    @Value("${encrypt.key}")
    private String key;

    @Value("${encrpyt.salt}")
    private String salt;

    /**
     * 단방향 암호화 해쉬 알고리즘으로 BCrypt를 사용
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 양뱡향 암호화 알고리즘으로 AES-256 사용
     * encrypt, decrypt 시에 Base64로 인코딩, 디코딩 하기위해 커스텀함
     * @return
     */
    @Bean
    public BytesEncryptor bytesEncryptor() {
        return new CustomAesBytesEncryptor(key, salt);
    }

}
