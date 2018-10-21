package com.depromeet.clippingserver.user.domain;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	public Long join(String deviceKey) {
		Optional<User> userOpt = userRepository.findByDeviceKey(deviceKey);
		if (userOpt.isPresent()) {
			return userOpt.get().getId();
		} else {
			User savedUser = userRepository.save(userOpt.orElse(User.builder().deviceKey(deviceKey).build()));
			return savedUser.getId();
		}
	}

}
