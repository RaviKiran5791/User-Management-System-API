package com.jsp.ums.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UsersDataNotFoundException extends RuntimeException {
	
	private String message;

}
