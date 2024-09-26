package kr.co.galloping.football_manager.global.config.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import kr.co.galloping.football_manager.global.config.security.filter.JwtAuthenticationFilter;
import kr.co.galloping.football_manager.global.config.security.filter.JwtTokenProvider;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtTokenProvider jwtTokenProvider;

	private static final String[] PUBLIC_POST_ENDPOINTS = {
		"/api/user/register",
		"/api/login/**",
		"/api/email/**",
	};

	private static final String[] PUBLIC_GET_ENDPOINTS = {
		"/swagger-ui/**",
		"/v3/api-docs/**",
	};

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf(AbstractHttpConfigurer::disable) // CSRF 비활성화
			.formLogin(AbstractHttpConfigurer::disable) // 기본 로그인 페이지 비활성화
			.authorizeHttpRequests(authorizeRequests -> authorizeRequests
				.requestMatchers("/h2/**").permitAll() // H2 콘솔 접근 허용
				.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()  // 정적 리소스 허용
				.requestMatchers(HttpMethod.POST, PUBLIC_POST_ENDPOINTS).permitAll()
				.requestMatchers(HttpMethod.GET, PUBLIC_GET_ENDPOINTS).permitAll()
				.anyRequest().authenticated()
			)
			.headers(headersConfigurer ->
				headersConfigurer
					.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin) // H2 콘솔을 위해 FrameOptions 설정
			)
			.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.addAllowedOrigin("*");  // 모든 도메인 허용 (필요에 따라 변경)
		configuration.addAllowedMethod("*");  // 모든 HTTP 메서드 허용
		configuration.addAllowedHeader("*");  // 모든 헤더 허용
		configuration.setAllowCredentials(true);  // 쿠키나 인증 관련 정보 허용

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);  // 모든 경로에 대해 CORS 설정 적용
		return source;
	}
}
