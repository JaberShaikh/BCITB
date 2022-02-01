package com.tissuebank.service;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;
import org.springframework.stereotype.Service;

import com.tissuebank.model.global.views.User;
import com.tissuebank.model.global.views.UserRole;
import com.tissuebank.service.UserService;

@Service("ldaploginService")
public class UserAuthPopulatorImpl implements LdapAuthoritiesPopulator {

 @Autowired
 private UserService userService;
 
 @Override
 public Collection<? extends GrantedAuthority> getGrantedAuthorities(DirContextOperations userData, String username) 
 {
	 Collection<GrantedAuthority> gas = new HashSet<GrantedAuthority>();
	 User user = null;
	 try {
		 user = userService.findUserByUsername(username);
	 } catch (Exception e) {
		 System.out.println("User Not Found");
		 e.printStackTrace();
	 }
	
	String primaryRole = "ROLE_NA";
	if(user!=null)
		for(UserRole userRole:user.getUserRoles())
			if(userRole.getIs_primary_role()==1)
				primaryRole=userRole.getRole().getRole_acronym();
	
	gas.add(new SimpleGrantedAuthority(primaryRole));
	return gas;
 }
}