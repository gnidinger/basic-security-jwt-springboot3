package kr.co.galloping.football_manager.global.config.security.userDetails;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import kr.co.galloping.football_manager.domain.user.entity.FmUser;
import lombok.Getter;

@Getter
public class AuthUser implements UserDetails {

	private Long id;
	private String email;
	private String password;
	private List<String> roles;

	private AuthUser(FmUser fmUser) {
		this.id = fmUser.getId();
		this.email = fmUser.getEmail();
		this.password = fmUser.getPassword();
		this.roles = List.of(fmUser.getRole().toString());
	}

	public static AuthUser of(FmUser fmUser) {
		return new AuthUser(fmUser);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(roles.get(0)));
	}

	@Override
	public String getUsername() {
		return email;  // 이메일을 사용하여 인증
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}