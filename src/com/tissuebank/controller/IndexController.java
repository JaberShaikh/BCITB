package com.tissuebank.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Map.Entry;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.Diff;
import org.apache.commons.lang3.builder.DiffResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tissuebank.model.Action;
import com.tissuebank.model.Consent;
import com.tissuebank.model.ConsentAudit;
import com.tissuebank.model.ConsentAuditSample;
import com.tissuebank.model.ConsentExclusion;
import com.tissuebank.model.ConsentValidate;
import com.tissuebank.model.ConsentedSamples;
import com.tissuebank.model.ConsentFile;
import com.tissuebank.model.InfectionRisk;
import com.tissuebank.model.Patient;
import com.tissuebank.model.Timeline;
import com.tissuebank.model.VariousActions;
import com.tissuebank.model.global.tables.AuditProcess;
import com.tissuebank.model.global.tables.AuditTeam;
import com.tissuebank.model.global.views.ActionRole;
import com.tissuebank.model.global.views.Department;
import com.tissuebank.model.global.views.DepartmentLocation;
import com.tissuebank.model.global.views.Location;
import com.tissuebank.model.global.views.Role;
import com.tissuebank.model.global.views.User;
import com.tissuebank.model.global.views.UserDepartment;
import com.tissuebank.model.global.views.UserRole;
import com.tissuebank.service.FormVersionConsentTermService;
import com.tissuebank.service.FormVersionService;
import com.tissuebank.service.ActionService;
import com.tissuebank.service.AuditProcessService;
import com.tissuebank.service.ConsentService;
import com.tissuebank.service.DepartmentLocationService;
import com.tissuebank.service.DepartmentService;
import com.tissuebank.service.FileService;
import com.tissuebank.service.FormTypeVersionConsentTermService;
import com.tissuebank.service.InfectionRiskService;
import com.tissuebank.service.LocationService;
import com.tissuebank.service.PatientService;
import com.tissuebank.service.RoleService;
import com.tissuebank.service.TimelineService;
import com.tissuebank.service.UserService;
import com.tissuebank.util.AllPatientData;
import com.tissuebank.util.BCITBEncryptDecrypt;
import com.tissuebank.util.BCITBVariousVariables;
import com.tissuebank.util.Email;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@SessionAttributes(value={"user_selected_department","user","primaryRole","session_patient","session_consent_validate","session_consents",
		"session_infection_risks","user_selected_locations","formVersionJson","sampleConsentedToJson","formVersionConsentTermJson",
		"formTypeVersionConsentTermJson","existing_audit_consents","various_actions","session_timeline","columnName","columnValue",
		"imported_consents","session_consent_audit","session_all_patient_data", "session_advanced_search_sql_script","session_basic_search_options"})
public class IndexController 
{
	@Autowired
	MimeMessage mimeMessage;

	@Autowired
	AuditProcessService auditProcessService;
	
	@Autowired
	ActionService actionService;

	@Autowired
	UserService userService;

	@Autowired
	LocationService locationService;

	@Autowired
	DepartmentService departmentService;

	@Autowired
	DepartmentLocationService departmentLocationService;

	@Autowired
	RoleService roleService;

	@Autowired
	PatientService patientService;	

	@Autowired
	InfectionRiskService infectionRiskService;	

	@Autowired
	FormVersionService formVersionService;

	@Autowired
	FormTypeVersionConsentTermService formTypeConsentTermService;
	
	@Autowired
	FormVersionConsentTermService formVersionConsentTermService;
	
	@Autowired
	ConsentService consentService;

	@Autowired
	FileService fileService;
	
	@Autowired
	TimelineService timelineService;
	
	@RequestMapping(value = {"/", "/login" }, method = RequestMethod.GET)
	public String loginPage(ModelMap model, HttpServletRequest request,
			@ModelAttribute("various_actions") VariousActions various_actions,
			@ModelAttribute("session_patient") Patient session_patient,
			@ModelAttribute("columnName") List<String> columnName,
			@ModelAttribute("columnValue") List<String> columnValue,
			@RequestParam(value = "error", required = false) String error) 
					throws AddressException, ParseException, MessagingException 
	{
		if (error != null)
			model.addAttribute("error", "Invalid username and password!");
		various_actions = unlockUserActionIfAny(various_actions,columnName,columnValue);
		session_patient = unlockPatient(session_patient, null, null);
		return "login";
	}
	
	@RequestMapping(value = {"/select_department_locations"}, method = RequestMethod.GET)
	public String departmentLocationsSelectionPage(ModelMap model,
			@ModelAttribute("user") User user,
			@ModelAttribute("primaryRole") Role primaryRole,
			@ModelAttribute("user_selected_department") Department user_selected_department,
			@ModelAttribute("user_selected_locations") List<Location> user_selected_locations,
			@ModelAttribute("various_actions") VariousActions various_actions,
			@ModelAttribute("session_patient") Patient session_patient,
			@ModelAttribute("columnName") List<String> columnName,
			@ModelAttribute("columnValue") List<String> columnValue,
			@ModelAttribute("existing_audit_consents") List<Consent> existing_audit_consents) 
					throws IOException, AddressException, ParseException, MessagingException
	{

		various_actions = unlockUserActionIfAny(various_actions,columnName,columnValue);
		session_patient.setPatient_which_department(user_selected_department.getDept_acronym());
		session_patient = unlockPatient(session_patient, user, user_selected_department);
		
		user_selected_department = new Department();
		user_selected_locations.clear();
		
		user = userService.findUserByUsername(getPrincipal());
		primaryRole = getPrimaryRole(userService.findUserByUsername(getPrincipal()));

		model.addAttribute("whichPageToShow", "select_department_locations"); 
		model.addAttribute("menuToShow", "ACTION");
		model.addAttribute("user", user);
		model.addAttribute("primaryRole", primaryRole);
		model.addAttribute("user_selected_department", user_selected_department);
		model.addAttribute("departments", departmentService.getAllDepartments());
		model.addAttribute("deptlocs", departmentLocationService.getDepartmentAllLocations(user.getUserDepartments()));
		model.addAttribute("various_actions", various_actions);
		if(primaryRole.getUserRoleAccess(BCITBVariousVariables.audit).toLowerCase().contains(BCITBVariousVariables.edit)) 
			model.addAttribute("existing_audit_consents", existing_audit_consents);

//		importConsentData(BCITBVariousVariables.HOTB, "H:\\Project\\Heam-Onc\\Scanned");
//		searchConsentFilesExists(BCITBVariousVariables.HOTB, "H:\\Project\\Heam-Onc\\Scanned");
		processConsents(BCITBVariousVariables.MONTHLY_REPORT_SENT_DATE, "");
		processConsents("AUDIT-REPORT", "");
		
		return "index";
	}
	
	@RequestMapping(value = {"/welcome"}, method={RequestMethod.POST,RequestMethod.GET})
	public String welcomePage(ModelMap model,
			@ModelAttribute("user_selected_department") Department user_selected_department,
			@ModelAttribute("user") User user,
			@ModelAttribute("primaryRole") Role primaryRole,
			@ModelAttribute("various_actions") VariousActions various_actions,
			@ModelAttribute("session_patient") Patient session_patient,
			@ModelAttribute("columnName") List<String> columnName,
			@ModelAttribute("columnValue") List<String> columnValue,
			@ModelAttribute("existing_audit_consents") List<Consent> existing_audit_consents)
	{

		if (!(user.getUser_id() > 0)) { // session timed out
			return "redirect:/timed_out";
		} else {

			various_actions = unlockUserActionIfAny(various_actions,columnName,columnValue);
			session_patient = unlockPatient(session_patient, user, user_selected_department);

			model.addAttribute("whichPageToShow", "welcome"); 
			model.addAttribute("user", user);
			model.addAttribute("primaryRole", primaryRole);
			model.addAttribute("user_selected_department", user_selected_department);
			model.addAttribute("consent_access", primaryRole.getUserRoleAccess(BCITBVariousVariables.consent));
			model.addAttribute("various_actions", various_actions);
			if(primaryRole.getUserRoleAccess(BCITBVariousVariables.audit).toLowerCase().contains(BCITBVariousVariables.edit)) 
				model.addAttribute("existing_audit_consents", existing_audit_consents);
			
			return "index";
		}
	}
	
	@RequestMapping(value = {"/choose_locations"}, method = RequestMethod.GET)
	public String selectedDepartmentLocationsPage(ModelMap model,
			@ModelAttribute("user") User user,
			@ModelAttribute("primaryRole") Role primaryRole,
			@ModelAttribute("user_selected_department") Department user_selected_department,
			@ModelAttribute("user_selected_locations") List<Location> user_selected_locations,
			@ModelAttribute("formVersionJson") String formVersionJson,
			@ModelAttribute("sampleConsentedToJson") String sampleConsentedToJson,
			@ModelAttribute("formVersionConsentTermJson") String formVersionConsentTermJson,
			@ModelAttribute("formTypeVersionConsentTermJson") String formTypeVersionConsentTermJson,
			@ModelAttribute("various_actions") VariousActions various_actions,
			@ModelAttribute("session_patient") Patient session_patient,
			@ModelAttribute("columnName") List<String> columnName,
			@ModelAttribute("columnValue") List<String> columnValue,
			@ModelAttribute("existing_audit_consents") List<Consent> existing_audit_consents,
			@RequestParam(value = "selected_dept_id", required = false, defaultValue = "0") String selected_dept_id,
			@RequestParam(value = "selected_locations", required = false, defaultValue = "") String selected_locations) 
					throws AddressException, ParseException, MessagingException
	{

		if(Integer.valueOf(selected_dept_id) > 0)
			user_selected_department = departmentService.getDepartmentById(Integer.valueOf(selected_dept_id));

		if (!(user.getUser_id() > 0)) { // session timed out
			return "redirect:/timed_out";
		} else {
			
			formVersionJson = "";
			sampleConsentedToJson = "";
			formVersionConsentTermJson = "";
			formTypeVersionConsentTermJson = "";
			
			if(!selected_locations.isEmpty()) {
				user_selected_locations.clear();
				for(Location loc:locationService.getAllLocationsFromIds(selected_locations))
					user_selected_locations.add(loc);
			}

			various_actions = unlockUserActionIfAny(various_actions,columnName,columnValue);
			session_patient = unlockPatient(session_patient, user, user_selected_department);
			
			model.addAttribute("whichPageToShow", "welcome"); 
			model.addAttribute("user", user);
			model.addAttribute("primaryRole", primaryRole);
			model.addAttribute("consent_access", primaryRole.getUserRoleAccess(BCITBVariousVariables.consent));
			model.addAttribute("user_selected_department", user_selected_department);
			model.addAttribute("user_selected_locations", user_selected_locations);
			model.addAttribute("various_actions", various_actions);
			if(primaryRole.getUserRoleAccess(BCITBVariousVariables.audit).toLowerCase().contains(BCITBVariousVariables.edit)) 
				model.addAttribute("existing_audit_consents", existing_audit_consents);
			processConsents("DELETE-WITHDRAWN-CONSENT", user_selected_department.getDept_acronym());
			
			return "index";
		}
	}
	
	@RequestMapping(value = {"/user_profile"}, method=RequestMethod.GET)
	public String userProfilePage(ModelMap model,
			@ModelAttribute("user") User user,
			@ModelAttribute("primaryRole") Role primaryRole,
			@ModelAttribute("various_actions") VariousActions various_actions,
			@ModelAttribute("session_patient") Patient session_patient,
			@ModelAttribute("columnName") List<String> columnName,
			@ModelAttribute("columnValue") List<String> columnValue,
			@ModelAttribute("user_selected_department") Department user_selected_department,
			@ModelAttribute("existing_audit_consents") List<Consent> existing_audit_consents)
	{
		if (!(user.getUser_id() > 0)) { // session timed out
			return "redirect:/timed_out";
		} else {
			
			various_actions = unlockUserActionIfAny(various_actions,columnName,columnValue);
			if(user_selected_department.getDept_acronym() != null && !user_selected_department.getDept_acronym().isEmpty()
					&& (session_patient.getPatient_which_department() == null || session_patient.getPatient_which_department().isEmpty()))
				session_patient.setPatient_which_department(user_selected_department.getDept_acronym());
			session_patient = unlockPatient(session_patient, user, user_selected_department);
			
			model.addAttribute("whichPageToShow", "user_profile"); 
			model.addAttribute("user", user);
			model.addAttribute("menuToShow", "ACTION");
			model.addAttribute("primaryRole", primaryRole);
			model.addAttribute("various_actions", various_actions);
			if(primaryRole.getUserRoleAccess(BCITBVariousVariables.audit).toLowerCase().contains(BCITBVariousVariables.edit)) 
				model.addAttribute("existing_audit_consents", existing_audit_consents);
			model.addAttribute("consent_access", primaryRole.getUserRoleAccess(BCITBVariousVariables.consent));
			
			return "index";
		}
	}

	@RequestMapping(value = {"/save_user"}, method = RequestMethod.GET)
	public String saveUserProfilePage(ModelMap model,
			@ModelAttribute("user") User user,
			@ModelAttribute("primaryRole") Role primaryRole,
			@ModelAttribute("various_actions") VariousActions various_actions,
			@ModelAttribute("existing_audit_consents") List<Consent> existing_audit_consents,			
			@RequestParam(value = "title") String title,			
			@RequestParam(value = "user_firstname") String user_firstname,			
			@RequestParam(value = "user_surname") String user_surname,
			@RequestParam(value = "selected_role_id", required = false, defaultValue = "0") int selected_role_id)
	{
		if (!(user.getUser_id() > 0)) { // session timed out
			return "redirect:/timed_out";
		} else {
			
			user.setTitle(title);
			user.setUser_firstname(user_firstname);
			user.setUser_surname(user_surname);
			userService.updateUserProfile(user,selected_role_id);
			user.setUserRoles(userService.getAllUserRoles(user));
			primaryRole = roleService.getRoleById(selected_role_id);

			model.addAttribute("whichPageToShow", "user_profile"); 
			model.addAttribute("user", user);
			model.addAttribute("primaryRole", primaryRole);
			model.addAttribute("menuToShow", "ACTION");
			model.addAttribute("various_actions", various_actions);
			if(primaryRole.getUserRoleAccess(BCITBVariousVariables.audit).toLowerCase().contains(BCITBVariousVariables.edit)) 
				model.addAttribute("existing_audit_consents", existing_audit_consents);
			model.addAttribute("consent_access", primaryRole.getUserRoleAccess(BCITBVariousVariables.consent));
			
			return "index";
		}
	}

