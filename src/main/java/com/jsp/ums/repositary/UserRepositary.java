package com.jsp.ums.repositary;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.ums.entity.User;

public interface UserRepositary extends JpaRepository<User, Integer>{

	public Optional<User> findByUsername(String username);
}
