package org.doif.projectv.common.security.encryptor;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.encrypt.AesBytesEncryptor;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;

import java.util.Base64;

public class CustomAesBytesEncryptor implements BytesEncryptor {

    private final BytesEncryptor bytesEncryptor;

    public CustomAesBytesEncryptor(String key, String salt) {
        BytesKeyGenerator generator = KeyGenerators.secureRandom(16);
        this.bytesEncryptor = new AesBytesEncryptor(key, salt, generator, AesBytesEncryptor.CipherAlgorithm.CBC);
    }

    @Override
    public byte[] encrypt(byte[] byteArray) {
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encrypt = bytesEncryptor.encrypt(byteArray);

        return encoder.encode(encrypt);
    }

    @Override
    public byte[] decrypt(byte[] encryptedByteArray) {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decodedByte = decoder.decode(encryptedByteArray);

        return bytesEncryptor.decrypt(decodedByte);
    }
}
