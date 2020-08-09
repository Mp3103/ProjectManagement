package com.meet.taskInterface;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meet.entity.Task;

public interface TaskInterface extends JpaRepository<Task, Integer> {

}
