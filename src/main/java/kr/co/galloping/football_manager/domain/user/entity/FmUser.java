package kr.co.galloping.football_manager.domain.user.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kr.co.galloping.football_manager.domain.user.entity.enums.Role;
import kr.co.galloping.football_manager.global.config.PBKDF2Encoder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "FM_USER")
public class FmUser {

	@Id
	@Column(name = "FM_USER_SEQ")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "EMAIL", nullable = false, unique = true)
	private String email;

	@Column(name = "NAME", nullable = false)
	private String name;

	@Column(name = "PASSWORD", nullable = false)
	private String password;

	@Column(name = "PHONE_NUMBER")
	private String phoneNumber;

	@Builder.Default
	@Enumerated(EnumType.STRING)
	@Column(name = "ROLE", nullable = false)
	private Role role = Role.USER;

	@Builder.Default
	@Column(name = "EMAIL_VERIFIED", nullable = false)
	private Boolean emailVerified = false;

	@Column(name = "PROVIDER")
	private String provider;

	@Column(name = "PROVIDER_ID")
	private String providerId;

	@Builder.Default
	@Column(name = "CREATED_AT")
	private LocalDateTime createdAt = LocalDateTime.now();

	@Column(name = "MODIFIED_AT")
	private LocalDateTime modifiedAt;

	public static FmUser create(String email, String name, String password, PBKDF2Encoder pbkdf2Encoder) {
		return FmUser.builder()
			.email(email)
			.name(name)
			.password(pbkdf2Encoder.encode(password))
			.emailVerified(true)
			.role(Role.USER)
			.build();
	}
}
