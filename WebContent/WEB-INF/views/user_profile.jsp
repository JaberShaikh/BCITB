<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<sec:csrfMetaTags />
</head>
<body>
	<div class="content py-5" style="background-color: #EAE8FF; color: #2E008B">
		<div class="container">
			<div class="row">
				 <div class="col-md-8 offset-md-2">
	                <span class="anchor" id="formUserEdit"></span>
	                <!-- form user info -->
	                 <div class="card card-outline-secondary">
	                     <div class="card-header" style="color: #FEFEFE">
	                         <h3 class="mb-0">User Profile</h3>
	                     </div>
	                     <div class="card-body">
							<form:form name="user_profile_form" action="save_user" method="get" modelAttribute="user"> 
							
							  <div class="form-group row row-bottom-margin">
			                    <label class="col-lg-3 col-form-label form-control-label">Primary Role</label>
			                    <div class="col-lg-9">
							      <form:select id="current_role_id" name="current_role_id" path="" multiple="multiple" 
							      		class="selectpicker col-xs-5" onchange="processUserPrimaryRoleSelection(this)">
									  <c:forEach items="${user.userRoles}" var = "user_role">
									  	<c:choose>
									  		<c:when test="${primaryRole.role_id eq user_role.role.role_id}">
										        <option selected="selected" value="${user_role.role.role_id}">${user_role.role.role_description}</option>
									  		</c:when>
									  		<c:otherwise>
										        <option value="${user_role.role.role_id}">${user_role.role.role_description}</option>
									  		</c:otherwise>
									  	</c:choose>
								      </c:forEach>
							      </form:select>
							    </div>
							  </div>
							
				              <div class="form-group row row-bottom-margin">
			                      <label class="col-lg-3 col-form-label form-control-label">Title</label>
			                      <div class="col-lg-9">
			                      	<form:input path="title" type="text" class="form-control col-xs-2" name="title" id="title" placeholder="Title" value="${user.title}"></form:input>
					                <label id="title-validation" style="color:red; display: none;"></label> 
			                      </div>
				              </div>
				              <div class="form-group row row-bottom-margin">
			                      <label class="col-lg-3 col-form-label form-control-label">First name</label>
			                      <div class="col-lg-9">
			                      	<form:input path="user_firstname" type="text" class="form-control col-xs-2" name="user_firstname" id="user_firstname" placeholder="Firstname" value="${user.user_firstname}"></form:input>
					                <label id="user_firstname-validation" style="color:red; display: none;"></label> 
			                      </div>
				              </div>
				              <div class="form-group row">
			                      <label class="col-lg-3 col-form-label form-control-label">Surname</label>
			                      <div class="col-lg-9">
			                      	<form:input path="user_surname" type="text" class="form-control col-xs-2" name="user_surname" id="user_surname" placeholder="Surname" value="${user.user_surname}"></form:input>
					                <label id="user_surname-validation" style="color:red; display: none;"></label> 
			                      </div>
				              </div>
				              
		                     <div class="form-group row row-bottom-margin">
		                         <label class="col-lg-3 col-form-label form-control-label"></label>
		                         <div class="col-lg-9">
				                    <form:button style="background-color: #2E008B; color: #FEFEFE" id="save_user_btn" class="btn btn-sm" type="button" 
				                    	name="save_user_btn" value="save_clicked" onclick="validateFormFields('',this,false)" data-toggle="modal" data-target="#waiting_modal">
				                    	<i class="fas fa-save"></i> Update Profile</form:button>
		                         </div>
		                      </div>
		                     
				              <br>
				              <div class="container" style="border:2px solid #E60E8B;">
	
				              	  <h5>For Office Use Only</h5>
				              	  
					              <div class="form-group row row-bottom-margin">
				                      <label class="col-lg-3 col-form-label form-control-label">Email</label>
				                      <div class="col-lg-9">
				                      	<input type="text" class="form-control col-xs-2" name="email" id="email" placeholder="Email" value="${user.email}" title="Email is always disabled." disabled="disabled"></input>
				                      	<span style="color:red" class="email-validation validation-error"></span>
				                      </div>
					              </div>
	
					              <div class="form-group row row-bottom-margin">
				                      <label class="col-lg-3 col-form-label form-control-label">Username</label>
				                      <div class="col-lg-9">
				                      	<input type="text" class="form-control col-xs-2" name="profile_username" id="profile_username" value="${user.username}" title="Username is always disabled." disabled="disabled"></input>
				                      </div>
					              </div>
					              
					              <div class="form-group row row-bottom-margin">
				                      <label class="col-lg-3 col-form-label form-control-label">Departments</label>
				                      <div class="col-lg-9">
						 				<c:forEach items="${user.userDepartments}" var="user_dept">
										    <li style="color: black; width: 250px !important;" class="list-group-item disabled">${user_dept.department.dept_name}</li>
						 				</c:forEach>
				                      </div>
					              </div>
					              <div class="form-group row row-bottom-margin">
				                      <label class="col-lg-3 col-form-label form-control-label">Locations</label>
				                      <div class="col-lg-9">
						 				<c:forEach items="${user.userLocations}" var="user_loc">
										    <li style="color: black; width: 400px !important;" class="list-group-item disabled">${user_loc.location.loc_name}</li>
						 				</c:forEach>
				                      </div>
					              </div>
							   <br>
							 </div>
							 <br>
				              <form:hidden id="selected_role_id" path="" name="selected_role_id"></form:hidden> 
				              <form:hidden id="user_id" path="user_id" name="user_id" value="${user.user_id}"></form:hidden> 
				              <form:hidden id="username" path="username" name="username" value="${user.username}"></form:hidden> 
				              <form:hidden id="email" path="email" name="email" value="${user.email}"></form:hidden> 
		                             
		                   </form:form>
	                  </div>
	                 </div>
	                 <!-- /form user info -->
	             </div>
			</div>
		</div>
	</div>
</body>
</html>