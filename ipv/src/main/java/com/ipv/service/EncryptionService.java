package com.ipv.service;

import org.jasypt.util.text.BasicTextEncryptor;

/**
 * 
 * Data persistence business logic layer
 * The interface-implementation architechture is required by the Spring framework (the multiple implementation is allowed)
 * There are many common interfaces between the services(like CRUD), so the common part is define in a BaseService
 * 
 * The service interface get the common methods by extends the BaseService
 * The other interfaces will be added later
 * 
 */
public interface EncryptionService {
	public String encrypt(String text);
	public String decrypt(String text);
	public String encrypt(BasicTextEncryptor encryptor, String text);
	public String decrypt(BasicTextEncryptor encryptor, String text);
	public BasicTextEncryptor createAnEncryptor(String key);

}
