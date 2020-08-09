package com.meet.userInterface;


import org.springframework.data.jpa.repository.JpaRepository;

import com.meet.entity.UserClass;

public interface UserInterface extends JpaRepository<UserClass, Integer> {

	
}
