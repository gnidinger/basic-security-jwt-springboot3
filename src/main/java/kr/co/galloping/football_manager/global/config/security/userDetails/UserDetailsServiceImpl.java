package kr.co.galloping.football_manager.global.config.security.userDetails;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.galloping.football_manager.domain.user.repository.FmUserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final FmUserRepository fmUserRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return fmUserRepository.findByEmail(email)
			.map(AuthUser::of)  // FmUser를 AuthUser로 변환
			.orElseThrow(() -> new UsernameNotFoundException("유저 정보를 찾을 수 없습니다: " + email));
	}
}
