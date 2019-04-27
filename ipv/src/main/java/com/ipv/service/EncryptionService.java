package com.ipv.service;

import org.jasypt.util.text.BasicTextEncryptor;

/**
 * Data persistence business logic layer
 * The interface-implementation architechture is required by the Spring framework (the multiple implementation is allowed)
 * There are many common interfaces between the services(like CRUD), so the common part is define in a BaseService
 * <p>
 * The service for the encryption and the decryption
 */
public interface EncryptionService {

    // Encrypt the input text
    public String encrypt(String text);

    // Decrypt the input text
    public String decrypt(String text);

    // Encrypt the input text with input encryptor
    public String encrypt(BasicTextEncryptor encryptor, String text);

    // Decrypt the input text with input encryptor
    public String decrypt(BasicTextEncryptor encryptor, String text);

    // Create an encryptor with input key
    public BasicTextEncryptor createAnEncryptor(String key);

}
