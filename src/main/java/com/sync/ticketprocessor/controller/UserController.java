package com.sync.ticketprocessor.controller;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sync.ticketprocessor.entity.User;
import com.sync.ticketprocessor.service.impl.UserDetailsImpl;

@RestController
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@RequestMapping(value = "/getUserDetails", method = RequestMethod.GET)
	public ResponseEntity<User> currentUserName(Authentication authentication) {
		User user = new User();
		try {
			UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
			user.setId(userPrincipal.getId());
			user.setUsername(userPrincipal.getUsername());
			user.setEmail(userPrincipal.getEmail());
			Collection<? extends GrantedAuthority> authorities = userPrincipal.getAuthorities();
			Set<String> roles = authorities.stream().map(role -> role.toString()).collect(Collectors.toSet());
			user.setRoles(roles);
		} catch (Exception e) {
			logger.error("Error getting user details");
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(user);
		}

		return ResponseEntity.status(HttpStatus.OK).body(user);
	}
}
