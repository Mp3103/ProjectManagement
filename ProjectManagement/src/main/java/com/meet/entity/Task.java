package com.meet.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
@Table(name ="TASK")
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="TASK_ID")
	private int task_id;
	
	@Column(name = "TASK_TITLE")
	private String task_title;
	
	@Column(name ="TASK_DESCRIPTION")
	private String atask_desc;
	
	@Column(name ="TASK_START_DATE")
	private Date start_Date; 
	
	@Column(name ="TASK_END_DATE")
	private Date end_Date;
	
	@Column(name = "TASK_STATUS")
	private int task_Status;

	
	@ManyToOne 
	@JoinColumn(name = "TASK_USER_ID")
	private UserClass task_user_id ;
	
	@ManyToOne 
	@JoinColumn(name = "TASK_PROJECT_ID")
	private Project task_project_id;

	
	public Task() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public Task(int task_id, String task_title, String atask_desc, Date start_Date, Date end_Date, int task_Status,
			UserClass task_user_id, Project task_project_id) {
		super();
		this.task_id = task_id;
		this.task_title = task_title;
		this.atask_desc = atask_desc;
		this.start_Date = start_Date;
		this.end_Date = end_Date;
		this.task_Status = task_Status;
		this.task_user_id = task_user_id;
		this.task_project_id = task_project_id;
	}


	public int getTask_Status() {
		return task_Status;
	}


	public void setTask_Status(int task_Status) {
		this.task_Status = task_Status;
	}


	public int getTask_id() {
		return task_id;
	}

	public void setTask_id(int task_id) {
		this.task_id = task_id;
	}

	public String getTask_title() {
		return task_title;
	}

	public void setTask_title(String task_title) {
		this.task_title = task_title;
	}

	public String getAtask_desc() {
		return atask_desc;
	}

	public void setAtask_desc(String atask_desc) {
		this.atask_desc = atask_desc;
	}

	public Date getStart_Date() {
		return start_Date;
	}

	public void setStart_Date(Date start_Date) {
		this.start_Date = start_Date;
	}

	public Date getEnd_Date() {
		return end_Date;
	}

	public void setEnd_Date(Date end_Date) {
		this.end_Date = end_Date;
	}

	public UserClass getTask_user_id() {
		return task_user_id;
	}

	public void setTask_user_id(UserClass task_user_id) {
		this.task_user_id = task_user_id;
	}

	public Project getTask_project_id() {
		return task_project_id;
	}

	public void setTask_project_id(Project task_project_id) {
		this.task_project_id = task_project_id;
	}

	

	
}
