package com.meet.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Columns;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "USERS")
public class UserClass {

	@Id
	@Column(name = "USER_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "USER_NAME")
	private String User_Name;
	@Column(name = "USER_PASSWORD")
	private String User_Password;
	@Column(name = "USER_FIRST_NAME")
	private String User_First_Name;
	@Column(name = "USER_LAST_NAME")
	private String User_Last_name;
	@Column(name = "USER_ROLE")
	private String User_role;
	@Column(name = "ENABLED")
	private int Enabled;

	@OneToMany(mappedBy = "task_user_id")
	private List<Task> ontask = new ArrayList<Task>();

	@ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinTable(name = "user_project", joinColumns = { @JoinColumn(name = "USER_ID") }, inverseJoinColumns = {
			@JoinColumn(name = "PROJECT_ID") })
	private List<Project> projects = new ArrayList<Project>();

	public UserClass() {
	}

	public UserClass(String user_Name, String user_Password, String user_First_Name, String user_Last_name,
			String user_role, int enabled) {
		super();
		User_Name = user_Name;
		User_Password = user_Password;
		User_First_Name = user_First_Name;
		User_Last_name = user_Last_name;
		User_role = user_role;
		Enabled = enabled;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public List<Task> getOntask() {
		return ontask;
	}

	public void setOntask(List<Task> ontask) {
		this.ontask = ontask;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUser_Name() {
		return User_Name;
	}

	public void setUser_Name(String user_Name) {
		User_Name = user_Name;
	}

	public String getUser_Password() {
		return User_Password;
	}

	public void setUser_Password(String user_Password) {
		User_Password = user_Password;
	}

	public String getUser_First_Name() {
		return User_First_Name;
	}

	public void setUser_First_Name(String user_First_Name) {
		User_First_Name = user_First_Name;
	}

	public String getUser_Last_name() {
		return User_Last_name;
	}

	public void setUser_Last_name(String user_Last_name) {
		User_Last_name = user_Last_name;
	}

	public String getUser_role() {
		return User_role;
	}

	public void setUser_role(String user_role) {
		User_role = user_role;
	}

	public int getEnabled() {
		return Enabled;
	}

	public void setEnabled(int enabled) {
		Enabled = enabled;
	}

}
