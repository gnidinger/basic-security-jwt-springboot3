package kr.co.galloping.football_manager.domain.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.galloping.football_manager.domain.user.dto.LoginDto;
import kr.co.galloping.football_manager.domain.user.entity.FmUser;
import kr.co.galloping.football_manager.global.config.PBKDF2Encoder;
import kr.co.galloping.football_manager.global.error.BusinessLogicException;
import kr.co.galloping.football_manager.global.error.ExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

	private final PBKDF2Encoder pbkdf2Encoder;
	private final FmUserService fmUserService;

	@Transactional
	public FmUser login(LoginDto.Request requestDto) {
		FmUser fmUser = fmUserService.findByEmail(requestDto.email());

		if (!pbkdf2Encoder.matches(requestDto.password(), fmUser.getPassword())) {
			throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
		}

		return fmUser;
	}
}
