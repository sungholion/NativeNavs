//package com.nativenavs.config;
//
//
//
//import org.jasypt.encryption.StringEncryptor;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
//
//@SpringBootTest(classes = JasyptConfig.class)
//public class JasyptDecryptionTest {
//
//    @Autowired
//    private StringEncryptor stringEncryptor;
//
//    @Test
//    public void testDecryption() {
//        String encryptedSecretKey = "ENC(NKtINeQJYRu8mUVC4JGRlMaU+VNhBhUfFruvMeRLCFqniy7euTBpiMyioaW660udQSuQSQGOMtU=)";
//        String decryptedSecretKey = stringEncryptor.decrypt(encryptedSecretKey);
//        assertNotNull(decryptedSecretKey);
//        System.out.println("Decrypted Secret Key: " + decryptedSecretKey);
//    }
//}