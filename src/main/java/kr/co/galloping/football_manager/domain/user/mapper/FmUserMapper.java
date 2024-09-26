package kr.co.galloping.football_manager.domain.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import kr.co.galloping.football_manager.domain.user.dto.FmUserDto;
import kr.co.galloping.football_manager.domain.user.entity.FmUser;

@Mapper(componentModel = "spring")
public interface FmUserMapper {

	// FmUser 엔티티를 UserDto.RegisterResponse로 변환
	@Mapping(source = "id", target = "id")
	@Mapping(source = "name", target = "name")
	FmUserDto.RegisterResponse mapToRegisterResponse(FmUser fmUser);
}
