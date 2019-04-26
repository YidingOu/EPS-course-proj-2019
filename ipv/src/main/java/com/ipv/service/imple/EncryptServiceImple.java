package com.ipv.service.imple;

import javax.annotation.PostConstruct;

import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ipv.service.EncryptionService;

/**
 * 
 * 
 */
@Service
public class EncryptServiceImple implements EncryptionService{

	@Value("${spring.encryption.key}")
	private String key;

	BasicTextEncryptor textEncryptor;
	
	//After the injection is done, override the repository in the super class
	@PostConstruct
	public void init() {
		textEncryptor = new BasicTextEncryptor();
		textEncryptor.setPasswordCharArray(key.toCharArray());

	}

	@Override
	public String encrypt(String text) {
		return textEncryptor.encrypt(text);
	}

	@Override
	public String decrypt(String text) {
		return textEncryptor.decrypt(text);
	}

	@Override
	public String encrypt(BasicTextEncryptor encryptor, String text) {
		return encryptor.encrypt(text);
	}

	@Override
	public String decrypt(BasicTextEncryptor encryptor, String text) {
		return encryptor.decrypt(text);
	}

	@Override
	public BasicTextEncryptor createAnEncryptor(String key) {
		BasicTextEncryptor newEncryptor = new BasicTextEncryptor();
		newEncryptor.setPassword(key);
		return newEncryptor;
	}



}
