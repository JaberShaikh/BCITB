var all_infection_risks = [];

function validateUserProfile()
{
	if (!checkEmpty($('#title')) || !checkEmpty($('#firstname')) || !checkEmpty($('#surname')) || !checkEmail($('#email')) || !checkEmailDomain($('#email')))
      {
		event.preventDefault();
      }
	else
	  {
	    document.getElementById('selected_role_id').value = document.getElementById('current_role_id').value;
	    alert(document.getElementById('firstname').value + ' ' + document.getElementById('surname').value + ', your profile has been saved successfully.');
	  }
}
function initialiseSelectedDeptAndLocs(buttonObj)
{
    var brands = $("#" + buttonObj.id + "_selected_locations option:selected");
    var selectedCheckBoxValues = [];
    $(brands).each(function(index, brand){
    	selectedCheckBoxValues.push([$(this).val()]);
    });
    document.getElementById('selected_dept_id').value = buttonObj.value;
    document.getElementById('selected_locations').value = selectedCheckBoxValues;
}
function checkEmpty(obj) {
	var name = $(obj).attr("name");
	$("."+name+"-validation").html("");	
	$(obj).css("border","");
	if($(obj).val() == "") {
		$(obj).css("border","#FF0000 1px solid");
		$("."+name+"-validation").html("'" + $(obj).attr("placeholder") + "' required");
		return false;
	}
	return true;	
}
function checkEmail(obj) {
	var result = true;
	var name = $(obj).attr("name");
	$("."+name+"-validation").html("");	
	$(obj).css("border","");
	
	result = checkEmpty(obj);
	
	if(!result) {
		$(obj).css("border","#FF0000 1px solid");
		$("."+name+"-validation").html("'" + $(obj).attr("placeholder") + "' required");
		return false;
	}
	
	var email_regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,3})+$/;
	result = email_regex.test($(obj).val());
	
	if(!result) {
		$(obj).css("border","#FF0000 1px solid");
		$("."+name+"-validation").html("'" + $(obj).attr("placeholder") + "' invalid");
		return false;
	}

	return result;	
}
function checkEmailDomain(email) {
	var emailValue = $(email).val(); 
	var name = $(email).attr("name");
	$("."+name+"-validation").html("");	
	$(email).css("border","");
	if((emailValue.substr(emailValue.length - 5) != "ac.uk") && (emailValue.substr(emailValue.length - 7) != "nhs.net"))
	{
		$(email).css("border","#FF0000 1px solid");
		$("."+name+"-validation").html("'" + $(email).attr("placeholder") + "' must have either 'ac.uk' or 'nhs.net' domain");
		return false;
	}
	return true;	
}	
function validateAddPatient()
{
	if (!checkEmpty($('#firstname')) || !checkEmpty($('#surname')) || !checkEmpty($('#hospital_number'))
			|| !checkEmpty($('#nhs_number')) || !checkEmpty($('#tissue_bank_number')) || !checkEmpty($('#date_of_birth')))
      {
		 event.preventDefault();
      }
	else
	  {
		alert('Patient ' + document.getElementById('firstname').value + ' ' + document.getElementById('surname').value + ' saved successfully.');
      }
}
function validateInfectionRisk()
{
	if (!checkEmpty($('#episode_start_date')) || !checkEmpty($('#episode_finished_date')))
      {
		 event.preventDefault();
      }
	else
	  {
		alert('Infection Risk for ' + document.getElementById('firstname').value + ' ' + document.getElementById('surname').value + ' saved successfully.');
      }
}
function confirmDelete()
{
    var retVal = confirm("Do you wish to delete?");
    if( retVal == true ) {
       return true;
    } else {
       return false;
    }
}
function initialiseForm(whichForm, arrayToUse)
{
	switch (whichForm)
	{
	case "PATIENT":
		if(arrayToUse != null)
			{
			    document.getElementById('patient_id').value = arrayToUse[0].trim();
			    document.getElementById('ir_patient_id').value = document.getElementById('patient_id').value;
			    document.getElementById('firstname').value = arrayToUse[1].trim();
			    document.getElementById('surname').value = arrayToUse[2].trim();
			    document.getElementById('volunteer').value = arrayToUse[3].trim();
			    document.getElementById('date_of_birth').value = arrayToUse[4].trim();
			    document.getElementById('hospital_number').value = arrayToUse[5].trim();
			    document.getElementById('nhs_number').value = arrayToUse[6].trim();
			    document.getElementById('tissue_bank_number').value = arrayToUse[7].trim();
		
			    document.getElementById("infection_risk_title").style.display = "";
			    document.getElementById("infection_risk_body").style.display = "";

			    document.getElementById("adding_patient_title").text = "Edit Patient";
			    
			    $("#prev_infection_risk").empty();
			    var dropdown = document.getElementById("prev_infection_risk");
			    all_infection_risks.forEach(function(item,index,arr){
			    	if(index === 0) {
				      var opt = document.createElement("option"); 
			    	  opt.value = item;
			    	  opt.text = item;
				      dropdown.options.add(opt);			    
			    	}
			    	else {
			    		var myStr = item + ''; 
			    		var newArr = myStr.split(',');
			    		if (newArr[1].trim() === document.getElementById('ir_patient_id').value) {
					      var opt = document.createElement("option"); 
				    	  opt.value = item;
					      opt.text = 'Type: ' + newArr[3].trim() + ' Start-date: ' + newArr[5].trim();
						  dropdown.options.add(opt);			    
			    		}
			    	}
			    });
			}
		else
			{
		    	document.getElementById('patient_id').value = 0;
			    document.getElementById('ir_patient_id').value = 0;
			    document.getElementById('firstname').value = "";
			    document.getElementById('surname').value = "";
			    document.getElementById('volunteer').selectedIndex = 0;
			    document.getElementById('date_of_birth').value = "";
			    document.getElementById('hospital_number').value = "";
			    document.getElementById('nhs_number').value = "";
			    document.getElementById('tissue_bank_number').value = "";
	
			    document.getElementById("infection_risk_title").style.display = "none";
			    document.getElementById("infection_risk_body").style.display = "none";

			    document.getElementById("adding_patient_title").text = "Add New Patient";
			}
		break;
	case "INFECTION_RISK":

		if(arrayToUse != null)
		{
			var strToProcess = arrayToUse.split(',')
		    document.getElementById('infection_risk_id').value = strToProcess[0].trim();
			document.getElementById('ir_patient_id').value = strToProcess[1].trim();
		    document.getElementById('infection_risk_exist').value = strToProcess[2].trim();
		    document.getElementById('type_of_infection').value = strToProcess[3].trim();
		    document.getElementById('episode_of_infection').value = strToProcess[4].trim();
		    document.getElementById('episode_start_date').value = strToProcess[5].trim();
		    document.getElementById('episode_finished_date').value = strToProcess[6].trim();
		    document.getElementById('delete_inf_risk').style.display = "";
		}
		else
		{
		    document.getElementById('infection_risk_id').value = 0;
			document.getElementById('ir_patient_id').value = document.getElementById('patient_id').value;
		    document.getElementById('infection_risk_exist').selectedIndex = 0;
		    document.getElementById('type_of_infection').selectedIndex = 0;
		    document.getElementById('episode_of_infection').selectedIndex = 0;
		    document.getElementById('episode_start_date').value = "";
		    document.getElementById('episode_finished_date').value = "";
		    document.getElementById('delete_inf_risk').style.display = "none";
		}
	}
}
function processPatientSelection(this_object)
{
    if (all_infection_risks.length <= 0) {
	    var brands = $("#prev_infection_risk option");
	    $(brands).each(function(index, brand) {
	    	all_infection_risks.push([$(this).val()]); // Store all infection risks
	    });
	}
    
	var table = $('#patient-table').DataTable();
    var last_chk_value = $(this_object).prop('checked');

    table.$("input[type=checkbox]").prop("checked", false);
    $(this_object).prop("checked", last_chk_value);
   	
   	var patArray = $(this_object).attr('value').split(',');
    if ($(this_object).prop('checked'))
    	{
    		initialiseForm('PATIENT', patArray);
    	}
    else
    	{
    		initialiseForm('PATIENT', null);
    	}
}

