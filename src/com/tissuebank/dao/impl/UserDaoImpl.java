package com.tissuebank.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tissuebank.dao.UserDao;
import com.tissuebank.model.global.views.User;
import com.tissuebank.model.global.views.UserRole;

@Transactional
@Repository("userDao")
public class UserDaoImpl implements UserDao {

 @Autowired
 private SessionFactory sessionFactory;

 @Override
 public User findUserByUsername(String username) 
 {
	 return (User) sessionFactory.getCurrentSession().createQuery(
			 "FROM User u where upper(u.username) = '" + username.toUpperCase() + "' " +
			 "AND u.active_user IS NOT NULL AND upper(u.active_user) = 'YES'").uniqueResult(); 
 }

 @SuppressWarnings("unchecked")
 @Override
 public List<UserRole> getAllUserRoles(User user) 
 {
	 return sessionFactory.getCurrentSession().createQuery("FROM UserRole u where u.user_id=" + user.getUser_id()).list(); 
 }
 
 @Override
 public void updateUserProfile(User user,int primaryRoleId) 
 {
	 
 	 sessionFactory.getCurrentSession().saveOrUpdate(user);
 	 
 	 int updatePrimaryRoleId=0;
 	 for(UserRole ur:getAllUserRoles(user)) 
 	 {
 		 if(ur.getRole_id()==primaryRoleId)
 			 updatePrimaryRoleId=1;
 		 else
 			 updatePrimaryRoleId=0;
 		 
 		 sessionFactory.getCurrentSession().createSQLQuery("UPDATE TB_USERS_ROLES ur SET ur.is_primary_role = " 
 			 + updatePrimaryRoleId + " WHERE ur.role_id = " + ur.getRole_id() +
 			 " AND ur.user_id = " + user.getUser_id()).executeUpdate();
 	 }
 	 
 }

@Override
public User findUserById(int user_id) {
	 return (User) sessionFactory.getCurrentSession().createQuery("FROM User u where u.user_id = '" + user_id + "'").uniqueResult(); 
}

@SuppressWarnings("unchecked")
@Override
public List<User> getAllUsers() {
	 return sessionFactory.getCurrentSession().createQuery("FROM User").list(); 
}

}