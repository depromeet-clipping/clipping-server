package com.depromeet.clippingserver.user.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.depromeet.clippingserver.user.domain.UserService;

@RestController
public class UserController {
	@Autowired
	private UserService userService;
	
	@PostMapping(value="/user")
	public ResponseEntity<Map<String, Long>> joinUser(@RequestHeader(value="DeviceKey") String deviceKey, HashMap<String, Long> re) {
		re.put("userId", userService.join(deviceKey));
		return ResponseEntity.ok().body(re);
	}
}
