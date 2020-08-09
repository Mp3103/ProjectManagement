package com.meet.userProjectController;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.meet.entity.Project;
import com.meet.entity.UserClass;
import com.meet.projectService.ProjectServiceInterface;
import com.meet.userController.Employee;
import com.meet.userService.UserServiceInterface;

@Controller
public class UserProjectController {

	private ProjectServiceInterface projectServiceInterface;

	private UserServiceInterface userServiceInterFace;

	@Autowired
	public UserProjectController(ProjectServiceInterface projectServiceInterface,
			UserServiceInterface userServiceInterFace) {
		super();
		this.projectServiceInterface = projectServiceInterface;
		this.userServiceInterFace = userServiceInterFace;

	}

	@RequestMapping("/addAWorker")
	public String addingAProject(@RequestParam("selectedEmployee") List<Integer> employeesId,
			@RequestParam("projectId") int id, UserClass userClass, Project project,Authentication authentication , Model model) {
	try {

		String username = authentication.getName();
		model.addAttribute("userDetail", username);
		Object authority = authentication.getAuthorities();
		String checkAuthority = authority.toString();
		model.addAttribute("userRole",checkAuthority);
		
		project.getUserClasses().add(userClass);

		List<Integer> gettingNumber = employeesId;

		for (Integer integer : gettingNumber) {
			project = projectServiceInterface.findProjectById(id);
			int i = integer;
			userClass = userServiceInterFace.getUserById(i);
			
			userClass.getProjects().add(project);

			userServiceInterFace.addNewUser(userClass);
			projectServiceInterface.addNewProject(project);
		}

		List<Project> listOfProjects = projectServiceInterface.findAllProject();
		model.addAttribute("projectList", listOfProjects);

		return "/admin/ProjectList";
	} catch (Exception e) {
		// TODO: handle exception
		return "blank";
	}
		
	}

	@RequestMapping("/getProject")
	public String allProjectList(Model model ,Authentication authentication) {
		
		try {
			String username = authentication.getName();
			model.addAttribute("userDetail", username);
			Object authority = authentication.getAuthorities();
			String checkAuthority = authority.toString();
			model.addAttribute("userRole",checkAuthority);
			
			List<Project> getProjectList = projectServiceInterface.findAllProject();

			model.addAttribute("projectList", getProjectList);

			return "/admin/ProjectList";

		}catch (Exception e) {
			return "blank";
			// TODO: handle exception
		}
		
		
	}
	
