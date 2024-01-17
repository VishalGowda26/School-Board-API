package com.school.sba.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.school.sba.requestdto.UserRequest;
import com.school.sba.responsedto.UserResponse;
import com.school.sba.util.ResponseStructure;

public interface UserService {
	public ResponseEntity<ResponseStructure<UserResponse>> registerUser(@RequestBody UserRequest userrequest);

	public ResponseEntity<ResponseStructure<UserResponse>> getUser(@PathVariable int userId);

	public ResponseEntity<ResponseStructure<UserResponse>> deleteUser(@PathVariable int userId);

}
