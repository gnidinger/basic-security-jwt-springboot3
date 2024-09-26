package kr.co.galloping.football_manager.domain.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import kr.co.galloping.football_manager.domain.user.dto.FmUserDto;
import kr.co.galloping.football_manager.domain.user.service.FmUserService;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class FmUserController {

	private final FmUserService fmUserService;

	@PostMapping("/register")
	@Operation(
		summary = "회원 가입",
		description = "사용자를 시스템에 등록합니다. 이메일, 이름, 비밀번호 유효성을 검증합니다.",
		tags = {"User"},
		requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
			required = true,
			content = @Content(
				mediaType = MediaType.APPLICATION_JSON_VALUE,
				schema = @Schema(implementation = FmUserDto.Register.class)
			)
		),
		responses = {
			@ApiResponse(
				responseCode = "201",
				description = "회원 가입 완료",
				content = @Content(
					mediaType = MediaType.APPLICATION_JSON_VALUE,
					schema = @Schema(implementation = FmUserDto.RegisterResponse.class)
				)
			)
		}
	)
	public ResponseEntity<FmUserDto.RegisterResponse> registerUser(@Valid @RequestBody FmUserDto.Register registerDto) {
		FmUserDto.RegisterResponse response = fmUserService.registerUser(registerDto);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
}
