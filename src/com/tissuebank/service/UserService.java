package com.tissuebank.service;

import java.util.List;

import com.tissuebank.model.global.views.User;
import com.tissuebank.model.global.views.UserRole;

public interface UserService {
    User findUserByUsername(String username);
    User findUserById(int user_id);
	void updateUserProfile(User user,int primaryRoleId);
    List<UserRole> getAllUserRoles(User user);
    List<User> getAllUsers();
}