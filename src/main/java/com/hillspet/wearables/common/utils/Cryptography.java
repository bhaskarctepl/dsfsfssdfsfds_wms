package com.hillspet.wearables.common.utils;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Cryptography {

	private static final Logger LOGGER = LogManager.getLogger(Cryptography.class);

	public static final String DEFAULT_ENCRYPTION_KEY = "qwe123!@#";
	private static final String PET_PARENT_ENCRYPTION_KEY = "8com.itlogica.petparent8";
	private static final String LOGIN_KEY = "AGLOGICAAGLOGICAAGLOGICAAGLOGICA";
	private static final String LOGIN_IV = "2019010720190107";

	private static final String CRYPTO_TRANSFORMATION = "DESede/ECB/PKCS5Padding";
	private static final String ALGO = "DESede";
	private static final String UTF8_CHAR_SET = "UTF-8";
	private static final String ASCII_CHAR_SET = "ASCII";

	public static String genPasswordSalt() {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[8];
		random.nextBytes(salt);
		return salt.toString();
	}

	public static String encrypt(String plainText) {
		return encrypt(plainText, DEFAULT_ENCRYPTION_KEY);
	}

	public static String encrypt(String plainText, String encryptionKey) {
		String encryptedText = "";
		if (plainText == null) {
			return encryptedText;
		}
		try {
			if (encryptionKey == null || encryptionKey.trim().length() == 0) {
				encryptionKey = DEFAULT_ENCRYPTION_KEY;
			}

			byte[] inputBytes = plainText.getBytes(ASCII_CHAR_SET);
			byte[] passwordBytes = encryptionKey.getBytes(ASCII_CHAR_SET);

			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] passwordHash = md.digest(passwordBytes);
			byte[] keyBytes = Arrays.copyOf(passwordHash, 24);

			for (int j = 0, k = 16; j < 8;) {
				keyBytes[k++] = keyBytes[j++];
			}

			SecretKey key = new SecretKeySpec(keyBytes, ALGO);

			Cipher cipher = Cipher.getInstance(CRYPTO_TRANSFORMATION);
			cipher.init(Cipher.ENCRYPT_MODE, key);

			byte[] plainTextBytes = inputBytes;
			byte[] cipherBuf = cipher.doFinal(plainTextBytes);
			byte[] base64Bytes = Base64.encodeBase64(cipherBuf);

			encryptedText = new String(base64Bytes);
			LOGGER.info("encryptedText after encryption is {}", encryptedText);
		} catch (Exception e) {
			LOGGER.error("error while encrypt ", e);
		}
		return encryptedText;
	}

	public static String decrypt(String encryptedString) {
		return decrypt(encryptedString, DEFAULT_ENCRYPTION_KEY);
	}

	public static String decrypt(String encryptedString, String encryptionKey) {
		String decpryt = "";
		if (encryptedString == null) {
			return decpryt;
		}

		try {
			byte[] inputBytes = Base64.decodeBase64(encryptedString);
			byte[] passwordBytes = encryptionKey.getBytes(ASCII_CHAR_SET);

			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] passwordHash = md.digest(passwordBytes);
			byte[] keyBytes = Arrays.copyOf(passwordHash, 24);

			for (int j = 0, k = 16; j < 8;) {
				keyBytes[k++] = keyBytes[j++];
			}

			SecretKey key = new SecretKeySpec(keyBytes, ALGO);

			Cipher decipher = Cipher.getInstance(CRYPTO_TRANSFORMATION);
			decipher.init(Cipher.DECRYPT_MODE, key);

			byte[] plainText = decipher.doFinal(inputBytes);
			decpryt = new String(plainText, UTF8_CHAR_SET);
			LOGGER.info("plaing String after decryption is {}", decpryt);

		} catch (Exception e) {
			LOGGER.error("error while decrypt ", e);
		}
		return decpryt;
	}

	public static String petParentEncrypt(String plainText) {
		return encrypt(plainText, PET_PARENT_ENCRYPTION_KEY);
	}

	public static String petParentEncrypt(String plainText, String encryptionKey) {
		String encryptedText = "";
		if (plainText == null) {
			return encryptedText;
		}
		try {
			if (encryptionKey == null || encryptionKey.trim().length() == 0) {
				encryptionKey = PET_PARENT_ENCRYPTION_KEY;
			}

			Cipher cipher = Cipher.getInstance(CRYPTO_TRANSFORMATION);
			cipher.init(Cipher.ENCRYPT_MODE, getSecreteKey(encryptionKey, UTF8_CHAR_SET));

			byte[] plainTextBytes = plainText.getBytes(UTF8_CHAR_SET);
			byte[] cipherBuf = cipher.doFinal(plainTextBytes);
			byte[] base64Bytes = Base64.encodeBase64(cipherBuf);

			encryptedText = new String(base64Bytes);
			LOGGER.info("encryptedText after encryption is {}", encryptedText);
		} catch (Exception e) {
			LOGGER.error("error while petParentEncrypt ", e);
		}
		return encryptedText;
	}

	public static String petParentDecrypt(String encryptedString) {
		return petParentDecrypt(encryptedString, PET_PARENT_ENCRYPTION_KEY);
	}

	public static String petParentDecrypt(String encryptedString, String petParentEncryptionKey) {
		String decpryt = "";
		if (encryptedString == null) {
			return decpryt;
		}
		try {
			byte[] message = Base64.decodeBase64(encryptedString);

			Cipher decipher = Cipher.getInstance(CRYPTO_TRANSFORMATION);
			decipher.init(Cipher.DECRYPT_MODE, getSecreteKey(petParentEncryptionKey, UTF8_CHAR_SET));

			byte[] plainText = decipher.doFinal(message);
			decpryt = new String(plainText, UTF8_CHAR_SET);
			LOGGER.info("plaing String after decryption is {}", decpryt);

		} catch (Exception e) {
			LOGGER.error("error while petParentDecrypt ", e);
		}
		return decpryt;
	}

	private static SecretKey getSecreteKey(String secretKey, String charSet) throws Exception {
		byte[] digestOfPassword = secretKey.getBytes(charSet);
		byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
		SecretKey key = new SecretKeySpec(keyBytes, ALGO);
		return key;
	}

	public static String encryptByAES(String input) {
		String encryptedValue = "";
		try {
			// IvParameterSpec iv = new IvParameterSpec(loginIV.getBytes(), 0, 16);
			SecureRandom randomSecureRandom = new SecureRandom();
			byte[] bytes = LOGIN_IV.getBytes();
			randomSecureRandom.nextBytes(bytes);
			IvParameterSpec iv = new IvParameterSpec(bytes);

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

			SecureRandom random = new SecureRandom();
			random.setSeed(LOGIN_KEY.getBytes());

			KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
			keyGenerator.init(random);
			SecretKey key = keyGenerator.generateKey();

			cipher.init(Cipher.ENCRYPT_MODE, key, iv);
			byte[] cipherText = cipher.doFinal(input.getBytes());
			encryptedValue = Base64.encodeBase64String(cipherText).toString();
		} catch (Exception e) {
			LOGGER.error("error while encryptByAES ", e);
		}
		return encryptedValue;
	}
	
	public static void main(String[] args) {
		System.out.println("==="+Cryptography.decrypt("tXjcc99n4gWLrAodB9azxA==", null));
		System.out.println("==="+Cryptography.petParentDecrypt("2u0ISnjxPr1r7jzoRK+1wA=="));
		//System.out.println("-----1------"+Cryptography.petParentDecrypt("Sprp1Cbx014V+IoLlTmMpg=="));
		//System.out.println("----2----"+Cryptography.encrypt(Cryptography.petParentDecrypt("Sprp1Cbx014V+IoLlTmMpg=="), "1793162651635247"));
	}
	
}