	@RequestMapping(value = {"/edit_patient"}, method=RequestMethod.POST)
	public String editPatient(ModelMap model,
			@ModelAttribute("user") User user,
			@ModelAttribute("primaryRole") Role primaryRole,
			@ModelAttribute("session_patient") Patient session_patient,
			@ModelAttribute("various_actions") VariousActions various_actions,
			@ModelAttribute("existing_audit_consents") List<Consent> existing_audit_consents,
			@ModelAttribute("user_selected_department") Department user_selected_department)
	{
		if (!(user.getUser_id() > 0)) { // session timed out
			return "redirect:/timed_out";
		} else {

			session_patient.setLocked_description("Patient [" + session_patient.getDatabase_id() + "] locked by " 
					+ user.getUser_firstname() + " " + user.getUser_surname() + " on " 
					+ DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()));
			patientService.savePatientVariousColumns(user_selected_department.getDept_acronym(), session_patient.getPatient_id(), 
					BCITBVariousVariables.locked_description, session_patient.getLocked_description());
			session_patient.setLocked_by(user.getUser_id());
			patientService.savePatientVariousColumns(user_selected_department.getDept_acronym(), session_patient.getPatient_id(), 
					BCITBVariousVariables.locked_by, String.valueOf(user.getUser_id()));
			
			model.addAttribute("whichPageToShow", "add_patient"); 
			model.addAttribute("user", user);
			model.addAttribute("primaryRole", primaryRole);
			model.addAttribute("various_actions", various_actions);
			if(primaryRole.getUserRoleAccess(BCITBVariousVariables.audit).toLowerCase().contains(BCITBVariousVariables.edit)) 
				model.addAttribute("existing_audit_consents", existing_audit_consents);
			model.addAttribute("consent_access", primaryRole.getUserRoleAccess(BCITBVariousVariables.consent));
			model.addAttribute("user_selected_department", user_selected_department);
			model.addAttribute("patient", session_patient);
			
			return "index";
			
		}
	}	

	@RequestMapping(value = {"/view_patient","/view_searched_patient","/view_timeline"}, 
			method={RequestMethod.POST,RequestMethod.GET})
	public String viewPatient(ModelMap model,HttpServletRequest request,
			@ModelAttribute("user") User user,
			@ModelAttribute("primaryRole") Role primaryRole,
			@ModelAttribute("session_patient") Patient session_patient,
			@ModelAttribute("various_actions") VariousActions various_actions,
			@ModelAttribute("existing_audit_consents") List<Consent> existing_audit_consents,
			@ModelAttribute("session_basic_search_options") List<String> session_basic_search_options,
			@ModelAttribute("session_advanced_search_sql_script") List<String> session_advanced_search_sql_script,
			@RequestParam(value = "patient_id",defaultValue = "0", required = false) String patient_id,			
			@RequestParam(value = "selected_department", required = false, defaultValue = "") String selected_department,
			@ModelAttribute("user_selected_department") Department user_selected_department)
	{
		if (!(user.getUser_id() > 0)) { // session timed out
			return "redirect:/timed_out";
		} else {
			
			if(Integer.parseInt(patient_id) > 0) 
				session_patient = patientService.getPatientFromID(user_selected_department.getDept_acronym(),Integer.parseInt(patient_id));
			session_patient.setPatient_which_department(user_selected_department.getDept_acronym());
			
			session_patient.setLocked_description("Patient [" + session_patient.getDatabase_id() + "] locked by " 
					+ user.getUser_firstname() + " " + user.getUser_surname() + " on " 
					+ DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()));
			patientService.savePatientVariousColumns(user_selected_department.getDept_acronym(), session_patient.getPatient_id(), 
					BCITBVariousVariables.locked_description, session_patient.getLocked_description());
			session_patient.setLocked_by(user.getUser_id());
			patientService.savePatientVariousColumns(user_selected_department.getDept_acronym(), session_patient.getPatient_id(), 
					BCITBVariousVariables.locked_by, String.valueOf(user.getUser_id()));
			
			model.addAttribute("whichPageToShow", "add_patient"); 
			model.addAttribute("user", user);
			model.addAttribute("primaryRole", primaryRole);
			model.addAttribute("various_actions", various_actions);
			model.addAttribute("consent_withdrawn_access", primaryRole.getUserRoleAccess(BCITBVariousVariables.consent_withdrawn)); 
			if(primaryRole.getUserRoleAccess(BCITBVariousVariables.audit).toLowerCase().contains(BCITBVariousVariables.edit)) 
				model.addAttribute("existing_audit_consents", existing_audit_consents);
			model.addAttribute("consent_access", primaryRole.getUserRoleAccess(BCITBVariousVariables.consent));
			model.addAttribute("user_selected_department", user_selected_department);
			model.addAttribute("patient", session_patient);
			if (request.getServletPath().equalsIgnoreCase("/view_searched_patient") || request.getServletPath().equalsIgnoreCase("/view_timeline")) {
				if(session_basic_search_options.size() >= 2)
					model.addAttribute("basic_search_options", session_basic_search_options.get(0) + "|" + session_basic_search_options.get(1));
				if(session_advanced_search_sql_script.size() > 0)
					model.addAttribute("advanced_search_sql_script", session_advanced_search_sql_script.get(0));
			}
			
			return "index";
			
		}
	}	
	
	@RequestMapping(value = {"/add_patient"}, method=RequestMethod.GET)
	public String addPatient(ModelMap model,
			@ModelAttribute("user") User user,
			@ModelAttribute("primaryRole") Role primaryRole,
			@ModelAttribute("session_patient") Patient session_patient,
			@ModelAttribute("session_consents") List<Consent> session_consents,
			@ModelAttribute("session_infection_risks") List<InfectionRisk> session_infection_risks,
			@ModelAttribute("session_consent_validate") ConsentValidate session_consent_validate,
			@ModelAttribute("various_actions") VariousActions various_actions,
			@ModelAttribute("existing_audit_consents") List<Consent> existing_audit_consents,
			@ModelAttribute("user_selected_department") Department user_selected_department)
	{
		if (!(user.getUser_id() > 0)) { // session timed out
			return "redirect:/timed_out";
		} else {
			
			session_patient.setPatient_which_department(user_selected_department.getDept_acronym());
			session_patient = unlockPatient(session_patient, user, user_selected_department);
			
			session_patient = new Patient();
			
			session_consents.clear();
			session_consents.add(new Consent());
			session_consent_validate = new ConsentValidate();
			
			session_infection_risks.clear(); 
			
			model.addAttribute("whichPageToShow", "add_patient"); 
			model.addAttribute("user", user);
			model.addAttribute("primaryRole", primaryRole);
			model.addAttribute("various_actions", various_actions);
			if(primaryRole.getUserRoleAccess(BCITBVariousVariables.audit).toLowerCase().contains(BCITBVariousVariables.edit)) 
				model.addAttribute("existing_audit_consents", existing_audit_consents);
			model.addAttribute("consent_access", primaryRole.getUserRoleAccess(BCITBVariousVariables.consent));
			model.addAttribute("user_selected_department", user_selected_department);
			model.addAttribute("patient", session_patient);
			
			return "index";
			
		}
	}

	@RequestMapping(value = {"/edit_consent"}, method=RequestMethod.POST)
	public String editConsent(ModelMap model,
			@ModelAttribute("user") User user,
			@ModelAttribute("primaryRole") Role primaryRole,
			@ModelAttribute("sampleConsentedToJson") String sampleConsentedToJson,
			@ModelAttribute("formTypeVersionConsentTermJson") String formTypeVersionConsentTermJson,
			@ModelAttribute("formVersionJson") String formVersionJson,
			@ModelAttribute("formVersionConsentTermJson") String formVersionConsentTermJson,
			@ModelAttribute("session_patient") Patient session_patient,
			@ModelAttribute("session_consents") List<Consent> session_consents,
			@ModelAttribute("session_consent_validate") ConsentValidate session_consent_validate,
			@ModelAttribute("session_consent_audit") ConsentAudit session_consent_audit,
			@ModelAttribute("various_actions") VariousActions various_actions,
			@ModelAttribute("existing_audit_consents") List<Consent> existing_audit_consents,
			@ModelAttribute("user_selected_locations") List<Location> user_selected_locations,
			@ModelAttribute("user_selected_department") Department user_selected_department) 
					throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
	{
		if (!(user.getUser_id() > 0)) { // session timed out
			return "redirect:/timed_out";
		} else {
			
			if(session_consents.size() <= 0)
				session_consents.add(new Consent());
			if(!(session_consent_validate.getConsent_validate_id() > 0))
				session_consent_validate = new ConsentValidate();
			if(!(session_consent_audit.getConsent_audit_id() > 0))
				session_consent_audit = new ConsentAudit();
			
			model.addAttribute("headerTitleToShow", "Consent For " + 
					session_patient.getPatient_firstname().toUpperCase() + " " + session_patient.getPatient_surname().toUpperCase()); 
			model.addAttribute("whichPageToShow", "add_consent"); 
			model.addAttribute("user", user);
			model.addAttribute("primaryRole", primaryRole);
			model.addAttribute("various_actions", various_actions);
			if(primaryRole.getUserRoleAccess(BCITBVariousVariables.audit).toLowerCase().contains(BCITBVariousVariables.edit)) 
				model.addAttribute("existing_audit_consents", existing_audit_consents);
			model.addAttribute("consent_access", primaryRole.getUserRoleAccess(BCITBVariousVariables.consent));
			model.addAttribute("consent_validate_access", primaryRole.getUserRoleAccess(BCITBVariousVariables.consent_validate));
			model.addAttribute("audit_access", primaryRole.getUserRoleAccess(BCITBVariousVariables.audit));
			model.addAttribute("user_selected_department", user_selected_department);
			model.addAttribute("consent", session_consents.get(0));
			model.addAttribute("consent_validate", session_consent_validate); 
			model.addAttribute("session_consent_audit", session_consent_audit); 
			model.addAttribute("patient", session_patient);
			model.addAttribute("consentFormTypes", consentService.getFormTypes(user_selected_department.getDept_acronym()));
			model.addAttribute("user_selected_locations", user_selected_locations);

			switch (user_selected_department.getDept_acronym().toUpperCase()) {
			case BCITBVariousVariables.HOTB:
				sampleConsentedToJson = getVariousDataFromDB("sampleConsentedToJson", user_selected_department.getDept_acronym(), sampleConsentedToJson);
				formTypeVersionConsentTermJson = getVariousDataFromDB("formTypeVersionConsentTermJson", user_selected_department.getDept_acronym(), formTypeVersionConsentTermJson);
				formVersionJson = getVariousDataFromDB("formVersionJson", user_selected_department.getDept_acronym(), formVersionJson);
				model.addAttribute("sampleConsentedToJson", sampleConsentedToJson);
				model.addAttribute("formTypeVersionConsentTermJson", formTypeVersionConsentTermJson);
				model.addAttribute("formVersionJson", formVersionJson);
				break;
			case BCITBVariousVariables.BGTB:
				formVersionConsentTermJson = getVariousDataFromDB("formVersionConsentTermJson", user_selected_department.getDept_acronym(), formVersionConsentTermJson);
				model.addAttribute("formVersionConsentTermJson", formVersionConsentTermJson);
				break;
			}
				
			return "index";
		}
	}

	@RequestMapping(value = {"/view_consent", "/view_searched_consent"}, method=RequestMethod.GET)
	public String viewConsent(ModelMap model,HttpServletRequest request,
			@ModelAttribute("user") User user,
			@ModelAttribute("primaryRole") Role primaryRole,
			@ModelAttribute("sampleConsentedToJson") String sampleConsentedToJson,
			@ModelAttribute("formTypeVersionConsentTermJson") String formTypeVersionConsentTermJson,
			@ModelAttribute("formVersionJson") String formVersionJson,
			@ModelAttribute("formVersionConsentTermJson") String formVersionConsentTermJson,
			@ModelAttribute("session_patient") Patient session_patient,
			@ModelAttribute("session_consents") List<Consent> session_consents,
			@ModelAttribute("session_consent_validate") ConsentValidate session_consent_validate,
			@ModelAttribute("session_consent_audit") ConsentAudit session_consent_audit,
			@ModelAttribute("various_actions") VariousActions various_actions,
			@ModelAttribute("existing_audit_consents") List<Consent> existing_audit_consents,
			@ModelAttribute("session_basic_search_options") List<String> session_basic_search_options,
			@ModelAttribute("session_advanced_search_sql_script") List<String> session_advanced_search_sql_script,
			@RequestParam(value = "consent_id", required = false, defaultValue = "0") String consent_id,
			@ModelAttribute("user_selected_locations") List<Location> user_selected_locations,
			@ModelAttribute("user_selected_department") Department user_selected_department)
	{
		if (!(user.getUser_id() > 0)) { // session timed out
			return "redirect:/timed_out";
		} else {
			
			if(Integer.valueOf(consent_id) > 0) {
				session_consents.clear();
				session_consents.add(consentService.getPatientConsent(user_selected_department.getDept_acronym(), Integer.valueOf(consent_id), "", "", null));
				session_consents = populateVariousBitsOfConsents(user_selected_department.getDept_acronym(), "ALL", session_consents);
				session_consents.get(0).setConsent_which_department(user_selected_department.getDept_acronym());
				session_consent_validate = new ConsentValidate(Integer.valueOf(consent_id), user_selected_department.getDept_acronym());
				session_consent_audit = new ConsentAudit(Integer.valueOf(consent_id), user_selected_department.getDept_acronym());
				session_patient = patientService.getPatientFromID(user_selected_department.getDept_acronym(), 
						session_consents.get(0).getCn_patient_id());
				session_patient.setPatient_which_department(user_selected_department.getDept_acronym());
				session_patient.setLocked_description("Patient [" + session_patient.getDatabase_id() + "] locked by " 
						+ user.getUser_firstname() + " " + user.getUser_surname() + " on " 
						+ DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()));
				patientService.savePatientVariousColumns(user_selected_department.getDept_acronym(), session_patient.getPatient_id(), 
						BCITBVariousVariables.locked_description, session_patient.getLocked_description());
				session_patient.setLocked_by(user.getUser_id());
				patientService.savePatientVariousColumns(user_selected_department.getDept_acronym(), session_patient.getPatient_id(), 
						BCITBVariousVariables.locked_by, String.valueOf(user.getUser_id()));
			}
					
			model.addAttribute("headerTitleToShow", "Consent For " + 
					session_patient.getPatient_firstname().toUpperCase() + " " + session_patient.getPatient_surname().toUpperCase()); 
			model.addAttribute("whichPageToShow", "add_consent"); 
			model.addAttribute("user", user);
			model.addAttribute("primaryRole", primaryRole);
			model.addAttribute("various_actions", various_actions);
			if(primaryRole.getUserRoleAccess(BCITBVariousVariables.audit).toLowerCase().contains(BCITBVariousVariables.edit)) 
				model.addAttribute("existing_audit_consents", existing_audit_consents);
			if(session_consents.get(0).getIs_validated() == null && session_consents.get(0).getIs_imported() == null)
				model.addAttribute("consent_access", primaryRole.getUserRoleAccess(BCITBVariousVariables.consent_draft));
			else
				model.addAttribute("consent_access", primaryRole.getUserRoleAccess(BCITBVariousVariables.consent));
			model.addAttribute("consent_validate_access", primaryRole.getUserRoleAccess(BCITBVariousVariables.consent_validate));
			model.addAttribute("audit_access", primaryRole.getUserRoleAccess(BCITBVariousVariables.audit));
			model.addAttribute("user_selected_department", user_selected_department);
			model.addAttribute("consent", session_consents.get(0));
			model.addAttribute("consent_validate", session_consent_validate);
			model.addAttribute("session_consent_audit", session_consent_audit); 
			model.addAttribute("patient", session_patient);
			model.addAttribute("consentFormTypes", consentService.getFormTypes(user_selected_department.getDept_acronym()));
			model.addAttribute("user_selected_locations", user_selected_locations);
			model.addAttribute("auditSampleTypes", auditProcessService.getAllAuditSampleTypes(user_selected_department.getDept_acronym())); 

			switch (user_selected_department.getDept_acronym().toUpperCase()) {
			case BCITBVariousVariables.HOTB:
				sampleConsentedToJson = getVariousDataFromDB("sampleConsentedToJson", user_selected_department.getDept_acronym(), sampleConsentedToJson);
				formTypeVersionConsentTermJson = getVariousDataFromDB("formTypeVersionConsentTermJson", user_selected_department.getDept_acronym(), formTypeVersionConsentTermJson);
				formVersionJson = getVariousDataFromDB("formVersionJson", user_selected_department.getDept_acronym(), formVersionJson);
				model.addAttribute("sampleConsentedToJson", sampleConsentedToJson);
				model.addAttribute("formTypeVersionConsentTermJson", formTypeVersionConsentTermJson);
				model.addAttribute("formVersionJson", formVersionJson);
				break;
			case BCITBVariousVariables.BGTB:
				formVersionConsentTermJson = getVariousDataFromDB("formVersionConsentTermJson", user_selected_department.getDept_acronym(), formVersionConsentTermJson);
				model.addAttribute("formVersionConsentTermJson", formVersionConsentTermJson);
				break;
			}
			if (request.getServletPath().equalsIgnoreCase("/view_searched_consent")) {
				if(session_basic_search_options.size() >= 2)
					model.addAttribute("basic_search_options", session_basic_search_options.get(0) + "|" + session_basic_search_options.get(1));
				if(session_advanced_search_sql_script.size() > 0)
					model.addAttribute("advanced_search_sql_script", session_advanced_search_sql_script.get(0));
			}
			
			return "index";
		}
	}
	
	@RequestMapping(value = {"/add_infection_risk"}, method=RequestMethod.POST)
	public String addInfectionRisk(ModelMap model,
			@ModelAttribute("user") User user,
			@ModelAttribute("primaryRole") Role primaryRole,
			@ModelAttribute("session_patient") Patient session_patient,
			@ModelAttribute("various_actions") VariousActions various_actions,
			@ModelAttribute("existing_audit_consents") List<Consent> existing_audit_consents,
			@ModelAttribute("user_selected_department") Department user_selected_department)
	{
		if (!(user.getUser_id() > 0)) { // session timed out
			return "redirect:/timed_out";
		} else {
			
			model.addAttribute("headerTitleToShow", "Infection Risk(s) For " + 
				session_patient.getPatient_firstname().toUpperCase() + " " + session_patient.getPatient_surname().toUpperCase()); 
			model.addAttribute("whichPageToShow", "add_infection_risk"); 
			model.addAttribute("user", user);
			model.addAttribute("primaryRole", primaryRole);
			model.addAttribute("various_actions", various_actions);
			if(primaryRole.getUserRoleAccess(BCITBVariousVariables.audit).toLowerCase().contains(BCITBVariousVariables.edit)) 
				model.addAttribute("existing_audit_consents", existing_audit_consents);
			model.addAttribute("consent_access", primaryRole.getUserRoleAccess(BCITBVariousVariables.consent));
			model.addAttribute("user_selected_department", user_selected_department);
			model.addAttribute("infection_risk", new InfectionRisk());
			model.addAttribute("patient", session_patient);
			model.addAttribute("infection_types", infectionRiskService.getAllInfectionTypes(user_selected_department.getDept_acronym()));
			model.addAttribute("sampleTypes", infectionRiskService.getAllSampleTypes(user_selected_department.getDept_acronym()));
			return "index";
			
		}
	}

	@RequestMapping(value={"/view_infection_risk","/view_searched_infection_risk"}, method=RequestMethod.GET)
	public String viewInfectionRisk(ModelMap model,HttpServletRequest request,
			@ModelAttribute("user") User user,
			@ModelAttribute("primaryRole") Role primaryRole,
			@ModelAttribute("session_patient") Patient session_patient,
			@ModelAttribute("various_actions") VariousActions various_actions,
			@ModelAttribute("session_infection_risks") List<InfectionRisk> session_infection_risks,
			@ModelAttribute("existing_audit_consents") List<Consent> existing_audit_consents,
			@ModelAttribute("session_basic_search_options") List<String> session_basic_search_options,
			@ModelAttribute("session_advanced_search_sql_script") List<String> session_advanced_search_sql_script,
			@RequestParam(value = "patient_id", required = false, defaultValue = "0") String patient_id,
			@ModelAttribute("user_selected_department") Department user_selected_department)
	{
		if (!(user.getUser_id() > 0)) { // session timed out
			return "redirect:/timed_out";
		} else {
			
			if(Integer.valueOf(patient_id) > 0) 
				session_patient = patientService.getPatientFromID(user_selected_department.getDept_acronym(), Integer.parseInt(patient_id));
			
			session_patient.setPatient_which_department(user_selected_department.getDept_acronym());
			session_patient.setLocked_description("Patient [" + session_patient.getDatabase_id() + "] locked by " 
					+ user.getUser_firstname() + " " + user.getUser_surname() + " on " 
					+ DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()));
			patientService.savePatientVariousColumns(user_selected_department.getDept_acronym(), session_patient.getPatient_id(), 
					BCITBVariousVariables.locked_description, session_patient.getLocked_description());
			session_patient.setLocked_by(user.getUser_id());
			patientService.savePatientVariousColumns(user_selected_department.getDept_acronym(), session_patient.getPatient_id(), 
					BCITBVariousVariables.locked_by, String.valueOf(user.getUser_id()));
			
			if(session_infection_risks.size() <= 0)
				session_infection_risks.add(new InfectionRisk());

			model.addAttribute("headerTitleToShow", "Infection Risk(s) For " + 
					session_patient.getPatient_firstname().toUpperCase() + " " + session_patient.getPatient_surname().toUpperCase()); 
			model.addAttribute("whichPageToShow", "add_infection_risk"); 
			model.addAttribute("user", user);
			model.addAttribute("primaryRole", primaryRole);
			model.addAttribute("various_actions", various_actions);
			if(primaryRole.getUserRoleAccess(BCITBVariousVariables.audit).toLowerCase().contains(BCITBVariousVariables.edit)) 
				model.addAttribute("existing_audit_consents", existing_audit_consents);
			if(consentService.getPatientConsents("all",user_selected_department.getDept_acronym(), session_patient.getPatient_id(), 
					"", "", null, BCITBVariousVariables.ascending).size() 
					== consentService.getPatientConsents(BCITBVariousVariables.is_validated,user_selected_department.getDept_acronym(), session_patient.getPatient_id(), 
					"", "", null, BCITBVariousVariables.ascending).size())
				model.addAttribute("consent_access", primaryRole.getUserRoleAccess(BCITBVariousVariables.consent));
			else
				model.addAttribute("consent_access", primaryRole.getUserRoleAccess(BCITBVariousVariables.consent_draft));
			model.addAttribute("user_selected_department", user_selected_department);
			model.addAttribute("infection_risk", session_infection_risks.get(0));
			model.addAttribute("patient", session_patient);
			model.addAttribute("infection_types", infectionRiskService.getAllInfectionTypes(user_selected_department.getDept_acronym()));
			model.addAttribute("sampleTypes", infectionRiskService.getAllSampleTypes(user_selected_department.getDept_acronym()));
			
			if (request.getServletPath().equalsIgnoreCase("/view_searched_infection_risk")) {
				if(session_basic_search_options.size() >= 2)
					model.addAttribute("basic_search_options", session_basic_search_options.get(0) + "|" + session_basic_search_options.get(1));
				if(session_advanced_search_sql_script.size() > 0)
					model.addAttribute("advanced_search_sql_script", session_advanced_search_sql_script.get(0));
			}
			return "index";
			
		}
	}
	
	public Patient updatePatient(User user, Role role, Patient patient) throws ParseException
	{
		Patient old_patient = null;
		old_patient = patientService.getPatientFromID(patient.getPatient_which_department(), patient.getPatient_id()); 
			
		DiffResult diff = old_patient.diff(patient);
		if (diff.getDiffs().size() > 0) { // Save patient only when something has changed
			patientService.updatePatientUsingDiff(patient.getPatient_which_department(), patient.getPatient_id(), diff, patient.getDatabase_id());
			timelineService.saveTimeline(patient.getPatient_which_department(), patient.getPatient_id(), user.getUser_id(), role.getRole_id(), BCITBVariousVariables.edit, 
					getDifferenceBetweenTwoObjects("","",diff,null,null),DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()), BCITBVariousVariables.patient,
					"Edits: Patient [" + patient.getDatabase_id() + ", " + patient.getSecondary_id() + "] fields edited on " + 
					DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + " by " + user.getUsername() + " [" + role.getRole_description() + "]",
					BCITBVariousVariables.yes, patient.getPatient_id());
			patient = updatePatientField(patient.getPatient_which_department(), BCITBVariousVariables.secondary_id, patient);
		}
		return patient;
	}
	
	@RequestMapping(value = {"/update_patient"}, method=RequestMethod.POST)
	public String updatePatient(ModelMap model,
			@ModelAttribute("user") User user,
			@ModelAttribute("primaryRole") Role primaryRole,
			@ModelAttribute("patient") Patient patient,
			@ModelAttribute("session_patient") Patient session_patient,
			@ModelAttribute("various_actions") VariousActions various_actions,
			@ModelAttribute("existing_audit_consents") List<Consent> existing_audit_consents,
			@ModelAttribute("user_selected_department") Department user_selected_department) throws ParseException
	{
		if (!(user.getUser_id() > 0)) { // session timed out
			return "redirect:/timed_out";
		} else {
			
			session_patient = patient;
			session_patient = updatePatient(user, primaryRole, session_patient);

			model.addAttribute("whichPageToShow", "add_patient"); 
			model.addAttribute("user", user);
			model.addAttribute("primaryRole", primaryRole);
			model.addAttribute("various_actions", various_actions);
			if(primaryRole.getUserRoleAccess(BCITBVariousVariables.audit).toLowerCase().contains(BCITBVariousVariables.edit)) 
				model.addAttribute("existing_audit_consents", existing_audit_consents);
			model.addAttribute("consent_access", primaryRole.getUserRoleAccess(BCITBVariousVariables.consent));
			model.addAttribute("user_selected_department", user_selected_department);
			model.addAttribute("patient", session_patient);
			
			return "index";

		}
	}	
	
	public Patient updatePatientField(String which_department, String whichFieldToCheck, Patient patient) throws ParseException
	{
		String field_value = "";
		Consent consent = null;
		
		switch (whichFieldToCheck) {
		case BCITBVariousVariables.secondary_id:
			for(Consent con: consentService.getPatientConsents("", which_department, patient.getPatient_id(), 
					"", "", null, BCITBVariousVariables.descending)){
				consent = con;
				break;
			}
			if(consent != null)
				if(consent.getSam_coll_before_sep_2006() != null && consent.getSam_coll_before_sep_2006().equalsIgnoreCase(BCITBVariousVariables.yes))
					field_value = patientService.generateSecondaryID(patient, "");
				else
					field_value = patientService.generateSecondaryID(patient, consent.getDate_of_consent());
			
			if(patient.getSecondary_id() != null && !field_value.equalsIgnoreCase(patient.getSecondary_id())) {
				patientService.savePatientVariousColumns(which_department, patient.getPatient_id(), BCITBVariousVariables.secondary_id, field_value);
				patient.setSecondary_id(field_value);
			}
			break;
		}
		return patient;
	}
	
	public List<Consent> updateConsent(User whichUser, Role whichRole, List<Consent> consents, List<Location> locations, Patient patient, List<String> columnName, List<String> columnValue) throws ParseException
	{
	
		DiffResult diff;
		ConsentFile old_file = new ConsentFile();

		if(consents.get(0).getDigital_cf_attachment() != null) {
			if (consents.get(0).getDigital_cf_attachment().getFile_name() != null && !consents.get(0).getDigital_cf_attachment().getFile_name().isEmpty()) {
				if (consents.get(0).getDigital_cf_attachment_id() != null && consents.get(0).getDigital_cf_attachment_id() > 0) {
					old_file = decryptFileData(consents.get(0).getConsent_which_department(), fileService.getFile(consents.get(0).getConsent_which_department(), 
							consents.get(0).getDigital_cf_attachment_id(), BCITBVariousVariables.digital_cf_attachment_file));
					diff = old_file.diff(consents.get(0).getDigital_cf_attachment());
					if (diff.getDiffs().size() > 0) { // Save file only when something has changed							
						consents.get(0).getDigital_cf_attachment().setFile_id(consents.get(0).getDigital_cf_attachment_id()); // If file already exist then get old file id so record can be updated
					}
				}
				consents.get(0).setDigital_cf_attachment_id(fileService.saveOrUpdateFileAndReturnFileId(
						consents.get(0).getConsent_which_department(), consents.get(0).getDigital_cf_attachment()));
			}
		} else {
			if (consents.get(0).getDigital_cf_attachment_id() != null && consents.get(0).getDigital_cf_attachment_id() > 0) { // If user has removed an existing file then delete it from DB
				fileService.deleteFile(consents.get(0).getConsent_which_department(), consents.get(0).getDigital_cf_attachment_id());
				consentService.saveConsentVariousColumns(consents.get(0).getConsent_which_department(), consents.get(0).getConsent_id(), 
						BCITBVariousVariables.digital_cf_attachment_file.replace("_file", "_id"), "");
				consents.get(0).setDigital_cf_attachment_id(0);
			}
		}
		if(consents.get(0).getAdditional_document() != null) {
			if (consents.get(0).getAdditional_document().getFile_name() != null && !consents.get(0).getAdditional_document().getFile_name().isEmpty()) {
				if (consents.get(0).getAdditional_document_id() != null && consents.get(0).getAdditional_document_id() > 0) {
					old_file = decryptFileData(consents.get(0).getConsent_which_department(), fileService.getFile(consents.get(0).getConsent_which_department(), 
							consents.get(0).getAdditional_document_id(), BCITBVariousVariables.additional_document_file));
					diff = old_file.diff(consents.get(0).getAdditional_document());
					if (diff.getDiffs().size() > 0) { // Save file only when something has changed							
						consents.get(0).getAdditional_document().setFile_id(consents.get(0).getAdditional_document_id()); // If file already exist then get old file id so record can be updated
					}
				}
				consents.get(0).setAdditional_document_id(fileService.saveOrUpdateFileAndReturnFileId(
						consents.get(0).getConsent_which_department(), consents.get(0).getAdditional_document()));
			}
		} else {
			if (consents.get(0).getAdditional_document_id() != null && consents.get(0).getAdditional_document_id() > 0) { // If user has removed an existing file then delete it from DB
				fileService.deleteFile(consents.get(0).getConsent_which_department(), consents.get(0).getAdditional_document_id());
				consentService.saveConsentVariousColumns(consents.get(0).getConsent_which_department(), consents.get(0).getConsent_id(), 
						BCITBVariousVariables.additional_document_file.replace("_file", "_id"), "");
				consents.get(0).setAdditional_document_id(0);
			}
		}
		if(consents.get(0).getVerbal_consent_document() != null) {
			if (consents.get(0).getVerbal_consent_document().getFile_name() != null && !consents.get(0).getVerbal_consent_document().getFile_name().isEmpty()) {
				if (consents.get(0).getVerbal_consent_document_id() != null && consents.get(0).getVerbal_consent_document_id() > 0) {
					old_file = decryptFileData(consents.get(0).getConsent_which_department(), fileService.getFile(consents.get(0).getConsent_which_department(), 
							consents.get(0).getVerbal_consent_document_id(), BCITBVariousVariables.verbal_consent_document_file));
					diff = old_file.diff(consents.get(0).getVerbal_consent_document());
					if (diff.getDiffs().size() > 0) { // Save file only when something has changed							
						consents.get(0).getVerbal_consent_document().setFile_id(consents.get(0).getVerbal_consent_document_id()); // If file already exist then get old file id so record can be updated
					}
				}
				consents.get(0).setVerbal_consent_document_id(fileService.saveOrUpdateFileAndReturnFileId(consents.get(0).getConsent_which_department(), 
						consents.get(0).getVerbal_consent_document()));
			}
		} else {
			if (consents.get(0).getVerbal_consent_document_id() != null && consents.get(0).getVerbal_consent_document_id() > 0) { // If user has removed an existing file then delete it from DB
				fileService.deleteFile(consents.get(0).getConsent_which_department(), consents.get(0).getVerbal_consent_document_id());
				consentService.saveConsentVariousColumns(consents.get(0).getConsent_which_department(), consents.get(0).getConsent_id(), 
						BCITBVariousVariables.verbal_consent_document_file.replace("_file", "_id"), "");
				consents.get(0).setVerbal_consent_document_id(0);
			}
		}
		if(consents.get(0).getWithdrawal_document() != null) {
			if (consents.get(0).getWithdrawal_document().getFile_name() != null && !consents.get(0).getWithdrawal_document().getFile_name().isEmpty()) {
				if (consents.get(0).getWithdrawal_document_id() != null && consents.get(0).getWithdrawal_document_id() > 0) {
					old_file = decryptFileData(consents.get(0).getConsent_which_department(), fileService.getFile(consents.get(0).getConsent_which_department(), 
							consents.get(0).getWithdrawal_document_id(), BCITBVariousVariables.withdrawal_document_file));
					diff = old_file.diff(consents.get(0).getWithdrawal_document());
					if (diff.getDiffs().size() > 0) { // Save file only when something has changed							
						consents.get(0).getWithdrawal_document().setFile_id(consents.get(0).getWithdrawal_document_id()); // If file already exist then get old file id so record can be updated
					}
				}
				consents.get(0).setWithdrawal_document_id(fileService.saveOrUpdateFileAndReturnFileId(consents.get(0).getConsent_which_department(), 
						consents.get(0).getWithdrawal_document()));
			}
		} else {
			if (consents.get(0).getWithdrawal_document_id() != null && consents.get(0).getWithdrawal_document_id() > 0) { // If user has removed an existing file then delete it from DB
				fileService.deleteFile(consents.get(0).getConsent_which_department(), consents.get(0).getWithdrawal_document_id());
				consentService.saveConsentVariousColumns(consents.get(0).getConsent_which_department(), consents.get(0).getConsent_id(), 
						BCITBVariousVariables.withdrawal_document_file.replace("_file", "_id"), "");
				consents.get(0).setWithdrawal_document_id(0);
			}
		}
		
		List<Consent> old_consents = new ArrayList<Consent>();
		old_consents.add(consentService.getPatientConsent(consents.get(0).getConsent_which_department(), consents.get(0).getConsent_id(), "", "",locations));
		old_consents = populateVariousBitsOfConsents(consents.get(0).getConsent_which_department(), "ALL", old_consents);
		
		boolean diffFound = false;
		if(consents.get(0).getConsent_exclusions().length() != old_consents.get(0).getConsent_exclusions().length())
			diffFound = true;
		else 
			for(String new_con_term_id:consents.get(0).getConsent_exclusions().split(",")) 
				if (!old_consents.get(0).getConsent_exclusions().contains(new_con_term_id))
					diffFound = true;
		
		List<String> old_values = new ArrayList<String>();
		List<String> new_values = new ArrayList<String>();

		if(diffFound == true) {
			old_values.clear();new_values.clear();
			for(String con_term_id:old_consents.get(0).getConsent_exclusions().split(",")) 
				if(!con_term_id.trim().isEmpty())
					old_values.add(consentService.getConsentTerm(consents.get(0).getConsent_which_department(), Integer.parseInt(con_term_id)).getDescription());
			for(String con_term_id:consents.get(0).getConsent_exclusions().split(","))
				if(!con_term_id.trim().isEmpty())
					new_values.add(consentService.getConsentTerm(consents.get(0).getConsent_which_department(), Integer.parseInt(con_term_id)).getDescription());
			for(String old_con_term_id:old_consents.get(0).getConsent_exclusions().split(",")) 
				if (!consents.get(0).getConsent_exclusions().contains(old_con_term_id))
					consentService.deleteConsentExclusion(consents.get(0).getConsent_which_department(),consents.get(0).getConsent_id(),Integer.parseInt(old_con_term_id));
			consentService.insertNewConsentExclusions(consents.get(0).getConsent_which_department(), consents.get(0));
			if(consents.get(0).getSam_coll_before_sep_2006() != null && consents.get(0).getSam_coll_before_sep_2006().equalsIgnoreCase(BCITBVariousVariables.yes))
				timelineService.saveTimeline(consents.get(0).getConsent_which_department(), consents.get(0).getConsent_id(), whichUser.getUser_id(), whichRole.getRole_id(), BCITBVariousVariables.edit, 
						getDifferenceBetweenTwoObjects("TWO-STRING-ARRAYS", "Consent Exclusion",null,old_values,new_values), 
						DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()), BCITBVariousVariables.consent_exclusions,
						"Edits: Consent (sample collected before Sep 2006) exclusion list edited on " + 
						DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + " by " + whichUser.getUsername() + 
						" [" + whichRole.getRole_description() + "]",BCITBVariousVariables.no, consents.get(0).getCn_patient_id());
			else
				timelineService.saveTimeline(consents.get(0).getConsent_which_department(), consents.get(0).getConsent_id(), whichUser.getUser_id(), whichRole.getRole_id(), BCITBVariousVariables.edit, 
						getDifferenceBetweenTwoObjects("TWO-STRING-ARRAYS", "Consent Exclusion",null,old_values,new_values), 
						DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()), BCITBVariousVariables.consent_exclusions,
						"Edits: Consent dated [" + new SimpleDateFormat("dd-MM-yyyy").format(
								new SimpleDateFormat("yyyy-MM-dd").parse(consents.get(0).getDate_of_consent())) + "] exclusion list edited on " + 
						DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + " by " + whichUser.getUsername() + 
						" [" + whichRole.getRole_description() + "]",BCITBVariousVariables.no, consents.get(0).getCn_patient_id());
		}
		
		switch (consents.get(0).getConsent_which_department().toUpperCase()) {
		case BCITBVariousVariables.HOTB:
			diffFound = false;
			if(consents.get(0).getSamples_consented_to().length() != old_consents.get(0).getSamples_consented_to().length())
				diffFound = true;
			else 
				for(String new_con_sample_id:consents.get(0).getSamples_consented_to().split(",")) 
					if (!old_consents.get(0).getSamples_consented_to().contains(new_con_sample_id))
						diffFound = true;
			if(diffFound == true) {
				old_values.clear();new_values.clear();
				for(String con_sample_id:old_consents.get(0).getSamples_consented_to().split(","))
					old_values.add(consentService.getConsentSampleType(consents.get(0).getConsent_which_department(), Integer.parseInt(con_sample_id)).getDescription());
				for(String con_sample_id:consents.get(0).getSamples_consented_to().split(","))
					new_values.add(consentService.getConsentSampleType(consents.get(0).getConsent_which_department(), Integer.parseInt(con_sample_id)).getDescription());
				for(String old_con_sample_id:old_consents.get(0).getSamples_consented_to().split(",")) 
					if (!consents.get(0).getSamples_consented_to().contains(old_con_sample_id))
						consentService.deleteConsentedSamples(consents.get(0).getConsent_which_department(),consents.get(0).getConsent_id(),Integer.parseInt(old_con_sample_id));
				consentService.insertNewConsentedSamples(consents.get(0).getConsent_which_department(), consents.get(0));
				if(consents.get(0).getSam_coll_before_sep_2006() != null && consents.get(0).getSam_coll_before_sep_2006().equalsIgnoreCase(BCITBVariousVariables.yes))
					timelineService.saveTimeline(consents.get(0).getConsent_which_department(), consents.get(0).getConsent_id(), whichUser.getUser_id(), whichRole.getRole_id(), BCITBVariousVariables.edit, 
							getDifferenceBetweenTwoObjects("TWO-STRING-ARRAYS", "Consented Samples",null,old_values,new_values),
							DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()), BCITBVariousVariables.consented_samples,
							"Edits: Consent (sample collected before Sep 2006) samples list edited on " + 
									DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + " by " + whichUser.getUsername() + 
									" [" + whichRole.getRole_description() + "]",BCITBVariousVariables.no, consents.get(0).getCn_patient_id());
				else
					timelineService.saveTimeline(consents.get(0).getConsent_which_department(), consents.get(0).getConsent_id(), whichUser.getUser_id(), whichRole.getRole_id(), BCITBVariousVariables.edit, 
							getDifferenceBetweenTwoObjects("TWO-STRING-ARRAYS", "Consented Samples",null,old_values,new_values),
							DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()), BCITBVariousVariables.consented_samples,
							"Edits: Consent dated " + new SimpleDateFormat("dd-MM-yyyy").format(
									new SimpleDateFormat("yyyy-MM-dd").parse(consents.get(0).getDate_of_consent())) + "] samples list edited on " + 
									DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + " by " + whichUser.getUsername() + 
									" [" + whichRole.getRole_description() + "]",BCITBVariousVariables.no, consents.get(0).getCn_patient_id());
			}
			break;
		}

		consents = populateVariousBitsOfConsents(consents.get(0).getConsent_which_department(), "ALL", consents);

		diff = old_consents.get(0).diff(consents.get(0));
		if (diff.getDiffs().size() > 0) { // Save consent only when something has changed
			consentService.updateConsentUsingDiff(consents.get(0).getConsent_which_department(), consents.get(0).getConsent_id(), diff);
			if(consents.get(0).getSam_coll_before_sep_2006() != null && consents.get(0).getSam_coll_before_sep_2006().equalsIgnoreCase(BCITBVariousVariables.yes))
				timelineService.saveTimeline(consents.get(0).getConsent_which_department(), consents.get(0).getConsent_id(), whichUser.getUser_id(), whichRole.getRole_id(), BCITBVariousVariables.edit, 
						getDifferenceBetweenTwoObjects("","",diff,null,null),DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()), BCITBVariousVariables.consent,
						"Edits: Consent (sample collected before Sep 2006) fields edited on " + DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + 
						" by " + whichUser.getUsername() + " [" + whichRole.getRole_description() + "]",BCITBVariousVariables.no, consents.get(0).getCn_patient_id());
			else
				timelineService.saveTimeline(consents.get(0).getConsent_which_department(), consents.get(0).getConsent_id(), whichUser.getUser_id(), whichRole.getRole_id(), BCITBVariousVariables.edit, 
						getDifferenceBetweenTwoObjects("","",diff,null,null),DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()), BCITBVariousVariables.consent,
						"Edits: Consent dated [" + new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(consents.get(0).getDate_of_consent())) + 
						"] fields edited on " + DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + 
						" by " + whichUser.getUsername() + " [" + whichRole.getRole_description() + "]",BCITBVariousVariables.no, consents.get(0).getCn_patient_id());
			
		}
		
		processAction(BCITBVariousVariables.consent, "", whichUser, whichRole, consents, null, null, null, locations, patient, columnName, columnValue);
		
		return consents;
		
	}
	
	@RequestMapping(value = {"/update_consent"}, method=RequestMethod.POST)
	public String updateConsent(ModelMap model,
			@ModelAttribute("user") User user,
			@ModelAttribute("primaryRole") Role primaryRole,
			@ModelAttribute("session_patient") Patient session_patient,
			@ModelAttribute("columnName") List<String> columnName,
			@ModelAttribute("columnValue") List<String> columnValue,
			@ModelAttribute("session_consents") List<Consent> session_consents,
			@ModelAttribute("various_actions") VariousActions various_actions,
			@ModelAttribute("existing_audit_consents") List<Consent> existing_audit_consents,
			@ModelAttribute("user_selected_locations") List<Location> user_selected_locations,
			@ModelAttribute("user_selected_department") Department user_selected_department) throws ParseException
	{
		if (!(user.getUser_id() > 0)) { // session timed out
			return "redirect:/timed_out";
		} else {
			
			session_patient = patientService.getPatientFromID(user_selected_department.getDept_acronym(), session_consents.get(0).getCn_patient_id());
			
			updateConsent(user, primaryRole,session_consents,user_selected_locations,session_patient,columnName,columnValue);
		 	session_patient = updatePatientField(user_selected_department.getDept_acronym(), BCITBVariousVariables.secondary_id, session_patient);

			model.addAttribute("whichPageToShow", "add_patient"); 
			model.addAttribute("user", user);
			model.addAttribute("primaryRole", primaryRole);
			model.addAttribute("various_actions", various_actions);
			if(primaryRole.getUserRoleAccess(BCITBVariousVariables.audit).toLowerCase().contains(BCITBVariousVariables.edit)) 
				model.addAttribute("existing_audit_consents", existing_audit_consents);
			model.addAttribute("consent_access", primaryRole.getUserRoleAccess(BCITBVariousVariables.consent));
			model.addAttribute("user_selected_department", user_selected_department);
			model.addAttribute("patient", session_patient);
			
			return "index";

		}
	}	
	
	public List<InfectionRisk> updateInfectionRisk(Patient patient, User whichUser, Role whichRole, String whichURL, 
			List<InfectionRisk> infection_risks, List<Action> active_actions, List<Location> locations, boolean process_action,
			List<String> columnName, List<String> columnValue) throws ParseException
	{
		
		DiffResult diff;
		InfectionRisk old_ir;
		List<InfectionRisk> previous_irs = new ArrayList<InfectionRisk>(
				infectionRiskService.getPatientInfectionRisks("", patient.getPatient_which_department(), patient.getPatient_id()));
		
		for(InfectionRisk new_ir: infection_risks) {
			old_ir = infectionRiskService.getInfectionRisk(new_ir.getIr_which_department(), new_ir.getInfection_risk_id());
			new_ir = infectionRiskService.populateInfectionRiskVariousBits(new_ir, patient.getPatient_which_department(), "INFECTION-TYPE");
			new_ir = infectionRiskService.populateInfectionRiskVariousBits(new_ir, patient.getPatient_which_department(), "SAMPLE-TYPE");
			if(old_ir != null) {
				if(!new_ir.getInfection_risk_exist().toLowerCase().contains(BCITBVariousVariables.unknown) 
						&& !new_ir.getInfection_risk_exist().toLowerCase().contains(BCITBVariousVariables.yes)) {
					infectionRiskService.updateInfectionRiskUsingObject(new_ir.getIr_which_department(), 
							new InfectionRisk(new_ir.getInfection_risk_id(), new_ir.getInfection_risk_exist(), 
							new_ir.getIr_patient_id(), BCITBVariousVariables.active, new_ir.getInfection_risk_notes()));
					timelineService.saveTimeline(new_ir.getIr_which_department(), new_ir.getInfection_risk_id(), whichUser.getUser_id(), whichRole.getRole_id(), BCITBVariousVariables.edit, 
							getDifferenceBetweenTwoObjects("","",old_ir.diff(new InfectionRisk(new_ir.getInfection_risk_id(), new_ir.getInfection_risk_exist(), 
							new_ir.getIr_patient_id(), BCITBVariousVariables.active, new_ir.getInfection_risk_notes())),null,null), 
							DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()), BCITBVariousVariables.infection_risk,
							"Edits: Infection Risk [" + new_ir.getInfection_risk_exist() + "] fields edited on " + 
									DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + " by " + whichUser.getUsername() + 
									" [" + whichRole.getRole_description() + "]",BCITBVariousVariables.no, new_ir.getIr_patient_id());
				} else {
					diff = old_ir.diff(new_ir);
					if (diff.getDiffs().size() > 0) { // Save IR only when something has changed
						infectionRiskService.updateInfectionRiskUsingDiff(new_ir.getIr_which_department(), new_ir.getInfection_risk_id(), diff);
						timelineService.saveTimeline(new_ir.getIr_which_department(), new_ir.getInfection_risk_id(), whichUser.getUser_id(), whichRole.getRole_id(), BCITBVariousVariables.edit, 
								getDifferenceBetweenTwoObjects("","",diff,null,null),DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()), BCITBVariousVariables.infection_risk,
								"Edits: Infection Risk [" + new_ir.getInfection_risk_exist() + "] fields edited on " + DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + 
								" by " + whichUser.getUsername() + " [" + whichRole.getRole_description() + "]",BCITBVariousVariables.no, new_ir.getIr_patient_id());
					}
				}
			} else {
				if(new_ir.getInfection_risk_exist() != null) {
					new_ir.setIr_patient_id(patient.getPatient_id()); // Set the new or existing patient ID
					new_ir.setStatus(BCITBVariousVariables.active);
					new_ir = infectionRiskService.insertNewInfectionRisk(new_ir.getIr_which_department(), new_ir);
					timelineService.saveTimeline(new_ir.getIr_which_department(), new_ir.getInfection_risk_id(), whichUser.getUser_id(), whichRole.getRole_id(), BCITBVariousVariables.add, 
							new_ir.toString(),DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()), BCITBVariousVariables.infection_risk,
							"Added: Infection Risk [" + new_ir.getInfection_risk_exist() + "] on " + 
									DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + " by " + whichUser.getUsername() + 
									" [" + whichRole.getRole_description() + "]",BCITBVariousVariables.no, new_ir.getIr_patient_id());
				}
			}
		}

		if(process_action == true)
			processAction(BCITBVariousVariables.infection_risk, whichURL, whichUser, whichRole, null, 
					infection_risks, previous_irs, active_actions, locations, patient, columnName, columnValue);

		return infection_risks;
	}

	public List<ConsentAuditSample> updateConsentAuditSamples(User whichUser, Role whichRole, ConsentAudit consent_audit, int patient_id) throws ParseException
	{
		
		if(consent_audit.getSample_types() != null) {
			
			DiffResult diff;
			ConsentAuditSample old_cas;

			for(ConsentAuditSample new_cas: consent_audit.getSample_types()) {
				old_cas = consentService.getConsentAuditSample(consent_audit.getCa_which_department(), new_cas.getAud_sample_pid());
				if(old_cas != null) {
					diff = old_cas.diff(new_cas);
					if (diff.getDiffs().size() > 0) { // Save sample only when something has changed
						consentService.updateConsentAuditSampleUsingDiff(consent_audit.getCa_which_department(), new_cas.getAud_sample_pid(), diff);
						timelineService.saveTimeline(consent_audit.getCa_which_department(), new_cas.getAud_sample_pid(), whichUser.getUser_id(), whichRole.getRole_id(), BCITBVariousVariables.edit, 
								getDifferenceBetweenTwoObjects("","",diff,null,null),DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()), BCITBVariousVariables.consent_audit_sample,
								"Edits: Audit Sample [" + new_cas.getAud_sample_id() + "] fields edited on " + DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + 
								" by " + whichUser.getUsername() + " [" + whichRole.getRole_description() + "]",BCITBVariousVariables.no, patient_id);
					}
				} else {
					new_cas = consentService.insertNewConsentAuditSample(consent_audit.getCa_which_department(), consent_audit.getConsent_audit_id(), new_cas);
					timelineService.saveTimeline(consent_audit.getCa_which_department(), new_cas.getAud_sample_pid(), whichUser.getUser_id(), whichRole.getRole_id(), BCITBVariousVariables.edit, 
							new_cas.toString(),DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()), BCITBVariousVariables.consent_audit_sample,
							"Added: Audit Sample [" + new_cas.getAud_sample_id() + "] on " + DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + 
							" by " + whichUser.getUsername() + " [" + whichRole.getRole_description() + "]",BCITBVariousVariables.no, patient_id);
				}
			}
		}
		return consent_audit.getSample_types();
	}
	
	public ConsentAudit updateConsentAudit(User user, Role role, ConsentAudit consent_audit, Consent consent) throws ParseException
	{
		AuditProcess aud_pro = auditProcessService.getAuditProcess();
		if(consent_audit.getAudit_triggered_date() == null && aud_pro != null && aud_pro.getAudit_process_date() != null) {
			consent_audit.setAudit_triggered_date(aud_pro.getAudit_process_date());
		}

		ConsentAudit return_consent_audit = new ConsentAudit(consent_audit.getCa_consent_id(), consent_audit.getCa_which_department());
		ConsentAudit old_con_audit = consentService.getConsentAudit(consent_audit.getCa_which_department(), consent_audit.getCa_consent_id());
		
		if(old_con_audit ==  null) {
			return_consent_audit = consentService.insertNewConsentAudit(consent_audit.getCa_which_department(), consent_audit);
			if(consent.getSam_coll_before_sep_2006() != null && consent.getSam_coll_before_sep_2006().equalsIgnoreCase(BCITBVariousVariables.yes))
				timelineService.saveTimeline(consent_audit.getCa_which_department(), consent_audit.getConsent_audit_id(), user.getUser_id(), role.getRole_id(), BCITBVariousVariables.add, 
						consent_audit.toString(),DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()), BCITBVariousVariables.consent_audit,
						"Added: Audit consent (sample collected before Sep 2006) on " + 
								DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + " by " + user.getUsername() + 
								" [" + role.getRole_description() + "]",BCITBVariousVariables.no, consent.getCn_patient_id());
			else
				timelineService.saveTimeline(consent_audit.getCa_which_department(), consent_audit.getConsent_audit_id(), user.getUser_id(), role.getRole_id(), BCITBVariousVariables.add, 
						consent_audit.toString(),DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()), BCITBVariousVariables.consent_audit,
						"Added: Audit consent dated on " + new SimpleDateFormat("dd-MM-yyyy").format(
								new SimpleDateFormat("yyyy-MM-dd").parse(consent.getDate_of_consent())) + " on " + 
								DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + " by " + user.getUsername() + 
								" [" + role.getRole_description() + "]",BCITBVariousVariables.no, consent.getCn_patient_id());
			
		} else {
			DiffResult diff = old_con_audit.diff(consent_audit);
			if (diff.getDiffs().size() > 0) { // Save Consent validation only when something has changed
				consentService.updateConsentAuditUsingDiff(consent_audit.getCa_which_department(), consent_audit.getConsent_audit_id(), diff);
				if(consent.getSam_coll_before_sep_2006() != null && consent.getSam_coll_before_sep_2006().equalsIgnoreCase(BCITBVariousVariables.yes))
					timelineService.saveTimeline(consent_audit.getCa_which_department(), consent_audit.getConsent_audit_id(), user.getUser_id(), role.getRole_id(), BCITBVariousVariables.edit, 
							getDifferenceBetweenTwoObjects("","",diff,null,null),DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()), BCITBVariousVariables.consent_audit,
							"Edits: Audit consent (sample collected before Sep 2006) on " + 
							DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + " by " + user.getUsername() + 
							" [" + role.getRole_description() + "]",BCITBVariousVariables.no, consent.getCn_patient_id());
				else
					timelineService.saveTimeline(consent_audit.getCa_which_department(), consent_audit.getConsent_audit_id(), user.getUser_id(), role.getRole_id(), BCITBVariousVariables.edit, 
							getDifferenceBetweenTwoObjects("","",diff,null,null),DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()), BCITBVariousVariables.consent_audit,
							"Edits: Audit consent dated on " + new SimpleDateFormat("dd-MM-yyyy").format(
							new SimpleDateFormat("yyyy-MM-dd").parse(consent.getDate_of_consent())) + " on " + 
							DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + " by " + user.getUsername() + 
							" [" + role.getRole_description() + "]",BCITBVariousVariables.no, consent.getCn_patient_id());
			}
			return_consent_audit = consent_audit;
		}
		return return_consent_audit;
	}
	
	public ConsentValidate updateConsentValidate(User user, Role role, ConsentValidate consent_validate, Consent consent) throws ParseException
	{
		ConsentValidate return_consent_validate = new ConsentValidate();
		
		ConsentValidate old_con_valid = consentService.getConsentValidate(consent_validate.getCv_which_department(), consent_validate.getCv_consent_id());
		
		if(old_con_valid ==  null) {
			return_consent_validate = consentService.insertNewConsentValidate(consent_validate.getCv_which_department(), consent_validate);
			if(consent.getSam_coll_before_sep_2006() != null && consent.getSam_coll_before_sep_2006().equalsIgnoreCase(BCITBVariousVariables.yes))
				timelineService.saveTimeline(consent_validate.getCv_which_department(), consent_validate.getConsent_validate_id(), user.getUser_id(), role.getRole_id(), BCITBVariousVariables.add, 
						consent_validate.toString(),DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()), BCITBVariousVariables.consent_validate,
						"Added: Validate consent (sample collected before Sep 2006) on " + 
								DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + " by " + user.getUsername() + 
								" [" + role.getRole_description() + "]",BCITBVariousVariables.no, consent.getCn_patient_id());
			else
				timelineService.saveTimeline(consent_validate.getCv_which_department(), consent_validate.getConsent_validate_id(), user.getUser_id(), role.getRole_id(), BCITBVariousVariables.add, 
						consent_validate.toString(),DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()), BCITBVariousVariables.consent_validate,
						"Added: Validate consent dated on " + new SimpleDateFormat("dd-MM-yyyy").format(
								new SimpleDateFormat("yyyy-MM-dd").parse(consent.getDate_of_consent())) + " on " + 
								DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + " by " + user.getUsername() + 
								" [" + role.getRole_description() + "]",BCITBVariousVariables.no, consent.getCn_patient_id());
		} else {
			DiffResult diff = old_con_valid.diff(consent_validate);
			if (diff.getDiffs().size() > 0) { // Save Consent validation only when something has changed
				consentService.updateConsentValidateUsingDiff(consent_validate.getCv_which_department(), consent_validate.getConsent_validate_id(), diff);
				if(consent.getSam_coll_before_sep_2006() != null && consent.getSam_coll_before_sep_2006().equalsIgnoreCase(BCITBVariousVariables.yes))
					timelineService.saveTimeline(consent_validate.getCv_which_department(), consent_validate.getConsent_validate_id(), user.getUser_id(), role.getRole_id(), BCITBVariousVariables.edit, 
							getDifferenceBetweenTwoObjects("","",diff,null,null),DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()), BCITBVariousVariables.consent_validate,
							"Edits: Validate consent (sample collected before Sep 2006) on " + 
									DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + " by " + user.getUsername() + 
									" [" + role.getRole_description() + "]",BCITBVariousVariables.no, consent.getCn_patient_id());
				else
					timelineService.saveTimeline(consent_validate.getCv_which_department(), consent_validate.getConsent_validate_id(), user.getUser_id(), role.getRole_id(), BCITBVariousVariables.edit, 
							getDifferenceBetweenTwoObjects("","",diff,null,null),DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()), BCITBVariousVariables.consent_validate,
							"Edits: Validate consent dated on " + new SimpleDateFormat("dd-MM-yyyy").format(
									new SimpleDateFormat("yyyy-MM-dd").parse(consent.getDate_of_consent())) + " on " + 
									DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + " by " + user.getUsername() + 
									" [" + role.getRole_description() + "]",BCITBVariousVariables.no, consent.getCn_patient_id());
			}
		}
		return return_consent_validate;
	}

	@RequestMapping(value = {"/update_infection_risk","/update_infection_risk_confirm_om_notification",
			"/update_infection_risk_confirm_tech_notification","/update_infection_risk_unknown",
			"/update_infection_risk_unknown_confirm_tech_notification"}, method=RequestMethod.POST)
	public String updateInfectionRisk(ModelMap model,HttpServletRequest request,
			@ModelAttribute("user") User user,
			@ModelAttribute("primaryRole") Role primaryRole,
			@ModelAttribute("session_patient") Patient session_patient,
			@ModelAttribute("session_infection_risks") List<InfectionRisk> session_infection_risks,
			@ModelAttribute("various_actions") VariousActions various_actions,
			@ModelAttribute("existing_audit_consents") List<Consent> existing_audit_consents,
			@ModelAttribute("columnName") List<String> columnName,
			@ModelAttribute("columnValue") List<String> columnValue,
			@ModelAttribute("user_selected_locations") List<Location> user_selected_locations,
			@ModelAttribute("user_selected_department") Department user_selected_department) throws ParseException
	{
		if (!(user.getUser_id() > 0)) // session timed out
			return "redirect:/timed_out";
		else {
			
			for(InfectionRisk ir:session_infection_risks) {
				session_patient = patientService.getPatientFromID(ir.getIr_which_department(), ir.getIr_patient_id());
				session_patient.setPatient_id(ir.getIr_patient_id());
				session_patient.setPatient_which_department(ir.getIr_which_department());
			}
			user_selected_department = departmentService.getDepartmentByDeptAcronym(session_patient.getPatient_which_department());

			if (request.getServletPath().equalsIgnoreCase("/update_infection_risk")) {
				updateInfectionRisk(session_patient, user, primaryRole, request.getRequestURI(), 
						session_infection_risks, various_actions.getActive_actions(), user_selected_locations, true, columnName, columnValue);
			} else {
				various_actions.setActive_actions(processAction(BCITBVariousVariables.infection_risk, request.getServletPath(), user, primaryRole, null, 
						session_infection_risks, session_infection_risks, various_actions.getActive_actions(), user_selected_locations, session_patient, columnName, columnValue));
			}
			
			model.addAttribute("whichPageToShow", "add_patient"); 
			model.addAttribute("user", user);
			model.addAttribute("primaryRole", primaryRole);
			if (request.getServletPath().equalsIgnoreCase("/update_infection_risk_confirm_om_notification") 
					|| request.getServletPath().equalsIgnoreCase("/update_infection_risk_confirm_tech_notification")
					|| request.getServletPath().equalsIgnoreCase("/update_infection_risk_unknown")
					|| request.getServletPath().equalsIgnoreCase("/update_infection_risk_unknown_confirm_tech_notification")) {
				model.addAttribute("menuToShow", "ACTION");
			}
			model.addAttribute("various_actions", various_actions);
			if(primaryRole.getUserRoleAccess(BCITBVariousVariables.audit).toLowerCase().contains(BCITBVariousVariables.edit)) 
				model.addAttribute("existing_audit_consents", existing_audit_consents);
			model.addAttribute("consent_access", primaryRole.getUserRoleAccess(BCITBVariousVariables.consent));
			model.addAttribute("user_selected_department", user_selected_department); 
			model.addAttribute("patient", session_patient);
			return "index";

		}
	}	

	@RequestMapping(value = {"/finalise_imported_consent","/finalise_audit_consent"}, method=RequestMethod.POST)
	public String finaliseImportedConsent(ModelMap model, HttpServletRequest request,
			@ModelAttribute("user") User user,
			@ModelAttribute("primaryRole") Role primaryRole,
			@ModelAttribute("session_patient") Patient session_patient,
			@ModelAttribute("session_consents") List<Consent> session_consents,
			@ModelAttribute("session_consent_validate") ConsentValidate session_consent_validate,
			@ModelAttribute("various_actions") VariousActions various_actions,
			@ModelAttribute("existing_audit_consents") List<Consent> existing_audit_consents,
			@ModelAttribute("imported_consents") List<Consent> imported_consents,
			@ModelAttribute("user_selected_locations") List<Location> user_selected_locations,
			@ModelAttribute("user_selected_department") Department user_selected_department) throws ParseException
	{
		if (!(user.getUser_id() > 0)) // session timed out
			return "redirect:/timed_out";
		else {
			
			session_consents = populateVariousBitsOfConsents(session_consents.get(0).getConsent_which_department(), "ALL", session_consents);
			final int my_con_id = session_consents.get(0).getConsent_id();
			
			if(request.getServletPath().equalsIgnoreCase("/finalise_imported_consent")) {
				
				consentService.saveConsentVariousColumns(session_consents.get(0).getConsent_which_department(), 
						session_consents.get(0).getConsent_id(), BCITBVariousVariables.is_finalised, BCITBVariousVariables.yes);

				DiffResult diff = consentService.getPatientConsent(session_consents.get(0).getConsent_which_department(), 
						session_consents.get(0).getConsent_id(), "", "", null).diff(session_consents.get(0));
				if(session_consents.get(0).getSam_coll_before_sep_2006() != null && session_consents.get(0).getSam_coll_before_sep_2006().equalsIgnoreCase(BCITBVariousVariables.yes))
					timelineService.saveTimeline(session_consents.get(0).getConsent_which_department(), session_consents.get(0).getConsent_id(), user.getUser_id(), 
							primaryRole.getRole_id(), BCITBVariousVariables.edit, getDifferenceBetweenTwoObjects("","",diff,null,null), 
							DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()), BCITBVariousVariables.consent,
							"Edit: Imported consent (sample collected before Sep 2006) finalised on " + DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + 
							" by " + user.getUsername() + " [" + primaryRole.getRole_description() + "]",BCITBVariousVariables.no, session_consents.get(0).getCn_patient_id());
				else
					timelineService.saveTimeline(session_consents.get(0).getConsent_which_department(), session_consents.get(0).getConsent_id(), user.getUser_id(), 
							primaryRole.getRole_id(), BCITBVariousVariables.edit, getDifferenceBetweenTwoObjects("","",diff,null,null), 
							DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()), BCITBVariousVariables.consent,
							"Edit: Imported consent dated " + new SimpleDateFormat("dd-MM-yyyy").format(
							new SimpleDateFormat("yyyy-MM-dd").parse(session_consents.get(0).getDate_of_consent())) + " finalised on " + 
							DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + " by " + user.getUsername() + 
							" [" + primaryRole.getRole_description() + "]",BCITBVariousVariables.no, session_consents.get(0).getCn_patient_id());
				
				imported_consents.removeIf(b -> b.getConsent_id() == my_con_id);
				
			} else if(request.getServletPath().equalsIgnoreCase("/finalise_audit_consent")) {
				
				consentService.saveConsentVariousColumns(session_consents.get(0).getConsent_which_department(), 
						session_consents.get(0).getConsent_id(), BCITBVariousVariables.marked_for_auditing, "");
				consentService.saveConsentVariousColumns(session_consents.get(0).getConsent_which_department(), 
						session_consents.get(0).getConsent_id(), BCITBVariousVariables.is_audited, BCITBVariousVariables.yes);
				existing_audit_consents.removeIf(b -> b.getConsent_id() == my_con_id);

			}

			if(session_patient.getSecondary_id() == null)
				session_patient = patientService.getPatientFromID(user_selected_department.getDept_acronym(), session_consents.get(0).getCn_patient_id());
			
		 	session_patient = updatePatientField(user_selected_department.getDept_acronym(), BCITBVariousVariables.secondary_id, session_patient);

			model.addAttribute("whichPageToShow", "add_patient"); 
			model.addAttribute("user", user);
			model.addAttribute("primaryRole", primaryRole);
			model.addAttribute("various_actions", various_actions);
			if(primaryRole.getUserRoleAccess(BCITBVariousVariables.audit).toLowerCase().contains(BCITBVariousVariables.edit)) 
				model.addAttribute("existing_audit_consents", existing_audit_consents);
			model.addAttribute("consent_access", primaryRole.getUserRoleAccess(BCITBVariousVariables.consent));
			model.addAttribute("user_selected_department", user_selected_department);
			model.addAttribute("patient", session_patient);
			
			return "index";			
		}
	}	
	
	@RequestMapping(value = {"/save_patient_consent_ir"}, method=RequestMethod.POST)
	public String savePatientConsentIr(ModelMap model,HttpServletRequest request,
			@ModelAttribute("user") User user,
			@ModelAttribute("primaryRole") Role primaryRole,
			@ModelAttribute("session_patient") Patient session_patient,
			@ModelAttribute("various_actions") VariousActions various_actions,
			@ModelAttribute("session_consents") List<Consent> session_consents,
			@ModelAttribute("columnName") List<String> columnName,
			@ModelAttribute("columnValue") List<String> columnValue,
			@ModelAttribute("session_infection_risks") List<InfectionRisk> session_infection_risks,
			@ModelAttribute("existing_audit_consents") List<Consent> existing_audit_consents,
			@ModelAttribute("user_selected_locations") List<Location> user_selected_locations,
			@ModelAttribute("user_selected_department") Department user_selected_department) throws ParseException
	{
		if (!(user.getUser_id() > 0)) // session timed out
			return "redirect:/timed_out";
		else {
			
			if(!(session_consents.get(0).getConsent_id() > 0)) { // Do NOT save consent again if user has refreshed the page
				
				DiffResult diff;
				Action action;
				
				if(!(session_patient.getPatient_id() > 0)) { // New patient
					
					session_patient.setStatus(BCITBVariousVariables.active);
					if(session_consents.get(0).getSam_coll_before_sep_2006().equalsIgnoreCase(BCITBVariousVariables.yes)) {
						session_patient.setSecondary_id(patientService.generateSecondaryID(session_patient,""));
						session_patient = patientService.insertNewPatientRecord(user_selected_department.getDept_acronym(),user_selected_department.getDept_prefix(), session_patient); 
					} else {
						session_patient.setSecondary_id(patientService.generateSecondaryID(session_patient,session_consents.get(0).getDate_of_consent()));
						session_patient = patientService.insertNewPatientRecord(user_selected_department.getDept_acronym(),user_selected_department.getDept_prefix(), session_patient); 
					}
					timelineService.saveTimeline(user_selected_department.getDept_acronym(), session_patient.getPatient_id(),user.getUser_id(), primaryRole.getRole_id(), BCITBVariousVariables.add, 
							session_patient.toString(),DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()), BCITBVariousVariables.patient,
							"Added: Patient [" + session_patient.getDatabase_id() + ", " + session_patient.getSecondary_id() + "] on " + 
									DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + " by " + user.getUsername() + 
									" [" + primaryRole.getRole_description() + "]",BCITBVariousVariables.yes, session_patient.getPatient_id());
					
				} else { // Existing patient
					
					Patient old_patient = patientService.getPatientFromID(user_selected_department.getDept_acronym(), session_patient.getPatient_id());
					
					if(old_patient != null) {
						diff = old_patient.diff(session_patient);
						if (diff.getDiffs().size() > 0) { // Save patient only when something has changed
							patientService.updatePatientUsingDiff(user_selected_department.getDept_acronym(), session_patient.getPatient_id(), diff, session_patient.getDatabase_id());
							timelineService.saveTimeline(user_selected_department.getDept_acronym(), session_patient.getPatient_id(), user.getUser_id(), primaryRole.getRole_id(), BCITBVariousVariables.edit, 
									getDifferenceBetweenTwoObjects("","",diff,null,null),DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()), BCITBVariousVariables.patient,
									"Edits: Patient [" + session_patient.getDatabase_id() + ", " + session_patient.getSecondary_id() + "] fields edited on " + 
									DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + " by " + user.getUsername() + 
									" [" + primaryRole.getRole_description() + "]",BCITBVariousVariables.yes, session_patient.getPatient_id());
						
						}
					}
				}
				
				session_patient.setLocked_description("Patient [" + session_patient.getDatabase_id() + "] locked by " 
						+ user.getUser_firstname() + " " + user.getUser_surname() + " on " 
						+ DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()));
				patientService.savePatientVariousColumns(user_selected_department.getDept_acronym(), session_patient.getPatient_id(), 
						BCITBVariousVariables.locked_description, session_patient.getLocked_description());
				session_patient.setLocked_by(user.getUser_id());
				patientService.savePatientVariousColumns(user_selected_department.getDept_acronym(), session_patient.getPatient_id(), 
						BCITBVariousVariables.locked_by, String.valueOf(user.getUser_id()));
				
				if(session_consents.get(0).getDigital_cf_attachment() != null)
					if (session_consents.get(0).getDigital_cf_attachment().getFile_name() != null && !session_consents.get(0).getDigital_cf_attachment().getFile_name().isEmpty()) 
						session_consents.get(0).setDigital_cf_attachment_id(fileService.saveOrUpdateFileAndReturnFileId(user_selected_department.getDept_acronym(), session_consents.get(0).getDigital_cf_attachment()));
				
				if(session_consents.get(0).getAdditional_document() != null)
					if (session_consents.get(0).getAdditional_document().getFile_name() != null && !session_consents.get(0).getAdditional_document().getFile_name().isEmpty()) 
						session_consents.get(0).setAdditional_document_id(fileService.saveOrUpdateFileAndReturnFileId(user_selected_department.getDept_acronym(), session_consents.get(0).getAdditional_document()));
				
				if(session_consents.get(0).getVerbal_consent_document() != null)
					if (session_consents.get(0).getVerbal_consent_document().getFile_name() != null && !session_consents.get(0).getVerbal_consent_document().getFile_name().isEmpty()) 
						session_consents.get(0).setVerbal_consent_document_id(fileService.saveOrUpdateFileAndReturnFileId(user_selected_department.getDept_acronym(), session_consents.get(0).getVerbal_consent_document()));
				
				if(session_consents.get(0).getWithdrawal_document() != null)
					if (session_consents.get(0).getWithdrawal_document().getFile_name() != null && !session_consents.get(0).getWithdrawal_document().getFile_name().isEmpty()) 
						session_consents.get(0).setWithdrawal_document_id(fileService.saveOrUpdateFileAndReturnFileId(user_selected_department.getDept_acronym(), session_consents.get(0).getWithdrawal_document()));
				
				session_consents.get(0).setStatus(BCITBVariousVariables.draft);
				session_consents.get(0).setCn_patient_id(session_patient.getPatient_id());
				session_consents.get(0).setCreation_date(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now()));
				consentService.insertNewConsent(user_selected_department.getDept_acronym(), session_consents.get(0));
				if(session_consents.get(0).getConsent_type() != null && session_consents.get(0).getConsent_type().equalsIgnoreCase(BCITBVariousVariables.partial))
					consentService.insertNewConsentExclusions(user_selected_department.getDept_acronym(), session_consents.get(0));
				switch (user_selected_department.getDept_acronym().toUpperCase()) {
				case BCITBVariousVariables.HOTB:
					consentService.insertNewConsentedSamples(user_selected_department.getDept_acronym(), session_consents.get(0));
					break;
				}
			 	session_patient = updatePatientField(user_selected_department.getDept_acronym(), BCITBVariousVariables.secondary_id, session_patient);
				action = new Action(session_consents.get(0).getConsent_id(),BCITBVariousVariables.consent, 
					BCITBVariousVariables.verify_consent, BCITBVariousVariables.new_consent_need_verfication,BCITBVariousVariables.active,
					DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()),user.getUser_id(),"");
				action.setDescription(populateActionsDescription(user_selected_department.getDept_acronym(), action, session_consents.get(0), session_patient.getDatabase_id()));
	
				actionService.saveAction(user_selected_department.getDept_acronym(), action);
				session_consents = populateVariousBitsOfConsents(user_selected_department.getDept_acronym(), "ALL", session_consents);
				
				if(session_consents.get(0).getSam_coll_before_sep_2006().equalsIgnoreCase(BCITBVariousVariables.yes)) {
					timelineService.saveTimeline(user_selected_department.getDept_acronym(), session_consents.get(0).getConsent_id(),user.getUser_id(), primaryRole.getRole_id(), 
							BCITBVariousVariables.add, session_consents.get(0).toString(),DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()), BCITBVariousVariables.consent,
							"Added: Consent (sample collected before Sep 2006), added on " + DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + 
							" by " + user.getUsername() + " [" + primaryRole.getRole_description() + "]",BCITBVariousVariables.no, session_consents.get(0).getCn_patient_id());
				} else {
					timelineService.saveTimeline(user_selected_department.getDept_acronym(), session_consents.get(0).getConsent_id(),user.getUser_id(), primaryRole.getRole_id(), 
							BCITBVariousVariables.add, session_consents.get(0).toString(),DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()), BCITBVariousVariables.consent,
							"Added: Consent dated " + new SimpleDateFormat("dd-MM-yyyy").format(
									new SimpleDateFormat("yyyy-MM-dd").parse(session_consents.get(0).getDate_of_consent())) + ", added on " + 
									DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + " by " + user.getUsername() + 
									" [" + primaryRole.getRole_description() + "]",BCITBVariousVariables.no, session_consents.get(0).getCn_patient_id());
				}
	
				List<InfectionRisk> previous_irs = new ArrayList<InfectionRisk>(
						infectionRiskService.getPatientInfectionRisks("",user_selected_department.getDept_acronym(), session_patient.getPatient_id()));
				
				boolean new_ir_found = true;
				for(InfectionRisk new_ir: session_infection_risks) {
					new_ir_found = true;
					for(InfectionRisk old_ir: previous_irs) {
						if(old_ir.getInfection_risk_id() == new_ir.getInfection_risk_id()) {
							new_ir_found = false;
							diff = old_ir.diff(new_ir);
							if (diff.getDiffs().size() > 0) { // Save IR only when something has changed
								infectionRiskService.updateInfectionRiskUsingDiff(user_selected_department.getDept_acronym(), new_ir.getInfection_risk_id(), diff);
								new_ir = infectionRiskService.populateInfectionRiskVariousBits(new_ir, user_selected_department.getDept_acronym(), "INFECTION-TYPE");
								timelineService.saveTimeline(user_selected_department.getDept_acronym(), new_ir.getInfection_risk_id(), user.getUser_id(), primaryRole.getRole_id(), BCITBVariousVariables.edit, 
										getDifferenceBetweenTwoObjects("","",diff,null,null),DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()), BCITBVariousVariables.infection_risk,
										"Edits: Infection Risk [" + new_ir.getInfection_risk_exist() + "] on " + DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + 
										" by " + user.getUsername() + " [" + primaryRole.getRole_description() + "]",BCITBVariousVariables.no, new_ir.getIr_patient_id());
							}
						}
					}
					if(new_ir_found == true) {
						if(new_ir.getInfection_risk_exist() != null) {
							new_ir.setIr_patient_id(session_patient.getPatient_id()); // Set the new or existing patient ID
							new_ir.setStatus(BCITBVariousVariables.active);
							new_ir = infectionRiskService.insertNewInfectionRisk(user_selected_department.getDept_acronym(), new_ir);
							timelineService.saveTimeline(user_selected_department.getDept_acronym(), new_ir.getInfection_risk_id(), user.getUser_id(), primaryRole.getRole_id(), BCITBVariousVariables.add, 
									new_ir.toString(),DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()), BCITBVariousVariables.infection_risk,
									"Added: Infection Risk [" + new_ir.getInfection_risk_exist() + "] on " + DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + 
									" by " + user.getUsername() + " [" + primaryRole.getRole_description() + "]",BCITBVariousVariables.no, new_ir.getIr_patient_id());
						}
					}
				}
				processAction(BCITBVariousVariables.infection_risk, request.getRequestURI(), user, primaryRole, null, session_infection_risks, previous_irs, 
						various_actions.getActive_actions(), user_selected_locations, session_patient, columnName, columnValue);
				
				if(session_consents.get(0).getSam_coll_before_sep_2006().equalsIgnoreCase(BCITBVariousVariables.yes)) {
					timelineService.saveTimeline(user_selected_department.getDept_acronym(), session_consents.get(0).getConsent_id(),user.getUser_id(), primaryRole.getRole_id(), 
							BCITBVariousVariables.verify_consent, BCITBVariousVariables.new_consent_need_verfication,
							DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()), BCITBVariousVariables.consent,
							"Action sent: Validate consent (sample collected before Sep 2006). Action sent to Data Officer(s) on " + 
							DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + " by " + user.getUsername() + 
							" [" + primaryRole.getRole_description() + "]",BCITBVariousVariables.no, session_consents.get(0).getCn_patient_id());
				} else {
					timelineService.saveTimeline(user_selected_department.getDept_acronym(), session_consents.get(0).getConsent_id(),user.getUser_id(), primaryRole.getRole_id(), 
							BCITBVariousVariables.verify_consent, BCITBVariousVariables.new_consent_need_verfication,
							DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()), BCITBVariousVariables.consent,
							"Action sent: Validate consent dated " + new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(
							session_consents.get(0).getDate_of_consent())) + ". Action sent to Data Officer(s) on " + DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").
							format(LocalDateTime.now()) + " by " + user.getUsername() + " [" + primaryRole.getRole_description() + "]",
							BCITBVariousVariables.no, session_consents.get(0).getCn_patient_id());
				}
			}
			
			model.addAttribute("whichPageToShow", "add_patient"); 
			model.addAttribute("user", user);
			model.addAttribute("primaryRole", primaryRole);
			model.addAttribute("various_actions", various_actions);
			if(primaryRole.getUserRoleAccess(BCITBVariousVariables.audit).toLowerCase().contains(BCITBVariousVariables.edit)) 
				model.addAttribute("existing_audit_consents", existing_audit_consents);
			model.addAttribute("consent_access", primaryRole.getUserRoleAccess(BCITBVariousVariables.consent));
			model.addAttribute("user_selected_department", user_selected_department);
			model.addAttribute("patient", session_patient);
			
			return "index";
			
		}
	}

	@RequestMapping(value = {"/search","/previous_advanced_search_result_page",
			"/previous_basic_search_result_page"}, method={RequestMethod.GET})
	public String newSearchPatientPage(ModelMap model,HttpServletRequest request,
			@ModelAttribute("user") User user,
			@ModelAttribute("primaryRole") Role primaryRole,
			@ModelAttribute("session_patient") Patient session_patient,
			@ModelAttribute("session_all_patient_data") AllPatientData session_all_patient_data,
			@ModelAttribute("session_advanced_search_sql_script") List<String> session_advanced_search_sql_script,
			@ModelAttribute("session_basic_search_options") List<String> session_basic_search_options,
			@ModelAttribute("user_selected_department") Department user_selected_department) 
	{
		if (!(user.getUser_id() > 0)) // session timed out
			return "redirect:/timed_out";
		else
		{
			session_patient.setPatient_which_department(user_selected_department.getDept_acronym());
			session_patient = unlockPatient(session_patient, user, user_selected_department);
			
			model.addAttribute("whichPageToShow", "search"); 
			model.addAttribute("user", user);
			model.addAttribute("primaryRole", primaryRole);
			model.addAttribute("user_selected_department", user_selected_department);
			model.addAttribute("export_search_result", primaryRole.getUserRoleAccess(BCITBVariousVariables.export_search_result));
			model.addAttribute("basic_search_criterias", 
					new AllPatientData().getSearchColumnNames("",BCITBVariousVariables.basic_search,
					primaryRole.getUserRoleAccess(BCITBVariousVariables.consent)));
			model.addAttribute("advanced_search_criterias", 
					new AllPatientData().getSearchColumnNames("",BCITBVariousVariables.advanced_search,
					primaryRole.getUserRoleAccess(BCITBVariousVariables.consent)));
			model.addAttribute("advanced_search_columns", 
					new AllPatientData().getSearchColumnNames("",BCITBVariousVariables.search_result_columns,
					primaryRole.getUserRoleAccess(BCITBVariousVariables.consent)));
			if(primaryRole.getUserRoleAccess(BCITBVariousVariables.timeline).equalsIgnoreCase(BCITBVariousVariables.view))
				model.addAttribute("consent_access", BCITBVariousVariables.patient + "_" + BCITBVariousVariables.timeline);
			else
				if(primaryRole.getUserRoleAccess(BCITBVariousVariables.consent).equalsIgnoreCase(BCITBVariousVariables.view_restricted))
					model.addAttribute("consent_access", BCITBVariousVariables.view_restricted);
				else 
					model.addAttribute("consent_access", BCITBVariousVariables.patient);
			if(request.getServletPath().equalsIgnoreCase("/previous_advanced_search_result_page")) {
				if(session_advanced_search_sql_script.size() >= 2) {
					model.addAttribute("advanced_search_sql_script", session_advanced_search_sql_script.get(0));
					model.addAttribute("advanced_search_selected_columns", session_advanced_search_sql_script.get(1));
				}
			} else if(request.getServletPath().equalsIgnoreCase("/previous_basic_search_result_page")) {
				if(session_basic_search_options.size() >= 2)
					model.addAttribute("basic_search_options", session_basic_search_options.get(0) + "|" + session_basic_search_options.get(1));
			}
				
			return "index";
		}
	}

	@RequestMapping(value = {"/user_actions","/save_action"}, method= {RequestMethod.GET,RequestMethod.POST})
	public String userActionsPage(ModelMap model,HttpServletRequest request,
			@ModelAttribute("user") User user,
			@ModelAttribute("primaryRole") Role primaryRole,
			@ModelAttribute("session_consents") List<Consent> session_consents,
			@ModelAttribute("session_patient") Patient session_patient,
			@ModelAttribute("various_actions") VariousActions various_actions,
			@ModelAttribute("columnName") List<String> columnName,
			@ModelAttribute("columnValue") List<String> columnValue,
			@ModelAttribute("existing_audit_consents") List<Consent> existing_audit_consents,
			@ModelAttribute("imported_consents") List<Consent> imported_consents,
			@ModelAttribute("user_selected_locations") List<Location> user_selected_locations,
			@ModelAttribute("user_selected_department")  Department user_selected_department) 
					throws ParseException, AddressException, MessagingException 
	{
		if (user.getUser_id() <= 0) // session timed out 
			return "redirect:/timed_out";
		else
		{

			if(request.getServletPath().equalsIgnoreCase("/save_action")) {
				processAction(BCITBVariousVariables.action, "", user, primaryRole, session_consents, null, null, various_actions.getActive_actions(), 
						user_selected_locations, session_patient, columnName, columnValue);
			} else if(request.getServletPath().equalsIgnoreCase("/user_actions")) {
				various_actions = unlockUserActionIfAny(various_actions,columnName,columnValue);	
			}
			if(user_selected_department.getDept_acronym() != null && !user_selected_department.getDept_acronym().isEmpty()
					&& (session_patient.getPatient_which_department() == null || session_patient.getPatient_which_department().isEmpty()))
				session_patient.setPatient_which_department(user_selected_department.getDept_acronym());
			session_patient = unlockPatient(session_patient, user, user_selected_department);
			
			user_selected_department = new Department();
			user_selected_locations.clear();
			various_actions = new VariousActions();
			
			model.addAttribute("whichPageToShow", "user_actions"); 
			model.addAttribute("user", user);
			model.addAttribute("primaryRole", primaryRole);
			model.addAttribute("menuToShow", "ACTION");
			model.addAttribute("various_actions", various_actions);
			model.addAttribute("consent_access", primaryRole.getUserRoleAccess(BCITBVariousVariables.consent));
			model.addAttribute("imported_data", primaryRole.getUserRoleAccess(BCITBVariousVariables.imported));
			model.addAttribute("audit_consent", primaryRole.getUserRoleAccess(BCITBVariousVariables.audit));
			
			processConsents("PROCESS-AUDIT", "");

			if(primaryRole.getUserRoleAccess(BCITBVariousVariables.audit).toLowerCase().contains(BCITBVariousVariables.edit)) {
				existing_audit_consents.clear();
				for(UserDepartment user_dept:user.getUserDepartments())
					for(Consent con:consentService.getPatientConsents(BCITBVariousVariables.marked_for_auditing, user_dept.getDepartment().getDept_acronym(), 
							0, "", "", null, BCITBVariousVariables.ascending)) {
						String db_id = patientService.getPatientFromID(user_dept.getDepartment().getDept_acronym(), 
								con.getCn_patient_id()).getDatabase_id();
						con.setConsent_which_department(user_dept.getDepartment().getDept_acronym());
						if(con.getSam_coll_before_sep_2006() != null && con.getSam_coll_before_sep_2006().equalsIgnoreCase(BCITBVariousVariables.yes))
							con.setDescription("(" + con.getConsent_which_department() + ") Audit consent of " + db_id + ", consent (sample collected before Sep 2006)");
						else
							con.setDescription("(" + con.getConsent_which_department() + ") Audit consent of " + db_id + ", consented on " + con.getDate_of_consent());
						if(consentService.getConsentAudit(con.getConsent_which_department(), con.getConsent_id()) != null) 
							con.setDescription(con.getDescription() + " (PARTIALLY AUDITED)");
						existing_audit_consents.add(con);
					}
				model.addAttribute("existing_audit_consents", existing_audit_consents);
			}
			
			if(primaryRole.getUserRoleAccess(BCITBVariousVariables.imported).toLowerCase().contains(BCITBVariousVariables.edit)) 
				model.addAttribute("imported_consents", imported_consents);
			
			return "index";
			
		}
	}

	@RequestMapping(value = {"/infection_risk_found_notify_om","/infection_risk_found_notify_tech",
			"/infection_risk_unknown_notify_tech","/infection_risk_unknown"}, method={RequestMethod.GET})
	public String correctInfectionRiskPage(ModelMap model, HttpServletRequest request,
			@ModelAttribute("user") User user,
			@ModelAttribute("primaryRole") Role primaryRole,
			@ModelAttribute("session_patient") Patient session_patient,
			@ModelAttribute("various_actions") VariousActions various_actions,
			@ModelAttribute("session_infection_risks") List<InfectionRisk> session_infection_risks,
			@ModelAttribute("existing_audit_consents") List<Consent> existing_audit_consents,
			@ModelAttribute("columnName") List<String> columnName,
			@ModelAttribute("columnValue") List<String> columnValue,
			@RequestParam(value = "selected_data_id", required = false, defaultValue = "0") String selected_data_id,
			@RequestParam(value = "selected_department", required = false, defaultValue = "") String selected_department,
			@ModelAttribute("user_selected_department")  Department user_selected_department) 

	{
		if (!(user.getUser_id() > 0)) // session timed out
			return "redirect:/timed_out";
		else
		{

			String which_dept = "";
			for(Action act:various_actions.getActive_actions()) {
				if(act.getAction_id() == Integer.parseInt(selected_data_id) && act.getAction_which_department().equalsIgnoreCase(selected_department)) {
					which_dept = act.getAction_which_department();
					InfectionRisk this_ir = infectionRiskService.getInfectionRisk(which_dept,act.getData_id());
					this_ir.setIr_which_department(which_dept);
					session_patient = patientService.getPatientFromID(act.getAction_which_department(), this_ir.getIr_patient_id());
					session_patient.setPatient_which_department(which_dept);
					session_infection_risks.clear();
					session_infection_risks.add(this_ir);
					
					columnName.clear();columnValue.clear();
					columnName.add(BCITBVariousVariables.status);columnValue.add(BCITBVariousVariables.locked);
					columnName.add(BCITBVariousVariables.locked_description);columnValue.add("Action [" + act.getDescription() + "] locked by " 
							+ user.getUser_firstname() + " " + user.getUser_surname() + " on " + DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()));
					columnName.add(BCITBVariousVariables.locked_by);columnValue.add(String.valueOf(user.getUser_id()));
					actionService.saveActionVariousColumns(which_dept, act.getAction_id(), columnName, columnValue);
					act.setStatus(BCITBVariousVariables.locked);
					session_patient.setLocked_description("Patient [" + session_patient.getDatabase_id() + "] locked by " 
							+ user.getUser_firstname() + " " + user.getUser_surname() + " on " 
							+ DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()));
					patientService.savePatientVariousColumns(which_dept, session_patient.getPatient_id(), 
							BCITBVariousVariables.locked_description, session_patient.getLocked_description());
					session_patient.setLocked_by(user.getUser_id());
					patientService.savePatientVariousColumns(which_dept, session_patient.getPatient_id(), 
							BCITBVariousVariables.locked_by, String.valueOf(user.getUser_id()));
				}
			}

			if(which_dept.trim().isEmpty())
				user_selected_department = departmentService.getDepartmentByDeptAcronym(which_dept);

			model.addAttribute("whichPageToShow", "pat_ir"); 
			model.addAttribute("user", user);
			model.addAttribute("primaryRole", primaryRole);
			model.addAttribute("menuToShow", "ACTION");
			model.addAttribute("various_actions", various_actions);
			if(primaryRole.getUserRoleAccess(BCITBVariousVariables.audit).toLowerCase().contains(BCITBVariousVariables.edit)) 
				model.addAttribute("existing_audit_consents", existing_audit_consents);
			model.addAttribute("consent_access", primaryRole.getUserRoleAccess(BCITBVariousVariables.consent));
			model.addAttribute("infection_risk", session_infection_risks.get(0));
			model.addAttribute("patient", session_patient);
			model.addAttribute("infection_types", infectionRiskService.getAllInfectionTypes(which_dept));
			model.addAttribute("sampleTypes", infectionRiskService.getAllSampleTypes(which_dept));
			model.addAttribute("user_selected_department", user_selected_department); 

			return "index";
			
		}
	}
	
	@RequestMapping(value = {"/verify_consent","/reapproach_patient","/query_patient", "/consent_withdrawn",
			"/remove_samples","/verify_imported_consent","/audit_consent"}, method={RequestMethod.GET})
	public String correctPatientConsentPage(ModelMap model, HttpServletRequest request,
			@ModelAttribute("user") User user,
			@ModelAttribute("primaryRole") Role primaryRole,
			@ModelAttribute("session_patient") Patient session_patient,
			@ModelAttribute("session_consent_validate") ConsentValidate session_consent_validate,
			@ModelAttribute("various_actions") VariousActions various_actions,
			@ModelAttribute("existing_audit_consents") List<Consent> existing_audit_consents,
			@ModelAttribute("sampleConsentedToJson") String sampleConsentedToJson,
			@ModelAttribute("formTypeVersionConsentTermJson") String formTypeVersionConsentTermJson,
			@ModelAttribute("formVersionJson") String formVersionJson,
			@ModelAttribute("formVersionConsentTermJson") String formVersionConsentTermJson,
			@ModelAttribute("session_consents") List<Consent> session_consents,
			@ModelAttribute("session_consent_audit") ConsentAudit session_consent_audit,
			@RequestParam(value = "selected_data_id", required = false, defaultValue = "0") String selected_data_id,
			@RequestParam(value = "selected_department", required = false, defaultValue = "") String selected_department,
			@ModelAttribute("columnName") List<String> columnName,
			@ModelAttribute("columnValue") List<String> columnValue,
			@ModelAttribute("user_selected_locations") List<Location> user_selected_locations,
			@ModelAttribute("user_selected_department") Department user_selected_department)
	{
		if (!(user.getUser_id() > 0)) // session timed out
			return "redirect:/timed_out";
		else
		{
			int ir_count = 0;
			List<Location> this_locations = new ArrayList<Location>();
			
			if (request.getServletPath().equalsIgnoreCase("/audit_consent")) {
				
				session_consent_audit = consentService.getConsentAudit(selected_department, Integer.parseInt(selected_data_id));
				if(session_consent_audit == null) session_consent_audit = new ConsentAudit(Integer.parseInt(selected_data_id), selected_department);
				session_consent_audit.setCa_consent_id(Integer.parseInt(selected_data_id));
				session_consent_audit.setCa_which_department(selected_department);
				
				user_selected_department = departmentService.getDepartmentByDeptAcronym(selected_department);
				
				session_consents.clear();
				session_consents.add(consentService.getPatientConsent(
						selected_department,Integer.parseInt(selected_data_id),"", "",null));
				
				session_consents.get(0).setConsent_which_department(selected_department);
				session_patient = patientService.getPatientFromID(selected_department, session_consents.get(0).getCn_patient_id());
				session_patient.setPatient_which_department(selected_department);

				session_patient.setLocked_description("Patient [" + session_patient.getDatabase_id() + "] locked by " 
						+ user.getUser_firstname() + " " + user.getUser_surname() + " on " 
						+ DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()));
				patientService.savePatientVariousColumns(selected_department, session_patient.getPatient_id(), 
						BCITBVariousVariables.locked_description, session_patient.getLocked_description());
				session_patient.setLocked_by(user.getUser_id());
				patientService.savePatientVariousColumns(selected_department, session_patient.getPatient_id(), 
						BCITBVariousVariables.locked_by, String.valueOf(user.getUser_id()));
				
			} else if (request.getServletPath().equalsIgnoreCase("/verify_imported_consent")) {

				user_selected_department = departmentService.getDepartmentByDeptAcronym(selected_department);

				session_consents.clear();
				session_consents.add(consentService.getPatientConsent(
						selected_department,Integer.parseInt(selected_data_id),"", "",null));
				session_consents.get(0).setConsent_which_department(selected_department);
				session_patient = patientService.getPatientFromID(selected_department, session_consents.get(0).getCn_patient_id());
				session_patient.setPatient_id(session_consents.get(0).getCn_patient_id());
				session_patient.setPatient_which_department(selected_department);
				
				session_consent_validate.setCv_consent_id(session_consents.get(0).getConsent_id());
				session_consent_validate.setCv_which_department(selected_department);

				session_patient.setLocked_description("Patient [" + session_patient.getDatabase_id() + "] locked by " 
						+ user.getUser_firstname() + " " + user.getUser_surname() + " on " 
						+ DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()));
				patientService.savePatientVariousColumns(selected_department, session_patient.getPatient_id(), 
						BCITBVariousVariables.locked_description, session_patient.getLocked_description());
				session_patient.setLocked_by(user.getUser_id());
				patientService.savePatientVariousColumns(selected_department, session_patient.getPatient_id(), 
						BCITBVariousVariables.locked_by, String.valueOf(user.getUser_id()));
				
				List<Action> this_active_actions = new ArrayList<Action>();
				this_active_actions.add(new Action(session_consents.get(0).getConsent_id(), BCITBVariousVariables.consent, 
						BCITBVariousVariables.verify_consent, BCITBVariousVariables.locked, selected_department));
				various_actions.setActive_actions(this_active_actions);
				
			} else {
				
				for(Action act:various_actions.getActive_actions()) {
					if(act.getAction_id() == Integer.parseInt(selected_data_id) && act.getAction_which_department().equalsIgnoreCase(selected_department)) {
						
						user_selected_department = departmentService.getDepartmentByDeptAcronym(act.getAction_which_department());
						session_consents.clear();
						session_consents.add(consentService.getPatientConsent(act.getAction_which_department(),act.getData_id(),"", "",null));
						session_patient = patientService.getPatientFromID(act.getAction_which_department(), session_consents.get(0).getCn_patient_id());
						session_patient.setPatient_which_department(act.getAction_which_department());
						for(InfectionRisk ir:infectionRiskService.getPatientInfectionRisks("",act.getAction_which_department(), 
								session_consents.get(0).getCn_patient_id()))
							if(ir.getInfection_risk_exist().toLowerCase().contains(BCITBVariousVariables.yes) || 
									ir.getInfection_risk_exist().toLowerCase().contains(BCITBVariousVariables.unknown))
								ir_count = ir_count + 1;
						
						columnName.clear();columnValue.clear();
						columnName.add(BCITBVariousVariables.status);columnValue.add(BCITBVariousVariables.locked);
						columnName.add(BCITBVariousVariables.locked_description);columnValue.add("Action [" + act.getDescription() + "] locked by " 
								+ user.getUser_firstname() + " " + user.getUser_surname() + " on " + DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()));
						columnName.add(BCITBVariousVariables.locked_by);columnValue.add(String.valueOf(user.getUser_id()));
						actionService.saveActionVariousColumns(act.getAction_which_department(), act.getAction_id(), columnName, columnValue);
						act.setStatus(BCITBVariousVariables.locked);
						if (request.getServletPath().equalsIgnoreCase("/verify_consent")) {
							session_consent_validate.setCv_consent_id(session_consents.get(0).getConsent_id());
							session_consent_validate.setCv_which_department(act.getAction_which_department());
						}
						session_patient.setLocked_description("Patient [" + session_patient.getDatabase_id() + "] locked by " 
								+ user.getUser_firstname() + " " + user.getUser_surname() + " on " 
								+ DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()));
						patientService.savePatientVariousColumns(act.getAction_which_department(), session_patient.getPatient_id(), 
								BCITBVariousVariables.locked_description, session_patient.getLocked_description());
						session_patient.setLocked_by(user.getUser_id());
						patientService.savePatientVariousColumns(act.getAction_which_department(), session_patient.getPatient_id(), 
								BCITBVariousVariables.locked_by, String.valueOf(user.getUser_id()));
					}
				}
			}
			for(DepartmentLocation dept_loc:departmentLocationService.getLocationsOfDepartment(user_selected_department))
				this_locations.add(dept_loc.getLocation());
			
			model.addAttribute("whichPageToShow", "pat_con_ir_val"); 
			model.addAttribute("user", user);
			model.addAttribute("primaryRole", primaryRole);
			model.addAttribute("menuToShow", "ACTION");
			model.addAttribute("various_actions", various_actions);
			if(primaryRole.getUserRoleAccess(BCITBVariousVariables.audit).toLowerCase().contains(BCITBVariousVariables.edit)) 
				model.addAttribute("existing_audit_consents", existing_audit_consents);
			model.addAttribute("consent_access", primaryRole.getUserRoleAccess(BCITBVariousVariables.consent));
			if(primaryRole.getUserRoleAccess(BCITBVariousVariables.audit).toLowerCase().contains(BCITBVariousVariables.edit)) 
				model.addAttribute("audit_consent", primaryRole.getUserRoleAccess(BCITBVariousVariables.audit));

			model.addAttribute("session_consent_audit", session_consent_audit);
			model.addAttribute("patient", session_patient);
			model.addAttribute("consent", new Consent());
			model.addAttribute("infection_risk", new InfectionRisk());
			model.addAttribute("consent_validate", new ConsentValidate());
			model.addAttribute("action", new Action());
			model.addAttribute("consentFormTypes", consentService.getFormTypes(user_selected_department.getDept_acronym()));
			model.addAttribute("infection_types", infectionRiskService.getAllInfectionTypes(user_selected_department.getDept_acronym()));
			model.addAttribute("sampleTypes", infectionRiskService.getAllSampleTypes(user_selected_department.getDept_acronym()));
			model.addAttribute("auditSampleTypes", auditProcessService.getAllAuditSampleTypes(user_selected_department.getDept_acronym())); 
			model.addAttribute("user_selected_department", user_selected_department); 
			model.addAttribute("user_selected_locations", this_locations);

			switch (user_selected_department.getDept_acronym().toUpperCase()) {
			case BCITBVariousVariables.HOTB:
				sampleConsentedToJson = getVariousDataFromDB("sampleConsentedToJson", user_selected_department.getDept_acronym(), "");
				formTypeVersionConsentTermJson = getVariousDataFromDB("formTypeVersionConsentTermJson", user_selected_department.getDept_acronym(), "");
				formVersionJson = getVariousDataFromDB("formVersionJson", user_selected_department.getDept_acronym(), "");
				model.addAttribute("sampleConsentedToJson", sampleConsentedToJson);
				model.addAttribute("formTypeVersionConsentTermJson", formTypeVersionConsentTermJson);
				model.addAttribute("formVersionJson", formVersionJson);
				break;
			case BCITBVariousVariables.BGTB:
				formVersionConsentTermJson = getVariousDataFromDB("formVersionConsentTermJson", user_selected_department.getDept_acronym(), "");
				model.addAttribute("formVersionConsentTermJson", formVersionConsentTermJson);
				break;
			}
			if(ir_count > 0)
				model.addAttribute("infectionRisksAlert", 
						"There are " + ir_count + " infection risks and actions will be sent to the relevant members of staff");
			
			return "index";
			
		}
	}

	@RequestMapping(value={"/logout","/timed_out","/accessDenied","/unknownError"}, method = RequestMethod.GET)
	public String errorOccuredPage(ModelMap model, HttpServletRequest request,
			@ModelAttribute("various_actions") VariousActions various_actions,
			@ModelAttribute("columnName") List<String> columnName,
			@ModelAttribute("columnValue") List<String> columnValue,
			@ModelAttribute("user") User user,
			@ModelAttribute("user_selected_department") Department user_selected_department,
			@ModelAttribute("session_patient") Patient session_patient) throws ServletException 
	{
		String messageToDisplay;

		if (request.getServletPath().equalsIgnoreCase("/timed_out"))
			messageToDisplay = "you've been timed out.";
		else if (request.getServletPath().equalsIgnoreCase("/logout"))
			messageToDisplay = "you've been logged out successfully.";
		else if (request.getServletPath().equalsIgnoreCase("/accessDenied"))
			messageToDisplay = "you are not authorized to access this page.";
		else
			messageToDisplay = "unkown error occured.";

		if(!getPrincipal().equalsIgnoreCase("anonymousUser"))
			messageToDisplay = "Dear " + getPrincipal() + ", " + messageToDisplay;
		else 
			messageToDisplay = StringUtils.capitalize(messageToDisplay);
			
		model.addAttribute("msg", messageToDisplay);
		user = new User();
		various_actions = unlockUserActionIfAny(various_actions,columnName,columnValue);
		session_patient = unlockPatient(session_patient, user, user_selected_department);
		request.logout();
		
		return "errorOccured";
	}

	public void processEmails(String which_department, String whatToProcess) throws ParseException, AddressException, MessagingException
	{
		Multipart multiPart = new MimeMultipart();
		MimeBodyPart messageText = new MimeBodyPart();
		AuditProcess this_ap = auditProcessService.getAuditProcess();
		String message_body = "";
		int this_count = 0;
		
		switch (whatToProcess) {
			case BCITBVariousVariables.MONTHLY_REPORT_SENT_DATE:
				int consent_validate_count = 0, consent_count = 0;
				List<String> non_validate_consents = new ArrayList<String>();
				for(Consent con : consentService.getPatientConsents(BCITBVariousVariables.MONTHLY_REPORT_SENT_DATE, 
						which_department, 0, "", "", null, BCITBVariousVariables.ascending)) {
					consent_count = consent_count + 1;
					if(con.getIs_validated() != null && con.getIs_validated().equalsIgnoreCase(BCITBVariousVariables.yes)) {
						consent_validate_count = consent_validate_count + 1;
					} else {
						non_validate_consents.add(patientService.getPatientFromID(which_department, con.getCn_patient_id()).getDatabase_id());
					}
				}
				List<Action> this_actions = new ArrayList<Action>(
						actionService.getActions(BCITBVariousVariables.active, which_department, 0, BCITBVariousVariables.ascending));
				message_body = "Monthly report for " + which_department + " scheduled on " 
						+ DateTimeFormatter.ofPattern("dd/MM/yyyy").format(LocalDateTime.now()) 
						+ " has returned the following results: <br><br>";
				this_count = this_count + 1; message_body = message_body + this_count + ". Number of new consents: " + consent_count + "<br>";
				this_count = this_count + 1; message_body = message_body + this_count + ". Number of validated consents: " + consent_validate_count + "<br>";
				if(non_validate_consents.size() > 0) {
					this_count = this_count + 1; message_body = message_body + this_count + ". Database IDs NOT yet validated:" + "<br>";
					for(String db_ids : non_validate_consents) {
						message_body = message_body + db_ids + "<br>";
					}
				}
				this_count = this_count + 1; message_body = message_body + this_count + ". Number of outstanding actions: " + this_actions.size() + "<br>";
				this_count = this_count + 1; message_body = message_body + this_count + ". List of outstanding actions with database IDs" + "<br>";
				for(Action act : this_actions) {
					message_body = message_body + act.getDescription() + "<br>";
				}
				if(!message_body.isEmpty()) {
					mimeMessage.setFrom(new InternetAddress("noreply-BCITB@qmul.ac.uk"));
					mimeMessage.setSubject(which_department + " monthly report");
					messageText.setContent(message_body
							+ "<br>Kind Regards<br>BCI Tissue Bank Team<br><br>P.S. Please do not reply to this message. Replies to this message are routed to an "
							+ "unmonitored mailbox. If you have questions please contact the BCI Tissue Bank Team bcitissuebankmgmt@qmul.ac.uk", 
							"text/html; charset=UTF-8");
					mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(userService.findUserById(this_ap.getReport_user_id()).getEmail()));
					multiPart.addBodyPart(messageText);
					mimeMessage.setContent(multiPart);
					Transport.send(mimeMessage);
				}
				break;
			case BCITBVariousVariables.SEND_REPORT_:
				Patient this_patient = new Patient();
				Consent this_consent = new Consent();
				for(ConsentAudit ca : consentService.getConsentAudits(which_department, 
						BCITBVariousVariables.audit_triggered_date, this_ap.getAudit_process_date())) {
					this_consent = consentService.getPatientConsent(which_department, 
							ca.getCa_consent_id(), "", "", null);
					if(this_consent != null && this_consent.getIs_audited() != null && 
							this_consent.getIs_audited().equalsIgnoreCase(BCITBVariousVariables.yes)) {
						this_patient = patientService.getPatientFromID(which_department, this_consent.getCn_patient_id());
						if(this_patient != null && this_patient.getDatabase_id() != null) {
							if(message_body.isEmpty()) {
								message_body = "Consent Audit scheduled on " + new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(
										ca.getAudit_triggered_date())) + "<br><br>";
							}
							this_count = this_count + 1;
							message_body = message_body + this_count + ". Database ID: " + this_patient.getDatabase_id() + "<br>"; 
							message_body = message_body + "Auditor 1: " + ca.getPrimary_auditor() + "<br>"; 
							message_body = message_body + "Auditor 2: " + ca.getSecondary_auditor() + "<br>"; 
							message_body = message_body + "Digital consent form: " + ca.getAud_digital_cf_attached() + "<br>"; 
							message_body = message_body + "Physical Consent form location: " + ca.getAud_cf_physical_location() + "<br>"; 
							message_body = message_body + "Patient Signature: " + ca.getAud_patient_signature() + "<br>"; 
							message_body = message_body + "Person taking signature: " + ca.getAud_person_taking_consent() + "<br>"; 
							message_body = message_body + "CF validity: " + ca.getAud_cf_validity() + "<br>"; 
							message_body = message_body + "Verify exclusions: " + ca.getAud_verify_consent_exclusions() + "<br>"; 
							message_body = message_body + "CF audit notes: " + ca.getAud_cf_audit_notes() + "<br>"; 
							message_body = message_body + "Discrepancies Identified: " + ca.getAud_data_discrepancies_identified() + "<br><br>"; 
						}
					}
				}
				if(!message_body.isEmpty()) {
					mimeMessage.setFrom(new InternetAddress("noreply-BCITB@qmul.ac.uk"));
					mimeMessage.setSubject("Audit report " + new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(
							this_ap.getAudit_process_date())));
					messageText.setContent(message_body
							+ "Kind Regards<br>BCI Tissue Bank Team<br><br>P.S. Please do not reply to this message. Replies to this message are routed to an "
							+ "unmonitored mailbox. If you have questions please contact the BCI Tissue Bank Team bcitissuebankmgmt@qmul.ac.uk", 
							"text/html; charset=UTF-8");
					mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(userService.findUserById(this_ap.getReport_user_id()).getEmail()));
					multiPart.addBodyPart(messageText);
					mimeMessage.setContent(multiPart);
					Transport.send(mimeMessage);
				}
				break;
			case BCITBVariousVariables.inform_new_audits:
				mimeMessage.setFrom(new InternetAddress("noreply-BCITB@qmul.ac.uk"));
				mimeMessage.setSubject("Consents To Audit");
				for(AuditTeam at : auditProcessService.getAuditTeams()) {
					if(at.getDepartment().equalsIgnoreCase(which_department)) {
						messageText.setContent("Dear " + at.getUser().getUser_firstname() + ",<br><br>"
								+ "There are consent forms that need to be audited in the BCI Tissue Bank Database. "
								+ "Please log-in as an auditor to perform audits as soon as possible. "
								+ "This will need to be performed in pairs with someone who is trained to handle samples. "
								+ "So please organise this with an appropriate member of staff prior to starting an audit. <br><br>"
								+ "Kind Regards<br>BCI Tissue Bank Team<br><br>P.S. Please do not reply to this message. Replies to this message are routed to an "
								+ "unmonitored mailbox. If you have questions please contact the BCI Tissue Bank Team bcitissuebankmgmt@qmul.ac.uk", "text/html; charset=UTF-8");
						mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(at.getUser().getEmail()));
						multiPart.addBodyPart(messageText);
						mimeMessage.setContent(multiPart);
						Transport.send(mimeMessage);
					}
				}
				break;
		}
		
	}
	
	public void processConsents(String whatToProcess, String whichDepartment) throws AddressException, ParseException, MessagingException
	{
		switch (whatToProcess) {
		case "AUDIT-REPORT":
			
			AuditProcess aud_pro = auditProcessService.getAuditProcess();
			for(Department dept:departmentService.getAllDepartments()) {
				switch (dept.getDept_acronym().toUpperCase()) {
				case BCITBVariousVariables.BGTB: case BCITBVariousVariables.HOTB:
					try {
						if (BeanUtils.getProperty(aud_pro, BCITBVariousVariables.SEND_REPORT_.toLowerCase() + 
								dept.getDept_acronym().toLowerCase()).equalsIgnoreCase(BCITBVariousVariables.yes)) {
							if(consentService.getPatientConsents(BCITBVariousVariables.marked_for_auditing, 
									dept.getDept_acronym(), 0, "", "", null, BCITBVariousVariables.ascending).size() <= 0) { // Check if there are no pending consent yet to be audited
								processEmails(dept.getDept_acronym(),BCITBVariousVariables.SEND_REPORT_);
								new Email().sendEmail("SEND-AUDIT-REPORT-ON-COMPLETION", dept.getDept_acronym(), 
										null, auditProcessService.getAuditProcess(), mimeMessage, userService, patientService, consentService, actionService);
								auditProcessService.updateAuditProcess(aud_pro.getAudit_process_id(), 
										BCITBVariousVariables.SEND_REPORT_ + dept.getDept_acronym().toUpperCase(), BCITBVariousVariables.no);
							}
						}
					} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
						e.printStackTrace();
					}
					break;
				}
			}
			break;
		case BCITBVariousVariables.MONTHLY_REPORT_SENT_DATE:
			
			aud_pro = auditProcessService.getAuditProcess();
			if(validateJavaDate(aud_pro.getMonthly_report_sent_date())) {
				if(!(Integer.valueOf(aud_pro.getMonthly_report_sent_date().split("-")[1]) == LocalDate.now().getMonthValue())) {
					for(Department dept:departmentService.getAllDepartments()) {
						switch (dept.getDept_acronym().toUpperCase()) {
						case BCITBVariousVariables.BGTB: case BCITBVariousVariables.HOTB:
							processEmails(dept.getDept_acronym(),BCITBVariousVariables.MONTHLY_REPORT_SENT_DATE);
							auditProcessService.updateAuditProcess(aud_pro.getAudit_process_id(), 
									BCITBVariousVariables.MONTHLY_REPORT_SENT_DATE, DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now()));
							break;
						}
					}
				}
			}
			break;
			
		case "PROCESS-AUDIT":
			
			aud_pro = auditProcessService.getAuditProcess();
			int number_of_consents_audit = 0, consent_id_index = 0;
			List<Integer> consent_ids;
			 
			if(aud_pro.getAudit_process_date() != null && ChronoUnit.MONTHS.between(LocalDate.parse(aud_pro.getAudit_process_date()), LocalDate.now()) 
					>= Integer.parseInt(aud_pro.getMonthly_cycle())) { // Check if six months has passed since the tissue bank start date?
				
				for(Department dept:departmentService.getAllDepartments()) {
					switch (dept.getDept_acronym().toUpperCase()) {
					case BCITBVariousVariables.BGTB: case BCITBVariousVariables.HOTB:
						
						if(consentService.getPatientConsents(BCITBVariousVariables.marked_for_auditing, 
								dept.getDept_acronym(), 0, "", "", null, BCITBVariousVariables.ascending).size() <= 0) // Check if there are no pending consent yet to be audited
						{
								consent_ids = new ArrayList<Integer>();
								for(Consent con:new ArrayList<Consent>(consentService.getPatientConsents(BCITBVariousVariables.ignore_audited_withdrawn, 
										dept.getDept_acronym(), 0, "", "", null, BCITBVariousVariables.ascending))) 
									consent_ids.add(con.getConsent_id());
								
								if(consent_ids.size() > 0) 
								{
									consent_id_index = 0;
									number_of_consents_audit = aud_pro.getNumber_of_consents();
									
									if(consent_ids.size() < number_of_consents_audit)
										number_of_consents_audit = consent_ids.size();

									while (number_of_consents_audit > 0) {
										consent_id_index = new Random().nextInt(consent_ids.size());
										consentService.saveConsentVariousColumns(dept.getDept_acronym(), consent_ids.get(consent_id_index), 
												BCITBVariousVariables.marked_for_auditing, BCITBVariousVariables.yes);
										consent_ids.remove(consent_id_index);
										number_of_consents_audit = number_of_consents_audit - 1;
									}
									
									if(number_of_consents_audit <= 0) {
										auditProcessService.updateAuditProcess(aud_pro.getAudit_process_id(), BCITBVariousVariables.audit_process_date, 
												DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now())); // Reset the audit date to current date
										auditProcessService.updateAuditProcess(aud_pro.getAudit_process_id(), 
												BCITBVariousVariables.SEND_REPORT_ + dept.getDept_acronym().toUpperCase(), BCITBVariousVariables.yes);
										processEmails(dept.getDept_acronym(),BCITBVariousVariables.inform_new_audits);
									}
									
								}
									
						}
						break;
					}
					
				}
				
			}
			
			break;
			
		case "DELETE-WITHDRAWN-CONSENT":
			
			for(Consent con:consentService.getPatientConsents(BCITBVariousVariables.consent_deletion_date, 
					whichDepartment, 0, "", "", null, BCITBVariousVariables.ascending)) 
				if(ChronoUnit.MONTHS.between(LocalDate.parse(con.getConsent_deletion_date()), LocalDate.now()) >= 
						BCITBVariousVariables.consent_withdrawn_deletion_months) { // Check if one day has passed since the deletion date
						
						if(con.getDigital_cf_attachment_id() != null && con.getDigital_cf_attachment_id() > 0)
							fileService.deleteFile(whichDepartment, con.getDigital_cf_attachment_id());
						if(con.getAdditional_document_id() != null && con.getAdditional_document_id() > 0)
							fileService.deleteFile(whichDepartment, con.getAdditional_document_id());
						if(con.getVerbal_consent_document_id() != null && con.getVerbal_consent_document_id() > 0)
							fileService.deleteFile(whichDepartment, con.getVerbal_consent_document_id());

						if(consentService.getPatientConsents(BCITBVariousVariables.consent_withdrawn, whichDepartment, con.getCn_patient_id(), "", "", null, "").size() ==
								consentService.getPatientConsents("", whichDepartment, con.getCn_patient_id(), "", "", null, "").size()) {
							timelineService.deleteTimeline(whichDepartment, con.getCn_patient_id(), BCITBVariousVariables.patient);
							patientService.savePatientVariousColumns(whichDepartment, con.getCn_patient_id(), BCITBVariousVariables.gender, "");
							patientService.savePatientVariousColumns(whichDepartment, con.getCn_patient_id(), BCITBVariousVariables.volunteer, "");
						}
						
						for(Action act:actionService.getActionsFromDataId(whichDepartment, con.getConsent_id(), 
								BCITBVariousVariables.consent, "", "", BCITBVariousVariables.ascending)) 
							timelineService.deleteTimeline(whichDepartment, act.getAction_id(), BCITBVariousVariables.action);
						timelineService.deleteTimeline(whichDepartment, con.getConsent_id(), BCITBVariousVariables.consent);
						timelineService.deleteTimeline(whichDepartment, con.getConsent_id(), BCITBVariousVariables.consent_files);
						timelineService.deleteTimeline(whichDepartment, con.getConsent_id(), BCITBVariousVariables.consent_validate);
						actionService.deleteAction(whichDepartment, con.getConsent_id(), BCITBVariousVariables.consent);
						consentService.deleteConsent("KEEP-WTHDRAWAL-DATA", whichDepartment, con.getConsent_id());
					
				}

			for(InfectionRisk ir:infectionRiskService.getPatientInfectionRisks(BCITBVariousVariables.ir_deletion_date,whichDepartment, 0)) 
				if(ChronoUnit.MONTHS.between(LocalDate.parse(ir.getIr_deletion_date()), LocalDate.now()) >= 
					BCITBVariousVariables.consent_withdrawn_deletion_months) { // Check if one day has passed since the deletion date
					timelineService.deleteTimeline(whichDepartment, ir.getInfection_risk_id(), BCITBVariousVariables.infection_risk);
					actionService.deleteAction(whichDepartment, ir.getInfection_risk_id(), BCITBVariousVariables.infection_risk);
					infectionRiskService.deleteInfectionRisk(whichDepartment, ir.getInfection_risk_id());
				}
			
			break;
		}
	}
	
	public String getVariousDataFromDB(String whatToProcess, String whichDepartment, String stringToProcess)
	{
		String stringToReturn = stringToProcess;
		
		switch (whatToProcess){
		case "formVersionJson":
			if(stringToReturn.length() <= 0)
				stringToReturn = JSONArray.fromObject(formVersionService.getAllFormVersions(whichDepartment)).toString();
			break;
		case "formTypeVersionConsentTermJson":
			if(stringToReturn.length() <= 0)
				stringToReturn = JSONArray.fromObject(formTypeConsentTermService.getAllFormTypeVersionConsentTerms(whichDepartment)).toString();
			break;
		case "formVersionConsentTermJson":
			if(stringToReturn.length() <= 0)
				stringToReturn = JSONArray.fromObject(formVersionConsentTermService.getAllFormVersionConsentTerms(whichDepartment)).toString();
			break;
		case "sampleConsentedToJson":
			if(stringToReturn.length() <= 0)
				stringToReturn = JSONArray.fromObject(consentService.getAllConsentedSampleTypes(whichDepartment)).toString();
			break;
		}
		return stringToReturn;
	}
	
	public String getDifferenceBetweenTwoObjects(String whatToProcess, String titleTextToShow, DiffResult diff, List<String> old_object, List<String> new_object)
	{
		String notes = "";
		switch (whatToProcess.toUpperCase()) {
		case "TWO-STRING-ARRAYS":

			notes = titleTextToShow + "= Old values [";
			for(int i = 0; i < old_object.size(); i++) 
				if (i == 0)
					notes = notes + old_object.get(i);
				else
					notes = notes + ", " + old_object.get(i);
			if(old_object.size() > 0)
				notes = notes + "] TO New Values [";
			else
				notes = notes + "null] TO New Values [";
				
			for(int i = 0; i < new_object.size(); i++) 
				if (i == 0)
					notes = notes + new_object.get(i);
				else
					notes = notes + ", " + new_object.get(i);
			if(new_object.size() > 0)
				notes = notes + "]";
			else
				notes = notes + "null]";
			
			break;
			
		default:
			
			if (diff.getDiffs().size() > 0) 
				for(Diff<?> d: diff.getDiffs()) 
					if(notes.equalsIgnoreCase(""))
						notes = d.getFieldName() + "= FROM [" + d.getLeft() + "] TO [" + d.getRight() + "]";
					else
						notes = notes + ", " + d.getFieldName() + "= FROM [" + d.getLeft() + "] TO [" + d.getRight() + "]";

			break;
		}
		return notes;
	}
	
     @SuppressWarnings("unused")
	  public static boolean validateJavaDate(String strDate)
	   {
		if (strDate == null || strDate.trim().isEmpty())
		    return false;
		else
		{
		    SimpleDateFormat sdfrmt = new SimpleDateFormat("yyyy-MM-dd");
		    sdfrmt.setLenient(false);
		    try
		    {
				Date javaDate = sdfrmt.parse(strDate); 
		    }
		    catch (ParseException e)
		    {
		        return false;
		    }
		    return true;
		}
    }    
 	public void searchConsentFilesExists(String which_department, String folder_to_search) throws IOException
 	{
 		List<String> file_not_found = new ArrayList<>();
		for(File fl: new File(folder_to_search).listFiles())
			if(fileService.getFile(which_department, fl.getName()) == null)
				file_not_found.add("File = " + fl.getName() + " NOT found in database");
		if(file_not_found.size() > 0) {
	 		PrintWriter printWriter = new PrintWriter(new FileWriter(folder_to_search + ".txt"));
			for(String str : file_not_found) {
				printWriter.print(str);
				printWriter.println();
			}
			printWriter.close();
		}
 	}
    
	@SuppressWarnings("deprecation")
	public void importConsentData(String which_department, String folder_to_search) throws IOException 
	{
		File[] listOfFiles;
		Patient patient = new Patient();
		List<Consent> consents = null;
		List<Patient> patients = null;
		String file_to_search = "", search_criteria = "";
		
		listOfFiles = new File(folder_to_search).listFiles();
		for (int k = 0; k < listOfFiles.length; k++) {
			if(fileService.getFile(which_department, listOfFiles[k].getName()) == null) {
				file_to_search = listOfFiles[k].getName().replaceFirst("[.][^.]+$", ""); search_criteria = "";
				switch (which_department) {
				case BCITBVariousVariables.HOTB:
					search_criteria = "";
					if(file_to_search.split("_").length > 0) {
						if(file_to_search.split("_")[0].length() >= 8) { // Consent date
							search_criteria = file_to_search.split("_")[0].substring(4, 8) + "-" + file_to_search.split("_")[0].substring(2, 4)
									+ "-" + file_to_search.split("_")[0].substring(0, 2);
							if(validateJavaDate(search_criteria) == false) {
								search_criteria = "";
							}
						} 
						if(search_criteria.trim().isEmpty()) {
							if (!file_to_search.toLowerCase().contains("nohos")) {
								search_criteria = file_to_search.split("_")[0];
							} else {
								search_criteria = file_to_search.split("_")[1];
							}
						}
					}
					if(!search_criteria.trim().isEmpty()) {
						consents = new ArrayList<>(consentService.searchConsents(BCITBVariousVariables.date_of_consent, 
								which_department, search_criteria, BCITBVariousVariables.ascending));
						if(consents != null && consents.size() > 0) {
							for(Consent con: consents) {
								patient = patientService.getPatientFromID(which_department, con.getCn_patient_id());
								if(patient != null && patient.getPatient_firstname() != null && patient.getPatient_firstname().length() >= 1 && 
										patient.getPatient_surname() != null && patient.getPatient_surname().length() >= 1) {
									if(file_to_search.split("_")[1].substring(0, 1).equalsIgnoreCase(patient.getPatient_firstname().substring(0, 1))
											&& file_to_search.split("_")[1].substring(1, 2).equalsIgnoreCase(patient.getPatient_surname().substring(0, 1))) {
										if(con.getDigital_cf_attachment_id() == null) {
											con.setDigital_cf_attachment_id(fileService.saveOrUpdateFileAndReturnFileId(which_department, 
													new ConsentFile(listOfFiles[k].getName(),URLConnection.getFileNameMap().getContentTypeFor(listOfFiles[k].getName()),FileUtils.readFileToByteArray(listOfFiles[k]),
													BCITBVariousVariables.digital_cf_attachment_file)));
											consentService.saveConsentVariousColumns(which_department, con.getConsent_id(), 
													BCITBVariousVariables.digital_cf_attachment_file.replace("_file", "_id"), String.valueOf(con.getDigital_cf_attachment_id()));
										}
									}
								}
							}
						} else {
							patients = patientService.searchPatientDetails(which_department, "EQUAL-STRING", 
									BCITBVariousVariables.hospital_number,search_criteria);
							if(patients != null && patients.size() > 0) {
								for(Patient pat : patients) {
									consents = new ArrayList<>(consentService.getPatientConsents("", which_department, pat.getPatient_id(), "", "", null, BCITBVariousVariables.ascending));
									if(consents != null && consents.size() > 0) {
										for(Consent con: consents) {
											if(con.getDigital_cf_attachment_id() == null) {
												con.setDigital_cf_attachment_id(fileService.saveOrUpdateFileAndReturnFileId(which_department, 
														new ConsentFile(listOfFiles[k].getName(),URLConnection.getFileNameMap().getContentTypeFor(
														listOfFiles[k].getName()),FileUtils.readFileToByteArray(listOfFiles[k]), BCITBVariousVariables.digital_cf_attachment_file)));
												consentService.saveConsentVariousColumns(which_department, con.getConsent_id(), 
														BCITBVariousVariables.digital_cf_attachment_file.replace("_file", "_id"), String.valueOf(con.getDigital_cf_attachment_id()));
											}
										}
									}
								}
							}
						}
					}
					break;
				case BCITBVariousVariables.BGTB:
					if(org.apache.commons.lang.NumberUtils.isDigits(file_to_search.replaceAll("\\D+","")) == true && file_to_search.length() >= 8) {
						search_criteria = file_to_search.substring(0, 4) + "-" + file_to_search.substring(4, 6) + "-" + file_to_search.substring(6, 8);
						for(Consent con: consentService.searchConsents(BCITBVariousVariables.date_of_consent, which_department, search_criteria, BCITBVariousVariables.ascending)) {
							if(con.getDigital_cf_attachment_id() == null) {
								con.setDigital_cf_attachment_id(fileService.saveOrUpdateFileAndReturnFileId(which_department, 
										new ConsentFile(listOfFiles[k].getName(),URLConnection.getFileNameMap().getContentTypeFor(listOfFiles[k].getName()),FileUtils.readFileToByteArray(listOfFiles[k]),
										BCITBVariousVariables.digital_cf_attachment_file)));
								consentService.saveConsentVariousColumns(which_department, con.getConsent_id(), 
										BCITBVariousVariables.digital_cf_attachment_file.replace("_file", "_id"), String.valueOf(con.getDigital_cf_attachment_id()));
							} else if(con.getAdditional_document_id() == null) {
								con.setAdditional_document_id(fileService.saveOrUpdateFileAndReturnFileId(which_department, 
										new ConsentFile(listOfFiles[k].getName(),URLConnection.getFileNameMap().getContentTypeFor(listOfFiles[k].getName()),FileUtils.readFileToByteArray(listOfFiles[k]),
										BCITBVariousVariables.additional_document_file)));
								consentService.saveConsentVariousColumns(which_department, con.getConsent_id(), 
										BCITBVariousVariables.additional_document_file.replace("_file", "_id"), String.valueOf(con.getAdditional_document_id()));
							}
						}
					} else {
						if(file_to_search.contains("_") == true)
							search_criteria = file_to_search.split("_")[0];
						else
							search_criteria = file_to_search;
						for(Patient pat:patientService.searchPatientDetails(which_department, "EQUAL-STRING", BCITBVariousVariables.old_pat_id, search_criteria)) {
							for(Consent con:consentService.getPatientConsents("", which_department, pat.getPatient_id(), "", "", null, BCITBVariousVariables.ascending)) {
								if(con.getDigital_cf_attachment_id() == null) {
									con.setDigital_cf_attachment_id(fileService.saveOrUpdateFileAndReturnFileId(which_department, 
											new ConsentFile(listOfFiles[k].getName(),URLConnection.getFileNameMap().getContentTypeFor(listOfFiles[k].getName()),FileUtils.readFileToByteArray(listOfFiles[k]),
											BCITBVariousVariables.digital_cf_attachment_file)));
									consentService.saveConsentVariousColumns(which_department, con.getConsent_id(), 
											BCITBVariousVariables.digital_cf_attachment_file.replace("_file", "_id"), String.valueOf(con.getDigital_cf_attachment_id()));
								} else if(con.getAdditional_document_id() == null) {
									con.setAdditional_document_id(fileService.saveOrUpdateFileAndReturnFileId(which_department, 
											new ConsentFile(listOfFiles[k].getName(),URLConnection.getFileNameMap().getContentTypeFor(listOfFiles[k].getName()),FileUtils.readFileToByteArray(listOfFiles[k]),
											BCITBVariousVariables.additional_document_file)));
									consentService.saveConsentVariousColumns(which_department, con.getConsent_id(), 
											BCITBVariousVariables.additional_document_file.replace("_file", "_id"), String.valueOf(con.getAdditional_document_id()));
								}
							}
						}
					}					
				}
			}
		}
	}

	public AllPatientData searchAllPatientData(String whatToProcess, String whichDepartment, String search_patient_keyword,String search_criteria, Role role) {

		AllPatientData apd = new AllPatientData();
		List<Patient> db_patients = new ArrayList<Patient>();
		if (!search_patient_keyword.trim().isEmpty() && !search_criteria.trim().isEmpty()) {
			for(Patient pat:patientService.searchPatientDetails(whichDepartment,"LIKE-STRING",search_criteria,search_patient_keyword)) {
				pat.setNumber_validated_consents(0); pat.setNumber_finalise_import_consents(0); pat.setNumber_consents(0); pat.setNumber_infection_risks(0);
				pat.setPatient_which_department(whichDepartment);
				for(Consent con: consentService.getPatientConsents("all",whichDepartment, 
						pat.getPatient_id(), "", "", null, BCITBVariousVariables.ascending)) {
					pat.setNumber_consents(pat.getNumber_consents() + 1);
					if(con.getIs_validated() != null && con.getIs_validated().equalsIgnoreCase(BCITBVariousVariables.yes)) 
						pat.setNumber_validated_consents(pat.getNumber_validated_consents() + 1);
					else if(con.getIs_finalised() != null && con.getIs_finalised().equalsIgnoreCase(BCITBVariousVariables.yes) 
							&& con.getIs_imported() != null && con.getIs_imported().equalsIgnoreCase(BCITBVariousVariables.yes)) 
						pat.setNumber_finalise_import_consents(pat.getNumber_finalise_import_consents() + 1);
				}
				if(pat.getWithdrawn_count() != null && pat.getWithdrawn_count() > 0)   // Hide all infection risks even if a single consent is withdrawn 
					for(InfectionRisk ir:infectionRiskService.getPatientInfectionRisks("",whichDepartment, pat.getPatient_id())) 
						if(ir.getInfection_risk_exist().toLowerCase().contains(BCITBVariousVariables.yes) || 
								ir.getInfection_risk_exist().toLowerCase().contains(BCITBVariousVariables.unknown) ||
								ir.getInfection_risk_exist().toLowerCase().contains(BCITBVariousVariables.imported))
							pat.setNumber_infection_risks(pat.getNumber_infection_risks() + 1);
				db_patients.add(pat);
			}
		}
		if(db_patients.size() > 0 || 
				role.getUserRoleAccess(BCITBVariousVariables.consent_withdrawn).equalsIgnoreCase(BCITBVariousVariables.edit)) {
			if(role.getUserRoleAccess(BCITBVariousVariables.timeline).equalsIgnoreCase(BCITBVariousVariables.view))
				apd.setAccess_data(BCITBVariousVariables.patient + "_" + BCITBVariousVariables.timeline);
			else
				apd.setAccess_data(BCITBVariousVariables.patient);
		}
		apd.setPatients(db_patients);
		return apd;
	}
	
	@SuppressWarnings({"rawtypes"})
	@RequestMapping(value = {"/uploadFileToSessionConsent","/uploadPatientFormData","/uploadFullPatientFormData","/uploadFullConsentFormData","/uploadConsentFormData",
			"/uploadInfectionRiskFormData","/uploadValidateFormData","/uploadFullConsentValidateFormData","/uploadFullConsentAuditFormData","uploadConsentAuditFormData",
			"/uploadActionFormData","/logConsentFileViewClick","uploadFullAuditSampleFormData"}, method={RequestMethod.POST})    
	public @ResponseBody String processUploadFileAndFormData(MultipartHttpServletRequest request,
			@ModelAttribute("user") User user,
			@ModelAttribute("primaryRole") Role primaryRole,
			@ModelAttribute("session_patient") Patient session_patient,
			@ModelAttribute("session_consent_validate") ConsentValidate session_consent_validate,
			@ModelAttribute("session_consents") List<Consent> session_consents,
			@ModelAttribute("various_actions") VariousActions various_actions,
			@ModelAttribute("columnName") List<String> columnName,
			@ModelAttribute("columnValue") List<String> columnValue,
			@ModelAttribute("session_consent_audit") ConsentAudit session_consent_audit,
			@ModelAttribute("session_infection_risks") List<InfectionRisk> session_infection_risks,
			@ModelAttribute("user_selected_department") Department user_selected_department) 
					throws IOException, IllegalAccessException, InvocationTargetException, IllegalStateException, NoSuchMethodException, ParseException
	{
		 String parameterName = "", whichFile = "";
		 int primaryID = 0;
		 Enumeration enumeration = request.getParameterNames();
		 AllPatientData apd = new AllPatientData();
		 
	     while(enumeration.hasMoreElements() && primaryID <= 0){ // Get primary id in the first loop
		     parameterName = (String) enumeration.nextElement();
		     switch (parameterName.toLowerCase()) {
		     case "infection_risk_id": case "aud_sample_pid":
		    	 if(!request.getParameter(parameterName).trim().isEmpty())
		    		 primaryID = Integer.parseInt(request.getParameter(parameterName));
		    	 break;
		     }
	     }
	     
	   	 if (request.getRequestURI().contains("uploadFileToSessionConsent")) {
			 Iterator<String> fileItr = request.getFileNames();
			 if (fileItr.hasNext()) {
				 MultipartFile mpf = null;
				 whichFile = request.getFileMap().entrySet().iterator().next().getKey();
				 while (fileItr.hasNext()) {
					mpf = request.getFile(fileItr.next());
					switch (whichFile.toLowerCase()) {
					case BCITBVariousVariables.additional_document_file:
						session_consents.get(0).setAdditional_document(new ConsentFile(mpf.getOriginalFilename(),mpf.getContentType(),mpf.getBytes(),whichFile));
						break;
					case BCITBVariousVariables.withdrawal_document_file:
						session_consents.get(0).setWithdrawal_document(new ConsentFile(mpf.getOriginalFilename(),mpf.getContentType(),mpf.getBytes(),whichFile));
						break;
					case BCITBVariousVariables.digital_cf_attachment_file:
						session_consents.get(0).setDigital_cf_attachment(new ConsentFile(mpf.getOriginalFilename(),mpf.getContentType(),mpf.getBytes(),whichFile));
						break;
					case BCITBVariousVariables.verbal_consent_document_file:
						session_consents.get(0).setVerbal_consent_document(new ConsentFile(mpf.getOriginalFilename(),mpf.getContentType(),mpf.getBytes(),whichFile));
						break;
					}
				 }
			 }
	   	 } else if(request.getRequestURI().contains("uploadFullPatientFormData") || request.getRequestURI().contains("uploadFullConsentFormData")
	   			|| request.getRequestURI().contains("uploadFullConsentValidateFormData") || request.getRequestURI().contains("uploadFullConsentAuditFormData")
	   			|| request.getRequestURI().contains("uploadConsentAuditFormData") || request.getRequestURI().contains("uploadFullAuditSampleFormData")) {

	   		 for (Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
    			 if(request.getRequestURI().contains("uploadFullPatientFormData")) 
    				 BeanUtils.setProperty(session_patient, entry.getKey(), entry.getValue()[0]);
    			 else if(request.getRequestURI().contains("uploadFullConsentFormData")) 
					 BeanUtils.setProperty(session_consents.get(0), entry.getKey(), entry.getValue()[0]);
    			 else if(request.getRequestURI().contains("uploadFullConsentValidateFormData")) 
					 BeanUtils.setProperty(session_consent_validate, entry.getKey(), entry.getValue()[0]);
    			 else if(request.getRequestURI().contains("uploadFullConsentAuditFormData") || request.getRequestURI().contains("uploadConsentAuditFormData")) 
					 BeanUtils.setProperty(session_consent_audit, entry.getKey(), entry.getValue()[0]);
    			 else if (request.getRequestURI().contains("uploadBasicSearchData"))
					 BeanUtils.setProperty(apd, entry.getKey(), entry.getValue()[0]);
    	   		 else if (request.getRequestURI().contains("uploadFullAuditSampleFormData")) {
    	   			 for(ConsentAuditSample cas: session_consent_audit.getSample_types()) {
    	   				 if(cas.getAud_sample_pid() == primaryID) {
    	   					 BeanUtils.setProperty(cas, entry.getKey(), entry.getValue()[0]);
    	   				 }
	   		 		 }
    	   		 }
	   		 }

			 if(request.getRequestURI().contains("uploadFullPatientFormData")) 
				 session_patient = updatePatient(user, primaryRole, session_patient);
			 else if(request.getRequestURI().contains("uploadFullConsentFormData")) {
				 updateConsent(user, primaryRole, session_consents, null, session_patient, columnName, columnValue);
			 	 session_patient = updatePatientField(user_selected_department.getDept_acronym(),BCITBVariousVariables.secondary_id, session_patient);
			 } else if(request.getRequestURI().contains("uploadFullConsentValidateFormData")) 
				 updateConsentValidate(user, primaryRole, session_consent_validate, session_consents.get(0));
			 else if(request.getRequestURI().contains("uploadFullConsentAuditFormData")) {
				 updateConsentAudit(user, primaryRole, session_consent_audit, session_consents.get(0));
				 updateConsentAuditSamples(user, primaryRole, session_consent_audit, session_consents.get(0).getCn_patient_id());
			 } 
	   	 } else if (request.getRequestURI().contains("uploadPatientFormData") || request.getRequestURI().contains("uploadConsentFormData") 
	   			 || request.getRequestURI().contains("uploadInfectionRiskFormData") || request.getRequestURI().contains("uploadValidateFormData")
	   			 || request.getRequestURI().contains("uploadActionFormData")) {
	   		 
    		 for (Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
    	   		 if (request.getRequestURI().contains("uploadConsentFormData")) {
    	   			 BeanUtils.setProperty(session_consents.get(0), entry.getKey(), entry.getValue()[0]);
    	   		 } else if (request.getRequestURI().contains("uploadPatientFormData")) {
    	   			 BeanUtils.setProperty(session_patient, entry.getKey(), entry.getValue()[0]);
    	   		 } else if (request.getRequestURI().contains("uploadInfectionRiskFormData")) {
	 	   			for(InfectionRisk ir: session_infection_risks) 
	 	   				if(ir.getInfection_risk_id() == primaryID) 
	 	   					BeanUtils.setProperty(ir, entry.getKey(), entry.getValue()[0]);
    	   		 } else if (request.getRequestURI().contains("uploadValidateFormData")) {
    	   			 	BeanUtils.setProperty(session_consent_validate, entry.getKey(), entry.getValue()[0]);
    	   		 } else if (request.getRequestURI().contains("uploadActionFormData")) {
    	   			 	for(Action act:various_actions.getActive_actions()) 
							if(act.getStatus().equalsIgnoreCase(BCITBVariousVariables.locked)) 
		                		BeanUtils.setProperty(act, entry.getKey(), entry.getValue()[0]);
    	   		 }
    		 }
    		 
	   	 } else if (request.getRequestURI().contains("logConsentFileViewClick")) {
	   		 	for (Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
	   		 		switch (entry.getKey()) {
					case BCITBVariousVariables.digital_cf_attachment_file: case BCITBVariousVariables.additional_document_file:
					case BCITBVariousVariables.verbal_consent_document_file: case BCITBVariousVariables.withdrawal_document_file:
						whichFile = entry.getKey().replace("_", " ") + " [" + entry.getValue()[0] + "] was viewed by " + user.getUsername();
						break;
					}
	   		 	}
				timelineService.saveTimeline(session_consents.get(0).getConsent_which_department(), session_consents.get(0).getConsent_id(), user.getUser_id(), primaryRole.getRole_id(), 
						BCITBVariousVariables.view, whichFile,DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()), BCITBVariousVariables.consent_files,
						"View Files: Consent dated " + new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(
						session_consents.get(0).getDate_of_consent())) + " files viewed on " + DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").
						format(LocalDateTime.now()) + " by " + user.getUsername() + " [" + primaryRole.getRole_description() + "]",
						BCITBVariousVariables.no, session_consents.get(0).getCn_patient_id());
	   	 }
	   	 return null;
	}
	
	public Patient findPatientFromDatabase(Patient patient, String whichDepartment, int patient_id) {
		if(patient_id > 0) {
			patient = patientService.getPatientFromID(whichDepartment, patient_id); 
			patient.setPatient_which_department(whichDepartment);
			if(consentService.getPatientConsents(BCITBVariousVariables.consent_withdrawn, whichDepartment, patient_id, "", "", null, "").size() ==
					consentService.getPatientConsents("", whichDepartment, patient_id, "", "", null, "").size()) {
				patient.setVolunteer(""); patient.setGender("");
			}
		}
		return patient;
	}
	
	@RequestMapping(value = {"/processPatientConsentInfectionRisk"}, method = RequestMethod.GET)    
	public @ResponseBody String processPatientConsentInfectionRisk(
			@ModelAttribute("user") User user,
			@ModelAttribute("primaryRole") Role primaryRole,
			@ModelAttribute("session_consent_validate") ConsentValidate session_consent_validate,
			@ModelAttribute("session_patient") Patient session_patient,
			@ModelAttribute("session_consents") List<Consent> session_consents,
			@ModelAttribute("session_infection_risks") List<InfectionRisk> session_infection_risks,
			@ModelAttribute("various_actions") VariousActions various_actions,
			@ModelAttribute("session_timeline") List<Timeline> session_timeline,
			@ModelAttribute("session_consent_audit") ConsentAudit session_consent_audit,
			@ModelAttribute("existing_audit_consents") List<Consent> existing_audit_consents,
			@ModelAttribute("imported_consents") List<Consent> imported_consents,
			@ModelAttribute("session_all_patient_data") AllPatientData session_all_patient_data,
			@RequestParam(value = "whichURL", required = false, defaultValue = "") String whichURL,
			@RequestParam(value = "whichDepartment", required = false, defaultValue = "") String whichDepartment,
			@RequestParam(value = "whatToProcess", required = false, defaultValue = "") String whatToProcess,
			@RequestParam(value = "nameOfInputbox", required = false, defaultValue = "") String nameOfInputbox[],
			@RequestParam(value = "valueOfInputbox", required = false, defaultValue = "") String valueOfInputbox[],
			@ModelAttribute("columnName") List<String> columnName,
			@ModelAttribute("columnValue") List<String> columnValue,
			@ModelAttribute("session_advanced_search_sql_script") List<String> session_advanced_search_sql_script,
			@ModelAttribute("session_basic_search_options") List<String> session_basic_search_options,
			@ModelAttribute("user_selected_department") Department user_selected_department,
			@ModelAttribute("user_selected_locations") List<Location> user_selected_locations) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, ParseException, IllegalStateException, IOException 
	 {  
		List<Action> consent_actions = null;
		Action this_action = null;
		String dataToReturn = "", sql_script = "", sel_columns = "";
		Consent this_consent;
		
		if(whichDepartment.trim().isEmpty())
			whichDepartment = user_selected_department.getDept_acronym();
		
		switch (whatToProcess) {
		case "GET-VARIOUS-SEARCH-OPTIONS":
			
			switch (nameOfInputbox[0].toLowerCase()) {
			case BCITBVariousVariables.location:
				String locs_mini_ids = "";
				for(Location loc : user_selected_locations)
					if(locs_mini_ids.isEmpty())
						locs_mini_ids = String.valueOf(loc.getLoc_id());
					else
						locs_mini_ids = locs_mini_ids + "," + loc.getLoc_id();
				dataToReturn = JSONArray.fromObject(locationService.getAllLocationsMinimalFromIds(locs_mini_ids)).toString();
				break;
			case BCITBVariousVariables.form_type:
				dataToReturn = JSONArray.fromObject(consentService.getFormTypes(whichDepartment)).toString();
				break;
			case BCITBVariousVariables.form_version:
				dataToReturn = JSONArray.fromObject(formVersionService.getAllFormVersions(whichDepartment)).toString();
				break;
			case BCITBVariousVariables.samples_consented_to:
				dataToReturn = JSONArray.fromObject(consentService.getAllConsentedSampleTypes(whichDepartment)).toString();
				break;
			case BCITBVariousVariables.consent_exclusions:
				dataToReturn = JSONArray.fromObject(consentService.getAllConsentTerms(whichDepartment)).toString();
				break;
			}
			break;
		
		case "ADVANCED-SEARCH": case "PREVIOUS-ADVANCED-SEARCH": case "EXPORT-SEARCH-RESULT-LOG-TIMELINE":
			
			for(int i=0; i<nameOfInputbox.length; i++) 
				sql_script = sql_script + nameOfInputbox[i].replace(",", "").replace("|", ",").replace("$", " ");

			switch (whatToProcess) {
			case "EXPORT-SEARCH-RESULT-LOG-TIMELINE":
				if (user.getUser_id() > 0 && session_advanced_search_sql_script.size() > 0 && !whichDepartment.isEmpty()) {
					timelineService.saveTimeline(whichDepartment, user.getUser_id(), user.getUser_id(), primaryRole.getRole_id(), BCITBVariousVariables.export_search_result, 
							session_advanced_search_sql_script.get(0),DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()), 
							BCITBVariousVariables.export_search_result,"Search result was exported in an Excel Spreadsheet on " + 
							DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + 
							" by " + user.getUsername() + " [" + primaryRole.getRole_description() + "]",BCITBVariousVariables.no, 0);
				}
				break;
			case "ADVANCED-SEARCH": case "PREVIOUS-ADVANCED-SEARCH":
				try {
					if(patientService.getPatientObjectArrayUsingSQL(whichDepartment, sql_script) == null) {
						dataToReturn = JSONArray.fromObject(patientService.getPatientObjectUsingSQL(whichDepartment, sql_script)).toString();
					} else {
						dataToReturn = JSONArray.fromObject(patientService.getPatientObjectArrayUsingSQL(whichDepartment, sql_script)).toString();
					}
					session_basic_search_options.clear();
					session_advanced_search_sql_script.clear();
					session_advanced_search_sql_script.add(sql_script);
					for(int i=0; i<valueOfInputbox.length; i++) 
						sel_columns = sel_columns + valueOfInputbox[i].replace("$", " ");
					session_advanced_search_sql_script.add(sel_columns);
				} catch (Exception e) {
					dataToReturn = JSONObject.fromObject(new AllPatientData("Error occured when processing SQL script \r\n" + sql_script)).toString();
				}
				break;
			}
			break;
			
		case "BASIC-SEARCH": case "PREVIOUS-BASIC-SEARCH":
			
			String search_patient_keyword = "", search_criteria = "";
			switch (whatToProcess) {
			case "BASIC-SEARCH":
				for(int i=0;i<nameOfInputbox.length;i++) {
					if(nameOfInputbox[i].equalsIgnoreCase("search_patient_keyword"))
						search_patient_keyword = valueOfInputbox[i];
					else if(nameOfInputbox[i].equalsIgnoreCase("search_criteria"))
						search_criteria = valueOfInputbox[i];
				}
				session_all_patient_data = searchAllPatientData(whatToProcess,whichDepartment,search_patient_keyword,search_criteria,primaryRole);
				session_advanced_search_sql_script.clear();
				session_basic_search_options.clear();
				session_basic_search_options.add(search_patient_keyword);
				session_basic_search_options.add(search_criteria);
				break;
			case "PREVIOUS-BASIC-SEARCH":
				if(session_basic_search_options.size() >= 2)
					session_all_patient_data = searchAllPatientData(whatToProcess,whichDepartment,
							session_basic_search_options.get(0),session_basic_search_options.get(1),primaryRole);
				break;
			}
			session_all_patient_data.setSearch_result_view_type(primaryRole.getUserRoleAccess(BCITBVariousVariables.consent));
			dataToReturn = JSONObject.fromObject(session_all_patient_data).toString();
			break;
		
		case "SAVE-CONSENT-AUDIT":
			
			if(session_consent_audit.getCa_which_department() == null && session_consents.get(0).getConsent_which_department() != null)
				session_consent_audit.setCa_which_department(session_consents.get(0).getConsent_which_department());
			if(session_consent_audit.getCa_consent_id() <= 0)
				session_consent_audit.setCa_consent_id(session_consents.get(0).getConsent_id());

			updateConsentAudit(user, primaryRole, session_consent_audit, session_consents.get(0));
			updateConsentAuditSamples(user, primaryRole, session_consent_audit, session_consents.get(0).getCn_patient_id());
			
			dataToReturn = JSONArray.fromObject(session_consent_audit).toString();

			break;
			
		case "REMOVE-FILE": 
			
			switch (nameOfInputbox[0].toLowerCase()) {
			case BCITBVariousVariables.additional_document_file:
				session_consents.get(0).setAdditional_document(null);
				break;
			case BCITBVariousVariables.withdrawal_document_file:
				session_consents.get(0).setWithdrawal_document(null);
				break;
			case BCITBVariousVariables.digital_cf_attachment_file:
				session_consents.get(0).setDigital_cf_attachment(null);
				break;
			case BCITBVariousVariables.verbal_consent_document_file:
				session_consents.get(0).setVerbal_consent_document(null);
				break;
			}
			
			dataToReturn = JSONArray.fromObject(new ConsentFile()).toString();
			
			break;

		case "PATIENT":

			session_patient = new Patient();
			if(nameOfInputbox.length == 1 && nameOfInputbox[0].equalsIgnoreCase(BCITBVariousVariables.patient_id)) {
				session_patient = findPatientFromDatabase(session_patient, whichDepartment, Integer.valueOf(valueOfInputbox[0]));
			} else
				for(Patient pat: patientService.getPatientDetails(whichDepartment, nameOfInputbox, valueOfInputbox)) {
					session_patient = pat;
					session_patient.setPatient_which_department(whichDepartment); 
					if(consentService.getPatientConsents(BCITBVariousVariables.consent_withdrawn, whichDepartment, session_patient.getPatient_id(), "", "", null, "").size() 
							== consentService.getPatientConsents("", whichDepartment, session_patient.getPatient_id(), "", "", null, "").size()) {
						session_patient.setVolunteer(""); session_patient.setGender("");
					}
				}
			
			if(session_patient.getPatient_id() > 0)
				return JSONObject.fromObject(session_patient).toString();
			else
				return JSONObject.fromObject(null).toString();
		
		case "ALERT-PATIENT-VOLUNTEER":
			
			if(session_patient.getVolunteer() == null)
				session_patient = findPatientFromDatabase(session_patient, whichDepartment, session_patient.getPatient_id());
			
			switch (whichDepartment) {
			case BCITBVariousVariables.BGTB: case BCITBVariousVariables.HOTB:
				if(valueOfInputbox[0].equalsIgnoreCase(BCITBVariousVariables.volunteer) && 
					session_patient.getVolunteer().equalsIgnoreCase(BCITBVariousVariables.no))
						return JSONObject.fromObject(null).toString(); // return null to show alert
				else if(!valueOfInputbox[0].equalsIgnoreCase(BCITBVariousVariables.volunteer) &&  
					session_patient.getVolunteer().equalsIgnoreCase(BCITBVariousVariables.yes))
						return JSONObject.fromObject(null).toString(); // return null to show alert
				break;
			case BCITBVariousVariables.CTB:
				if(valueOfInputbox[0].equalsIgnoreCase(BCITBVariousVariables.patient) && session_patient.getVolunteer().equalsIgnoreCase(BCITBVariousVariables.yes))
					return JSONObject.fromObject(null).toString(); // return null to show alert
				else if(!valueOfInputbox[0].equalsIgnoreCase(BCITBVariousVariables.patient) && session_patient.getVolunteer().equalsIgnoreCase(BCITBVariousVariables.no))
					return JSONObject.fromObject(null).toString(); // return null to show alert
				break;
			}

			dataToReturn =  JSONObject.fromObject(session_patient).toString();
			break;
			
		case "ALLOWING-USER-TO-EDIT-PATIENT": case "ALLOWING-USER-TO-EDIT-INFECTION-RISK":

			if (session_patient.getPatient_id() <= 0 && valueOfInputbox.length > 0 && Integer.parseInt(valueOfInputbox[0]) > 0) {
				session_patient.setPatient_which_department(whichDepartment);
				session_patient = patientService.getPatientFromID(whichDepartment, Integer.parseInt(valueOfInputbox[0]));
			}

			if(consentService.getPatientConsents("all",whichDepartment, session_patient.getPatient_id(), 
					"", "", user_selected_locations, BCITBVariousVariables.ascending).size() 
					== consentService.getPatientConsents(BCITBVariousVariables.is_validated,whichDepartment, session_patient.getPatient_id(), 
					"", "", user_selected_locations, BCITBVariousVariables.ascending).size() && 
					!primaryRole.getUserRoleAccess(BCITBVariousVariables.consent).toLowerCase().contains(BCITBVariousVariables.edit) && 
					!primaryRole.getUserRoleAccess(BCITBVariousVariables.consent_draft).toLowerCase().contains(BCITBVariousVariables.edit))
				dataToReturn = JSONObject.fromObject(null).toString();
			else
				dataToReturn = JSONObject.fromObject(session_patient).toString();

			break;
			
		case "UNLOCK-ACTION-AND-REFRESH":
			
			for(Action act:various_actions.getLocked_actions())
				if(act.getAction_id() == Integer.parseInt(valueOfInputbox[0])) {
					actionService.saveActionVariousColumns(act.getAction_which_department(), 
							act.getAction_id(), BCITBVariousVariables.status, BCITBVariousVariables.active);
					act.setStatus(BCITBVariousVariables.active);
					dataToReturn = JSONObject.fromObject(act).toString();
				}
			break;

		case "UNLOCK-PATIENT-AND-REFRESH":
			
			patientService.savePatientVariousColumns(whichDepartment, Integer.parseInt(valueOfInputbox[0]), BCITBVariousVariables.locked_description, "");
			patientService.savePatientVariousColumns(whichDepartment, Integer.parseInt(valueOfInputbox[0]), BCITBVariousVariables.locked_by, "");
			dataToReturn = JSONObject.fromObject(patientService.getPatientFromID(whichDepartment, Integer.parseInt(valueOfInputbox[0]))).toString();
			break;

		case "CHECK-PATIENT-LOCKED-AND-SUBMIT": case "CHECK-PATIENT-STATUS-IMPORTED-CONSENT-AND-SUBMIT": case "CHECK-PATIENT-STATUS-AUDIT-CONSENT-AND-SUBMIT":

			switch (whatToProcess) {
			case "CHECK-PATIENT-LOCKED-AND-SUBMIT": 
				dataToReturn = JSONObject.fromObject(patientService.getPatientFromID(whichDepartment,Integer.parseInt(valueOfInputbox[0]))).toString();
				break;
			case "CHECK-PATIENT-STATUS-IMPORTED-CONSENT-AND-SUBMIT": case "CHECK-PATIENT-STATUS-AUDIT-CONSENT-AND-SUBMIT": 
				this_consent = consentService.getPatientConsent(whichDepartment, Integer.parseInt(valueOfInputbox[0]), "", "", null);
				if(this_consent != null)
					dataToReturn = JSONObject.fromObject(patientService.getPatientFromID(whichDepartment,this_consent.getCn_patient_id())).toString();
				break;
			}
			break;
			
		case "CHECK-ACTION-LOCKED-AND-SUBMIT":

			this_action = actionService.getAction(whichDepartment, Integer.parseInt(valueOfInputbox[0]));
			dataToReturn = JSONObject.fromObject(this_action).toString();

			if(!this_action.getStatus().equalsIgnoreCase(BCITBVariousVariables.locked)) {
				switch (this_action.getData_type()) {
				case BCITBVariousVariables.consent:
					dataToReturn = JSONObject.fromObject(patientService.getPatientFromID(whichDepartment,
							consentService.getPatientConsent(whichDepartment, this_action.getData_id(), "", "", null).getCn_patient_id())).toString();
					break;
				case BCITBVariousVariables.infection_risk:
					dataToReturn = JSONObject.fromObject(patientService.getPatientFromID(whichDepartment,
							infectionRiskService.getInfectionRisk(whichDepartment, this_action.getData_id()).getIr_patient_id())).toString();
					break;
				}
			}
			break;

		case "UNLOCK-USER-ACTION": 
			
			dataToReturn = JSONObject.fromObject(new Action()).toString();
			various_actions = unlockUserActionIfAny(various_actions,columnName,columnValue);
			break;
			
		case "SAVE-USER-SELECTED-ACTION-TYPE":
			
			if(!valueOfInputbox[0].trim().isEmpty()) {
				for(Action act:various_actions.getActive_actions()) {
					if(act.getStatus().equalsIgnoreCase(BCITBVariousVariables.locked)) {
						act.setUser_selected_action_type(valueOfInputbox[0]);
						dataToReturn = JSONObject.fromObject(act).toString();
					}
				}
			}
			break;
		
		case "GET-SINGLE-AUDIT-SAMPLE-FROM-SESSION":
			
			if(session_consent_audit.getConsent_audit_id() <= 0 && session_consents.get(0).getConsent_id() > 0) {
				session_consent_audit = consentService.getConsentAudit(whichDepartment, session_consents.get(0).getConsent_id());
				session_consent_audit.setCa_which_department(whichDepartment);
			}
				
			if(session_consent_audit.getSample_types() == null) {
				session_consent_audit.setSample_types(new ArrayList<ConsentAuditSample>(consentService.getConsentAuditSamples(whichDepartment, session_consent_audit.getConsent_audit_id())));
				for(ConsentAuditSample cas:session_consent_audit.getSample_types())
					cas.setAudit_sample_type(auditProcessService.getAuditSampleType(whichDepartment, cas.getAud_sample_type_id()));
			}
			for(ConsentAuditSample cas:session_consent_audit.getSample_types()) {
				if(cas.getAud_sample_pid() == Integer.parseInt(valueOfInputbox[0])) 
					dataToReturn = JSONObject.fromObject(cas).toString();
			}
			break;
			
		case "SAVE-SESSION-INFECTION-RISK":

			for(InfectionRisk ir:session_infection_risks)
				if(ir.getIr_which_department() != null && !ir.getIr_which_department().trim().isEmpty())
					if(session_patient.getPatient_which_department() == null || session_patient.getPatient_which_department().trim().isEmpty())
						session_patient.setPatient_which_department(ir.getIr_which_department());
			
			if(whichURL.contains("/infection_risk_found_notify_om") || whichURL.contains("/verify_consent"))
				session_infection_risks = new ArrayList<InfectionRisk>(updateInfectionRisk(session_patient, user, primaryRole, 
						"",session_infection_risks, various_actions.getActive_actions(), user_selected_locations, false, columnName, columnValue));
			else
				session_infection_risks = new ArrayList<InfectionRisk>(updateInfectionRisk(session_patient, user, primaryRole, 
						"",session_infection_risks, various_actions.getActive_actions(), user_selected_locations, true, columnName, columnValue));
				
			dataToReturn = JSONArray.fromObject(session_infection_risks).toString();

			break;

		case "GET-IMPORT-CONSENTS-FROM-SESSION":
			
			if(imported_consents.size() <= 0) {
				for(UserDepartment user_dept:user.getUserDepartments())
					for(Consent con: consentService.getPatientConsents(BCITBVariousVariables.is_imported, 
							user_dept.getDepartment().getDept_acronym(), 0, "", "", null, BCITBVariousVariables.ascending)) {
						if(actionService.getActionsFromDataId(user_dept.getDepartment().getDept_acronym(), con.getConsent_id(), 
								BCITBVariousVariables.consent, "", BCITBVariousVariables.active, BCITBVariousVariables.ascending).size() <= 0) {
							con.setConsent_which_department(user_dept.getDepartment().getDept_acronym());
							if(con.getSam_coll_before_sep_2006() != null && con.getSam_coll_before_sep_2006().equalsIgnoreCase(BCITBVariousVariables.yes))
								con.setDescription("(" + con.getConsent_which_department() + ") Validate imported consent (sample collected before Sep 2006)");
							else
								con.setDescription("(" + con.getConsent_which_department() + ") Validate imported consent dated " + con.getDate_of_consent());
							imported_consents.add(con);
						}
					}
			}
			
			dataToReturn = JSONArray.fromObject(imported_consents).toString();
			break;
			
		case "GET-AUDIT-CONSENTS-FROM-SESSION":
			
			dataToReturn = JSONArray.fromObject(existing_audit_consents).toString();
			break;
			
		case "GET-ALL-CONSENTS-FROM-DB":
			
			dataToReturn = JSONArray.fromObject(populateVariousBitsOfConsents(whichDepartment,
					"ALL", consentService.getPatientConsents("all",whichDepartment, Integer.parseInt(valueOfInputbox[0]), 
					"", "", null, BCITBVariousVariables.ascending))).toString();
			break;

		case "GET-SINGLE-COMPLETE-CONSENT-FROM-SESSION":
			
			session_consents = new ArrayList<Consent>(populateVariousBitsOfConsents(whichDepartment, "ALL", session_consents));
			session_consents.get(0).setConsent_which_department(whichDepartment);
			dataToReturn = JSONObject.fromObject(session_consents.get(0)).toString();
			break;

		case "GET-SINGLE-COMPLETE-CONSENT-AUDIT-FROM-SESSION":
			
			session_consent_audit = consentService.getConsentAudit(whichDepartment, Integer.parseInt(valueOfInputbox[0]));
			if (session_consent_audit == null)
				session_consent_audit = new ConsentAudit(Integer.parseInt(valueOfInputbox[0]), whichDepartment);
			session_consent_audit.setCa_which_department(whichDepartment);
			
			session_consent_audit.setSample_types(new ArrayList<ConsentAuditSample>(consentService.getConsentAuditSamples(whichDepartment, session_consent_audit.getConsent_audit_id())));
			for(ConsentAuditSample cas:session_consent_audit.getSample_types())
				cas.setAudit_sample_type(auditProcessService.getAuditSampleType(whichDepartment, cas.getAud_sample_type_id()));
			
			dataToReturn = JSONObject.fromObject(session_consent_audit).toString();

			break;
			
		case "GET-SINGLE-COMPLETE-CONSENT-VALIDATE-FROM-SESSION":
			
			session_consent_validate = consentService.getConsentValidate(whichDepartment, Integer.parseInt(valueOfInputbox[0]));
			if (session_consent_validate == null)
				session_consent_validate = new ConsentValidate(Integer.parseInt(valueOfInputbox[0]), whichDepartment);
			session_consent_validate.setCv_which_department(whichDepartment);
			dataToReturn = JSONObject.fromObject(session_consent_validate).toString();
			
			break;

		case "DISCARD-SAMPLE-AUDIT-FROM-SESSION":
			
			if(session_consent_audit.getSample_types() != null && session_consent_audit.getSample_types().size() > 0)
				session_consent_audit.getSample_types().removeIf((ConsentAuditSample cas) -> 
					cas.getAud_sample_pid() == Integer.parseInt(valueOfInputbox[0])); // Remove the audit sample from buffer
			
			dataToReturn = JSONArray.fromObject(session_consent_audit.getSample_types()).toString();
			
		case "GET-ALL-AUDIT-SAMPLES-FROM-SESSION":
			
			for(ConsentAuditSample cas:session_consent_audit.getSample_types())
				if(cas.getAud_sample_type_id() > 0)
					cas.setAudit_sample_type(auditProcessService.getAuditSampleType(whichDepartment, cas.getAud_sample_type_id()));
				
			dataToReturn = JSONArray.fromObject(session_consent_audit.getSample_types()).toString();
			
			break;
			
		case "GET-ALL-INFECTION-RISKS-FROM-DB-SESSION":

			session_infection_risks.clear();
			for(InfectionRisk ir:infectionRiskService.getPatientInfectionRisks("",whichDepartment, Integer.parseInt(valueOfInputbox[0]))) {
				if(ir.getIr_deletion_date() == null) {
					ir.setIr_which_department(whichDepartment);
					session_infection_risks.add(ir);
				}
			}
			if(session_infection_risks.size() > 0)
				dataToReturn = JSONArray.fromObject(session_infection_risks).toString();
			else
				dataToReturn = JSONArray.fromObject(null).toString();
			break;
		
		case "GET-SINGLE-COMPLETED-ACTION-FROM-SESSION": case "GET-SINGLE-COMPLETED-ACTION": 
			
			switch (whatToProcess) {
			case "GET-SINGLE-COMPLETED-ACTION":
				dataToReturn = JSONObject.fromObject(actionService.getAction(whichDepartment, Integer.parseInt(valueOfInputbox[0]))).toString();
				break;
			case "GET-SINGLE-COMPLETED-ACTION-FROM-SESSION": 
				for(Action act:various_actions.getCompleted_actions())
					if(act.getAction_id() == Integer.parseInt(valueOfInputbox[0]) && act.getAction_which_department().equalsIgnoreCase(whichDepartment))
						dataToReturn = JSONObject.fromObject(act).toString();
				break;
			}
			break;
		
		case "GET-SINGLE-TIMELINE":

			for(Timeline tl:session_timeline) 
				if(tl.getTimeline_id() == Integer.parseInt(valueOfInputbox[0]))
					dataToReturn = JSONObject.fromObject(tl).toString();
						
			break;
			
		case "GET-ALL-TIMELINE-FROM-DB":
			
			session_timeline.clear();
			for(Timeline tl: getTimelineData(user_selected_department.getDept_acronym(),Integer.parseInt(valueOfInputbox[0]),BCITBVariousVariables.ascending))
				session_timeline.add(tl);
			dataToReturn = JSONArray.fromObject(session_timeline).toString();
			break;
			
		case "GET-ACTIVE-ACTIONS-FROM-SESSION": case "GET-COMPLETED-ACTIONS-FROM-SESSION": case "GET-LOCKED-ACTIONS-FROM-SESSION": case "GET-LOCKED-PATIENTS-FROM-SESSION":
			
			switch (whatToProcess) {
			case "GET-LOCKED-PATIENTS-FROM-SESSION":
				List<Patient> locked_patients = new ArrayList<Patient>();
				for(UserDepartment user_dept:user.getUserDepartments()) 
					for(Patient pat: patientService.getLockedPatient("",user_dept.getDepartment().getDept_acronym(),user.getUser_id())) {
						pat.setPatient_which_department(user_dept.getDepartment().getDept_acronym());
						locked_patients.add(pat);
					}
				dataToReturn = JSONArray.fromObject(locked_patients).toString();
				break;
			case "GET-ACTIVE-ACTIONS-FROM-SESSION": 
				various_actions.setActive_actions(getUserTasks(
						"ACTIVE-ACTIONS",BCITBVariousVariables.ascending, user, primaryRole));
				dataToReturn = JSONArray.fromObject(various_actions.getActive_actions()).toString();
				break;
			case "GET-COMPLETED-ACTIONS-FROM-SESSION": 
				various_actions.setCompleted_actions(getUserTasks(
						"COMPLETED-ACTIONS",BCITBVariousVariables.ascending, user, primaryRole));
				dataToReturn = JSONArray.fromObject(various_actions.getCompleted_actions()).toString();
				break;
			case "GET-LOCKED-ACTIONS-FROM-SESSION": 
				various_actions.setLocked_actions(getUserTasks(
						"LOCKED-ACTIONS",BCITBVariousVariables.ascending, user, primaryRole));
				dataToReturn = JSONArray.fromObject(various_actions.getLocked_actions()).toString();
				break;
			}
			break;

		case "GET-CONSENT-ACTIONS-FROM-DB":
			
			consent_actions = new ArrayList<Action>(actionService.getActionsFromDataId(whichDepartment, 
					Integer.parseInt(valueOfInputbox[0]),BCITBVariousVariables.consent, "", "",BCITBVariousVariables.ascending));
			for(Action act:consent_actions) {
				for(ActionRole act_role:actionService.getActionRoles(act.getAction_type()))
					if(act.getAll_roles() == null)
						act.setAll_roles(act_role.getRole().getRole_description());
					else
						act.setAll_roles(act.getAll_roles() + ", " + act_role.getRole().getRole_description());
			}
				
			dataToReturn = JSONArray.fromObject(consent_actions).toString();
			break;
		
		case "GET-FIRST-INFECTION-RISK-FROM-SESSION":

			dataToReturn = JSONObject.fromObject(session_infection_risks.get(0)).toString();
			break;
		
		case "GET-SINGLE-INFECTION-RISK-FROM-SESSION":

			for(InfectionRisk ir:session_infection_risks) 
				if(ir.getInfection_risk_id() == Integer.parseInt(valueOfInputbox[0])) 
					dataToReturn = JSONObject.fromObject(ir).toString();
			break;

		case "GET-ALL-INFECTION-RISKS-FROM-SESSION":
			
			dataToReturn = JSONArray.fromObject(session_infection_risks).toString();
			break;
		
		case "DISCARD-INFECTION-RISK-FROM-SESSION-IRS":
			
			if(session_infection_risks.size() > 0) 
				session_infection_risks.removeIf((InfectionRisk ir) -> ir.getInfection_risk_id() == Integer.parseInt(valueOfInputbox[0])); // Remove the old IR from buffer
			break;
		
		case "ADD-NEW-AUDIT-SAMPLE-TO-SESSION":
			
			if(session_consent_audit.getSample_types() == null || session_consent_audit.getSample_types().size() <= 0) {
				session_consent_audit.setSample_types(new ArrayList<ConsentAuditSample>(
						consentService.getConsentAuditSamples(user_selected_department.getDept_acronym(), session_consent_audit.getConsent_audit_id())));
				for(ConsentAuditSample cas:session_consent_audit.getSample_types())
					cas.setAudit_sample_type(auditProcessService.getAuditSampleType(user_selected_department.getDept_acronym(), cas.getAud_sample_type_id()));
			}

			List<ConsentAuditSample> this_cas_list = new ArrayList<ConsentAuditSample>(session_consent_audit.getSample_types());
	        if(this_cas_list.size() > 0) {
	        	this_cas_list.add(new ConsentAuditSample((this_cas_list.size() + 1) * (-1), user_selected_department.getDept_acronym()));
	        } else {
	        	this_cas_list.add(new ConsentAuditSample(-1, user_selected_department.getDept_acronym()));
	        }
        	session_consent_audit.setSample_types(this_cas_list);

			dataToReturn = JSONObject.fromObject(this_cas_list.get(session_consent_audit.getSample_types().size() - 1)).toString();
			
			break;

		case "ADD-NEW-INFECTION-RISK-TO-SESSION-IRS":
			
			Iterator<InfectionRisk> itr = session_infection_risks.iterator();
			while(itr.hasNext()) {
				InfectionRisk ir = itr.next();
				if(ir.getInfection_risk_exist() == null || ir.getInfection_risk_exist().isEmpty())
					itr.remove();
			}
			
	        if(session_infection_risks.size() > 0) 
	        	session_infection_risks.add(new InfectionRisk((session_infection_risks.size() + 1) * (-1),
	        			user_selected_department.getDept_acronym(), session_patient.getPatient_id()));
	        else
	        	session_infection_risks.add(new InfectionRisk(-1,user_selected_department.getDept_acronym()));

			dataToReturn = JSONObject.fromObject(session_infection_risks.get(session_infection_risks.size() - 1)).toString();
			
			break;
			
		}

		return dataToReturn;

	} 	
	
	public Patient unlockPatient(Patient patient, User user, Department dept)
	{
		if(patient != null && patient.getPatient_id() > 0) {
			if(dept != null && dept.getDept_acronym() != null && !dept.getDept_acronym().isEmpty())
				patient.setPatient_which_department(dept.getDept_acronym());
			if(patient.getPatient_which_department() != null && !patient.getPatient_which_department().isEmpty()) {
				patient.setLocked_description("");
				patient.setLocked_by(0);
				patientService.savePatientVariousColumns(patient.getPatient_which_department(), patient.getPatient_id(), 
						BCITBVariousVariables.locked_description, "");
				patientService.savePatientVariousColumns(patient.getPatient_which_department(), patient.getPatient_id(), 
						BCITBVariousVariables.locked_by, "");
			}
		} else if(user != null && user.getUser_id() > 0 && dept.getDept_acronym() != null) {
			for(Patient pat: patientService.getLockedPatient("",dept.getDept_acronym(), user.getUser_id())) {
				pat.setLocked_description("");
				pat.setLocked_by(0);
				patientService.savePatientVariousColumns(dept.getDept_acronym(), pat.getPatient_id(), 
						BCITBVariousVariables.locked_description, "");
				patientService.savePatientVariousColumns(dept.getDept_acronym(), pat.getPatient_id(), 
						BCITBVariousVariables.locked_by, "");
			}
		} else {
			for(Department department : departmentService.getAllDepartments()) {
				switch (department.getDept_acronym()) {
				case BCITBVariousVariables.BGTB: case BCITBVariousVariables.HOTB:
					for(Patient pat: patientService.getLockedPatient(BCITBVariousVariables.locked_description,department.getDept_acronym(),0)) {
						try {
							if(ChronoUnit.DAYS.between(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("d-M-yyyy").parse(
											pat.getLocked_description().split(" ")[pat.getLocked_description().split(" ").length-2]))), LocalDate.now()) >= 1) {
								patientService.savePatientVariousColumns(department.getDept_acronym(), pat.getPatient_id(), 
										BCITBVariousVariables.locked_description, "");
								patientService.savePatientVariousColumns(department.getDept_acronym(), pat.getPatient_id(), 
										BCITBVariousVariables.locked_by, "");
							}
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
					break;
				}
			}
		}
		return patient;
	}

	public VariousActions unlockUserActionIfAny(VariousActions various_actions, List<String> columnName, List<String> columnValue)
	{
		if(various_actions.getActive_actions() != null)
			for(Action act:various_actions.getActive_actions()) 
				if(act.getStatus().equalsIgnoreCase(BCITBVariousVariables.locked)) {
					columnName.clear();columnValue.clear();
					columnName.add(BCITBVariousVariables.status);columnValue.add(BCITBVariousVariables.active);
					columnName.add(BCITBVariousVariables.locked_description);columnValue.add("");
					columnName.add(BCITBVariousVariables.locked_by);columnValue.add("");
					actionService.saveActionVariousColumns(act.getAction_which_department(), act.getAction_id(), columnName, columnValue);
					act.setStatus(BCITBVariousVariables.active);
				}
		return various_actions;
	}
	
	public List<Action> processAction(String whatToProcess, String whichURLToProcess, User whichUser, Role whichRole, 
			List<Consent> consents, List<InfectionRisk> infection_risks, List<InfectionRisk> previous_infection_risks, 
			List<Action> active_actions, List<Location> locations, Patient patient, List<String> columnName, List<String> columnValue) throws ParseException
	{
		Action action = new Action();
		
		switch (whatToProcess) {
		case BCITBVariousVariables.consent:
			if(consents.get(0).getWithdrawn() != null && consents.get(0).getWithdrawn().equalsIgnoreCase(BCITBVariousVariables.yes) 
					&& (consents.get(0).getIs_withdrawn() == null || !consents.get(0).getIs_withdrawn().equalsIgnoreCase(BCITBVariousVariables.yes))) { // Check if consent was withdrawn before 
				
				for(Action act:actionService.getActionsFromDataId(consents.get(0).getConsent_which_department(), consents.get(0).getConsent_id(), 
						BCITBVariousVariables.consent, "", BCITBVariousVariables.active,BCITBVariousVariables.ascending)) {
					actionService.saveActionVariousColumns(consents.get(0).getConsent_which_department(), act.getAction_id(), BCITBVariousVariables.status, BCITBVariousVariables.not_applicable);
				}
				for(InfectionRisk ir:infectionRiskService.getPatientInfectionRisks("",consents.get(0).getConsent_which_department(), consents.get(0).getCn_patient_id())) {
					infectionRiskService.saveInfectionRiskVariousColumns(consents.get(0).getConsent_which_department(), ir.getInfection_risk_id(), 
							BCITBVariousVariables.ir_deletion_date, DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now()));
					for(Action act:actionService.getActionsFromDataId(consents.get(0).getConsent_which_department(), ir.getInfection_risk_id(), 
							BCITBVariousVariables.infection_risk, "", BCITBVariousVariables.active,BCITBVariousVariables.ascending)) {
						actionService.saveActionVariousColumns(consents.get(0).getConsent_which_department(), act.getAction_id(), 
								BCITBVariousVariables.status, BCITBVariousVariables.not_applicable);
					}
				}

				consentService.saveConsentVariousColumns(consents.get(0).getConsent_which_department(), consents.get(0).getConsent_id(), 
						BCITBVariousVariables.is_withdrawn, BCITBVariousVariables.yes);
				consentService.saveConsentVariousColumns(consents.get(0).getConsent_which_department(), consents.get(0).getConsent_id(), 
						BCITBVariousVariables.consent_deletion_date, DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now()));
				patientService.savePatientVariousColumns(consents.get(0).getConsent_which_department(), consents.get(0).getCn_patient_id(), 
						BCITBVariousVariables.withdrawn_count, String.valueOf(patientService.getPatientFromID(consents.get(0).getConsent_which_department(), 
								consents.get(0).getCn_patient_id()).getWithdrawn_count() == null? 1:patientService.getPatientFromID(consents.get(0).getConsent_which_department(), 
										consents.get(0).getCn_patient_id()).getWithdrawn_count() + 1));
				
				if(consents.get(0).getSam_coll_before_sep_2006() != null && consents.get(0).getSam_coll_before_sep_2006().equalsIgnoreCase(BCITBVariousVariables.yes))
					timelineService.saveTimeline(consents.get(0).getConsent_which_department(), consents.get(0).getConsent_id(), 
							whichUser.getUser_id(), whichRole.getRole_id(), BCITBVariousVariables.consent_withdrawn, "",
							DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()),BCITBVariousVariables.consent_withdrawn,
							"Consent (sample collected before Sep 2006) was withdrawn by " + whichUser.getUsername() + 
							" [" + whichRole.getRole_description() + "] on " + DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now())
							,BCITBVariousVariables.no, consents.get(0).getCn_patient_id());
				else
					timelineService.saveTimeline(consents.get(0).getConsent_which_department(), consents.get(0).getConsent_id(), 
							whichUser.getUser_id(), whichRole.getRole_id(), BCITBVariousVariables.consent_withdrawn, "",
							DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()),BCITBVariousVariables.consent_withdrawn,
							"Consent dated " + new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(consents.get(0).getDate_of_consent())) 
							+ " was withdrawn by " + whichUser.getUsername() + " [" + whichRole.getRole_description() + "] on " 
							+ DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()), BCITBVariousVariables.no, consents.get(0).getCn_patient_id());
				
				action = new Action(consents.get(0).getConsent_id(), BCITBVariousVariables.consent, 
						BCITBVariousVariables.remove_samples,BCITBVariousVariables.consent_withdrawn_remove_samples,BCITBVariousVariables.active,
						DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()),whichUser.getUser_id(),"");
				action.setDescription(populateActionsDescription(consents.get(0).getConsent_which_department(), action, consents.get(0), patient.getDatabase_id()));
				actionService.saveAction(consents.get(0).getConsent_which_department(), action);

				if(consents.get(0).getSam_coll_before_sep_2006() != null && consents.get(0).getSam_coll_before_sep_2006().equalsIgnoreCase(BCITBVariousVariables.yes))
					timelineService.saveTimeline(consents.get(0).getConsent_which_department(), action.getAction_id(), 
							whichUser.getUser_id(), whichRole.getRole_id(), BCITBVariousVariables.remove_samples, 
							BCITBVariousVariables.consent_withdrawn_remove_samples + ". Notes: " + consents.get(0).getConsent_notes(),
							DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()),BCITBVariousVariables.action,
							"Action sent: Consent (sample collected before Sep 2006) withdrawn. Action sent to Technician(s) on " + 
							DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + " by " + whichUser.getUsername() + 
							" [" + whichRole.getRole_description() + "]",BCITBVariousVariables.no, consents.get(0).getCn_patient_id());
				else
					timelineService.saveTimeline(consents.get(0).getConsent_which_department(), action.getAction_id(), 
							whichUser.getUser_id(), whichRole.getRole_id(), BCITBVariousVariables.remove_samples, 
							BCITBVariousVariables.consent_withdrawn_remove_samples + ". Notes: " + consents.get(0).getConsent_notes(),
							DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()),BCITBVariousVariables.action,
							"Action sent: Consent dated " + new SimpleDateFormat("dd-MM-yyyy").format(
							new SimpleDateFormat("yyyy-MM-dd").parse(consents.get(0).getDate_of_consent())) + " withdrawn. Action sent to Technician(s) on " + 
							DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + " by " + whichUser.getUsername() + 
							" [" + whichRole.getRole_description() + "]",BCITBVariousVariables.no, consents.get(0).getCn_patient_id());
				
				action = new Action(consents.get(0).getConsent_id(), BCITBVariousVariables.consent, 
						BCITBVariousVariables.consent_withdrawn, BCITBVariousVariables.consent_withdrawn_notification,BCITBVariousVariables.active,
						DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()),whichUser.getUser_id(), "");
				action.setDescription(populateActionsDescription(consents.get(0).getConsent_which_department(), action, consents.get(0), patient.getDatabase_id()));
				actionService.saveAction(consents.get(0).getConsent_which_department(), action);
						
				if(consents.get(0).getSam_coll_before_sep_2006() != null && consents.get(0).getSam_coll_before_sep_2006().equalsIgnoreCase(BCITBVariousVariables.yes))
					timelineService.saveTimeline(consents.get(0).getConsent_which_department(), action.getAction_id(), 
							whichUser.getUser_id(), whichRole.getRole_id(), BCITBVariousVariables.consent_withdrawn, 
							BCITBVariousVariables.consent_withdrawn_notification + ". Notes: " + consents.get(0).getConsent_notes(),
							DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()),BCITBVariousVariables.action,
							"Action sent: Consent (sample collected before Sep 2006) withdrawn. Action sent to Operations Manager on " + 
							DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + " by " + whichUser.getUsername() + 
							" [" + whichRole.getRole_description() + "]",BCITBVariousVariables.no, consents.get(0).getCn_patient_id());
				else
					timelineService.saveTimeline(consents.get(0).getConsent_which_department(), action.getAction_id(), 
							whichUser.getUser_id(), whichRole.getRole_id(), BCITBVariousVariables.consent_withdrawn, 
							BCITBVariousVariables.consent_withdrawn_notification + ". Notes: " + consents.get(0).getConsent_notes(),
							DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()),BCITBVariousVariables.action,
							"Action sent: Consent dated " + new SimpleDateFormat("dd-MM-yyyy").format(
							new SimpleDateFormat("yyyy-MM-dd").parse(consents.get(0).getDate_of_consent())) + " withdrawn. Action sent to Operations Manager on " + 
							DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + " by " + whichUser.getUsername() + 
							" [" + whichRole.getRole_description() + "]",BCITBVariousVariables.no, consents.get(0).getCn_patient_id());
			
			} else if(consents.get(0).getWithdrawn() != null && consents.get(0).getWithdrawn().equalsIgnoreCase(BCITBVariousVariables.no) 
					&& (consents.get(0).getIs_withdrawn() != null && consents.get(0).getIs_withdrawn().equalsIgnoreCase(BCITBVariousVariables.yes))) { // Check if consent is withdrawn 
				
				consentService.saveConsentVariousColumns(consents.get(0).getConsent_which_department(), consents.get(0).getConsent_id(), 
						BCITBVariousVariables.is_withdrawn, "");
				consentService.saveConsentVariousColumns(consents.get(0).getConsent_which_department(), consents.get(0).getConsent_id(), 
						BCITBVariousVariables.consent_deletion_date, "");
				patientService.savePatientVariousColumns(consents.get(0).getConsent_which_department(), consents.get(0).getCn_patient_id(), 
						BCITBVariousVariables.withdrawn_count, String.valueOf(patientService.getPatientFromID(consents.get(0).getConsent_which_department(), 
								consents.get(0).getCn_patient_id()).getWithdrawn_count() == null? 0:patientService.getPatientFromID(consents.get(0).getConsent_which_department(), 
								consents.get(0).getCn_patient_id()).getWithdrawn_count() - 1));
				
				for(Action act:actionService.getActionsFromDataId(consents.get(0).getConsent_which_department(), consents.get(0).getConsent_id(), 
						BCITBVariousVariables.consent, BCITBVariousVariables.consent_withdrawn, BCITBVariousVariables.active,BCITBVariousVariables.ascending)) 
					actionService.saveActionVariousColumns(consents.get(0).getConsent_which_department(), act.getAction_id(), BCITBVariousVariables.status, BCITBVariousVariables.not_applicable);
				for(Action act:actionService.getActionsFromDataId(consents.get(0).getConsent_which_department(), consents.get(0).getConsent_id(), 
						BCITBVariousVariables.consent, BCITBVariousVariables.remove_samples, BCITBVariousVariables.active,BCITBVariousVariables.ascending)) 
					actionService.saveActionVariousColumns(consents.get(0).getConsent_which_department(), act.getAction_id(), BCITBVariousVariables.status, BCITBVariousVariables.not_applicable);
				
				for(InfectionRisk ir:infectionRiskService.getPatientInfectionRisks("",consents.get(0).getConsent_which_department(), consents.get(0).getCn_patient_id())) 
					if(ir.getIr_deletion_date() != null && !ir.getIr_deletion_date().isEmpty())
						infectionRiskService.saveInfectionRiskVariousColumns(consents.get(0).getConsent_which_department(), ir.getInfection_risk_id(), 
								BCITBVariousVariables.ir_deletion_date, "");
				
				if(consents.get(0).getSam_coll_before_sep_2006() != null && consents.get(0).getSam_coll_before_sep_2006().equalsIgnoreCase(BCITBVariousVariables.yes))
					timelineService.saveTimeline(consents.get(0).getConsent_which_department(), action.getAction_id(), 
							whichUser.getUser_id(), whichRole.getRole_id(), BCITBVariousVariables.consent_withdrawn, 
							"Notes: " + consents.get(0).getConsent_notes(), DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()),
							BCITBVariousVariables.action, "Edits: Consent (sample collected before Sep 2006) withdrawn reverted on " + 
							DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + " by " + 
							whichUser.getUsername() + " [" + whichRole.getRole_description() + "]",BCITBVariousVariables.no, consents.get(0).getCn_patient_id());
				else
					timelineService.saveTimeline(consents.get(0).getConsent_which_department(), action.getAction_id(), 
							whichUser.getUser_id(), whichRole.getRole_id(), BCITBVariousVariables.consent_withdrawn, 
							"Notes: " + consents.get(0).getConsent_notes(), DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()),
							BCITBVariousVariables.action, "Edits: Consent dated " + new SimpleDateFormat("dd-MM-yyyy").format(  
							new SimpleDateFormat("yyyy-MM-dd").parse(consents.get(0).getDate_of_consent())) + " withdrawn reverted on " + 
							DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + " by " + 
							whichUser.getUsername() + " [" + whichRole.getRole_description() + "]",BCITBVariousVariables.no, consents.get(0).getCn_patient_id());
				
			}
			break;
			
		case BCITBVariousVariables.infection_risk:

			InfectionRisk prev_infection_risk = null;
			
			for(InfectionRisk ir:infection_risks) {
				
				prev_infection_risk = null;
				for(InfectionRisk prev_ir:previous_infection_risks) 
					if(ir.getInfection_risk_id() == prev_ir.getInfection_risk_id()) 
						prev_infection_risk = prev_ir;

				if(patient.getDatabase_id() == null || patient.getDatabase_id().isEmpty())
					patient = patientService.getPatientFromID(ir.getIr_which_department(), ir.getIr_patient_id());
				
				if(prev_infection_risk == null) {
					if(ir.getInfection_risk_exist().toLowerCase().contains(BCITBVariousVariables.yes)) {
						
						action = new Action(ir.getInfection_risk_id(), BCITBVariousVariables.infection_risk, 
								BCITBVariousVariables.infection_risk_found_notify_om, BCITBVariousVariables.infection_risk_found_om_note, BCITBVariousVariables.active,
								DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()),whichUser.getUser_id(), "");
						action.setDescription(populateActionsDescription(ir.getIr_which_department(), action, null, patient.getDatabase_id()));
						
						actionService.saveAction(ir.getIr_which_department(), action);
						
						action = new Action(ir.getInfection_risk_id(), BCITBVariousVariables.infection_risk, 
								BCITBVariousVariables.infection_risk_found_notify_tech, BCITBVariousVariables.infection_risk_found_tech_note, BCITBVariousVariables.active,
								DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()),whichUser.getUser_id(),"");
						action.setDescription(populateActionsDescription(ir.getIr_which_department(), action, null, patient.getDatabase_id()));
						
						actionService.saveAction(ir.getIr_which_department(), action);
						timelineService.saveTimeline(ir.getIr_which_department(), ir.getInfection_risk_id(), whichUser.getUser_id(), whichRole.getRole_id(), BCITBVariousVariables.infection_risk_exist, 
								BCITBVariousVariables.infection_risk_found_om_note + " " + BCITBVariousVariables.infection_risk_found_tech_note,
								DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()), BCITBVariousVariables.infection_risk,
								"Action sent: Infection risk [" + ir.getInfection_risk_exist() + "] action sent to Operations Manager and Technician(s) on " + 
								DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + " by " + whichUser.getUsername() + 
								" [" + whichRole.getRole_description() + "]",BCITBVariousVariables.no, ir.getIr_patient_id());
						
					} else if(ir.getInfection_risk_exist().toLowerCase().contains(BCITBVariousVariables.unknown)) {
						
						action = new Action(ir.getInfection_risk_id(), BCITBVariousVariables.infection_risk, 
								BCITBVariousVariables.infection_risk_unknown, BCITBVariousVariables.unknown_infection_risk_note, BCITBVariousVariables.active,
								DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()),whichUser.getUser_id(),"");
						action.setDescription(populateActionsDescription(ir.getIr_which_department(), action, null, patient.getDatabase_id()));
						
						actionService.saveAction(ir.getIr_which_department(), action);
						
						action = new Action(ir.getInfection_risk_id(), BCITBVariousVariables.infection_risk, 
								BCITBVariousVariables.infection_risk_unknown_notify_tech, BCITBVariousVariables.unknown_infection_risk_tech_note, BCITBVariousVariables.active,
								DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()),whichUser.getUser_id(),"");
						action.setDescription(populateActionsDescription(ir.getIr_which_department(), action, null, patient.getDatabase_id()));
						
						actionService.saveAction(ir.getIr_which_department(), action);
						timelineService.saveTimeline(ir.getIr_which_department(), ir.getInfection_risk_id(), whichUser.getUser_id(), whichRole.getRole_id(), BCITBVariousVariables.infection_risk_unknown, 
								BCITBVariousVariables.unknown_infection_risk_note + " " + BCITBVariousVariables.unknown_infection_risk_tech_note,
								DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()), BCITBVariousVariables.infection_risk,
								"Action sent: Infection risk [" + ir.getInfection_risk_exist() + "] action sent to Clinician(s) and Technician(s) on " + 
								DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + " by " + whichUser.getUsername() + 
								" [" + whichRole.getRole_description() + "]",BCITBVariousVariables.no, ir.getIr_patient_id());
					}
				} else {
					if(prev_infection_risk.getInfection_risk_exist().toLowerCase().contains(BCITBVariousVariables.unknown)) {
						if(ir.getInfection_risk_exist().toLowerCase().contains(BCITBVariousVariables.yes)) {
							
							for(Action act:actionService.getActionsFromDataId(ir.getIr_which_department(), ir.getInfection_risk_id(), 
									BCITBVariousVariables.infection_risk, "", BCITBVariousVariables.active,BCITBVariousVariables.ascending)) 
								actionService.saveActionVariousColumns(ir.getIr_which_department(), act.getAction_id(), BCITBVariousVariables.status, BCITBVariousVariables.not_applicable);
							
							action = new Action(ir.getInfection_risk_id(), BCITBVariousVariables.infection_risk, 
									BCITBVariousVariables.infection_risk_found_notify_om, BCITBVariousVariables.infection_risk_found_om_note, BCITBVariousVariables.active,
									DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()),whichUser.getUser_id(),"");
							action.setDescription(populateActionsDescription(ir.getIr_which_department(), action, null, patient.getDatabase_id()));
							
							actionService.saveAction(ir.getIr_which_department(), action);
							
							action = new Action(ir.getInfection_risk_id(), BCITBVariousVariables.infection_risk, 
									BCITBVariousVariables.infection_risk_found_notify_tech, BCITBVariousVariables.infection_risk_found_tech_note, BCITBVariousVariables.active,
									DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()),whichUser.getUser_id(),"");
							action.setDescription(populateActionsDescription(ir.getIr_which_department(), action, null, patient.getDatabase_id()));
							
							actionService.saveAction(ir.getIr_which_department(), action);
							
							timelineService.saveTimeline(ir.getIr_which_department(), ir.getInfection_risk_id(), whichUser.getUser_id(), whichRole.getRole_id(), 
									BCITBVariousVariables.infection_risk_unknown, getDifferenceBetweenTwoObjects("","",prev_infection_risk.diff(ir),null,null)
									+ ". Notes: " + ir.getInfection_risk_notes(), DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()),BCITBVariousVariables.infection_risk, 
									"Action completed: Infection risk [" + prev_infection_risk.getInfection_risk_exist() + "]. changed to [" + ir.getInfection_risk_exist() + "] on " + 
									DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + " by " + whichUser.getUsername() + " [" + whichRole.getRole_description() + "]",
									BCITBVariousVariables.no, ir.getIr_patient_id());
							
							timelineService.saveTimeline(ir.getIr_which_department(), ir.getInfection_risk_id(), whichUser.getUser_id(), whichRole.getRole_id(), BCITBVariousVariables.infection_risk_exist, 
									BCITBVariousVariables.infection_risk_found_om_note + " " + BCITBVariousVariables.infection_risk_found_tech_note,
									DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()), BCITBVariousVariables.infection_risk,
									"Action sent: Infection risk [" + ir.getInfection_risk_exist() + "] action sent to Operations Manager and Technician(s) on " + 
									DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + " by " + whichUser.getUsername() + 
									" [" + whichRole.getRole_description() + "]",BCITBVariousVariables.no, ir.getIr_patient_id());
						
						} else if(ir.getInfection_risk_exist().toLowerCase().contains(BCITBVariousVariables.unknown)) {

							for(Action act:active_actions) 
								if(act.getStatus().equalsIgnoreCase(BCITBVariousVariables.locked)) {
									act.setStatus(BCITBVariousVariables.completed);
									columnName.clear();columnValue.clear();
									columnName.add(BCITBVariousVariables.status);columnValue.add(BCITBVariousVariables.completed);
									columnName.add(BCITBVariousVariables.locked_description);columnValue.add("");
									columnName.add(BCITBVariousVariables.locked_by);columnValue.add("");
									columnName.add(BCITBVariousVariables.completed_date_time);columnValue.add(DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()));
									columnName.add(BCITBVariousVariables.completed_by);columnValue.add(String.valueOf(whichUser.getUser_id()));
									columnName.add(BCITBVariousVariables.notes);columnValue.add("Completed by " + whichUser.getUser_firstname() + " " + whichUser.getUser_surname() + " on " + 
											DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + ". Notes: " + ir.getInfection_risk_notes());
									actionService.saveActionVariousColumns(ir.getIr_which_department(), act.getAction_id(), columnName, columnValue);
									timelineService.saveTimeline(ir.getIr_which_department(), ir.getInfection_risk_id(), whichUser.getUser_id(), whichRole.getRole_id(), BCITBVariousVariables.infection_risk_unknown, 
											"Notes: " + ir.getInfection_risk_notes(),DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()), BCITBVariousVariables.infection_risk,
											"Action completed: Infection Risk [" + ir.getInfection_risk_exist() + "]. Notification confirmed on " + 
											DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + " by " + whichUser.getUsername() + 
											" [" + whichRole.getRole_description() + "]",BCITBVariousVariables.no, ir.getIr_patient_id());
								}
							
						}
						
					} else if(prev_infection_risk.getInfection_risk_exist().toLowerCase().contains(BCITBVariousVariables.yes)) {
						if(ir.getInfection_risk_exist().toLowerCase().contains(BCITBVariousVariables.yes)) {
							if(whichURLToProcess.toLowerCase().contains(BCITBVariousVariables.update_infection_risk_confirm_om_notification) 
									|| whichURLToProcess.toLowerCase().contains(BCITBVariousVariables.update_infection_risk_confirm_tech_notification)) {
								for(Action act:active_actions) 
									if(act.getStatus().equalsIgnoreCase(BCITBVariousVariables.locked)) {
										act.setStatus(BCITBVariousVariables.completed);

										columnName.clear();columnValue.clear();
										columnName.add(BCITBVariousVariables.status);columnValue.add(BCITBVariousVariables.completed);
										columnName.add(BCITBVariousVariables.locked_description);columnValue.add("");
										columnName.add(BCITBVariousVariables.locked_by);columnValue.add("");
										columnName.add(BCITBVariousVariables.completed_date_time);columnValue.add(DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()));
										columnName.add(BCITBVariousVariables.completed_by);columnValue.add(String.valueOf(whichUser.getUser_id()));
										columnName.add(BCITBVariousVariables.notes);columnValue.add("Completed by " + whichUser.getUser_firstname() + " " + whichUser.getUser_surname() + " on " + 
												DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + ". Notes: " + ir.getInfection_risk_notes());
										actionService.saveActionVariousColumns(ir.getIr_which_department(), act.getAction_id(), columnName, columnValue);
										timelineService.saveTimeline(ir.getIr_which_department(), ir.getInfection_risk_id(), whichUser.getUser_id(), whichRole.getRole_id(), BCITBVariousVariables.infection_risk_exist, 
												"Notes: " + ir.getInfection_risk_notes(),DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()), BCITBVariousVariables.infection_risk,
												"Action completed: Infection Risk [" + ir.getInfection_risk_exist() + "]. Notification confirmed on " + 
												DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + " by " + whichUser.getUsername() + 
												" [" + whichRole.getRole_description() + "]",BCITBVariousVariables.no, ir.getIr_patient_id());
									}
							}
						} else {
							if(!ir.getInfection_risk_exist().toLowerCase().contains(BCITBVariousVariables.yes)) {
								for(Action act:actionService.getActionsFromDataId(ir.getIr_which_department(), ir.getInfection_risk_id(), 
										BCITBVariousVariables.infection_risk, "", BCITBVariousVariables.active,BCITBVariousVariables.ascending)) 
									actionService.saveActionVariousColumns(ir.getIr_which_department(), act.getAction_id(), BCITBVariousVariables.status, BCITBVariousVariables.not_applicable);
							}
							if(ir.getInfection_risk_exist().toLowerCase().contains(BCITBVariousVariables.unknown)) {
								
								action = new Action(ir.getInfection_risk_id(), BCITBVariousVariables.infection_risk, 
										BCITBVariousVariables.infection_risk_unknown, BCITBVariousVariables.unknown_infection_risk_note, BCITBVariousVariables.active,
										DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()),whichUser.getUser_id(),"");
								action.setDescription(populateActionsDescription(ir.getIr_which_department(), action, null, patient.getDatabase_id()));
								
								actionService.saveAction(ir.getIr_which_department(), action);
								
								action = new Action(ir.getInfection_risk_id(), BCITBVariousVariables.infection_risk, 
										BCITBVariousVariables.infection_risk_unknown_notify_tech, BCITBVariousVariables.unknown_infection_risk_tech_note, BCITBVariousVariables.active,
										DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()),whichUser.getUser_id(),"");
								action.setDescription(populateActionsDescription(ir.getIr_which_department(), action, null, patient.getDatabase_id()));
								
								actionService.saveAction(ir.getIr_which_department(), action);
								
								timelineService.saveTimeline(ir.getIr_which_department(), ir.getInfection_risk_id(), whichUser.getUser_id(), whichRole.getRole_id(), 
										BCITBVariousVariables.infection_risk_exist, getDifferenceBetweenTwoObjects("","",prev_infection_risk.diff(ir),null,null)
										+ ". Notes: " + ir.getInfection_risk_notes(), DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()),BCITBVariousVariables.infection_risk, 
										"Action completed: Infection Risk [" + prev_infection_risk.getInfection_risk_exist() + "]. changed to [" + ir.getInfection_risk_exist() + "] on " + 
										DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + " by " + whichUser.getUsername() + " [" + whichRole.getRole_description() + "]",
										BCITBVariousVariables.no, ir.getIr_patient_id());
								
								timelineService.saveTimeline(ir.getIr_which_department(), ir.getInfection_risk_id(), whichUser.getUser_id(), whichRole.getRole_id(), BCITBVariousVariables.infection_risk_unknown, 
										BCITBVariousVariables.unknown_infection_risk_note + " " + BCITBVariousVariables.unknown_infection_risk_tech_note,
										DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()), BCITBVariousVariables.infection_risk,
										"Action sent: Infection risk [" + ir.getInfection_risk_exist() + "] action sent to Clinician(s) or Investigator(s) and Technician(s) on " + 
										DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + " by " + whichUser.getUsername() + 
										" [" + whichRole.getRole_description() + "]",BCITBVariousVariables.no, ir.getIr_patient_id());
								
							} 
						}
					} else if(!prev_infection_risk.getInfection_risk_exist().toLowerCase().contains(BCITBVariousVariables.yes) 
							|| !prev_infection_risk.getInfection_risk_exist().toLowerCase().contains(BCITBVariousVariables.unknown)) {
						if(ir.getInfection_risk_exist().toLowerCase().contains(BCITBVariousVariables.yes)) {
							
							action = new Action(ir.getInfection_risk_id(), BCITBVariousVariables.infection_risk, 
									BCITBVariousVariables.infection_risk_found_notify_om, BCITBVariousVariables.infection_risk_found_om_note, BCITBVariousVariables.active,
									DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()),whichUser.getUser_id(),"");
							action.setDescription(populateActionsDescription(ir.getIr_which_department(), action, null, patient.getDatabase_id()));
							
							actionService.saveAction(ir.getIr_which_department(), action);
							
							action = new Action(ir.getInfection_risk_id(), BCITBVariousVariables.infection_risk, 
									BCITBVariousVariables.infection_risk_found_notify_tech, BCITBVariousVariables.infection_risk_found_tech_note, BCITBVariousVariables.active,
									DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()),whichUser.getUser_id(),"");
							action.setDescription(populateActionsDescription(ir.getIr_which_department(), action, null, patient.getDatabase_id()));
							
							actionService.saveAction(ir.getIr_which_department(), action);
							
							timelineService.saveTimeline(ir.getIr_which_department(), ir.getInfection_risk_id(), whichUser.getUser_id(), whichRole.getRole_id(), BCITBVariousVariables.infection_risk_exist, 
									BCITBVariousVariables.infection_risk_found_om_note + " " + BCITBVariousVariables.infection_risk_found_tech_note,
									DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()), BCITBVariousVariables.infection_risk,
									"Action sent: Infection risk [" + ir.getInfection_risk_exist() + "] action sent to Operations Manager and Technician(s) on " + 
									DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + " by " + whichUser.getUsername() + 
									" [" + whichRole.getRole_description() + "]",BCITBVariousVariables.no, ir.getIr_patient_id());
							
						} else if(ir.getInfection_risk_exist().toLowerCase().contains(BCITBVariousVariables.unknown)) {
							
							action = new Action(ir.getInfection_risk_id(), BCITBVariousVariables.infection_risk, 
									BCITBVariousVariables.infection_risk_unknown, BCITBVariousVariables.unknown_infection_risk_note, BCITBVariousVariables.active,
									DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()),whichUser.getUser_id(),"");
							action.setDescription(populateActionsDescription(ir.getIr_which_department(), action, null, patient.getDatabase_id()));
							
							actionService.saveAction(ir.getIr_which_department(), action);
							
							action = new Action(ir.getInfection_risk_id(), BCITBVariousVariables.infection_risk, 
									BCITBVariousVariables.infection_risk_unknown_notify_tech, BCITBVariousVariables.unknown_infection_risk_tech_note, BCITBVariousVariables.active,
									DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()),whichUser.getUser_id(),"");
							action.setDescription(populateActionsDescription(ir.getIr_which_department(), action, null, patient.getDatabase_id()));
							
							actionService.saveAction(ir.getIr_which_department(), action);

							timelineService.saveTimeline(ir.getIr_which_department(), ir.getInfection_risk_id(), whichUser.getUser_id(), whichRole.getRole_id(), BCITBVariousVariables.infection_risk_unknown, 
									BCITBVariousVariables.unknown_infection_risk_note + " " + BCITBVariousVariables.unknown_infection_risk_tech_note,
									DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()), BCITBVariousVariables.infection_risk,
									"Action sent: Infection risk [" + ir.getInfection_risk_exist() + "] action sent to Clinician(s) or Investigator(s) and Technician(s) on " + 
									DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + " by " + whichUser.getUsername() + 
									" [" + whichRole.getRole_description() + "]",BCITBVariousVariables.no, ir.getIr_patient_id());
							
						} else {

							for(Action act:active_actions) 
								if(act.getStatus().equalsIgnoreCase(BCITBVariousVariables.locked)) {
									act.setStatus(BCITBVariousVariables.completed);
									columnName.clear();columnValue.clear();
									columnName.add(BCITBVariousVariables.status);columnValue.add(BCITBVariousVariables.completed);
									columnName.add(BCITBVariousVariables.locked_description);columnValue.add("");
									columnName.add(BCITBVariousVariables.locked_by);columnValue.add("");
									columnName.add(BCITBVariousVariables.completed_date_time);columnValue.add(DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()));
									columnName.add(BCITBVariousVariables.completed_by);columnValue.add(String.valueOf(whichUser.getUser_id()));
									columnName.add(BCITBVariousVariables.notes);columnValue.add("Completed by " + whichUser.getUser_firstname() + " " + whichUser.getUser_surname() + " on " + 
											DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + ". Notes: " + ir.getInfection_risk_notes());
									actionService.saveActionVariousColumns(ir.getIr_which_department(), act.getAction_id(), columnName, columnValue);
									if(whichURLToProcess.toLowerCase().contains(BCITBVariousVariables.unknown))
										timelineService.saveTimeline(ir.getIr_which_department(), ir.getInfection_risk_id(), whichUser.getUser_id(), whichRole.getRole_id(), BCITBVariousVariables.infection_risk_unknown, 
												"Notes: " + ir.getInfection_risk_notes(),DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()), BCITBVariousVariables.infection_risk,
												"Action completed: Infection Risk [" + ir.getInfection_risk_exist() + "]. Notification confirmed on " + 
												DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + " by " + whichUser.getUsername() + 
												" [" + whichRole.getRole_description() + "]",BCITBVariousVariables.no, ir.getIr_patient_id());
									else
										timelineService.saveTimeline(ir.getIr_which_department(), ir.getInfection_risk_id(), whichUser.getUser_id(), whichRole.getRole_id(), BCITBVariousVariables.infection_risk_exist, 
												"Notes: " + ir.getInfection_risk_notes(),DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()), BCITBVariousVariables.infection_risk,
												"Action completed: Infection Risk [" + ir.getInfection_risk_exist() + "]. Notification confirmed on " + 
												DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + " by " + whichUser.getUsername() + 
												" [" + whichRole.getRole_description() + "]",BCITBVariousVariables.no, ir.getIr_patient_id());
								}
						}
					}
				}
			}
			break;

		case BCITBVariousVariables.action: 
			
			String this_notes = "", this_description = "";

			if(patient.getDatabase_id() == null || patient.getDatabase_id().isEmpty())
				patient = patientService.getPatientFromID(consents.get(0).getConsent_which_department(),consents.get(0).getConsent_id());
			
			switch (whatToProcess) {
			case BCITBVariousVariables.action:
				for(Action act:active_actions) {
					if(act.getStatus().equalsIgnoreCase(BCITBVariousVariables.locked)) {
						switch (act.getUser_selected_action_type()) {
						case BCITBVariousVariables.validate: case BCITBVariousVariables.confirm_notification: case BCITBVariousVariables.confirm_samples_disposal:
							
							switch (act.getUser_selected_action_type().toLowerCase()) {
							case BCITBVariousVariables.validate: 
								consentService.saveConsentVariousColumns(consents.get(0).getConsent_which_department(), 
										consents.get(0).getConsent_id(), BCITBVariousVariables.is_finalised, BCITBVariousVariables.yes);
								consentService.saveConsentVariousColumns(consents.get(0).getConsent_which_department(), consents.get(0).getConsent_id(), 
										BCITBVariousVariables.is_validated, BCITBVariousVariables.yes);
								this_notes = "Consent validated by " + whichUser.getUser_firstname() + " " + whichUser.getUser_surname() + " on " + 
										DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + ".";
								if(consents.get(0).getSam_coll_before_sep_2006() != null && consents.get(0).getSam_coll_before_sep_2006().equalsIgnoreCase(BCITBVariousVariables.yes))
									this_description = "Action completed: Validate consent (sample collected before Sep 2006) completed on " + DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + 
											" by " + whichUser.getUsername() + " [" + whichRole.getRole_description() + "]";
								else
									this_description = "Action completed: Validate consent dated " +  new SimpleDateFormat("dd-MM-yyyy").format(
											new SimpleDateFormat("yyyy-MM-dd").parse(consents.get(0).getDate_of_consent())) + 
											" completed on " + DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + 
											" by " + whichUser.getUsername() + " [" + whichRole.getRole_description() + "]";
								break;
							case BCITBVariousVariables.confirm_notification:
								this_notes = "Notification of consent withdrawal confirmed by " + whichUser.getUser_firstname() + " " + whichUser.getUser_surname() + " on " + 
										DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + ".";
								if(consents.get(0).getSam_coll_before_sep_2006() != null && consents.get(0).getSam_coll_before_sep_2006().equalsIgnoreCase(BCITBVariousVariables.yes))
									this_description = "Action completed: Consent (sample collected before Sep 2006) withdrawn. Notification confirmed on " + DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + 
											" by " + whichUser.getUsername() + " [" + whichRole.getRole_description() + "]";
								else
									this_description = "Action completed: Consent dated " +  new SimpleDateFormat("dd-MM-yyyy").format(
											new SimpleDateFormat("yyyy-MM-dd").parse(consents.get(0).getDate_of_consent())) + 
											" withdrawn. Notification confirmed on " + DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + 
											" by " + whichUser.getUsername() + " [" + whichRole.getRole_description() + "]";
								break;
							case BCITBVariousVariables.confirm_samples_disposal:
								this_notes = "Sample(s) disposal confirmed by " + whichUser.getUser_firstname() + " " + whichUser.getUser_surname() + " on " + 
										DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + ".";
								if(consents.get(0).getSam_coll_before_sep_2006() != null && consents.get(0).getSam_coll_before_sep_2006().equalsIgnoreCase(BCITBVariousVariables.yes))
									this_description = "Action completed: Consent (sample collected before Sep 2006) withdrawn. Sample(s) removal confirmed on " + 
											DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + 
											" by " + whichUser.getUsername() + " [" + whichRole.getRole_description() + "]";
								else
									this_description = "Action completed: Consent dated " +  new SimpleDateFormat("dd-MM-yyyy").format(
											new SimpleDateFormat("yyyy-MM-dd").parse(consents.get(0).getDate_of_consent())) + 
											" withdrawn. Sample(s) removal confirmed on " + DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + 
											" by " + whichUser.getUsername() + " [" + whichRole.getRole_description() + "]";
								break;
							}
							
							act.setStatus(BCITBVariousVariables.completed);
							
							if(consents.get(0).getConsent_notes() != null)
								this_notes = this_notes + " Notes: " + consents.get(0).getConsent_notes();
							
							columnName.clear();columnValue.clear();
							columnName.add(BCITBVariousVariables.status);columnValue.add(BCITBVariousVariables.completed);
							columnName.add(BCITBVariousVariables.locked_description);columnValue.add("");
							columnName.add(BCITBVariousVariables.locked_by);columnValue.add("");
							columnName.add(BCITBVariousVariables.completed_date_time);columnValue.add(DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()));
							columnName.add(BCITBVariousVariables.completed_by);columnValue.add(String.valueOf(whichUser.getUser_id()));
							columnName.add(BCITBVariousVariables.notes);columnValue.add(this_notes);
							
							actionService.saveActionVariousColumns(act.getAction_which_department(), act.getAction_id(), columnName, columnValue);
							timelineService.saveTimeline(act.getAction_which_department(), act.getAction_id(), 
									whichUser.getUser_id(), whichRole.getRole_id(), act.getUser_selected_action_type(), this_notes,
									DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()), BCITBVariousVariables.action, 
									this_description,BCITBVariousVariables.no, consents.get(0).getCn_patient_id());
							
							break;
			
						case BCITBVariousVariables.verify_consent: case BCITBVariousVariables.reapproach_patient: case BCITBVariousVariables.query_patient: 

							switch (act.getUser_selected_action_type()) {
							case BCITBVariousVariables.reapproach_patient: case BCITBVariousVariables.query_patient:
								switch (act.getUser_selected_action_type()) {
								case BCITBVariousVariables.reapproach_patient:
									if(consents.get(0).getSam_coll_before_sep_2006() != null && consents.get(0).getSam_coll_before_sep_2006().equalsIgnoreCase(BCITBVariousVariables.yes))
										this_description = "Action pending: Validate consent (sample collected before Sep 2006). Action " + act.getUser_selected_action_type().replace("_", " ") 
											+ " sent to Tissue Bank Aquisition Officer(s) on " + DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + 
											" by " + whichUser.getUsername() + " [" + whichRole.getRole_description() + "]";
									else
										this_description = "Action pending: Validate consent dated " +  new SimpleDateFormat("dd-MM-yyyy").format(
												new SimpleDateFormat("yyyy-MM-dd").parse(consents.get(0).getDate_of_consent())) + 
											". Action " + act.getUser_selected_action_type().replace("_", " ") + " sent to Tissue Bank Aquisition Officer(s) on " + 
											DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + 
											" by " + whichUser.getUsername() + " [" + whichRole.getRole_description() + "]";
									break;
								case BCITBVariousVariables.query_patient:
									if(consents.get(0).getSam_coll_before_sep_2006() != null && consents.get(0).getSam_coll_before_sep_2006().equalsIgnoreCase(BCITBVariousVariables.yes))
										this_description = "Action pending: Validate consent (sample collected before Sep 2006). Action " + act.getUser_selected_action_type().replace("_", " ") 
											+ " sent to Operations Manager on " + DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + 
											" by " + whichUser.getUsername() + " [" + whichRole.getRole_description() + "]";
									else
										this_description = "Action pending: Validate consent dated " +  new SimpleDateFormat("dd-MM-yyyy").format(
												new SimpleDateFormat("yyyy-MM-dd").parse(consents.get(0).getDate_of_consent())) + 
											". Action " + act.getUser_selected_action_type().replace("_", " ") + " sent to Operations Manager on " + 
											DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + 
											" by " + whichUser.getUsername() + " [" + whichRole.getRole_description() + "]";
									break;
								}
								break;
							default:
								if(consents.get(0).getSam_coll_before_sep_2006() != null && consents.get(0).getSam_coll_before_sep_2006().equalsIgnoreCase(BCITBVariousVariables.yes))
									this_description = "Action sent: Validate consent (sample collected before Sep 2006). Action sent to Data Officer(s) on " + 
											DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + 
											" by " + whichUser.getUsername() + " [" + whichRole.getRole_description() + "]";
								else
									this_description = "Action sent: Validate consent dated " +  new SimpleDateFormat("dd-MM-yyyy").format(
											new SimpleDateFormat("yyyy-MM-dd").parse(consents.get(0).getDate_of_consent())) + 
											". Action sent to Data Officer(s) on " + DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + 
											" by " + whichUser.getUsername() + " [" + whichRole.getRole_description() + "]";
								break;
							}
							
							act.setStatus(BCITBVariousVariables.completed);

							columnName.clear();columnValue.clear();
							columnName.add(BCITBVariousVariables.status);columnValue.add(BCITBVariousVariables.completed);
							columnName.add(BCITBVariousVariables.locked_description);columnValue.add("");
							columnName.add(BCITBVariousVariables.locked_by);columnValue.add("");
							columnName.add(BCITBVariousVariables.completed_date_time);columnValue.add(DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()));
							columnName.add(BCITBVariousVariables.completed_by);columnValue.add(String.valueOf(whichUser.getUser_id()));
							columnName.add(BCITBVariousVariables.notes);columnValue.add("Completed by " + whichUser.getUser_firstname() + " " + whichUser.getUser_surname() + " on " + 
								DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()) + ". Notes: " + act.getUser_selected_action_notes());
							columnName.add(BCITBVariousVariables.description);columnValue.add(this_description);
							
							actionService.saveActionVariousColumns(act.getAction_which_department(), act.getAction_id(), columnName, columnValue);
							
							action = new Action(consents.get(0).getConsent_id(), 
									BCITBVariousVariables.consent, act.getUser_selected_action_type(), act.getUser_selected_action_notes(), BCITBVariousVariables.active,
									DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()),whichUser.getUser_id(),"");
							action.setDescription(populateActionsDescription(act.getAction_which_department(),action, consents.get(0), patient.getDatabase_id()));
							
							timelineService.saveTimeline(act.getAction_which_department(), actionService.saveAction(act.getAction_which_department(), action).getAction_id(), 
								whichUser.getUser_id(), whichRole.getRole_id(), act.getUser_selected_action_type().toLowerCase(),act.getUser_selected_action_notes(),
								DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss").format(LocalDateTime.now()),BCITBVariousVariables.action,this_description,
								BCITBVariousVariables.no, consents.get(0).getCn_patient_id());
							
							break;
							
						}
					}
				}
				break;
			}
			break;
		}
		return active_actions;
	}

	public String populateActionsDescription(String department, Action action, Consent consent, String patient_db_id) throws ParseException
	{
		String action_description = "(" + department + ") ";
		
		switch (action.getAction_type()) {
		case BCITBVariousVariables.verify_consent: case BCITBVariousVariables.consent_withdrawn: case BCITBVariousVariables.remove_samples:
		case BCITBVariousVariables.reapproach_patient: case BCITBVariousVariables.query_patient:
				
			switch (action.getAction_type()) {
			case BCITBVariousVariables.verify_consent:
				action_description = action_description + "Validate consent of ";
				break;
			case BCITBVariousVariables.consent_withdrawn:
				action_description = action_description + WordUtils.capitalize(action.getAction_type().replace("_", " ")) + " of ";
				break;
			case BCITBVariousVariables.remove_samples:
				action_description = action_description + WordUtils.capitalize(action.getAction_type().replace("_", " ")) + " of ";
				break;
			case BCITBVariousVariables.reapproach_patient:
				action_description = action_description + WordUtils.capitalize(action.getAction_type().replace("_", " ")) + " ";
				break;
			case BCITBVariousVariables.query_patient:
				action_description = action_description + "Query ";
				break;
			}
			
			if(consent.getDate_of_consent() != null && patient_db_id != null && !patient_db_id.trim().isEmpty())
				if(consent.getSam_coll_before_sep_2006() != null && consent.getSam_coll_before_sep_2006().equalsIgnoreCase(BCITBVariousVariables.yes))
					action_description = action_description + patient_db_id + " consent (sample collected before Sep 2006)";
				else
					action_description = action_description + patient_db_id + " consented on " + new SimpleDateFormat("dd-MM-yyyy").format(
							new SimpleDateFormat("yyyy-MM-dd").parse(consent.getDate_of_consent()));
			
			break;
			
		case BCITBVariousVariables.infection_risk_found_notify_om: case BCITBVariousVariables.infection_risk_found_notify_tech: 
		case BCITBVariousVariables.infection_risk_unknown: case BCITBVariousVariables.infection_risk_unknown_notify_tech:

			switch (action.getAction_type()) {
			case BCITBVariousVariables.infection_risk_found_notify_om: case BCITBVariousVariables.infection_risk_found_notify_tech:
				action_description = action_description + "Infection risk of ";
				break;
			case BCITBVariousVariables.infection_risk_unknown: case BCITBVariousVariables.infection_risk_unknown_notify_tech:
				action_description = action_description + "Unknown infection risk of ";
				break;
			}

			action_description = action_description + patient_db_id;
			
			break;
		}

		return action_description;
	}

	public List<Timeline> getTimelineData(String whichDepartment, int patient_id, String asc_or_desc)
	{
		boolean withdrawn_consent_found = false;
		InfectionRisk this_ir = null;
		List<Timeline> return_timeline = new ArrayList<Timeline>();
		
		if(consentService.getPatientConsents(BCITBVariousVariables.consent_withdrawn, whichDepartment, patient_id, "", "", null, BCITBVariousVariables.ascending).size() == 
				consentService.getPatientConsents("", whichDepartment, patient_id, "", "", null, BCITBVariousVariables.ascending).size())
			withdrawn_consent_found = true;

		for(Timeline tl:timelineService.getTimeline("PATIENT-ID", whichDepartment, "", patient_id, BCITBVariousVariables.ascending)) {
			if(tl.getIs_encrypted() != null && tl.getIs_encrypted().equalsIgnoreCase(BCITBVariousVariables.yes))
				tl.setDecrypted_notes(BCITBEncryptDecrypt.decrypt(tl.getNotes(), tl.getData_type()));
			if(withdrawn_consent_found == true) {
				tl.setDecrypted_notes(""); tl.setNotes(""); // hide notes even if a single consent is withdrawn
			}
			return_timeline.add(tl);
			if(tl.getData_type().equalsIgnoreCase(BCITBVariousVariables.infection_risk)) { // Do not show 'infection risks' which are marked for deletion
				this_ir = infectionRiskService.getInfectionRisk(whichDepartment, tl.getData_id());
				if(this_ir != null && this_ir.getIr_deletion_date() != null) 
					return_timeline.remove(return_timeline.size() - 1);
			}
		}
	
		return return_timeline;

	}
	
	public List<Action> getUserTasks(String whatToProcess, String asc_or_desc, User user, Role primaryRole)
	{
		List<Action> all_actions = new ArrayList<Action>();
		
		for(UserDepartment user_dept:user.getUserDepartments()) 
			for(Action act:actionService.getActions("",user_dept.getDepartment().getDept_acronym(),0, asc_or_desc)) {
				act.setAction_which_department(user_dept.getDepartment().getDept_acronym());
				all_actions.add(act);
			}
		
		List<Action> process_this_actions = new ArrayList<Action>();
		List<Action> actions_to_return = new ArrayList<Action>();
		boolean action_found = false;
		
		switch (whatToProcess) {
		case "LOCKED-ACTIONS":
		
			for(Action act:all_actions) 
				if(act.getStatus().equalsIgnoreCase(BCITBVariousVariables.locked) && act.getLocked_by() != null && act.getLocked_by() == user.getUser_id()) 
					actions_to_return.add(act);
			
			break;

		case "ACTIVE-ACTIONS": case "COMPLETED-ACTIONS":
			
			for(ActionRole ra:primaryRole.getActionRoles()) 
				for(Action act: all_actions)
					if(act.getAction_type().equalsIgnoreCase(ra.getAction_type()))
						process_this_actions.add(act);
			
			switch (whatToProcess) {
			case "ACTIVE-ACTIONS":
				for(Action act: process_this_actions) 
					if(act.getStatus() != null && act.getStatus().equalsIgnoreCase(BCITBVariousVariables.active)) 
						actions_to_return.add(act);
				break;
			case "COMPLETED-ACTIONS":
				for(int i=process_this_actions.size() - 1; i >= 0; i--) {
					if(process_this_actions.get(i).getStatus().equalsIgnoreCase(BCITBVariousVariables.completed)) {
						action_found = false;
						for(Action existing_action:actions_to_return)
							if(existing_action.getAction_id() == process_this_actions.get(i).getAction_id() 
								&& existing_action.getAction_which_department().equalsIgnoreCase(process_this_actions.get(i).getAction_which_department()))
								action_found = true;
						if(action_found == false) 
							actions_to_return.add(process_this_actions.get(i));
					}
				}
				break;
			}
			
			break;
		
		}
		return actions_to_return;
	}	 
	 
	private List<Consent> populateVariousBitsOfConsents(String whichDepartment, String whatToProcess, List<Consent> consents)
	{
		List<Consent> final_consents = new ArrayList<Consent>(consents);
		switch (whatToProcess) {
		case "ALL":
			final_consents = populateVariousBitsOfConsents(whichDepartment, "FORM-VERSION", final_consents);
			final_consents = populateVariousBitsOfConsents(whichDepartment, "FILES", final_consents);
			final_consents = populateVariousBitsOfConsents(whichDepartment, "LOCATION", final_consents);
			final_consents = populateVariousBitsOfConsents(whichDepartment, "CONSENT-EXCLUSION", final_consents);
			final_consents = populateVariousBitsOfConsents(whichDepartment, "CONSENTED-SAMPLES", final_consents);
			break;
		case "FILES":
			ConsentFile this_file = null;
			for(Consent con: final_consents) {
				if (con.getDigital_cf_attachment_id() != null && con.getDigital_cf_attachment_id() > 0) {
					this_file = fileService.getFile(whichDepartment, con.getDigital_cf_attachment_id(), BCITBVariousVariables.digital_cf_attachment_file);
					if (this_file != null) 
						con.setDigital_cf_attachment(decryptFileData(whichDepartment, this_file));
					else
						con.setDigital_cf_attachment_id(0);
				}
				if (con.getVerbal_consent_document_id() != null && con.getVerbal_consent_document_id() > 0) {
					this_file = fileService.getFile(whichDepartment, con.getVerbal_consent_document_id(), BCITBVariousVariables.verbal_consent_document_file);
					if (this_file != null) 
						con.setVerbal_consent_document(decryptFileData(whichDepartment, this_file));
					else
						con.setVerbal_consent_document_id(0);
				}
				if (con.getWithdrawal_document_id() != null && con.getWithdrawal_document_id() > 0) {
					this_file = fileService.getFile(whichDepartment, con.getWithdrawal_document_id(), BCITBVariousVariables.withdrawal_document_file);
					if (this_file != null) 
						con.setWithdrawal_document(decryptFileData(whichDepartment, this_file));
					else
						con.setWithdrawal_document_id(0);
				}
				if (con.getAdditional_document_id() != null && con.getAdditional_document_id() > 0) {
					this_file = fileService.getFile(whichDepartment, con.getAdditional_document_id(), BCITBVariousVariables.additional_document_file);
					if (this_file != null) 
						con.setAdditional_document(decryptFileData(whichDepartment, this_file));
					else
						con.setAdditional_document_id(0);
				}
			}
			break;
		case "LOCATION":
			for(Consent con: final_consents)
				if(con.getLoc_id() != null && con.getLoc_id() > 0)
					con.setLocation(locationService.getLocation(con.getLoc_id()).getLoc_name());
			break;
		case "CONSENT-EXCLUSION":
			for(Consent con: final_consents) { 
				con.setConsent_exclusions("");
				con.setConsent_exclusions_list("");
				for(ConsentExclusion ce : consentService.getConsentExclusions(whichDepartment, con.getConsent_id())) { 
					if (con.getConsent_exclusions().isEmpty()) {
						con.setConsent_exclusions(String.valueOf(ce.getConsent_term_id()));
						con.setConsent_exclusions_list(consentService.getConsentTerm(whichDepartment, ce.getConsent_term_id()).getDescription());
					} else {
						con.setConsent_exclusions(con.getConsent_exclusions() + "," + String.valueOf(ce.getConsent_term_id()));
						con.setConsent_exclusions_list(con.getConsent_exclusions_list() + "," + consentService.getConsentTerm(whichDepartment, ce.getConsent_term_id()).getDescription());
					}
				}
			}
			break;
		case "CONSENTED-SAMPLES":
			for(Consent con: final_consents) { 
				con.setSamples_consented_to("");
				con.setSamples_consented_to_list("");
				for(ConsentedSamples cs : consentService.getConsentedSamples(BCITBVariousVariables.HOTB, con.getConsent_id())) { 
					if (con.getSamples_consented_to().isEmpty()) {
						con.setSamples_consented_to(String.valueOf(cs.getConsent_sample_type_id()));
						con.setSamples_consented_to_list(consentService.getConsentSampleType(BCITBVariousVariables.HOTB, cs.getConsent_sample_type_id()).getDescription());
					} else {
						con.setSamples_consented_to(con.getSamples_consented_to() + "," + String.valueOf(cs.getConsent_sample_type_id()));
						con.setSamples_consented_to_list(con.getSamples_consented_to_list() + "," + 
								consentService.getConsentSampleType(BCITBVariousVariables.HOTB, cs.getConsent_sample_type_id()).getDescription());
					}
				}
			}
			break;
		case "FORM-VERSION":
			for(Consent con: final_consents) {
				if(con.getForm_version_id() != null && con.getForm_version_id() > 0)
					con.setForm_version(formVersionService.getFormVersion(whichDepartment, con.getForm_version_id()));
			}
			break;
		}
		return final_consents;
	}
	 
	private ConsentFile decryptFileData(String dept_acronym, ConsentFile encryptedFile)  
	{
		encryptedFile.setFile_data(BCITBEncryptDecrypt.decrypt(encryptedFile.getFile_data(), encryptedFile.getFile_name()));
		return encryptedFile;
	}	
	private Role getPrimaryRole(User user)
	{
		Role role = null;
		for(UserRole userRole:user.getUserRoles())
			if(userRole.getIs_primary_role()==1) {
				role=userRole.getRole();
				role.setActionRoles(userRole.getRole().getActionRoles());
			}
		return role;
	}
	
	private String getPrincipal()
	{
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) 
			userName = ((UserDetails)principal).getUsername();
		else
			userName = principal.toString();
		return userName;
	}

	@ModelAttribute("user_selected_department")
	public Department user_selected_department(){
		return new Department();
	}

	@ModelAttribute("user")
	public User user(){
		return new User();
	}

	@ModelAttribute("primaryRole")
	public Role primaryRole(){
		return new Role();
	}

	@ModelAttribute("session_patient")
	public Patient session_patient(){
		return new Patient();
	}

	@ModelAttribute("session_consent_validate")
	public ConsentValidate session_consent_validate(){
		return new ConsentValidate();
	}

	@ModelAttribute("session_consents")
	public List<Consent> session_consents(){
		return new ArrayList<Consent>();
	}

	@ModelAttribute("session_infection_risks")
	public List<InfectionRisk> session_infection_risks(){
		return new ArrayList<InfectionRisk>();
	}

	@ModelAttribute("various_actions")
	public VariousActions various_actions(){
		return new VariousActions();
	}

	@ModelAttribute("session_timeline")
	public List<Timeline> session_timeline(){
		return new ArrayList<Timeline>();
	}
	
	@ModelAttribute("user_selected_locations")
	public List<Location> user_selected_locations(){
		return new ArrayList<Location>();
	}

	@ModelAttribute("formVersionJson")
	public String formVersionJson(){
		return new String();
	}
	
	@ModelAttribute("sampleConsentedToJson")
	public String sampleConsentedToJson(){
		return new String();
	}
	
	@ModelAttribute("formVersionConsentTermJson")
	public String formVersionConsentTermJson(){
		return new String();
	}
	
	@ModelAttribute("formTypeVersionConsentTermJson")
	public String formTypeVersionConsentTermJson(){
		return new String();
	}

	@ModelAttribute("imported_consents")
	public List<Consent> imported_consents(){
		return new ArrayList<Consent>();
	}
	
	@ModelAttribute("existing_audit_consents")
	public List<Consent> existing_audit_consents(){
		return new ArrayList<Consent>();
	}

	@ModelAttribute("columnName")
	public List<String> columnName(){
		return new ArrayList<String>();
	}

	@ModelAttribute("columnValue")
	public List<String> columnValue(){
		return new ArrayList<String>();
	}
	
	@ModelAttribute("session_consent_audit")
	public ConsentAudit session_consent_audit(){
		return new ConsentAudit();
	}
	
	@ModelAttribute("session_all_patient_data")
	public AllPatientData session_all_patient_data(){
		return new AllPatientData();
	}
	
	@ModelAttribute("session_advanced_search_sql_script")
	public List<String> session_advanced_search_sql_script(){
		return new ArrayList<String>();
	}
	
	@ModelAttribute("session_basic_search_options")
	public List<String> session_basic_search_options(){
		return new ArrayList<String>();
	}
	
}