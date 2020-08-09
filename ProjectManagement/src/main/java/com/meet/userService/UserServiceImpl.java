package com.meet.userService;

import org.springframework.data.domain.Pageable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.meet.entity.UserClass;
import com.meet.userInterface.UserClassInterface;
import com.meet.userInterface.UserInterface;

@Service
public class UserServiceImpl implements UserServiceInterface {

	private UserInterface userInterface;

	private EntityManager entityManager;
	
	private UserClassInterface userClassInterface;

	@Autowired
	public UserServiceImpl(UserInterface userInterface, EntityManager entityManager,UserClassInterface userClassInterface) {
		super();
		this.userInterface = userInterface;
		this.entityManager = entityManager;
		this.userClassInterface=userClassInterface;
	}

	@Override
	@Transactional
	public List<UserClass> getAllUser() {

		Session session = entityManager.unwrap(Session.class);

		Query<UserClass> query = session
				.createQuery("from UserClass  WHERE User_role LIKE ('ROLE_USER') OR User_role LIKE ('ROLE_LEADER') "
						+ "ORDER BY id DESC");
		List<UserClass> queryDetails = query.list();

		return queryDetails;

	}
	

	@Override
	public UserClass addNewUser(UserClass userClass) {

		return userInterface.save(userClass);
	}
	
	@Override
	public void removeUserFromProject(UserClass userClass) {
		userInterface.save(userClass);
	}
	

	@Override
	public UserClass getUserById(int id) {
		return userInterface.getOne(id);
	}

	@Override
	@Transactional
	public int updateEmployee(int id, String fName, String lname, String userName,String role, int blockorNot) {
		Session session = entityManager.unwrap(Session.class);

		Query query = session.createQuery("update UserClass set User_Name ='" + userName + "', User_First_Name='"
				+ fName + "',User_Last_name='" + lname +"',User_role='"+role+ "',Enabled='" + blockorNot + "'  where id = '" + id + "'");
		int updated = query.executeUpdate();
		return updated;
	}
	
	@Override
	@Transactional
	public int updateEmployeeWithoutRole(int id, String fName, String lname, String userName, int blockorNot) {
		Session session = entityManager.unwrap(Session.class);

		Query query = session.createQuery("update UserClass set User_Name ='" + userName + "', User_First_Name='"
				+ fName + "',User_Last_name='" + lname + "',Enabled='" + blockorNot + "'  where id = '" + id + "'");
		int updated = query.executeUpdate();
		return updated;
	}

	@Override
	public Page<UserClass> findpaginated(int pageNo, int pageSize) {

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

		return this.userInterface.findAll(pageable);
	}

	@Override
	public List<UserClass> getUserId(int id) {
		Session session = entityManager.unwrap(Session.class);
		Query<UserClass> userDetail = session.createQuery("from UserClass where id=" + id);

		List<UserClass> employeeDetail = userDetail.list();
		return employeeDetail;
	}

	@Override
	public List<UserClass> getAllLeader() {

		Session session = entityManager.unwrap(Session.class);

		Query<UserClass> query = session
				.createQuery("from UserClass  WHERE User_role = 'ROLE_LEADER'  ORDER BY id DESC");
		List<UserClass> queryDetails = query.list();

		return queryDetails;
	}

	
	@Override
	public List<UserClass> getAllUsersWithUserRole() {

		Session session = entityManager.unwrap(Session.class);

		Query<UserClass> query = session
				.createQuery("from UserClass  WHERE User_role = 'ROLE_USER'  ORDER BY id DESC");
		List<UserClass> queryDetails = query.list();

		return queryDetails;
	}

	
	@Override
	public List<UserClass> getAllUserWthoutProject() {

		Session session = entityManager.unwrap(Session.class);

		System.out.println(session.createQuery("FROM user_project"));
		
		Query<UserClass> query = session.createQuery("from UserClass  where id NOT IN(select id from user_project)");
		List<UserClass> queryDetails = query.list();

		return queryDetails;
	}

	@Override
	public List<UserClass> getAllEmployee() {
		return userInterface.findAll();
	}

	@Override
	public Page<UserClass> listAll(int pageNumber) {
		Pageable pageable=PageRequest.of(pageNumber-1, 7);
		return userClassInterface.findAll(pageable);
	}
	
	@Override
	public UserClass getUserByUsername(String username) {
		
		Session session = entityManager.unwrap(Session.class);

		Criteria criteria = session.createCriteria(UserClass.class);
		 
		
		UserClass userDetail = (UserClass) criteria.add(Restrictions.eq("User_Name", username))
				.uniqueResult();		
		return userDetail;
	}
}
