package com.jsp.ums.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jsp.ums.repositary.UserRepositary;
@Service
public class CustumUserDetailService implements UserDetailsService{
	
	@Autowired
	private UserRepositary userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
//		User user = userRepo.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("User Name Not Found"));
//		
//		return new CustumUserDetails(user);
		
		return userRepo.findByUsername(username).map(user->new CustumUserDetails(user)).orElseThrow(()->new UsernameNotFoundException("User Not Authenticated"));
	}

}