package com.depromeet.clippingserver.user.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

	public Optional<User> findByDeviceKey(String deviceKey);

}
