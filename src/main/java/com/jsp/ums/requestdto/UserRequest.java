package com.jsp.ums.requestdto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
	@NotNull(message = "User Name Cannot be null")
	@NotBlank(message="User name cannot be blank")
//	@NotEmpty(message = "user name cannot be not null & not blank" )
	@Pattern(regexp = "^[A-Z][a-z]*(\\s[A-Z][a-z]*)?$", message = "Username should follow initcap format")
	private String name;
	
//	@NotBlank(message = "email cannot be blank")
	@NotEmpty(message = "email cannot be not null & not blank")
	@Email(regexp = "[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+\\.[a-z]{2,}", message = "invalid email ")
	private String email;
	
	@NotBlank(message = "Inavalid")
	@NotNull(message = "Invalid")
	@Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
	message = "Password must"+ " contain at least one letter, one number, one special character")
	private String password;
}
