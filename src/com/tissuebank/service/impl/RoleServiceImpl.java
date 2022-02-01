package com.tissuebank.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tissuebank.dao.RoleDao;
import com.tissuebank.model.global.views.Role;
import com.tissuebank.service.RoleService;

@Service("roleService")
@Transactional
public class RoleServiceImpl implements RoleService
{
	@Autowired
	private RoleDao roleDao;
	
	@Override
	public List<Role> getAllRoles() {
		return roleDao.getAllRoles();
	}

	@Override
	public Role getRoleById(int role_id) {
		return roleDao.getRoleById(role_id);
	}
	
}