	@RequestMapping("/allProjectForLeader")
	public String getProjectForLeader(Model model ,Authentication authentication) {
		try {
			String username = authentication.getName();
			model.addAttribute("userDetail", username);
			Object authority = authentication.getAuthorities();
			String checkAuthority = authority.toString();
			model.addAttribute("userRole",checkAuthority);
			
			
			UserClass userClassdetail = userServiceInterFace.getUserByUsername(username);

			List<Project> listOfProject = projectServiceInterface.getProjectListByLeaderId(userClassdetail.getId());
			model.addAttribute("projectList", listOfProject);
			return"/leader/ProjectListForLeader";
		} catch (Exception e) {
			// TODO: handle exception
			return "blank";
		} 
		
		
	}

	
	
	
	@RequestMapping("/projectDetail")
	public String fullprojectDetail(@RequestParam("projectId") int id,Authentication authentication , Model model) {
		try {
			String username = authentication.getName();
			model.addAttribute("userDetail", username);
			Object authority = authentication.getAuthorities();
			String checkAuthority = authority.toString();
			model.addAttribute("userRole",checkAuthority);
			
			Project project = projectServiceInterface.findProjectById(id);

			model.addAttribute("titleofProject", project.getTitle());

			model.addAttribute("descProject", project.getDescription());

			model.addAttribute("startDate", project.getStart_date());

			model.addAttribute("endDate", project.getEnd_date());

			model.addAttribute("leaderUSerName", project.getLeaderId().getUser_Name());

			model.addAttribute("projectId", project.getProject_id());

			List<UserClass> userClass = project.getUserClasses();
			model.addAttribute("listOfEmployee", userClass);

			return "/admin/ProjectDetail";
		} catch (Exception e) {
			// TODO: handle exception
			return "blank";
				
		}
		
	
	}

	
	@RequestMapping("/editInProjectForLeader")
	public String projectDetailForLeader(@RequestParam("projectId") int id,Authentication authentication , Model model) {
	
		try {
			String username = authentication.getName();
			model.addAttribute("userDetail", username);
			Object authority = authentication.getAuthorities();
			String checkAuthority = authority.toString();
			model.addAttribute("userRole",checkAuthority);
			
			Project project = projectServiceInterface.findProjectById(id);

			model.addAttribute("titleofProject", project.getTitle());

			model.addAttribute("descProject", project.getDescription());

			model.addAttribute("startDate", project.getStart_date());

			model.addAttribute("endDate", project.getEnd_date());

			model.addAttribute("leaderUSerName", project.getLeaderId().getUser_Name());

			model.addAttribute("projectId", project.getProject_id());

			List<UserClass> userClass = project.getUserClasses();
			model.addAttribute("listOfEmployee", userClass);

			return "/leader/ProjectDetailsForLeader";
		} catch (Exception e) {
			// TODO: handle exception
			return "blank";
		}
		
		
	}
	
	
	
	@RequestMapping("/editInProject")
	public String updateProjectDetail(@RequestParam("projectId") int projectId,Authentication authentication , Model model, Project project) {
		
		try {

			String username = authentication.getName();
			model.addAttribute("userDetail", username);
			Object authority = authentication.getAuthorities();
			String checkAuthority = authority.toString();
			model.addAttribute("userRole",checkAuthority);
			
			project = projectServiceInterface.findProjectById(projectId);

			model.addAttribute("project", project);

			return "/admin/ProjectEdit";
		} catch (Exception e) {
			return "blank";
			// TODO: handle exception
		}
		
	}

	@RequestMapping(value = "/editProject", params = "cancel=cancel")
	public String cancel(Model model,Authentication authentication) {
		return allProjectList(model, authentication);
	}

	@RequestMapping(value = "/editProject", params = "submit=Save & Continue")
	public String submitProjectandContinue(Model model, @RequestParam("projectTitle") String title,
			@RequestParam("bodyOfProject") String desc, @RequestParam("startDate") Date startDate,
			@RequestParam("endDate") Date enddate, @RequestParam("projectId") int projectId,
			Authentication authentication) {
		
		try {
			
			String username = authentication.getName();
			model.addAttribute("userDetail", username);
			Object authority = authentication.getAuthorities();
			String checkAuthority = authority.toString();
			model.addAttribute("userRole",checkAuthority);
			
			projectServiceInterface.updateDetail(projectId, title, desc, startDate, enddate);
			return editLeader(model, projectId, authentication);
		} catch (Exception e) {
			return "blank";
			// TODO: handle exception
		}
	
		
	}

	@RequestMapping(value = "/editProject", params = "submit=Save & Exit")
	public String submitProjectandExit(Model model, @RequestParam("projectTitle") String title,
			@RequestParam("bodyOfProject") String desc, @RequestParam("startDate") Date startDate,
			@RequestParam("endDate") Date enddate, @RequestParam("projectId") int id,
			Authentication authentication) {
	
		try {
			String username = authentication.getName();
			model.addAttribute("userDetail", username);
			Object authority = authentication.getAuthorities();
			String checkAuthority = authority.toString();
			model.addAttribute("userRole",checkAuthority);
			
			projectServiceInterface.updateDetail(id, title, desc, startDate, enddate);

			return allProjectList(model, authentication);	
		} catch (Exception e) {
			// TODO: handle exception
			return "blank";
		}
		
		
	}

