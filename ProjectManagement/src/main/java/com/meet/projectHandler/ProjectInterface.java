package com.meet.projectHandler;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meet.entity.Project;

public interface ProjectInterface extends JpaRepository<Project,Integer> {


	
}
