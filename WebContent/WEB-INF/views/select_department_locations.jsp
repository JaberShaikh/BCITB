<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri = "http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
</head>
<body>
 <form name="choose_locations_form" action="choose_locations" method="GET">
   <div class="row">
	<c:forEach items="${departments}" var="dept">
        <c:set var = "this_dept_id" value = "" scope = "session"/>
		<c:forEach items = "${user.userDepartments}" var = "user_dept">
		    <c:if test = "${empty this_dept_id}">
			    <c:if  test = "${user_dept.department.dept_id eq dept.dept_id}">
		            <c:set var = "this_dept_id" value = "${dept.dept_id}" scope = "session"/>
			    </c:if>
		    </c:if>
		</c:forEach>
		<c:if test = "${not empty this_dept_id}">
	        <div class="col-xs-12 col-sm-6 col-md-6 col-lg-4">          
	          <div class="card rounded-0 p-0 shadow-sm">
	 	        <h5 style="color: #2E008B" class="text-center">${dept.dept_name}</h5>
	  	        <div class="container">
			    <img id="${dept.dept_acronym}" data-popover-toggle="popover" data-popover-title="${dept.dept_name}" data-popover-content="${dept.dept_description}"
			    	onmouseenter="showDepartmentDetails(this, 'SHOW')" onmouseleave="showDepartmentDetails(this, 'HIDE')"
			   		class="img-thumbnail img-responsive" src="<c:url value="/resources/images/${dept.dept_acronym}.jpg"/>" alt="${dept.dept_name}" width="600">
			   	</div>			 	        
	            <div class="card-body text-center">
	                <label style="color: #2E008B" for="${dept.dept_id}_selected_locations">Select Location</label>
				      <c:choose>
					      <c:when test = "${dept.dept_id eq 2}"> <!-- Disabled CTB for now -->
						      <select id="${dept.dept_id}_selected_locations" name="${dept.dept_id}_selected_locations" multiple title="None" 
						      		data-selected-text-format="count" class="selectpicker form-control" data-actions-box="true" disabled="disabled">
								<c:forEach items = "${user.userLocations}" var = "user_loc">
								  <c:forEach items="${deptlocs}" var = "dept_loc">
								    <c:if  test = "${user_loc.location.loc_id eq dept_loc.loc_id && dept_loc.dept_id eq this_dept_id}">
							          <option value="${dept_loc.loc_id}">${dept_loc.location.loc_name}</option>
								    </c:if>
								  </c:forEach>
								</c:forEach>
						      </select>
				              <button style="margin-top:5px; background-color: #2E008B; color: #FEFEFE" class="btn btn-sm select_location_btn" name="${dept.dept_id}" 
				              		type="button" id="${dept.dept_id}" value="${dept.dept_id}" onclick="processUserSelection(this);" disabled="disabled">
				              		<i class="fas fa-check-circle"></i> Submit</button>
					      </c:when>
					      <c:otherwise>
						      <select id="${dept.dept_id}_selected_locations" name="${dept.dept_id}_selected_locations" multiple title="None" 
						      		data-selected-text-format="count" class="selectpicker form-control" data-actions-box="true">
								<c:forEach items = "${user.userLocations}" var = "user_loc">
								  <c:forEach items="${deptlocs}" var = "dept_loc">
								    <c:if  test = "${user_loc.location.loc_id eq dept_loc.loc_id && dept_loc.dept_id eq this_dept_id}">
							          <option value="${dept_loc.loc_id}">${dept_loc.location.loc_name}</option>
								    </c:if>
								  </c:forEach>
								</c:forEach>
						      </select>
				              <button style="margin-top:5px; background-color: #2E008B; color: #FEFEFE" class="btn btn-sm select_location_btn" name="${dept.dept_id}" 
				              		type="button" id="${dept.dept_id}" value="${dept.dept_id}" onclick="processUserSelection(this);">
				              		<i class="fas fa-check-circle"></i> Submit</button>
					      </c:otherwise>
				      </c:choose>
	            </div>
	          </div> 
	        </div>
      		</c:if>
			<c:if test = "${empty this_dept_id}">
		        <div class="col-xs-12 col-sm-6 col-md-6 col-lg-4">          
		          <div class="card rounded-0 p-0 shadow-sm">
		 	        <h5 style="color: #EAE8FF" class="text-center">${dept.dept_name}</h5>
		  	        <div class="container">
				    <img id="${dept.dept_acronym}" data-popover-toggle="popover" data-popover-title="${dept.dept_name}" data-popover-content="${dept.dept_description}" 
				    	onmouseenter="showDepartmentDetails(this, 'SHOW')" onmouseleave="showDepartmentDetails(this, 'HIDE')" style="opacity: 0.5;"
				   		class="img-thumbnail img-responsive" src="<c:url value="/resources/images/${dept.dept_acronym}.jpg"/>" alt="${dept.dept_name}" width="600">
				   	</div>			 	        
		            <div class="card-body text-center">
		                <label style="color: #2E008B" for="not_available_selected_locations">Select Location</label>
					      <select id="not_available_selected_locations" name="not_available_selected_locations" class="selectpicker" multiple title="None" disabled></select>
			              <button style="margin-top:5px; background-color: #2E008B; color: #FEFEFE" class="btn btn-sm select_location_btn" type="submit" name="${dept.dept_id}" id="${dept.dept_id}"
			              		value="${dept.dept_id}" disabled="disabled"><i class="fas fa-check-circle"></i> Submit</button>
		            </div>
		          </div>          
		        </div>
			</c:if>
      	</c:forEach>
     </div>
     <input type="hidden" id="selected_dept_id" name="selected_dept_id"></input>
     <input type="hidden" id="selected_locations" name="selected_locations"></input>
   </form>
   <br><br>
	<div class="alert" id="dept_details_alert" style="display: none; background-color: #EAE8FF; color: #513CA1">
	  <div id="dept_details_title" style="font-size: 30px;"></div><div id="dept_details_body"></div>
	</div>
</body>
</html>