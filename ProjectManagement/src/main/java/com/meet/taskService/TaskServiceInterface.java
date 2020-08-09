package com.meet.taskService;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import com.meet.entity.Project;
import com.meet.entity.Task;
import com.meet.entity.UserClass;

public interface TaskServiceInterface {

	public Task addNewTask(Task task);
	
	public List<Task> getAllTask();
	
	public Task taskDetail(int id);
	
	public int updateInATask(String title , String description , Date startDate , Date endDate , int taskID);
	
	public Task updateTask(Task projectTask);
	
	public List<Task> userTaskDetail(int userID);
	
	public List<Task> pendingTaskList(int userID);
	
	public List<Task> completedTaskList(int userID);

	public Page<Task> listAll(int pageNumber);

	public List<Task> totalTasksForEmployee(int userID);

	public List<Task> lastFivePendingTasks(int userID);

	public List<Task> taskListByProjectId(int projectId);

	public List<Task> listOfPendingTaskbyProjectIdForLeader(int projectId);

	public List<Task> listOfCompletedTaskbyProjectIdForLeader(int projectId);
	
}
