package kr.co.galloping.football_manager.domain.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import kr.co.galloping.football_manager.domain.user.entity.FmUser;
import kr.co.galloping.football_manager.domain.user.dto.LoginDto;

@Mapper(componentModel = "spring")
public interface LoginMapper {

	// FmUser 엔티티를 LoginDto.Response로 변환
	@Mapping(source = "id", target = "id")
	@Mapping(source = "name", target = "name")
	LoginDto.Response mapToResponse(FmUser fmUser);
}
