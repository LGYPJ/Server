package com.garamgaebi.GaramgaebiServer;

import com.garamgaebi.GaramgaebiServer.domain.member.entity.Member;
import com.garamgaebi.GaramgaebiServer.domain.member.entity.MemberFcm;
import com.garamgaebi.GaramgaebiServer.domain.member.repository.MemberRepository;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class GaramgaebiServerApplicationTests {

    @Qualifier("jasyptStringEncryptor")
    @Autowired
    StringEncryptor jasyptEncryptor;

    @Test
    public void test() {
        PooledPBEStringEncryptor encryptor = (PooledPBEStringEncryptor) jasyptEncryptor;

        String plainText = "jdbc:log4jdbc:mysql://garamgaebi-dev-db.cdnkkneucvsy.ap-northeast-2.rds.amazonaws.com:3306/Garamgaebi";

        System.out.println(encryptor.encrypt(plainText));
        System.out.println(encryptor.decrypt(encryptor.encrypt(plainText)));
    }
}
