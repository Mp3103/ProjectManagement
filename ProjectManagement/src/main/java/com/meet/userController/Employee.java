package com.meet.userController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.meet.entity.Task;
import com.meet.entity.UserClass;
import com.meet.taskService.TaskService;
import com.meet.userService.UserServiceInterface;

@Controller
public class Employee {

	private BCryptPasswordEncoder bCryptPasswordEncoder;

	private UserServiceInterface userServiceInterface;

	private TaskService taskService;

	@Autowired
	public Employee(BCryptPasswordEncoder bCryptPasswordEncoder, UserServiceInterface userServiceInterface,
			TaskService taskService) {
		super();
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.userServiceInterface = userServiceInterface;
		this.taskService = taskService;
	}

	@RequestMapping("/addEmployee")
	public String addEmployee(Model model, UserClass userClass, Authentication authentication) {
		try {
			String username = authentication.getName();
			model.addAttribute("userDetail", username);
			Object authority = authentication.getAuthorities();
			String checkAuthority = authority.toString();
			model.addAttribute("userRole", checkAuthority);

			model.addAttribute("userClass", userClass);

			return "/admin/CreateEmployee";
		} catch (Exception e) {
			return "blank";
			// TODO: handle exception
		}

	}

	@RequestMapping(value = "/addNewEmployee", method = RequestMethod.POST)
	public String createdCustomer(@ModelAttribute("userClass") UserClass userClass, Model model,
			Authentication authentication) {
		try {
			String username = authentication.getName();
			model.addAttribute("userDetail", username);
			Object authority = authentication.getAuthorities();
			String checkAuthority = authority.toString();
			model.addAttribute("userRole", checkAuthority);

			String newpassword = bCryptPasswordEncoder.encode(userClass.getUser_Password());

			userClass.setUser_Password(newpassword);
			userClass.setUser_role("ROLE_USER");
			userClass.setEnabled(1);

			try {
				userServiceInterface.addNewUser(userClass);
				List<UserClass> usersList = userServiceInterface.getAllUser();
				model.addAttribute("userList", usersList);

				return employeeList(model, authentication);
			} catch (Exception e) {
				// TODO: handle exception
				List<UserClass> usersList = userServiceInterface.getAllUser();
				model.addAttribute("userList", usersList);
				return employeeList(model, authentication);
			}
		} catch (Exception e) {
			return "blank";
			// TODO: handle exception
		}

	}

	@RequestMapping("/listOfEmployee")
	public String employeeList(Model model, Authentication authentication) {
		try {
			return listByPage(model, authentication, 1);

		} catch (Exception e) {
			return "blank";
			// TODO: handle exception
		}

	}

	@RequestMapping("/listOfEmployee/page/{pageNumber}")
	public String listByPage(Model model, Authentication authentication, @PathVariable("pageNumber") int currentPage) {

		try {
			String username = authentication.getName();
			model.addAttribute("userDetail", username);
			Object authority = authentication.getAuthorities();
			String checkAuthority = authority.toString();
			model.addAttribute("userRole", checkAuthority);

			model.addAttribute("userDetail", username);

			Page<UserClass> page = userServiceInterface.listAll(currentPage);

			long totalItems = page.getTotalElements();
			int totalPages = page.getTotalPages();

			List<UserClass> users = page.getContent();

			model.addAttribute("currentPage", currentPage);
			model.addAttribute("totalItems", totalItems);
			model.addAttribute("totalPages", totalPages);
			model.addAttribute("userList", users);
			return "/admin/EmployeeList";

		} catch (Exception e) {
			return "blank";
			// TODO: handle exception
		}

	}

	// for leader list of employee which is not editable
	@RequestMapping("/listOfEmployeeForLeader")
	public String employeeListForLeader(Model model, Authentication authentication) {
		try {
			return listByPageForLeader(model, authentication, 1);
		} catch (Exception e) {
			return "blank";
			// TODO: handle exception
		}

	}

