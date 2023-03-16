package com.garamgaebi.GaramgaebiServer;

import com.garamgaebi.GaramgaebiServer.global.config.JasyptConfig;
import org.assertj.core.api.Assertions;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GaramgaebiServerApplicationTests {

    @Qualifier("jasyptStringEncryptor")
    @Autowired StringEncryptor baseEncryptor;

    @Test
    public void jasypt_test() {
        String plainText = "admin";

        PooledPBEStringEncryptor encryptor = (PooledPBEStringEncryptor) baseEncryptor;

        String encryptText = encryptor.encrypt(plainText);
        Assertions.assertThat(plainText).isNotEqualTo(encryptText);
        System.out.println(encryptText);
        Assertions.assertThat(plainText).isEqualTo(encryptor.decrypt(encryptText));
        System.out.println(encryptor.decrypt(encryptText));

    }
}
