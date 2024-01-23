
package com.jsp.ums.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jsp.ums.entity.User;
import com.jsp.ums.exceptionhandler.UserNotFoundByIdException;
import com.jsp.ums.exceptionhandler.UsersDataNotFoundException;
import com.jsp.ums.repositary.UserRepositary;
import com.jsp.ums.requestdto.UserRequest;
import com.jsp.ums.responsedto.UserResponse;
import com.jsp.ums.service.UserService;
import com.jsp.ums.util.ResponseStructure;
@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepositary userrepo;
	
	@Autowired
	private ResponseStructure<UserResponse> structure;
	
	public User mapToUser(UserRequest userRequest)
	{
		return User.builder()
				.username(userRequest.getName())
				.email(userRequest.getEmail())
				.password(passwordEncoder.encode(userRequest.getPassword()))
				.build();
	}
	
	public UserResponse mapToUserResponse(User user)
	{
		return UserResponse.builder()
				.id(user.getId())
				.name(user.getUsername())
				.email(user.getEmail())
				.build();
	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> saveUser(UserRequest userRequest) {
		User user = mapToUser(userRequest);
	     user = userrepo.save(user); 
	     
	     UserResponse userResponse = mapToUserResponse(user);
	     
	     structure.setStatuscode(HttpStatus.CREATED.value());
	     structure.setMessage("User Created");
	     structure.setData(userResponse);
	     
		return new ResponseEntity<ResponseStructure<UserResponse>>(structure,HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> findUserById(int id) {
		
	    User user = userrepo.findById(id).orElseThrow(()->new UserNotFoundByIdException("User Not Present for id "+id));
		
			
			UserResponse userResponse = mapToUserResponse(user);
			
			structure.setStatuscode(HttpStatus.FOUND.value());
			structure.setMessage("User Data found for a given id");
			structure.setData(userResponse);
			
			return new ResponseEntity<ResponseStructure<UserResponse>>(structure,HttpStatus.CREATED);
		
		
	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> updateUserById(UserRequest userRequest, int id) {
		
//		User olduser = userrepo.findById(id).map(u -> {
//			u.setId(id);
//			return userrepo.save(u);
//		}).orElseThrow(() -> new RuntimeException());
		
	    User olduser = userrepo.findById(id).orElseThrow(()->new UserNotFoundByIdException("User Not Found To Update for id "+id));
		
		User user = mapToUser(userRequest);
		user.setId(olduser.getId());
		
	    user = userrepo.save(user);
	    
	    UserResponse userResponse = mapToUserResponse(user);
	    
	    structure.setStatuscode(HttpStatus.OK.value());
	    structure.setMessage("User Data Updated");
	    structure.setData(userResponse);
		
		return new ResponseEntity<ResponseStructure<UserResponse>>(structure,HttpStatus.OK);
	}



	@Override
	public ResponseEntity<ResponseStructure<List<UserResponse>>> findAllUsers() {
		
		List<User> userList = userrepo.findAll();
		if(!userList.isEmpty())
		{
			List<UserResponse> list=new ArrayList<>();
			
			for(User user : userList)
			{
				UserResponse userResponse = mapToUserResponse(user);
			     list.add(userResponse);
			}
			ResponseStructure<List<UserResponse>> structure=new ResponseStructure<>();
			
			structure.setStatuscode(HttpStatus.FOUND.value());
			structure.setMessage("User Data Found");
			structure.setData(list);
			
			return new ResponseEntity<ResponseStructure<List<UserResponse>>>(structure,HttpStatus.FOUND);
		}
		else
			throw new UsersDataNotFoundException("Users Data Not Present to Fetch");
	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> deleteUserById(int id) {
		
		User user = userrepo.findById(id).orElseThrow(() -> new UserNotFoundByIdException("User Not Present to delete for id "+id));
		
		userrepo.delete(user);
		
		UserResponse userResponse = mapToUserResponse(user);
		
		structure.setStatuscode(HttpStatus.OK.value());
		structure.setMessage("User Deleted Successfully");
		structure.setData(userResponse);
		
		
		return new ResponseEntity<ResponseStructure<UserResponse>>(structure,HttpStatus.OK);
	}

}
