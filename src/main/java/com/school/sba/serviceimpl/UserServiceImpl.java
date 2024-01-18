package com.school.sba.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.school.sba.entity.User;
import com.school.sba.enums.UserRole;
import com.school.sba.exception.ConstraintViolationException;
import com.school.sba.exception.UserNotFoundByIdException;
import com.school.sba.repository.UserRepo;
import com.school.sba.requestdto.UserRequest;
import com.school.sba.responsedto.UserResponse;
import com.school.sba.service.UserService;
import com.school.sba.util.ResponseStructure;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepo repo;
	@Autowired
	ResponseStructure<UserResponse> structure;

	/*------------------------------> User Request <--------------------------------------------*/

	private User mapToUser(UserRequest userRequest) {
		return User.builder().username(userRequest.getUsername()).password(userRequest.getPassword())
				.firstName(userRequest.getFirstName()).lastName(userRequest.getLastName())
				.contactNo(userRequest.getContactNo()).email(userRequest.getEmail()).userrole(userRequest.getUserrole())
				.build();
	}

	/*------------------------------> User Request <--------------------------------------------*/
	private UserResponse mapToUserResponse(User user) {
		return UserResponse.builder().userId(user.getUserId()).username(user.getUsername())
				.firstName(user.getFirstName()).lastName(user.getLastName()).email(user.getEmail())
				.userrole(user.getUserrole()).build();
	}

	/*------------------------------> TO Register User <--------------------------------------------*/
	int admin = 0;

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> registerUser(UserRequest userrequest) {
		User user = mapToUser(userrequest);
		user.setDeleted(false);
		boolean existsByUserrole = repo.existsByUserrole(UserRole.ADMIN);
		if (existsByUserrole==false || user.getUserrole()!=UserRole.ADMIN) {
			try {
				user = repo.save(user);
			} catch (Exception e) {
				throw new ConstraintViolationException("Pls check the user details Entered details already exist");
			}
			structure.setStatus(HttpStatus.CREATED.value());
			structure.setMessage("Data Saved Successfully");
			structure.setData(mapToUserResponse(user));
		} else {
			throw new ConstraintViolationException("There should only be one admin which is already Exist");
		}
		return new ResponseEntity<ResponseStructure<UserResponse>>(structure, HttpStatus.CREATED);
	}

	/*------------------------------> TO Get User <--------------------------------------------*/
	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> getUser(int userid) {
		User user1 = repo.findById(userid).orElseThrow(() -> new UserNotFoundByIdException("No details Found"));
		structure.setStatus(HttpStatus.FOUND.value());
		structure.setMessage("User details for the given id successfully fetched");
		structure.setData(mapToUserResponse(user1));
		return new ResponseEntity<ResponseStructure<UserResponse>>(structure, HttpStatus.FOUND);
	}

	/*------------------------------> TO Delete User <--------------------------------------------*/
	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> deleteUser(int userId) {
		User user = new User();
		try {
			user = repo.findById(userId).get();
		} catch (Exception e) {
			throw new UserNotFoundByIdException("No details Found");
		}
		if (user != null) {
			user.setDeleted(true);
			repo.delete(user);
			structure.setStatus(HttpStatus.FOUND.value());
			structure.setMessage("User successfully deleted");
			structure.setData(mapToUserResponse(user));
		}
		return new ResponseEntity<ResponseStructure<UserResponse>>(structure, HttpStatus.FOUND);
	}
}
