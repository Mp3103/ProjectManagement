package com.meet.userInterface;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.meet.entity.UserClass;

public interface UserClassInterface extends PagingAndSortingRepository<UserClass, Integer> {

}
