package com.tissuebank.service;

import java.util.List;

import com.tissuebank.model.global.views.Role;

public interface RoleService 
{
	List<Role> getAllRoles(); 
	Role getRoleById(int role_id); 
}
