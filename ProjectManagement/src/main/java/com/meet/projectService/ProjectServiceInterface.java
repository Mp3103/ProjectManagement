package com.meet.projectService;

import java.sql.Date;
import java.util.List;

import com.meet.entity.Project;
import com.meet.entity.UserClass;

public interface ProjectServiceInterface {

	public Project addNewProject(Project project);
	
	public Project findProject(String projectName);
	
	public Project findProjectById(int id);
	
	public void removeProjectFromUser(Project project);
	
	public List<Project> listOfAllProject(int id);
	
	public List<Project> findAllProject();
	
	public List<Project> lastFiveProjects();
	
	public int updateDetail(int id ,String title , String description , Date startdate , Date endate);

	public int updateLeader(int leaderId , int projectId);

	public List<Project> getRunningProjects();

	public List<Project> getProjectListByLeaderId(int leaderId);

	public List<Project> getProjectListWithEndDate(int id);

	public List<Project> lastFiveProjectListByLeaderId(int leaderId);
}