	@RequestMapping("/editLeader")
	public String editLeader(Model model, @RequestParam("projectId") int projectId,Authentication authentication) {
		
		try {
			String username = authentication.getName();
			model.addAttribute("userDetail", username);
			Object authority = authentication.getAuthorities();
			String checkAuthority = authority.toString();
			model.addAttribute("userRole",checkAuthority);
			
			List<UserClass> userClasses = userServiceInterFace.getAllLeader();

			model.addAttribute("listOfLeader", userClasses);

			model.addAttribute("projectId", projectId);
			return "/admin/EditLeader";
		} catch (Exception e) {
			// TODO: handle exception
			return "blank";
		}
		
		
	}

	@RequestMapping(value = "/changeLeader", params = "cancel=cancel")
	public String cancelOrExit(Model model ,Authentication authentication) {
		return cancel(model, authentication);

	}

	@RequestMapping(value = "/changeLeader", params = "submit=Save & Exit")
	public String updatedLeader(Model model, UserClass userClass, 
			@RequestParam("selectedEmployee") int leaderId,
			@RequestParam("projectId") int projectId,
			Authentication authentication) {
		
		try {
			String username = authentication.getName();
			model.addAttribute("userDetail", username);
			Object authority = authentication.getAuthorities();
			String checkAuthority = authority.toString();
			model.addAttribute("userRole",checkAuthority);
			
			userClass = userServiceInterFace.getUserById(leaderId);
			projectServiceInterface.updateLeader(userClass.getId(), projectId);

			return allProjectList(model, authentication);
		} catch (Exception e) {
			return "blank";			// TODO: handle exception
		}
		
	

	}

	@RequestMapping(value = "/changeLeader", params = "submit=Save & Continue")
	public String updateLeaderAndNext(Model model, @RequestParam("selectedEmployee") int leaderId,
			@RequestParam("projectId") int projectId,Authentication authentication,
			UserClass userClass, Project project) {
		try {

			String username = authentication.getName();
			model.addAttribute("userDetail", username);
			Object authority = authentication.getAuthorities();
			String checkAuthority = authority.toString();
			model.addAttribute("userRole",checkAuthority);
			
			userClass = userServiceInterFace.getUserById(leaderId);
			return projectUpdateEmployeeList(model, projectId, authentication, userClass, project);
		} catch (Exception e) {
			return "blank";
			// TODO: handle exception
		}
		
	}

	@RequestMapping("/updateEmployeeList")
	public String projectUpdateEmployeeList(Model model, @RequestParam("projectId") int projectId,
			Authentication authentication ,UserClass userClass,
			Project project) {
		try {
			String username = authentication.getName();
			model.addAttribute("userDetail", username);
			Object authority = authentication.getAuthorities();
			String checkAuthority = authority.toString();
			model.addAttribute("userRole",checkAuthority);
			
			project = projectServiceInterface.findProjectById(projectId);

			List<UserClass> listOfUser = project.getUserClasses();

			model.addAttribute("listOfWorkingEmployee", listOfUser);

			List<UserClass> getAllUser = userServiceInterFace.getAllUsersWithUserRole();

			getAllUser.removeAll(listOfUser);

			model.addAttribute("listOfRemainEmployee", getAllUser);

			model.addAttribute("projectId", projectId);

			return "/admin/ProjectUpdateEmployees";
		} catch (Exception e) {
			return "blank";
			// TODO: handle exception
		}
		
		
	
	}
	
