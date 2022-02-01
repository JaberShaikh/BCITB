package com.tissuebank.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tissuebank.dao.UserDao;
import com.tissuebank.model.global.views.User;
import com.tissuebank.model.global.views.UserRole;
import com.tissuebank.service.UserService;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

 @Autowired
 private UserDao userDao;
 
 @Override
 public User findUserByUsername(String username) {
	 return userDao.findUserByUsername(username);
 }

@Override
public void updateUserProfile(User user, int primaryRoleId) {
	 userDao.updateUserProfile(user, primaryRoleId);
}

@Override
public User findUserById(int user_id) {
	return userDao.findUserById(user_id);
}

@Override
public List<UserRole> getAllUserRoles(User user) {
	return userDao.getAllUserRoles(user);
}

@Override
public List<User> getAllUsers() {
	return userDao.getAllUsers();
}

}