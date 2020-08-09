package com.meet.taskService;

import java.sql.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.meet.entity.Project;
import com.meet.entity.Task;
import com.meet.entity.UserClass;
import com.meet.taskInterface.TaskInterface;

@Service
public class TaskService implements TaskServiceInterface {

	private TaskInterface taskInterface;

	private EntityManager entityManager;

	@Autowired
	public TaskService(TaskInterface taskInterface, EntityManager entityManager) {
		super();
		this.taskInterface = taskInterface;
		this.entityManager = entityManager;
	}

	@Override
	public Task addNewTask(Task task) {
		return taskInterface.save(task);
	}

	@Override
	public List<Task> getAllTask() {
		// TODO Auto-generated method stub
		return taskInterface.findAll();
	}

	@Override
	public Task taskDetail(int id) {
		// TODO Auto-generated method stub
		return taskInterface.getOne(id);
	}

	@Override
	@Transactional
	public int updateInATask( String title, String description, Date startDate, Date endDate , int taskID) {
		Session session = entityManager.unwrap(Session.class);

		Query query = session.createQuery("update ProjectTask set task_title ='" + title + "', atask_desc ='"+description+"', start_Date= '"+startDate+"',end_Date='"+endDate+"'where task_id = '"+taskID+"'");
		int updated = query.executeUpdate();

		return updated;
	}
	
	@Override
	@Transactional
	public List<Task> taskListByProjectId(int projectId) {
		Session session = entityManager.unwrap(Session.class);

		Query<Task> query = session.createQuery("From Task where task_project_id="+projectId);
		List<Task> taskListOfEmployee  = query.list();
		
		return taskListOfEmployee;
	}
	
	@Override
	@Transactional
	public List<Task> listOfPendingTaskbyProjectIdForLeader(int projectId) {
		Session session = entityManager.unwrap(Session.class);

		Query<Task> query = session.createQuery("From Task where task_project_id='"+projectId+"' and task_Status=0");
		List<Task> taskListOfEmployee  = query.list();
		
		return taskListOfEmployee;
	}
	
	@Override
	@Transactional
	public List<Task> listOfCompletedTaskbyProjectIdForLeader(int projectId) {
		Session session = entityManager.unwrap(Session.class);

		Query<Task> query = session.createQuery("From Task where task_project_id='"+projectId+"' and (task_Status=1 or task_Status=2)");
		List<Task> taskListOfEmployee  = query.list();
		
		return taskListOfEmployee;
	}

	@Override
	public Task updateTask(Task projectTask) {

		return taskInterface.save(projectTask);
	}

	@Override
	@Transactional
	public List<Task> userTaskDetail(int userID) {
		
		Session session = entityManager.unwrap(Session.class);

		Query<Task> taskList = session.createQuery("from Task where task_user_id="+userID);
		
		List<Task> taskListOfEmployee  = taskList.list();
		
		return taskListOfEmployee;
	}

	//this is for employee
	@Override
	@Transactional
	public List<Task> pendingTaskList(int userID) {
		Session session = entityManager.unwrap(Session.class);

		Query<Task> taskStartStatus = session.createQuery("from Task where task_user_id = '"+userID+"' and task_Status = 0");
		
		List<Task> taskStatus  = taskStartStatus.list();
		
		return taskStatus;
	}

	
	//this is for employee
	@Override
	@Transactional
	public List<Task> completedTaskList(int userID) {
		
		Session session = entityManager.unwrap(Session.class);

		Query<Task> taskWithNoStatus = session.createQuery("from Task where task_user_id ='"+userID+"' and (task_Status=1 or task_Status=2)");
		
		List<Task> taskWithoutStatus = taskWithNoStatus.list();
		return taskWithoutStatus;
	}
	
	//this is for employee
	@Override
	@Transactional
	public List<Task> totalTasksForEmployee(int userID) {
		Session session = entityManager.unwrap(Session.class);

		Query<Task> query = session.createQuery("from Task where task_user_id = '"+userID+"'");
		
		List<Task> totalTasks  = query.list();
		
		return totalTasks;
	}
	
	//this is for employee
	@Override
	@Transactional
	public List<Task> lastFivePendingTasks(int userID) {
		Session session = entityManager.unwrap(Session.class);

		Query<Task> query = session.createQuery("from Task where task_user_id = '"+userID+"' and task_Status = 0").setMaxResults(5);
		
		List<Task> totalTasks  = query.list();
		
		return totalTasks;
	}
	
	@Override
	public Page<Task> listAll(int pageNumber) {
		Pageable pageable=PageRequest.of(pageNumber-1, 6);
		return taskInterface.findAll(pageable);
	}

}
