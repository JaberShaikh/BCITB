<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
</head>
<body>

<section id="cover">
    <div id="cover-caption">
        <div id="container" class="container">
            <div class="row">
                <div class="col-sm-6 offset-sm-3 text-center">
                    <h3>Edit Consent Form</h3>
                    <div class="info-form">
                        <form:form action="save_consent" method="post" modelAttribute="consent" class="form-inlin justify-content-center">
						  <div class="form-group row">
						    <label for="consent_id" class="col-sm-10 col-form-label text-left">Consent ID</label>
						    <div class="col-sm-10">
						      <form:input type="text" class="form-control input-sm floatlabel" id="consent_id" path="consent_id" readonly="true"></form:input>
						    </div>
						  </div>
						  <div class="form-group row">
						    <label for="firstname" class="col-sm-5 col-form-label text-left">First Name</label>
						    <div class="col-sm-10">
				               <form:input type="text" id="firstname" path="firstname" placeholder="${consent.firstname}" class="form-control input-sm floatlabel"></form:input> 
 						       <span style="color:red" class="firstname-validation validation-error"></span> 
						    </div>
						  </div>          
						  <div class="form-group row">
						    <label for="surname" class="col-sm-5 col-form-label text-left">Surname</label>
						    <div class="col-sm-10">
				               <form:input type="text" id="surname" path="surname" placeholder="${consent.surname}" class="form-control input-sm floatlabel"></form:input> 
				               <span style="color:red" class="surname-validation validation-error"></span>
						    </div>
						  </div>
 						  <div class="form-group row">
						    <label for="current_loc_id" class="col-sm-5 col-form-label text-left">Location(s)</label>
						    <div class="col-sm-10">
						      <select id="addConsentLocationsRadioBtns" name="addConsentLocationsRadioBtns" multiple class="selectpicker">
								  <c:forEach items="${user_locs}" var = "user_loc">
								 	<c:choose>
								 		<c:when test="${user_loc.loc_id == consent.loc_id}">
									        <option selected="selected" value="${user_loc.loc_id}">${user_loc.loc_name}</option>
								 		</c:when>
								 		<c:otherwise>
									        <option value="${user_loc.loc_id}">${user_loc.loc_name}</option>
								 		</c:otherwise>
								 	</c:choose>
							      </c:forEach>
						      </select>
						    </div>
						  </div> 
						  
 						<form:hidden path="loc_id" name="loc_id" id="loc_id"/> 
						  
			  	  		<form:button style="background-color: #2E008B; color: #FEFEFE" type="submit" name="saveCancelSubmitButtons" onClick="validateAddConsent()"
			  	  			value="save_clicked" class="btn btn-sm"><i class="fas fa-check-circle"></i> Update Consent</form:button>
			  	  		&nbsp;&nbsp;&nbsp;&nbsp;
			  	  		<form:button style="background-color: #E60E8B; color: #FEFEFE" type="submit" name="saveCancelSubmitButtons" 
			  	  			value="cancel_clicked" class="btn btn-sm"><i class="fas fa-window-close"></i> Cancel</form:button>
			  	  		
                      </form:form>
                    </div>
                    <br>
                </div>
            </div>
        </div>
    </div>
</section>
</body>
</html>