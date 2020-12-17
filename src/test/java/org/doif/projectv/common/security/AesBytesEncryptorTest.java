package org.doif.projectv.common.security;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.encrypt.AesBytesEncryptor;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Base64;

public class AesBytesEncryptorTest {

    @Test
    public void 테스트() throws Exception {
        // given
        String password = "key";
        String salt = "11";
        String plainText = "김명진 김명진 김명진 김명진 김명진 김명진 김명진 김명진 김명진";

        Base64.Encoder encoder = Base64.getEncoder();
        Base64.Decoder decoder = Base64.getDecoder();

        BytesKeyGenerator generator = KeyGenerators.secureRandom(16);
        AesBytesEncryptor aesBytesEncryptor = new AesBytesEncryptor(password, salt, generator, AesBytesEncryptor.CipherAlgorithm.CBC);

        // when
        byte[] encrypt = aesBytesEncryptor.encrypt(plainText.getBytes());
        String encodedEncrypt = encoder.encodeToString(encrypt);

        byte[] decodedEncrypt = decoder.decode(encodedEncrypt.getBytes());
        byte[] decrypt = aesBytesEncryptor.decrypt(decodedEncrypt);

        // then
        Assertions.assertThat(new String(decrypt)).isEqualTo(plainText);
    }

    @Test
    public void 테스트2() throws Exception {
        // given
//        GroovyTest.main(new String[]{});

        // when
//        File file = new File("C:\\Users\\MJ\\dev\\my-project\\java-project\\project-v", "build.gradle");
//        byte[] bytes = FileCopyUtils.copyToByteArray(file);
//
//        String s = new String(bytes);
//
//        String replace = s.replace("version = '0.0.1-SNAPSHOT'", "version = '0.0.2'");
//
//        FileCopyUtils.copy(replace.getBytes(), file);


        // then
    }

}
