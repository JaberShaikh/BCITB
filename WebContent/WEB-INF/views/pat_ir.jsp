<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri = "http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
	<sec:csrfMetaTags />
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body onload="reloadData()">
	<div class="content py-5" style="background-color:#EAE8FF;color:#2E008B;">
		<div class="container">
			<div class="panel-group" id="patient_consent_menu" role="tablist" aria-multiselectable="true">
			  <div class="panel panel-default">
			    <div class="panel-heading" role="tab" id="patient_heading" style="border-style:solid;border-width:medium;border-radius:25px;">
			      <h5 class="panel-title" style="position:relative;left:10px;">
			        <a data-toggle="collapse" data-parent="#patient_consent_menu" href="#patient_sub_menu" aria-expanded="true"  
			        	aria-controls="patient_sub_menu" onclick="changePlusMinusIcon(this)"> Patient <i id="patient_sub_menu_icon" class="fa fa-plus" style="float:right;position:relative;right:20px;"></i>
			        </a>
			      </h5>
			    </div>
			    <div id="patient_sub_menu" class="panel-collapse collapse" role="tabpanel" aria-labelledby="patient_heading">
			      <div class="panel-body">
					<%@ include file="add_patient.jsp" %>
			      </div>
			    </div>
			  </div>
			</div>
		</div>
	</div>
	<div>
		<%@ include file="add_infection_risk.jsp" %>
	</div>
</body>
</html>