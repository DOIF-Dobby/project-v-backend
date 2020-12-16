package org.doif.projectv.common.security;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class EncryptorTest {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    BytesEncryptor bytesEncryptor;

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
}
