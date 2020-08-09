package com.meet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.meet.entity.Project;
import com.meet.entity.Task;
import com.meet.entity.UserClass;
import com.meet.projectService.ProjectServiceInterface;
import com.meet.taskService.TaskServiceInterface;
import com.meet.userService.UserServiceInterface;

@Controller
public class HomeController {

	private ProjectServiceInterface projectServiceInterface;

	private UserServiceInterface userServiceInterface;

	private TaskServiceInterface taskServiceInterface;

	@Autowired
	public HomeController(ProjectServiceInterface projectServiceInterface, UserServiceInterface userServiceInterface,
			TaskServiceInterface taskServiceInterface) {
		super();
		this.projectServiceInterface = projectServiceInterface;
		this.userServiceInterface = userServiceInterface;
		this.taskServiceInterface = taskServiceInterface;
	}

	@RequestMapping("/")
	private String goToHomePage(Model model , Authentication authentication) {
		
		System.out.println("Comming Here");
		
		String username = authentication.getName();
		Object authority = authentication.getAuthorities();
		String checkAuthority = authority.toString();
		String roles = "[ROLE_ADMIN]";
		String userRole = "[ROLE_LEADER]";
		UserClass userClass = userServiceInterface.getUserByUsername(username);
		
		model.addAttribute("userDetail", username);
		//total number of projects - admin home page
		int totalProject=projectServiceInterface.findAllProject().size();
		model.addAttribute("totalProject",totalProject);
		
		//total number of employees - admin home page
		int totalEmployees=userServiceInterface.getAllUser().size();
		model.addAttribute("totalEmployees",totalEmployees);
		
		//list of five projects - admin home page
		List<Project> listOfFiveProjects=projectServiceInterface.lastFiveProjects();
		model.addAttribute("listOfFiveProjects",listOfFiveProjects);
		
		//list of running projects - admin home page
		int listOfRunningProjects=projectServiceInterface.getRunningProjects().size();
		model.addAttribute("listOfRunningProjects",listOfRunningProjects);
		
		
		
		if (checkAuthority.equals(roles)) {
			model.addAttribute("userRole", checkAuthority);
			return "/admin/HomePage";

		}else  if (checkAuthority.equals(userRole)) {
			//number of all tasks - leader home page
			List<Project> project = projectServiceInterface.getProjectListByLeaderId(userClass.getId());		
			List<Task> listOfTotalTask = null;
			for (Project project2 : project) {
				int projectId = project2.getProject_id();
				listOfTotalTask = taskServiceInterface.taskListByProjectId(projectId);
			}
			int numberOfTotalTask=listOfTotalTask.size();
			model.addAttribute("numberOfTotalTask",numberOfTotalTask);
			
			//number of pending task - leader home page
			List<Task> listOfPendingTask = null;
			for (Project project2 : project) {
				int projectId = project2.getProject_id();
				listOfPendingTask = taskServiceInterface.listOfPendingTaskbyProjectIdForLeader(projectId);
			}
			int numberOfPendingTask=listOfPendingTask.size();
			model.addAttribute("numberOfPendingTask",numberOfPendingTask);
			
			//number of pending task - leader home page
			List<Task> listOfCompletedTask = null;
			for (Project project2 : project) {
				int projectId = project2.getProject_id();
				listOfCompletedTask = taskServiceInterface.listOfCompletedTaskbyProjectIdForLeader(projectId);
			}
			int numberOfCompletedTask=listOfCompletedTask.size();
			model.addAttribute("numberOfCompletedTask",numberOfCompletedTask);
					
			//last Five Project As Leader - leader homr page
			List<Project> lastFiveProjectForLeader=projectServiceInterface.lastFiveProjectListByLeaderId(userClass.getId());
			model.addAttribute("lastFiveProjectForLeader",lastFiveProjectForLeader);
			
			model.addAttribute("userRole", checkAuthority);
			return"/leader/HomePageForLeader";

		}else {
			model.addAttribute("userRole", checkAuthority);
			//number of completed task - employee home page
			int numberOfCompletedTaskOfEmployee = taskServiceInterface.completedTaskList(userClass.getId()).size();
			model.addAttribute("numberOfCompletedTaskOfEmployee",numberOfCompletedTaskOfEmployee);
					
			//number of pending task - employee home page
			int numberOfPendingTaskOfEmployee = taskServiceInterface.pendingTaskList(userClass.getId()).size();
			model.addAttribute("numberOfPendingTaskOfEmployee",numberOfPendingTaskOfEmployee);
			
			//number of total task - employee home page
			int numberOfTotalTasksForEmployee=taskServiceInterface.totalTasksForEmployee(userClass.getId()).size();
			model.addAttribute("numberOfTotalTasksForEmployee", numberOfTotalTasksForEmployee);
			
			//number of last five pending task - employee home page
			List<Task> lastFivePendingTasksForEmployee=taskServiceInterface.lastFivePendingTasks(userClass.getId());
			model.addAttribute("lastFivePendingTasksForEmployee", lastFivePendingTasksForEmployee);
			
			return "/user/HomePageForUser";
		}
	}

	@RequestMapping("/againOnMainPage")
	public String goToMainPageAgain(Model model, Authentication authentication) {
		return goToHomePage(model, authentication);
	}


	
}
