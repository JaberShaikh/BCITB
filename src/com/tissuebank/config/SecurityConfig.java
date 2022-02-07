package com.tissuebank.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;

import com.tissuebank.model.global.views.Role;
import com.tissuebank.service.RoleService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
 
 @SuppressWarnings("unused")
 @Autowired
 private DataSource dataSource;

 @Autowired
 private RoleService roleService;
 
 @Autowired
 @Qualifier("ldaploginService")
 LdapAuthoritiesPopulator ldapAuthoritiesPopulator;
 
 @Autowired
 public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
 auth.ldapAuthentication()
 .contextSource()
// THE BELOW URLs ARE NOT USED ANYMORE
//.url("ldaps://uk-chs-ledc03.qmcr.qmul.ac.uk:636")
//.managerDn("cn=srv-ldapedc01, ou=Service Accounts, dc=qmcr, dc=qmul, dc=ac, dc=uk") 
 .url("ldap://uk-chs-ldc01.qmcr.qmul.ac.uk:389")
 .managerDn("cn=srv-ldapedc01, ou=Service Accounts, dc=qmcr, dc=qmul, dc=ac, dc=uk") 
 .managerPassword("Oj3DrbW8kVrQ") 
 .and()
 .groupRoleAttribute("dc=qmcr,dc=qmul,dc=ac,dc=uk")
 .userSearchFilter("(sAMAccountName={0})")
 .userSearchBase("dc=qmcr,dc=qmul,dc=ac,dc=uk")
 .ldapAuthoritiesPopulator(ldapAuthoritiesPopulator);
 }
  
 @Override
 protected void configure(HttpSecurity http) throws Exception 
 {
	 String roleAllowed = "";
	 for (Role role: roleService.getAllRoles())
		 if (roleAllowed.isEmpty())
			 roleAllowed = "hasRole('" + role.getRole_acronym() + "')";
		 else
			 roleAllowed = roleAllowed + " or hasRole('" + role.getRole_acronym() + "')";
	  http
	 .authorizeRequests()
	 .antMatchers("/").permitAll()
	 .antMatchers("/select_department_locations").access(roleAllowed)
	 .and()
	 .formLogin()
	 .loginProcessingUrl("/login")
	 .loginPage("/login")
	 .failureUrl("/login?error")
	 .defaultSuccessUrl("/select_department_locations")
	 .usernameParameter("username")
	 .passwordParameter("password")
	 .and()
	 .exceptionHandling()
	 .accessDeniedPage("/accessDenied");
 }
 
}