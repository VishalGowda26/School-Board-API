package com.school.sba.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.school.sba.requestdto.UserRequest;
import com.school.sba.responsedto.UserResponse;
import com.school.sba.util.ResponseStructure;

import jakarta.validation.Valid;

public interface UserService {
	public ResponseEntity<ResponseStructure<UserResponse>> registerAdmin(@RequestBody UserRequest userrequest);

	public ResponseEntity<ResponseStructure<UserResponse>> registerUser(@RequestBody @Valid UserRequest userrequest,
			@PathVariable int userId);

	public ResponseEntity<ResponseStructure<UserResponse>> getUser(@PathVariable int userId);

	public ResponseEntity<ResponseStructure<UserResponse>> deleteUser(@PathVariable int userId);
}
