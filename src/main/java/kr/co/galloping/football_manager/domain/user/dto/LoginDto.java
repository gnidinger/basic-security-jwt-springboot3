package kr.co.galloping.football_manager.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

public class LoginDto {

	public record Request(
		@NotBlank(message = "아이디를 입력하셔야 합니다.")
		@Schema(description = "사용자의 이메일 주소. 이메일 형식을 따릅니다.", example = "user@example.com")
		String email,

		@NotBlank(message = "패스워드를 입력하셔야 합니다.")
		@Schema(description = "사용자의 비밀번호. 8자 이상이며, 최소 하나의 문자와 하나의 숫자를 포함해야 합니다.", example = "password123")
		String password
	) {
	}

	@Getter
	@Builder
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public static class Response {
		@Schema(description = "사용자 식별자")
		private Long id;

		@Schema(description = "사용자 이름")
		private String name;
	}
}
