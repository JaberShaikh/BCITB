package com.tissuebank.dao;

import java.util.List;

import com.tissuebank.model.global.views.User;
import com.tissuebank.model.global.views.UserRole;

public interface UserDao {
  User findUserByUsername(String username);
  User findUserById(int user_id);
  void updateUserProfile(User user,int primaryRoleId);
  List<User> getAllUsers();
  List<UserRole> getAllUserRoles(User user);
}