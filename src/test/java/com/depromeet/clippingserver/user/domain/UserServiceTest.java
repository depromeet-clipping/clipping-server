package com.depromeet.clippingserver.user.domain;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest @Transactional
@ActiveProfiles("test")
public class UserServiceTest {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Value("${test.invalid_device_key}")
	String INVALID_DEVICE_KEY;
	
	@Value("${test.device_key}")
	String DEVICE_KEY;
	
	@Test
	public void testExistUserJoin() {
		Long userID = 1L;
		assertEquals("등록된 유저의 아이디가 일치하지 않습니다.", userID, userService.join(DEVICE_KEY));
	}
	
	@Test
	public void testNotExistUserJoin() {
		Long userId = userService.join(INVALID_DEVICE_KEY);
		Optional<User> res = userRepository.findByDeviceKey(INVALID_DEVICE_KEY);
		assertEquals("등록된 유저의 아이디가 일치하지 않습니다.",userId, res.get().getId());
	}
}
