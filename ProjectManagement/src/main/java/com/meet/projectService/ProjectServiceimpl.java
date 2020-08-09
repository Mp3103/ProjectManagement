package com.meet.projectService;

import java.sql.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meet.entity.Project;
import com.meet.entity.UserClass;
import com.meet.projectHandler.ProjectInterface;
import com.meet.userService.UserServiceInterface;

@Service
public class ProjectServiceimpl implements ProjectServiceInterface {

	private ProjectInterface projectInterface;

	private EntityManager etityManager;

	private UserServiceInterface userServicInterface;

	@Autowired
	public ProjectServiceimpl(ProjectInterface projectInterface, EntityManager etityManager,
			UserServiceInterface userServicInterface) {
		super();
		this.projectInterface = projectInterface;
		this.etityManager = etityManager;
		this.userServicInterface = userServicInterface;
	}

	@Override
	public Project addNewProject(Project project) {
		return projectInterface.save(project);
	}
	
	@Override
	public void removeProjectFromUser(Project project) {
		projectInterface.save(project);
	}

	@Override
	public Project findProject(String projectName) {

		Session session = etityManager.unwrap(Session.class);

		Criteria criteria = session.createCriteria(Project.class);

		Project userClass = (Project) criteria.add(Restrictions.eq("title", projectName)).uniqueResult();

		return userClass;
	}

	@Override
	public Project findProjectById(int id) {

		return projectInterface.getOne(id);
	}

	@Override
	@Transactional
	public List<Project> listOfAllProject(int id) {

		Session session = etityManager.unwrap(Session.class);

		TypedQuery<Project> projects = session.createQuery(
				"select u.id ,u.User_Name  from UserClass AS u  LEFT OUTER JOIN Project AS p on p.leaderId = u.id WHERE p.poject_id ="
						+ id,
				Project.class);

		List<Project> projects2 = projects.getResultList();
		return projects2;
	}
	
	@Override
	public List<Project> lastFiveProjects(){
		Session session = etityManager.unwrap(Session.class);
		Query<Project> query=session.createQuery("from Project order by project_id desc")
					.setMaxResults(5);
		List<Project> fiveProjects=query.getResultList();
		return fiveProjects;
	}
	
	@Override
	public List<Project> getRunningProjects() {
		Session session = etityManager.unwrap(Session.class);

		Query<Project> query = session.createQuery("From Project where project_end_date > SYSDATE()");

		List<Project> projectByLeaderId = query.list();
		return projectByLeaderId;
	}

	@Override
	public List<Project> findAllProject() {
		return projectInterface.findAll();
	}

	@Override
	@Transactional
	public int updateDetail(int id ,String title, String description, Date startdate, Date endate) {

		Session session = etityManager.unwrap(Session.class);

		Query query = session.createQuery("update Project set title ='"+ title +"',description='" + description+ "',start_date='" + startdate + "' , end_date = '"+endate+"' where id = '" + id + "'");
		
		int updated = query.executeUpdate();
		
		return updated;
	}

	@Override
	@Transactional
	public int updateLeader(int leaderId , int projectId) {
		

		Session session = etityManager.unwrap(Session.class);

		Query query = session.createQuery("update Project set leaderId = '"+leaderId+"' where id = '" + projectId + "'");
		
		int updated = query.executeUpdate();
		
		
		return updated;
	}
	
	@Override
	public List<Project> getProjectListByLeaderId(int leaderId) {
		Session session = etityManager.unwrap(Session.class);

		Query<Project> query = session.createQuery("From Project where leaderId =" + leaderId);

		List<Project> projectByLeaderId = query.list();
		return projectByLeaderId;
	}
	
	@Override
	public List<Project> lastFiveProjectListByLeaderId(int leaderId) {
		Session session = etityManager.unwrap(Session.class);

		Query<Project> query = session.createQuery("From Project where leaderId =" + leaderId).setMaxResults(5);

		List<Project> projectByLeaderId = query.list();
		return projectByLeaderId;
	}
	
	@Override
	public List<Project> getProjectListWithEndDate(int id){
		Session session = etityManager.unwrap(Session.class);

		Query<Project> query = session.createQuery("From Project where sysdate()> project_end_date and leaderId =" + id);

		List<Project> projectByLeaderId = query.list();
		return projectByLeaderId;
	}


}
