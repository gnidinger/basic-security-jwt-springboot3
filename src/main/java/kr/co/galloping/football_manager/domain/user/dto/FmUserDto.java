package kr.co.galloping.football_manager.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class FmUserDto {

	public record Register(
		@NotBlank(message = "이메일을 입력하셔야 합니다.")
		@Schema(description = "사용자 이메일 주소. 이메일 형식을 따릅니다.", example = "user@example.com")
		@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "이메일 형식이 맞지 않습니다.")
		String email,

		@NotBlank(message = "사용자 이름을 입력하셔야 합니다.")
		@Schema(description = "사용자 이름. 2자 이상 10자 이하로 구성됩니다.", example = "사용자")
		@Pattern(regexp = "^[가-힣a-zA-Z0-9!@#$%^&*()-_+=<>?\\s]{2,10}$", message = "이름은 문자, 숫자, 특수문자(!@#$%^&*()-_+=<>?)를 포함한 2자 이상 10자 이하 여야 합니다.")
		String name,

		@NotBlank(message = "비밀번호를 입력하셔야 합니다.")
		@Schema(description = "사용자 비밀번호. 8자 이상이며, 최소 하나의 문자와 하나의 숫자를 포함해야 합니다.", example = "password123")
		@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d\\W_]{8,15}$", message = "비밀번호는 숫자, 문자를 포함해 8자리 이상 15자리 이하 여야 합니다.")
		String password,

		@NotBlank(message = "확인을 위한 비밀번호를 입력하셔야 합니다.")
		@Schema(description = "비밀번호 확인을 위한 필드. 비밀번호와 동일한 값을 입력해야 합니다.", example = "password123")
		@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d\\W_]{8,15}$", message = "비밀번호는 숫자, 문자를 포함해 8자리 이상 15자리 이하 여야 합니다.")
		String passwordRepeat,

		@NotNull(message = "이메일 검증 여부를 입력하셔야 합니다.")
		@Schema(description = "이메일 인증 여부. 인증을 완료한 경우 true입니다.", example = "true")
		Boolean isVerified
	) {
	}

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class RegisterResponse {

		@Schema(description = "사용자 식별자")
		Long id;

		@Schema(description = "사용자 이름")
		String name;
	}
}
