package com.school.sba.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.school.sba.requestdto.UserRequest;
import com.school.sba.responsedto.UserResponse;
import com.school.sba.service.UserService;
import com.school.sba.util.ResponseStructure;

import jakarta.validation.Valid;

@RestController
public class UserController {
	@Autowired
	UserService service;

	@PostMapping("/users/register")
	public ResponseEntity<ResponseStructure<UserResponse>> registerAdmin(@RequestBody @Valid UserRequest userrequest) {
		return service.registerAdmin(userrequest);
	}

	@PostMapping("/users/{userId}/register")
	public ResponseEntity<ResponseStructure<UserResponse>> registerUser(@RequestBody @Valid UserRequest userrequest,
			@PathVariable int userId) {
		return service.registerUser(userrequest, userId);
	}

	@GetMapping("/users/{userId}")
	public ResponseEntity<ResponseStructure<UserResponse>> getUser(@PathVariable int userId) {
		return service.getUser(userId);
	}

	@DeleteMapping("/users/{userId}")
	public ResponseEntity<ResponseStructure<UserResponse>> deleteUser(@PathVariable int userId) {
		return service.deleteUser(userId);

	}
}
