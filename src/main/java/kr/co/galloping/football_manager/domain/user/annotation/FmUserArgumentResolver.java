package kr.co.galloping.football_manager.domain.user.annotation;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import kr.co.galloping.football_manager.domain.user.entity.FmUser;
import kr.co.galloping.football_manager.domain.user.service.FmUserService;
import kr.co.galloping.football_manager.global.config.security.userDetails.AuthUser;
import kr.co.galloping.football_manager.global.error.BusinessLogicException;
import kr.co.galloping.football_manager.global.error.ExceptionCode;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FmUserArgumentResolver implements HandlerMethodArgumentResolver {

	private final FmUserService fmUserService;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterAnnotation(CurrentFmUser.class) != null
			&& parameter.getParameterType().equals(FmUser.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !authentication.isAuthenticated()) {
			throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
		}

		Object principal = authentication.getPrincipal();
		if (!(principal instanceof AuthUser authUser)) {
			throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
		}

		return fmUserService.findById(authUser.getId());
	}
}
