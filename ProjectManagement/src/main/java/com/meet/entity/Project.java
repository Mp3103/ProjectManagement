package com.meet.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Project")
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "POJECT_ID")
	private int project_id;
	@Column(name = "PROJECT_TITLE")
	private String title;
	@Column(name = "PROJECT_DESCRIPTION")
	private String description;
	@Column(name = "PROJECT_START_DATE")
	private Date start_date;
	@Column(name = "PROJECT_END_DATE")
	private Date end_date;
	@OneToOne
	@JoinColumn(name = "PROJECT_LEADER_ID")
	private UserClass leaderId;

	@ManyToMany(mappedBy = "projects", cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	private List<UserClass> userClasses = new ArrayList<UserClass>();

	@OneToMany(mappedBy = "task_project_id")
	private List<Task> taskList = new ArrayList<>();

	public Project() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Project(int project_id, String title, String description, Date start_date, Date end_date,
			UserClass leaderId) {
		super();
		this.project_id = project_id;
		this.title = title;
		this.description = description;
		this.start_date = start_date;
		this.end_date = end_date;
		this.leaderId = leaderId;
	}

	public List<UserClass> getUserClasses() {
		return userClasses;
	}

	public void setUserClasses(List<UserClass> userClasses) {
		this.userClasses = userClasses;
	}

	public UserClass getLeaderId() {
		return leaderId;
	}

	public void setLeaderId(UserClass leaderId) {
		this.leaderId = leaderId;
	}

	public int getProject_id() {
		return project_id;
	}

	public void setProject_id(int project_id) {
		this.project_id = project_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	public List<Task> getTaskList() {
		return taskList;
	}

	public void setTaskList(List<Task> taskList) {
		this.taskList = taskList;
	}

}