	@RequestMapping("/listOfEmployeeForLeader/page/{pageNumber}")
	public String listByPageForLeader(Model model, Authentication authentication,
			@PathVariable("pageNumber") int currentPage) {
		try {
			String username = authentication.getName();
			model.addAttribute("userDetail", username);
			Object authority = authentication.getAuthorities();
			String checkAuthority = authority.toString();
			model.addAttribute("userRole", checkAuthority);

			model.addAttribute("userDetail", username);

			Page<UserClass> page = userServiceInterface.listAll(currentPage);

			long totalItems = page.getTotalElements();
			int totalPages = page.getTotalPages();

			List<UserClass> users = page.getContent();

			model.addAttribute("currentPage", currentPage);
			model.addAttribute("totalItems", totalItems);
			model.addAttribute("totalPages", totalPages);
			model.addAttribute("userList", users);

			return "/leader/EmployeeListForLeader";

		} catch (Exception e) {
			return "blank";
			// TODO: handle exception
		}

	}

	@RequestMapping("/listOfEmployeeForUser")
	public String employeeListForUser(Model model, Authentication authentication) {
		try {
			return listByPageForUser(model, authentication, 1);

		} catch (Exception e) {
			return "blank";
			// TODO: handle exception
		}
	}

	@RequestMapping("/listOfEmployeeForUser/page/{pageNumber}")
	public String listByPageForUser(Model model, Authentication authentication,
			@PathVariable("pageNumber") int currentPage) {

		try {
			String username = authentication.getName();
			model.addAttribute("userDetail", username);
			Object authority = authentication.getAuthorities();
			String checkAuthority = authority.toString();
			model.addAttribute("userRole", checkAuthority);

			model.addAttribute("userDetail", username);

			Page<UserClass> page = userServiceInterface.listAll(currentPage);

			long totalItems = page.getTotalElements();
			int totalPages = page.getTotalPages();

			List<UserClass> users = page.getContent();

			model.addAttribute("currentPage", currentPage);
			model.addAttribute("totalItems", totalItems);
			model.addAttribute("totalPages", totalPages);
			model.addAttribute("userList", users);

			return "/user/EmployeeListForUser";
		} catch (Exception e) {
			return "blank";
			// TODO: handle exception
		}

	}

	@RequestMapping("/editEmloyeeDetail")
	public String employeeDetail(@RequestParam("employee") int id, Authentication authentication, Model model) {
		try {
			String username = authentication.getName();
			model.addAttribute("userDetail", username);
			Object authority = authentication.getAuthorities();
			String checkAuthority = authority.toString();
			model.addAttribute("userRole", checkAuthority);

			UserClass employeeById = userServiceInterface.getUserById(id);

			model.addAttribute("employeeDetail", employeeById);

			return "/admin/EditEmployeeDetail";
		} catch (Exception e) {
			return "blank";
			// TODO: handle exception
		}

	}

	@RequestMapping("/editDetailOfEnployee")
	public String updateEmployee(@RequestParam("id") int id, @RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName, @RequestParam("userName") String userNameForEmployee,
			@RequestParam("password") String passWord, @RequestParam("role") String role,
			@RequestParam("employeeRole") String employeeRole, @RequestParam("selectEmployeeStatus") int enabled,
			Model model, UserClass userClass, Authentication authentication) {
		
		try {
			String username = authentication.getName();
			model.addAttribute("userDetail", username);
			Object authority = authentication.getAuthorities();
			String checkAuthority = authority.toString();
			model.addAttribute("userRole", checkAuthority);

			if (checkAuthority.equals("ROLE_ADMIN")) {
				return listByPage(model, authentication, 1);
			}

			List<Task> employeeTask = taskService.pendingTaskList(id);

			if (employeeTask.size() == 0) {

				userServiceInterface.updateEmployee(id, firstName, lastName, userNameForEmployee, employeeRole, enabled);

				List<UserClass> usersList = userServiceInterface.getAllUser();

				model.addAttribute("userList", usersList);
				return listByPage(model, authentication, 1);

			} else {
			
				userServiceInterface.updateEmployeeWithoutRole(id, firstName, lastName, userNameForEmployee, enabled);

				List<UserClass> usersList = userServiceInterface.getAllUser();

				model.addAttribute("userList", usersList);
				return listByPage(model, authentication, 1);
			}
		}catch (Exception e) {
			// TODO: handle exception
			return "blank";
		}
			
			
	
	}
}
