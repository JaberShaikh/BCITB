<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri = "http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
	<sec:csrfMetaTags />
</head>
<body>
   <div class="container">
       <div class="row">
            <!-- Tabs with icons on Card -->
            <div class="card card-nav-tabs h-50">
                <div class="card-header card-header-primary">
                    <div class="nav-tabs-navigation">
                        <div class="nav-tabs-wrapper">
                            <ul class="nav nav-tabs" data-tabs="tabs">
                                <li class="nav-item">
                                   <a class="nav-link active" id="basic_search_tab_anchor" href="#basic_search_tab" data-toggle="tab">
                                    	Basic Search
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" id="advanced_search_tab_anchor" href="#advanced_search_tab" data-toggle="tab"> 
                                    	Advanced Search
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
	         <form:form name="search_form" method="GET" action="basic_advance_search" autocomplete="off">
                <div class="card-body">
                  <div class="tab-content">
                    <div class="tab-pane active" id="basic_search_tab">
					  <div class="container">
					   <div class="col-md-8 mx-auto">
			            <div class="row">
	                        <div>
				               	<input id="search_patient_keyword" name="search_patient_keyword" type="text" class="form-control" 
				               			placeholder="Search Keyword"></input>
				            	<label id="search_patient_keyword-validation" style="color:red;display:none;"></label>
	                        </div>
	                        <div>
						      <select id="select_search_criteria" name="select_search_criteria" class="form-control" style="text-transform: capitalize;"
						      		onchange="hideAndShowContainer(this);">
								  <c:forEach items="${basic_search_criterias}" var = "search_criteria">
							          <option value="${search_criteria}">${fn:replace(search_criteria, "_", " ")}</option>
								  </c:forEach>
						      </select>
	                        </div>
	                        <div>
						 		<button id="basic_search_patient_btn" name="basic_search_patient_btn" 
						 			style="background-color: #2E008B; color: #FEFEFE;" class="btn wrn-btn"
						 			type="button" onclick="return validateFormFields('',this,false);">
									<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true" style="display:none"></span>						 			
					 				<i class="fas fa-search"></i> Search Patient</button>
				 			</div>
			            </div>
			       </div>  
			     </div>  
			    </div>                         
                <div class="tab-pane" id="advanced_search_tab">
				  <div class="container">
				   <div class="col-md-8 mx-auto">
					  <div class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
					    <label for="select_advanced_search_criteria" class="col-sm-4 col-form-label text-left">Add Search Criteria </label>
					    <div class="col-sm-4 col-md-4">
				              <select id="select_advanced_search_criteria" name="select_advanced_search_criteria" style="text-transform: capitalize;"
				              		class="form-control form-control-sm floatlabel">
						          <option value=""></option>
								  <c:forEach items="${advanced_search_criterias}" var = "adv_search_criteria">
							          <option value="${adv_search_criteria}">${fn:replace(adv_search_criteria, "_", " ")}</option>
								  </c:forEach>
				              </select> 
					    </div>
				 		<button id="advanced_search_add_option_btn" name="advanced_search_add_option_btn" 
				 			style="background-color: #2E008B; color: #FEFEFE;" class="btn btn-sm"
				 			type="button" onclick="processUserSelection(this)">
			 				<i class="fas fa-plus-square"></i> Add</button>
			 			&nbsp;&nbsp;
				 		<button id="advanced_search_clear_option_btn" name="advanced_search_clear_option_btn" 
				 			style="background-color: #2E008B; color: #FEFEFE;" class="btn btn-sm"
				 			type="button" onclick="processUserSelection(this)">
			 				<i class="fas fa-broom"></i> Clear</button>
					  </div>
					 </div>
					<div class="table-responsive" id="advanced_search_options_div" class="text-center invisible-scrollbar">
					</div>
					<br>
					  <div id="select_search_result_columns_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;display:none;">
					    <label for="select_search_result_columns" class="col-sm-4 col-form-label text-left">Select search result columns to display 
					    	<i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
					    <div class="col-sm-6 col-md-6">
				              <select id="select_search_result_columns" name="select_search_result_columns" style="text-transform: capitalize;" 
				              		data-actions-box="true" multiple="multiple" data-size="5" class="selectpicker form-control form-control-sm floatlabel" 
				              		data-selected-text-format="count" onchange="processUserSelection(this)">
								  <c:forEach items="${advanced_search_columns}" var = "adv_search_col">
							          <option value="${adv_search_col}" style="text-transform: capitalize">${fn:replace(adv_search_col, "_", " ")}</option>
								  </c:forEach>
				              </select> 
				          <label id="select_search_result_columns-validation" style="color:red; display: none;"></label> 
					    </div>
                      <br>
				 		<button id="advanced_search_btn" name="advanced_search_btn" style="background-color:#2E008B;color:#FEFEFE;" 
				 			class="btn btn-sm" type="button" onclick="processUserSelection(this)">
							<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true" style="display:none"></span>						 			
			 			<i class="fas fa-search"></i> Search Now</button>
					  </div>
	  		      </div>  
			    </div> 
			  </div>
			</div>                        
		  <input type="hidden" name="selected_department" id="selected_department" value="${user_selected_department.dept_acronym}"/>
		  <input type="hidden" name="patient_id" id="patient_id"/>
		  <input type="hidden" name="advanced_search_cols" id="advanced_search_cols"/>
	    </form:form>
	   </div>
  	  </div>
  	</div>
	<div id="search_result_div" style="overflow:auto;" class="table table-responsive">
		<table id="search_result_table" class="table table-striped table-bordered"></table>
	</div>
	<h6 id="no_search_result_found_h6" style="display:none;">No Search Results Found</h6>
</body>
</html>