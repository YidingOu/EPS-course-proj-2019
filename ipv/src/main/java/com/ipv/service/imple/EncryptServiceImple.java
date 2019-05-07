package com.ipv.service.imple;

import javax.annotation.PostConstruct;

import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ipv.service.EncryptionService;

/**
 *The function body for the encrytion service
 */
@Service
public class EncryptServiceImple implements EncryptionService {

    @Value("${spring.encryption.key}")
    private String key;

    BasicTextEncryptor textEncryptor;

    //After the injection is done, override the repository in the super class
    @PostConstruct
    public void init() {
        textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPasswordCharArray(key.toCharArray());

    }

    // Encrypt the input text
    @Override
    public String encrypt(String text) {
        return textEncryptor.encrypt(text);
    }

    // Decrypt the input text
    @Override
    public String decrypt(String text) {
        return textEncryptor.decrypt(text);
    }

    // Encrypt the input text with input encryptor
    @Override
    public String encrypt(BasicTextEncryptor encryptor, String text) {
    	
        return encryptor.encrypt(text);
    }

    // Decrypt the input text with input encryptor
    @Override
    public String decrypt(BasicTextEncryptor encryptor, String text) {
    	String t = encryptor.decrypt(text);
        return t;
    }

    // Create an encryptor with input key
    @Override
    public BasicTextEncryptor createAnEncryptor(String key) {
        BasicTextEncryptor newEncryptor = new BasicTextEncryptor();
        newEncryptor.setPasswordCharArray(key.toCharArray());
        return newEncryptor;
    }


}
