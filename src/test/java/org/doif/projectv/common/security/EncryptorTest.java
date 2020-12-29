package org.doif.projectv.common.security;

import org.assertj.core.api.Assertions;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class EncryptorTest {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    BytesEncryptor bytesEncryptor;

    @Value("${encrypt.key}")
    private String key;

    @Value("${encrypt.algorithm}")
    private String algorithm;

    @Test
    @DisplayName("단 방향 해시 테스트")
    public void test1() throws Exception {
        // given
        String plainText = "김명진";

        // when
        String encode = passwordEncoder.encode(plainText);

        // then
        passwordEncoder.matches(plainText, encode);
    }

    @Test
    @DisplayName("양 방향 암호화, 복호화 테스트")
    public void test2() throws Exception {
        // given
        String plainText = "김명진씨";

        // when
        byte[] encrypt = bytesEncryptor.encrypt(plainText.getBytes());
        byte[] decrypt = bytesEncryptor.decrypt(encrypt);

        // then
        Assertions.assertThat(new String(decrypt)).isEqualTo(plainText);

    }

    @Test
    public void 테스트() throws Exception {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(key);
        config.setAlgorithm(algorithm);
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);

        String id = encryptor.encrypt("");
        String pw = encryptor.encrypt("");
        System.out.println("id = " + id);
        System.out.println("pw = " + pw);

    }
}
