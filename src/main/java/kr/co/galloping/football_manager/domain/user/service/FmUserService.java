package kr.co.galloping.football_manager.domain.user.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.galloping.football_manager.domain.user.dto.FmUserDto;
import kr.co.galloping.football_manager.domain.user.entity.FmUser;
import kr.co.galloping.football_manager.domain.user.mapper.FmUserMapper;
import kr.co.galloping.football_manager.domain.user.repository.FmUserRepository;
import kr.co.galloping.football_manager.global.config.PBKDF2Encoder;
import kr.co.galloping.football_manager.global.error.BusinessLogicException;
import kr.co.galloping.football_manager.global.error.ExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FmUserService {

	private final FmUserMapper fmUserMapper;
	private final PBKDF2Encoder pbkdf2Encoder;
	private final FmUserRepository fmUserRepository;

	@Transactional
	public FmUserDto.RegisterResponse registerUser(FmUserDto.Register registerDto) {
		// 이메일 중복 확인
		if (fmUserRepository.findByEmail(registerDto.email()).isPresent()) {
			throw new BusinessLogicException(ExceptionCode.EMAIL_EXIST);
		}

		// 이메일 검증 확인
		if (registerDto.isVerified() == null || !registerDto.isVerified()) {
			throw new BusinessLogicException(ExceptionCode.EMAIL_NOT_VERIFIED);
		}

		// 비밀번호 일치 확인
		if (!registerDto.password().equals(registerDto.passwordRepeat())) {
			throw new BusinessLogicException(ExceptionCode.PASSWORD_MISMATCH);
		}

		try {
			// 사용자 생성 및 저장
			FmUser fmUser = fmUserRepository.save(
				FmUser.create(registerDto.email(), registerDto.name(), registerDto.password(), pbkdf2Encoder)
			);
			// FmUser 엔티티를 UserDto.RegisterResponse로 변환하여 반환
			return fmUserMapper.mapToRegisterResponse(fmUser);
		} catch (DataIntegrityViolationException e) {
			throw new BusinessLogicException(ExceptionCode.EMAIL_EXIST);  // 이메일 중복 예외 처리
		}
	}

	@Transactional(readOnly = true)
	public FmUser findByEmail(String email) {
		return fmUserRepository.findByEmail(email)
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
	}

	@Transactional(readOnly = true)
	public FmUser findById(Long id) {
		return fmUserRepository.findById(id)
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
	}
}
