<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<!DOCTYPE html>
<html lang="en">
<head>
</head>
<body>

<script type="text/javascript">

	function populateSearchKeyword()
	{
	  if (document.getElementById("search_consent_keyword").value == '')
		  {
		  	alert('Cannot search an empty string');
		  	return false;
		  }
	}

	jQuery(function ($) {

		$('.filterable .btn-filter').click(function(){
	        var $panel = $(this).parents('.filterable'),
	        $filters = $panel.find('.filters input'),
	        $tbody = $panel.find('.table tbody');
	        if ($filters.prop('disabled') == true) {
	            $filters.prop('disabled', false);
	            $filters.first().focus();
	        } else {
	            $filters.val('').prop('disabled', true);
	            $tbody.find('.no-result').remove();
	            $tbody.find('tr').show();
	        }
	    });

	    $('.filterable .filters input').keyup(function(e){
	        /* Ignore tab key */
	        var code = e.keyCode || e.which;
	        if (code == '9') return;
	        /* Useful DOM data and selectors */
	        var $input = $(this),
	        inputContent = $input.val().toLowerCase(),
	        $panel = $input.parents('.filterable'),
	        column = $panel.find('.filters th').index($input.parents('th')),
	        $table = $panel.find('.table'),
	        $rows = $table.find('tbody tr');
	        /* Dirtiest filter function ever ;) */
	        var $filteredRows = $rows.filter(function(){
	            var value = $(this).find('td').eq(column).text().toLowerCase();
	            return value.indexOf(inputContent) === -1;
	        });
	        /* Clean previous no-result if exist */
	        $table.find('tbody .no-result').remove();
	        /* Show all rows, hide filtered ones (never do that outside of a demo ! xD) */
	        $rows.show();
	        $filteredRows.hide();
	        /* Prepend no-result row if all rows are filtered */
	        if ($filteredRows.length === $rows.length) {
	            $table.find('tbody').prepend($('<tr class="no-result text-center"><td colspan="'+ $table.find('.filters th').length +'">No result found</td></tr>'));
	        }
	    });
	});
	
</script>

  <div class="main-text hidden-xs">
    <div class="col-md-12 text-center" style="background-color: #EAE8FF; color: #2E008B">
       <h2 class="text-center">Search for consent records</h2>
	    <section class="search-sec">
		    <div class="container">
				<form id="search_consent" name="search_consent" action="search_consent" method="get">
		            <div class="row">
		                <div class="col-lg-12">
		                    <div class="row">
		                        <div class="col-lg-9 col-md-2 col-sm-12 p-0">
		                            <input id="search_consent_keyword" name="search_consent_keyword" type="text" class="form-control search-slt" placeholder="Enter Keyword"></input>
		                        </div>
		                        <div class="col-lg-3 col-md-2 col-sm-12 p-0">
					              <button style="background-color: #2E008B; color: #FEFEFE" class="btn btn-lg wrn-btn" type="submit" 
					              		onClick="return populateSearchKeyword()"><i class="fas fa-search"></i> Search Consent</button>
		                        </div>
		                    </div>
		                </div>
		            </div>
		        </form>
		    </div>
	    </section>
		<br>
	    <c:if test = "${not empty search_consent_keyword}">
		    <div class="container">
		     <div class="col-md-12">
		     	<c:choose>
		     		<c:when test="${not empty search_consent_result}">
				    	<h3>You have searched for '<strong>${search_consent_keyword}</strong>'</h3>
		     		</c:when>
		     		<c:otherwise>
				    	<h3>No record found for '<strong>${search_consent_keyword}</strong>'</h3>
		     		</c:otherwise>
		     	</c:choose>
		     	<br>
			    <c:if test = "${not empty search_consent_result}">
				    <div class="row">
				        <div class="panel panel-primary filterable">
				            <div class="panel-heading">
				                <div class="float-left">
				                    <button style="background-color: #E60E8B; color: #FEFEFE" class="btn btn-default btn-xs btn-filter"><span class="fas fa-filter"></span> Filter</button>
				                </div>
				            </div>
				            <table class="table table-bordered">
				                <thead>
				                    <tr class="filters">
				                        <th style="width: 30%"><input class="text-center" type="text" class="form-control" placeholder="First Name" disabled></th>
				                        <th style="width: 30%"><input class="text-center" type="text" class="form-control" placeholder="Last Name" disabled></th>
				                    </tr>
				                </thead>
				                <tbody>
									<c:forEach items = "${search_consent_result}" var = "consent">
										<tr>
					                        <td style="width: 30%">${consent.firstname}</td>
					                        <td style="width: 30%">${consent.surname}</td>
					                        <c:if test="${fn:containsIgnoreCase(consent_access, 'EDIT')}">
												<td style="width: 30%"><a style="background-color: #E60E8B" href="edit_consent?consent_id=${consent.consent_id}" class="btn"><i style="color: #FEFEFE" class="fas fa-edit"></i></a>
													&nbsp;&nbsp;&nbsp;
													<a style="background-color: #E60E8B" href="delete_consent?consent_id=${consent.consent_id}" class="btn" onclick="return confirmDeleteConsent()"><i style="color: #FEFEFE" class="fas fa-trash-alt"></i></a></td>
					                        </c:if>
					                    </tr>
									</c:forEach>			                    
				                </tbody>
				            </table>
				        </div>
				    </div>
			    </c:if>
		      </div>
			</div>
		</c:if>
     </div>
   </div>
</body>
</html>
