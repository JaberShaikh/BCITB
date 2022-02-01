<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri = "http://java.sun.com/jsp/jstl/functions"%>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
<div class='table-responsive'>
 <table class="table table-bordered" style="background-color:#EAE8FF;color:#2EOO8B;"> 
  <tbody>
    <tr>
      <td>
		<section class="search-sec">
		     <div class="container">
		      <div class="d-flex justify-content-center">    
		        <form name="search_patient_form" action="search_patient" method="get" autocomplete="off" onsubmit="return false">
		            <div class="row">
                        <div>
			               	<input id="search_patient_keyword" name="search_patient_keyword" type="text" class="form-control" 
			               		placeholder="Search Keyword"></input>
			            	<label id="search_patient_keyword-validation" style="color:red;display:none;"></label>
                        </div>
                        <div>
					      <select id="select_search_criteria" name="select_search_criteria" class="form-control" style="text-transform: capitalize;"
					      	onchange="hideAndShowContainer(this);">
							  <c:forEach items="${search_criterias}" var = "search_criteria">
						          <option value="${search_criteria}">${fn:replace(search_criteria, "_", " ")}</option>
							  </c:forEach>
					      </select>
                        </div>
                        <div>
					 		<button id="search_patient_btn" style="background-color: #2E008B; color: #FEFEFE;" class="btn wrn-btn"
					 				type="button" onclick="return validateFormFields('',this,false);">
				 					<i class="fas fa-search"></i> Search Patient</button>
			 			</div>
		            </div>
		        </form>
		      </div>
		    </div>
		</section>
		<br><br><br>
		<section class="search-sec">
		  <c:if test = "${not empty search_patient_keyword}">
		   <div class="text-center">
		   	<c:choose>
		   		<c:when test="${(not empty search_patient_result)}">
		    		<h5>You have searched for <strong>${search_patient_keyword}</strong></h5>
		   		</c:when>
		   		<c:otherwise>
		    		<h5>No record found for <strong>${search_patient_keyword}</strong></h5>
		   		</c:otherwise>
		   	</c:choose>
		   </div>
		   </c:if>
		   <c:if test = "${not empty search_patient_result}">
	        <form name="view_patient_form" action="view_patient" method="get" autocomplete="off" onsubmit="return false"> 
	        	<table id="patient_search_result_table" class="table table-striped table-bordered"> 
				    <thead>
				        <tr>
				        	<c:choose>
				        		<c:when test="${fn:containsIgnoreCase(consent_access, 'VIEW_RESTRICTED')}">
						            <th>Database ID </th>
						            <th>Secondary ID </th>
						            <th>Volunteer </th>
						            <th>Age </th>
						            <th>Old Pat ID</th>
						            <th>Consent(s) </th>
						            <th>Infection Risk(s) </th>
				        		</c:when>
				        		<c:otherwise>
						            <th>Database ID</th>
						            <th>Secondary ID</th>
						            <th>Volunteer</th>
						            <th>Firstname</th>
						            <th>Surname</th>
						            <th>DoB</th>
						            <th>NHS Number</th>
						            <th>Old Pat ID</th>
						            <th>Consent(s)</th>
						            <th>Withdrawn?</th>
						            <th>Infection Risk(s)</th>
						            <th>Validated Consent(s)</th>
						            <th>Imported</th>
						            <th>Finalise Consent(s)</th>
				        		</c:otherwise>
				        	</c:choose>
			        		<c:if test="${not (consent_access eq 'view_restricted')}">
				            	<th>Access</th>
				            </c:if>
				        </tr>
				    </thead>
	                <tbody>
						<c:forEach items = "${search_patient_result}" var="patient">
							<tr>
				        	<c:choose>
				        		<c:when test="${fn:containsIgnoreCase(consent_access, 'view_restricted')}">
			                        <td>${patient.database_id}</td>
			                        <td>${patient.secondary_id}</td>
			                        <td>${patient.volunteer}</td>
			                        <td>${patient.age}</td>
			                        <td>${patient.old_pat_id}</td>
			                        <td>${patient.number_consents}</td>
			                        <td>${patient.number_infection_risks}</td>
				        		</c:when>
				        		<c:otherwise>
			                        <td>${patient.database_id}</td>
			                        <td>${patient.secondary_id}</td>
			                        <td>${patient.volunteer}</td>
			                        <td>${patient.patient_firstname}</td>
			                        <td>${fn:toUpperCase(patient.patient_surname)}</td>
			                        <td>${patient.date_of_birth}</td>
			                        <td>${patient.nhs_number}</td>
			                        <td>${patient.old_pat_id}</td>
			                        <td>${patient.number_consents}</td>
			                        <td>${empty patient.withdrawn_count? 0:patient.withdrawn_count}</td>
			                        <td>${patient.number_infection_risks}</td>
			                        <td>${patient.number_validated_consents}</td>
			                        <c:choose>
			                        	<c:when test="${patient.status eq 'imported'}">
			                        		<td>YES</td>
			                        	</c:when>
			                        	<c:otherwise>
			                        		<td></td>
			                        	</c:otherwise>
			                        </c:choose>
			                        <td>${patient.number_finalise_import_consents}</td>
				        		</c:otherwise>
				        	</c:choose>
				        	<c:choose>
				        		<c:when test="${not (consent_access eq 'view_restricted')}">
									<td>
									<c:choose>
										<c:when test="${not empty patient.locked_description}">
								  	  		<button style="background-color:#d62828;color:#FEFEFE;" name="patient_locked_btn" id="patient_locked_btn" 
								  	  			type="button" class="btn btn-sm" value="${patient.locked_description}" onclick="processUserSelection(this)">
								  	  			<i class="fas fa-ban"></i> Patient Locked</button>
										</c:when>
										<c:otherwise>
											<c:if test="${patient.number_consents gt 0 || fn:toLowerCase(consent_withdrawn_access) eq 'edit'}">
												<c:if test="${fn:containsIgnoreCase(timeline, 'VIEW')}">
													<div class="form-check">
													  <input style="background-color:#2E008B;color:#FEFEFE;" class="form-check-input" type="checkbox" value="" 
													  		id="view_patient_timeline_chk_${patient.patient_id}">
													  <label class="form-check-label" for="view_patient_timeline_chk_${patient.patient_id}">
													    	Show Timeline?
													  </label>
													</div>												
												</c:if>
									  	  		<button style="background-color:#2E008B;color:#FEFEFE;" name="view_searched_patient" id="view_searched_patient" 
									  	  			type="button" onclick="processUserSelection(this)" value="${patient.patient_id}" class="btn btn-sm">
										  			<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true" style="display:none"></span>
									  	  			<i class="fas fa-eye"></i> View Patient</button>
											</c:if>
										</c:otherwise>
									</c:choose>
									</td>
				        		</c:when>
				        	</c:choose>
		                    </tr>
						</c:forEach>			                    
	                </tbody>
	            </table>
				<input type="hidden" name="patient_id" id="patient_id"/>
				<input type="hidden" name="selected_department" id="selected_department"/>
	         </form>
		   </c:if>
		   <div class="text-center">
		    <br><br><br>
			<c:if test="${(not empty search_deleted_patient_result)}">
		  		<h5>'<strong>${search_patient_keyword}</strong>' found under deleted records</h5>
			</c:if>
		   </div>
		   <c:if test = "${not empty search_deleted_patient_result}">
		    <div id="deleted_patient_search_result_div" class="container table-responsive table-bordered">
		        <div class="row">
		            <table id="deleted_patient_search_result" class="table table-bordered table-hover table-sm">
					    <thead>
					        <tr>
					            <th>Database ID </th>
					            <th>Secondary ID </th>
					            <th>Reason </th>
					        </tr>
					    </thead>
		                <tbody>
							<c:forEach items = "${search_deleted_patient_result}" var="patient">
								<tr>
			                        <td>${patient.database_id}</td>
			                        <td>${patient.secondary_id}</td>
			                        <td>${patient.status_reason}</td>
			                    </tr>
							</c:forEach>			                    
		                </tbody>
		            </table>
			   </div>
			 </div>
		   </c:if>   
		</section>
      </td>
    </tr>
  </tbody>
 </table>
</div>
</body>
