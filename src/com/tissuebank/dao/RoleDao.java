package com.tissuebank.dao;

import java.util.List;

import com.tissuebank.model.global.views.Role;

public interface RoleDao
{
	List<Role> getAllRoles(); 
	Role getRoleById(int role_id); 
}
