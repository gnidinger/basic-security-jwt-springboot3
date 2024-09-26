package kr.co.galloping.football_manager.domain.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.galloping.football_manager.domain.user.entity.FmUser;

public interface FmUserRepository extends JpaRepository<FmUser, Long> {
	Optional<FmUser> findByEmail(String email);
}
