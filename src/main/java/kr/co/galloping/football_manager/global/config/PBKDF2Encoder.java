package kr.co.galloping.football_manager.global.config;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PBKDF2Encoder implements PasswordEncoder {

	@Value("${security.password.encoder.secret}")
	private String secret;

	@Value("${security.password.encoder.iteration}")
	private Integer iteration;

	@Value("${security.password.encoder.key-length}")
	private Integer keyLength;

	private static final int SALT_LENGTH = 16; // Salt 길이를 설정

	// 랜덤 Salt 생성
	private byte[] generateSalt() {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[SALT_LENGTH];
		random.nextBytes(salt);
		return salt;
	}

	// 비밀번호 인코딩 (salt 포함)
	@Override
	public String encode(CharSequence rawPassword) {
		try {
			byte[] salt = generateSalt();  // 랜덤 salt 생성
			byte[] hash = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512")
				.generateSecret(new PBEKeySpec(rawPassword.toString().toCharArray(), salt, iteration, keyLength))
				.getEncoded();
			String encodedSalt = Base64.getEncoder().encodeToString(salt);  // salt를 Base64로 인코딩
			String encodedHash = Base64.getEncoder().encodeToString(hash);  // 해시된 비밀번호를 Base64로 인코딩
			return encodedSalt + ":" + encodedHash;  // Salt와 해시를 함께 반환
		} catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
			throw new RuntimeException(ex);
		}
	}

	// 비밀번호 일치 확인 (salt 포함)
	@Override
	public boolean matches(CharSequence rawPassword, String storedPassword) {
		try {
			String[] parts = storedPassword.split(":");  // 저장된 비밀번호에서 salt와 해시를 분리
			byte[] salt = Base64.getDecoder().decode(parts[0]);
			byte[] storedHash = Base64.getDecoder().decode(parts[1]);

			// 입력된 비밀번호를 저장된 salt로 다시 해싱
			byte[] inputHash = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512")
				.generateSecret(new PBEKeySpec(rawPassword.toString().toCharArray(), salt, iteration, keyLength))
				.getEncoded();

			return java.util.Arrays.equals(inputHash, storedHash);  // 해시값이 일치하는지 비교
		} catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
			throw new RuntimeException(ex);
		}
	}
}