	@RequestMapping("/projectUpdateEmployeesForLeader")
	public String projectUpdateEmployeeListForLeader(Model model, @RequestParam("projectId") int projectId,
			Authentication authentication ,UserClass userClass,
			Project project) {
	
		try {

			String username = authentication.getName();
			model.addAttribute("userDetail", username);
			Object authority = authentication.getAuthorities();
			String checkAuthority = authority.toString();
			model.addAttribute("userRole",checkAuthority);
			model.addAttribute("userDetail", username);

			project = projectServiceInterface.findProjectById(projectId);

			List<UserClass> listOfUser = project.getUserClasses();

			model.addAttribute("listOfWorkingEmployee", listOfUser);

			List<UserClass> getAllUser = userServiceInterFace.getAllUsersWithUserRole();

			getAllUser.removeAll(listOfUser);

			model.addAttribute("listOfRemainEmployee", getAllUser);

			model.addAttribute("projectId", projectId);

			return "/leader/ProjectUpdateEmployeesForLeader";
		} catch (Exception e) {
			return "blank";

			// TODO: handle exception
		}
		
	}

	@RequestMapping( value = "/deleteEmployeeFromProject" , method = RequestMethod.POST)
	public String deleteListOfEmployeesInProject(Model model, @RequestParam("employee") int employeeId,
			@RequestParam("id") int projectId, UserClass userClass, Project project,
			Authentication authentication) {
		
		try {
			String username = authentication.getName();

			Object authority = authentication.getAuthorities();
			String checkAuthority = authority.toString();
			model.addAttribute("userRole",checkAuthority);
			model.addAttribute("userDetail", username);

			
			UserClass user = userServiceInterFace.getUserById(employeeId);
			Project project2 = projectServiceInterface.findProjectById(projectId);

			project2.getUserClasses().remove(user);
			user.getProjects().remove(project2);

			userServiceInterFace.removeUserFromProject(user);
			projectServiceInterface.removeProjectFromUser(project2);

			project = projectServiceInterface.findProjectById(projectId);

			List<UserClass> listOfUser = project.getUserClasses();

			model.addAttribute("listOfWorkingEmployee", listOfUser);

			List<UserClass> getAllUser = userServiceInterFace.getAllUsersWithUserRole();

			getAllUser.removeAll(listOfUser);

			model.addAttribute("listOfRemainEmployee", getAllUser);

			model.addAttribute("projectId", projectId);
			
			if(checkAuthority.equals("[ROLE_ADMIN]"))
				return "/admin/ProjectUpdateEmployees";
			else
				return "/leader/ProjectUpdateEmployeesForLeader";
		} catch (Exception e) {
			return "blank";

			// TODO: handle exception
		}
		
		
	}
	
	@RequestMapping(value = "/addEmployeeToProject" , method = RequestMethod.POST )
	public String addEmployeeToProject(Model model, @RequestParam("employeeId") int employeeId,
			@RequestParam("projectId") int projectId, UserClass userClass, Project project ,
			Authentication authentication) {
		try {
			String username = authentication.getName();

			Object authority = authentication.getAuthorities();
			String checkAuthority = authority.toString();
			
			model.addAttribute("userRole",checkAuthority);
			model.addAttribute("userDetail", username);
			
			UserClass user = userServiceInterFace.getUserById(employeeId);
			Project project2 = projectServiceInterface.findProjectById(projectId);

			project2.getUserClasses().add(user);
			user.getProjects().add(project2);

			userServiceInterFace.addNewUser(user);
			projectServiceInterface.addNewProject(project2);

			project = projectServiceInterface.findProjectById(projectId);

			List<UserClass> listOfUser = project.getUserClasses();
			model.addAttribute("listOfWorkingEmployee", listOfUser);

			List<UserClass> getAllUser = userServiceInterFace.getAllUsersWithUserRole();
			getAllUser.removeAll(listOfUser);
			model.addAttribute("listOfRemainEmployee", getAllUser);

			model.addAttribute("projectId", projectId);
			
			if(checkAuthority.equals("[ROLE_ADMIN]") )
				return "/admin/ProjectUpdateEmployees";
			else
				return "/leader/ProjectUpdateEmployeesForLeader";
		
		} catch (Exception e) {
				return "blank";
		}
			// TODO: handle exception
		}
		
}
