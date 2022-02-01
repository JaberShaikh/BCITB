<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri = "http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
	<sec:csrfMetaTags/>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<form:form name="timeline_form" method="POST" action="view_timeline" modelAttribute="action" autocomplete="off">
<body onload="reloadData()">
<div class="content py-5" style="background-color:#EAE8FF;color:#2E008B;">
	<div class="container">
		<div class="row">
             <span class="anchor"></span>
              <div class="card card-outline-secondary">
                  <div class="card-header" style="color: #FEFEFE">
                      <h3 class="mb-0">Timeline</h3>
              </div>
           <div class="card-body">
			 <table class="table table-bordered"> 
			  <tbody>
			    <tr>
			      <td>
					<div id="view_timeline_list_div" class="form-check" 
						style="display:none;border:solid 2px black;max-height:150px;margin-bottom:10px;overflow:auto;">
					<br><br>
					</div>
					  <div id="view_timeline_notes_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;display:none;">
					    <label for="view_timeline_notes" class="col-sm-5 col-form-label text-left">Timeline Notes</label>
					    <div class="col-sm-6 col-md-6">
			               <textarea id="view_timeline_notes" name="view_timeline_notes" 
			               		rows="10" class="form-control form-control-sm floatlabel" readonly="readonly"></textarea>
					    </div>
					  </div>	 									
					<h6 id="no_timeline_found_h6" style="display:none;">No Timeline Found</h6>
			       </td>
			      </tr>
			     </tbody>
			   </table>
          </div>
      </div>
   </div>
  </div>
 </div>
</body>
</form:form>
</html>