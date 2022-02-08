package com.sync.ticketprocessor.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sync.ticketprocessor.config.security.JwtUtils;
import com.sync.ticketprocessor.entity.User;
import com.sync.ticketprocessor.enums.Role;
import com.sync.ticketprocessor.payload.login.JwtResponse;
import com.sync.ticketprocessor.payload.login.LoginRequest;
import com.sync.ticketprocessor.payload.login.MessageResponse;
import com.sync.ticketprocessor.payload.login.SignupRequest;
import com.sync.ticketprocessor.repository.UserCrudRepository;
import com.sync.ticketprocessor.service.impl.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserCrudRepository userCrudRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());
		//Mahesh change that to fetch details from property file
		//Mahesh Response body is not required here, we need a seperate api for that
		ResponseCookie resCookie = ResponseCookie.from("jwt", jwt)
				.httpOnly(true)
				.secure(false)
				.path("/")
				.maxAge(1 * 24 * 60 * 60)
				.build();

		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, resCookie.toString()).body(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));

	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userCrudRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userCrudRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()));

		Set<String> strRoles = signUpRequest.getRoles();
		Set<String> roles = new HashSet<>();

		if (strRoles == null) {
			throw new RuntimeException("Error: Role is not found.");
		} else {
			strRoles.forEach(role -> {
				if (Role.isExist(role)) {
					roles.add(role);
				} else {
					throw new RuntimeException("Error: Role is not found.");
				}
			});
		}

		user.setRoles(roles);
		userCrudRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
}