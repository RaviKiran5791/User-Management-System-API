package com.jsp.ums.repositary;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.ums.entity.User;

public interface UserRepositary extends JpaRepository<User, Integer>{

}
