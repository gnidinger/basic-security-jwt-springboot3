package kr.co.galloping.football_manager.domain.user.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import kr.co.galloping.football_manager.domain.user.dto.LoginDto;
import kr.co.galloping.football_manager.domain.user.entity.FmUser;
import kr.co.galloping.football_manager.domain.user.mapper.LoginMapper;
import kr.co.galloping.football_manager.domain.user.service.LoginService;
import kr.co.galloping.football_manager.global.config.security.filter.JwtTokenProvider;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LoginController {

	private final LoginMapper loginMapper;
	private final LoginService loginService;
	private final JwtTokenProvider jwtTokenProvider;

	@PostMapping("/login")
	@Operation(
		summary = "로그인",
		description = "이메일과 비밀번호를 이용해 로그인을 처리합니다. JWT를 생성 및 반환합니다.",
		tags = {"Authentication"},
		requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
			required = true,
			content = @Content(
				mediaType = MediaType.APPLICATION_JSON_VALUE,
				schema = @Schema(implementation = LoginDto.Request.class)
			)
		),
		responses = {
			@ApiResponse(
				responseCode = "200",
				headers = @Header(name = "Authorization", description = "JWT 토큰", schema = @Schema(type = "string")),
				content = @Content(
					mediaType = MediaType.APPLICATION_JSON_VALUE,
					schema = @Schema(implementation = LoginDto.Response.class)
				)
			),
		}
	)
	public ResponseEntity<LoginDto.Response> login(@Valid @RequestBody LoginDto.Request requestDto) {
		FmUser fmUser = loginService.login(requestDto);
		String token = jwtTokenProvider.createToken(fmUser.getEmail()); // JWT 생성

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", token); // 토큰을 헤더에 추가

		LoginDto.Response response = loginMapper.mapToResponse(fmUser);

		return ResponseEntity.ok().headers(headers).body(response);
	}
}