jQuery(function ($) {
	
    $("#prev_infection_risk").on('change', function(e){
    	e.preventDefault();
    	var inf_risk_val  = $("#prev_infection_risk :selected").val();
    	if (document.getElementById("prev_infection_risk").selectedIndex <= 0)
        {
            initialiseForm('INFECTION_RISK', null);
        }
        else
        {
            initialiseForm('INFECTION_RISK', inf_risk_val);
        }
    });
	
    $('#patient-table').DataTable({
         "aLengthMenu": [[2, 5, 10, -1], [2, 5, 10, "All"]],
         "iDisplayLength": 2
    });	
	
	$('.date').datetimepicker({
        format: 'dd-mm-yyyy',
        weekStart: 1,
        todayBtn:  true,
        autoclose: true,
        todayHighlight: true,
        startView: 2,
        minView: 2,
        forceParse: 0
    });	
	
	$("img[data-popover-title]").each(function(e, elem) {
		  var _this = this;
		  $(this).popover({
		    title: $(this).data('popover-title'),
		    content: $(this).data('popover-content'),
		    trigger: "manual",
		    fallbackPlacement : ['left', 'right', 'top', 'bottom'],
		    placement: "left",
		    animation: false
		  }).on("mouseenter", function() {
		    $(_this).popover("show");
		    $(_this).siblings("a[data-popover-title]").each( function() {
		    	$(this).popover("hide");
		    });
		  }).parent().on("mouseleave", function() {
		    $(_this).popover("hide");
		  })
	});
	
  $(".select_location_btn").click(function(event){
	if($('#selected_dept_id').val() == "" || $('#selected_locations').val() == "")
    {
		alert('Please select location(s) before cliking the submit button');
		event.preventDefault();
    }
  });	
	
  $(".sidebar-dropdown > a").click(function() {
  $(".sidebar-submenu").slideUp(200);
  if (
    $(this)
      .parent()
      .hasClass("active")
  ) {
    $(".sidebar-dropdown").removeClass("active");
    $(this)
      .parent()
      .removeClass("active");
  } else {
    $(".sidebar-dropdown").removeClass("active");
    $(this)
      .next(".sidebar-submenu")
      .slideDown(200);
    $(this)
      .parent()
      .addClass("active");
  }
  
});

$("#close-sidebar").click(function() {
  $(".page-wrapper").removeClass("toggled");
});
$("#show-sidebar").click(function() {
  $(".page-wrapper").addClass("toggled");
});

});
