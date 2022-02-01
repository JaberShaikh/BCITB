<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page session="true"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
  <title>BCI Tissue Bank</title>

  <meta charset="utf-8" name="viewport" content="width=device-width, initial-scale=1">
  
  <script type="text/javascript" src="<c:url value="/webjars/jquery/1.9.1/jquery.min.js"/>"></script>
  <script type="text/javascript" src="<c:url value="/webjars/bootstrap/4.3.1/js/bootstrap.min.js"/>"></script>  
  
  <link rel="stylesheet" href="<c:url value="/webjars/bootstrap/4.3.1/css/bootstrap.min.css"/>"/>  
  <link rel="stylesheet" href="<c:url value="/resources/css/login.css"/>"/>  

</head>
<body>
	<script type="text/javascript">
		function processWaitingButtonSpinner() {
			document.getElementById('login_waiting_span').style.display = '';
			document.getElementById('login_btn').disabled = true;
			document.user_login_form.submit();
		}
	</script>
    <%@ include file="header.jsp" %>
	<div class="login-form">
	     <form:form name="user_login_form" action="login" method="post" autocomplete="off"> 
	    	<c:if test="${not empty error}">
				<div class="form-group has-error text-center">
				    <label class="control-label" for="username">${error}</label>
				</div>
	    	</c:if>
			<c:if test="${not empty msg}">
				<div class="form-group has-error text-center">
				    <label class="control-label" for="username">${msg}</label>
				</div>
	  		</c:if>    
	        <h4 class="text-center">Please Sign In</h4>       
	        <div class="form-group">
	            <input type="text" class="form-control" id="username" name="username" placeholder="Username" required="required" title="Enter your username.">
	        </div>
	        <div class="form-group">
	            <input type="password" class="form-control" id="password" name="password" placeholder="Password" required="required" title="Enter your password.">
	        </div>
	        <p><p>
	        <div class="form-group">
	           <button id="login_btn" name="login_btn" type="submit" class="btn btn-primary btn-block" onclick="processWaitingButtonSpinner()">
	           		<span id="login_waiting_span" class="spinner-border spinner-border-sm" role="status" aria-hidden="true" style="display:none"></span> Log in</button>
	        </div>
		 	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	    </form:form> 
	</div>
	
	<div class="container">
	  <div class="alert alert-warning" style="color: black; background-color: #E1E8FF">
		  <h6 class="alert-body"> <strong>Warning!</strong> Do not attempt to access this system unless you are authorised to do so. This system is fully logged for auditing purposes. <br> 
		  Authorisation in this context means you are using logon credentials which have been issued directly to you.<br>
		  Failure to comply with the above statement will be an infringement of Queen Mary University of London security policy and standard operating procedures.</h6>
		  <hr>
		  <h6 class="alert-body">For a full list of these documents please click <a href="https://bcinet.qmcr.qmul.ac.uk/it/policies-procedures/">here</a> </h6>
	  </div>
	  <div class="panel-group" id="HelpMenu">
	    <div class="panel panel-default">
	      <div class="panel-heading">
	        <h4 class="panel-title">
	          <a data-toggle="collapse" data-parent="#HelpMenu" href="#resetPassword"> Is this the first time you will be logging into this application or have you recently reset your password?</a>
	        </h4>
	      </div>
	      <div id="resetPassword" class="panel-collapse collapse in">
	        <div class="panel-body">You need to change your password / recently updated password before logging in for the first time. 
	        	Click <a href="https://selfservice.bcc.qmul.ac.uk/showLogin.cc">here</a> to update your password.</div>
	      </div>
	    </div>
	    <div class="panel panel-default" style="color: black; background-color: #E1E8FF">
	      <div class="panel-heading" style="color: black; background-color: #E1E8FF">
	        <h4 class="panel-title">
	          <a data-toggle="collapse" data-parent="#HelpMenu" href="#credentials"> Are you unable to login to the application?</a>
	        </h4>
	      </div>
	      <div id="credentials" class="panel-collapse collapse">
	        <div class="panel-body">Please make sure that you have entered the correct credentials and try again.</div>
	      </div>
	    </div>
	    <div class="panel panel-default" style="color: black; background-color: #E1E8FF">
	      <div class="panel-heading" style="color: black; background-color: #E1E8FF">
	        <h4 class="panel-title">
	          <a data-toggle="collapse" data-parent="#HelpMenu" href="#forgottonPassword"> Have you forgotten or need to change your password?</a>
	        </h4>
	      </div>
	      <div id="forgottonPassword" class="panel-collapse collapse">
	        <div class="panel-body">Click <a href="https://selfservice.bcc.qmul.ac.uk/showLogin.cc">here</a> to change your password. 
	        Should you have any issues changing your password, please send an email to 
	        <a href="mailto:bcc-webhelpdesk@qmul.ac.uk">bcc-webhelpdesk@qmul.ac.uk</a> using your registered email address.</div>
	      </div>
	    </div>
	    <div class="panel panel-default" style="color: black; background-color: #E1E8FF">
	      <div class="panel-heading" style="color: black; background-color: #E1E8FF">
	        <h4 class="panel-title">
	          <a data-toggle="collapse" data-parent="#HelpMenu" href="#applicationIssues"> Are you having issues accessing the application?</a>
	        </h4>
	      </div>
	      <div id="applicationIssues" class="panel-collapse collapse">
	        <div class="panel-body">Please check the <a href="https://selfservice.bcc.qmul.ac.uk/showLogin.cc">Barts Cancer Centre Status Page</a> 
	        to check for any service updates and scheduled maintenance.</div>
	      </div>
	    </div>
	    <div class="panel panel-default" style="color: black; background-color: #E1E8FF">
	      <div class="panel-heading" style="color: black; background-color: #E1E8FF">
	        <h4 class="panel-title">
	          <a data-toggle="collapse" data-parent="#HelpMenu" href="#otherIssues"> For any other issues</a>
	        </h4>
	      </div>
	      <div id="otherIssues" class="panel-collapse collapse">
	        <div class="panel-body">Please send an email to <a href="mailto:bci-ecm-db-support@qmul.ac.uk">bci-ecm-db-support@qmul.ac.uk</a> 
	        and Bart's IT Team will get back to you as soon as possible.</div>
	      </div>
	    </div>
	  </div> 
	</div>
</body>
</html>