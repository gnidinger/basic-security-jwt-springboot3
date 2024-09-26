package kr.co.galloping.football_manager.global.error;

import org.springframework.security.core.AuthenticationException;

public class DuplicateEmailProviderException extends AuthenticationException {
	public DuplicateEmailProviderException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public DuplicateEmailProviderException(String msg) {
		super(msg);
	}
}
