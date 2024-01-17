package com.school.sba.requestdto;

import com.school.sba.enums.UserRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {

	@NotEmpty(message = "username should not be null")
	@Pattern(regexp = "[a-zA-Z]+\\.?", message = "username must" + " contain every word first letter capital")
	private String username;

	@NotEmpty(message = "Password is required")
	@Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Password must"
			+ " contain at least one letter, one number, one special character")
	private String password;

	@NotEmpty(message = "firstname should not be empty")
	private String firstName;

	@NotEmpty(message = "lastname should not be empty")
	private String lastName;

	private long contactNo;

	@NotBlank(message = "Email should not be blank")
	@Email(regexp = "[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+\\.[a-z]{2,}", message = "invalid email ")
	private String email;

	private UserRole userrole;

}
