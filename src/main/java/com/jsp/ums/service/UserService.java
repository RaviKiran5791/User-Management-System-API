package com.jsp.ums.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.jsp.ums.entity.User;
import com.jsp.ums.requestdto.UserRequest;
import com.jsp.ums.responsedto.UserResponse;
import com.jsp.ums.util.ResponseStructure;

public interface UserService{

	public ResponseEntity<ResponseStructure<UserResponse>> saveUser(UserRequest userRequest);

	public ResponseEntity<ResponseStructure<UserResponse>> findUserById(int id);

	public ResponseEntity<ResponseStructure<UserResponse>> updateUserById(UserRequest userRequest ,int id);

	public ResponseEntity<ResponseStructure<List<UserResponse>>> findAllUsers();

	public ResponseEntity<ResponseStructure<UserResponse>> deleteUserById(int id);

}
