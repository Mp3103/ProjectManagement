package com.meet.projectController;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.meet.entity.Project;
import com.meet.entity.UserClass;
import com.meet.projectService.ProjectServiceInterface;
import com.meet.userService.UserServiceInterface;

@Controller
public class ProjectController {

	private ProjectServiceInterface projectServiceInterface;

	private UserServiceInterface userServiceInterFace;

	@Autowired
	public ProjectController(ProjectServiceInterface projectServiceInterface,
			UserServiceInterface userServiceInterFace) {
		super();
		this.projectServiceInterface = projectServiceInterface;
		this.userServiceInterFace = userServiceInterFace;
	}

	@RequestMapping("/addNewProject")
	public String addNewProject(Model model, Project project, Authentication authentication) {
	
	try {
		
	String username = authentication.getName();
		model.addAttribute("userDetail", username);
		Object authority = authentication.getAuthorities();
		String checkAuthority = authority.toString();
		model.addAttribute("userRole",checkAuthority);
		
		List<UserClass> listOfEmployee = userServiceInterFace.getAllUser();
		model.addAttribute("listOfAvailableEmployee", listOfEmployee);
		return "/admin/CreateProject";
		
	} catch (Exception exiption) { 
		return "blank";
	}
	}

	@RequestMapping("/createProject")
	public String generateNewProject(@RequestParam("projectTitle") String title,
			@RequestParam("bodyOfProject") String desc, @RequestParam("startDate") Date startDate,
			@RequestParam("endDate") Date endDate, @RequestParam("selectedEmployee") String selectedLeader,
			Authentication authentication, Model model, Project project) {
		try {
		
			String username = authentication.getName();
			model.addAttribute("userDetail", username);
			Object authority = authentication.getAuthorities();
			String checkAuthority = authority.toString();
			model.addAttribute("userRole",checkAuthority);
			
			int leaderID = Integer.parseInt(selectedLeader);
			UserClass leaderDetail = userServiceInterFace.getUserById(leaderID);

			project.setTitle(title);
			project.setDescription(desc);
			project.setStart_date(startDate);
			project.setEnd_date(endDate);
			project.setLeaderId(leaderDetail);

			projectServiceInterface.addNewProject(project);

			List<UserClass> employeeDetail = userServiceInterFace.getAllUser();
			model.addAttribute("emoloyeeList", employeeDetail);
			
			Project project2 = projectServiceInterface.findProject(title);
			model.addAttribute("projectId", project2.getProject_id());
			
			return "/admin/SelectEmployeeForProject";

		} catch (Exception e) {

			return "blank";
		}
			}
	
	
	
	
	
}
