package com.tissuebank.util;

public class UnusedFunctions {

//	public class MonthlyReportToOM implements Job {
//
//		@Override
//		public void execute(JobExecutionContext context) throws JobExecutionException {
//			for(Department dept : departmentService.getAllDepartments()) {
//				switch (dept.getDept_acronym().toUpperCase()) {				
//				case BCITBVariousVariables.BGTB: case BCITBVariousVariables.HOTB:
//					new Email().sendEmail("MONTHLY-REPORT-TO-OM", dept.getDept_acronym(), 
//							null, auditProcessService.getAuditProcess(), mimeMessage, userService, 
//							patientService, consentService, actionService);
//					break;
//				}
//			}
//		}
//
//	}
//
//	public class SemiAnnualAudit implements Job {
//
//		@Override
//		public void execute(JobExecutionContext context) throws JobExecutionException {
//			
//			List<Integer> consent_ids;
//			int consent_id_index,number_of_consents_audit;
//			AuditProcess aud_pro = auditProcessService.getAuditProcess();
//			
//			for(Department dept:departmentService.getAllDepartments()) {
//				switch (dept.getDept_acronym().toUpperCase()) {
//				case BCITBVariousVariables.BGTB: case BCITBVariousVariables.HOTB:
//					
//					if(consentService.getPatientConsents(BCITBVariousVariables.marked_for_auditing, 
//							dept.getDept_acronym(), 0, "", "", null, BCITBVariousVariables.ascending).size() <= 0) // Check if there are no pending consent yet to be audited
//					{
//							consent_ids = new ArrayList<Integer>();
//							for(Consent con:new ArrayList<Consent>(consentService.getPatientConsents(BCITBVariousVariables.ignore_audited_withdrawn, 
//									dept.getDept_acronym(), 0, "", "", null, BCITBVariousVariables.ascending))) 
//								consent_ids.add(con.getConsent_id());
//							
//							if(consent_ids.size() > 0) 
//							{
//								consent_id_index = 0;
//								number_of_consents_audit = aud_pro.getNumber_of_consents();
//								
//								if(consent_ids.size() < number_of_consents_audit)
//									number_of_consents_audit = consent_ids.size();
//
//								while (number_of_consents_audit > 0) {
//									consent_id_index = new Random().nextInt(consent_ids.size());
//									consentService.saveConsentVariousColumns(dept.getDept_acronym(), consent_ids.get(consent_id_index), 
//											BCITBVariousVariables.marked_for_auditing, BCITBVariousVariables.yes);
//									consent_ids.remove(consent_id_index);
//									number_of_consents_audit = number_of_consents_audit - 1;
//								}
//								
//								if(number_of_consents_audit <= 0) {
//									auditProcessService.updateAuditProcess(aud_pro.getAudit_process_id(), BCITBVariousVariables.audit_process_date, 
//											DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now())); // Reset the audit date to current date
//									auditProcessService.updateAuditProcess(aud_pro.getAudit_process_id(), 
//											BCITBVariousVariables.SEND_REPORT_ + dept.getDept_acronym().toUpperCase(), BCITBVariousVariables.yes);
//									new Email().sendEmail("SEMI-ANNUAL-AUDIT-TRIGGERED", dept.getDept_acronym(), 
//											auditProcessService.getAuditTeams(), auditProcessService.getAuditProcess(), 
//											mimeMessage, userService, patientService, consentService, actionService);
//								}
//								
//							}
//					}
//					break;
//				}
//				
//			}
//		}
//
//	}
//	@RequestMapping(value = {"/search_patient"}, method={RequestMethod.GET})
//	public String searchPatientPage(ModelMap model,
//			@ModelAttribute("user") User user,
//			@ModelAttribute("primaryRole") Role primaryRole,
//			@ModelAttribute("various_actions") VariousActions various_actions,
//			@ModelAttribute("session_patient") Patient session_patient,
//			@ModelAttribute("existing_audit_consents") List<Consent> existing_audit_consents,
//			@RequestParam(value = "search_patient_keyword", required = false, defaultValue = "") String search_patient_keyword,
//			@RequestParam(value = "select_search_criteria", required = false, defaultValue = "") String select_search_criteria,
//			@ModelAttribute("user_selected_locations") List<Location> user_selected_locations,
//			@ModelAttribute("user_selected_department") Department user_selected_department) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException 			
//	{
//		if (!(user.getUser_id() > 0)) // session timed out
//			return "redirect:/timed_out";
//		else
//		{
//			String consent_withdrawn_access = primaryRole.getUserRoleAccess(BCITBVariousVariables.consent_withdrawn);
//			
//			session_patient.setPatient_which_department(user_selected_department.getDept_acronym());
//			session_patient = unlockPatient(session_patient, user, user_selected_department);
//
//			List<Patient> patients_found = new ArrayList<Patient>();
//			List<Consent> consents_found = new ArrayList<Consent>();
//
//			if (!search_patient_keyword.trim().equalsIgnoreCase("") && !select_search_criteria.trim().equalsIgnoreCase("")) {
//				for(Patient pat:patientService.searchPatientDetails(user_selected_department.getDept_acronym(),select_search_criteria,search_patient_keyword)) {
//					pat.setNumber_validated_consents(0); pat.setNumber_finalise_import_consents(0); pat.setNumber_consents(0); pat.setNumber_infection_risks(0);
//					pat.setPatient_which_department(user_selected_department.getDept_acronym());
//					consents_found = new ArrayList<Consent>(consentService.getPatientConsents("all",user_selected_department.getDept_acronym(), 
//							pat.getPatient_id(), "", "", user_selected_locations, BCITBVariousVariables.ascending));
//					if(consents_found.size() > 0) {
//						for(Consent con: consents_found) {
//							if(con.getDate_of_consent() != null) {
//								pat.setNumber_consents(pat.getNumber_consents() + 1);
//								if(con.getIs_validated() != null && con.getIs_validated().equalsIgnoreCase(BCITBVariousVariables.yes)) 
//									pat.setNumber_validated_consents(pat.getNumber_validated_consents() + 1);
//								else if(con.getIs_finalised() != null && con.getIs_finalised().equalsIgnoreCase(BCITBVariousVariables.yes) 
//										&& con.getIs_imported() != null && con.getIs_imported().equalsIgnoreCase(BCITBVariousVariables.yes)) 
//									pat.setNumber_finalise_import_consents(pat.getNumber_finalise_import_consents() + 1);
//							}
//						}
//						if(pat.getWithdrawn_count() != null && pat.getWithdrawn_count() > 0)   // Hide all infection risks even if a single consent is withdrawn 
//							for(InfectionRisk ir:infectionRiskService.getPatientInfectionRisks("",user_selected_department.getDept_acronym(), pat.getPatient_id())) 
//								if(ir.getInfection_risk_exist().toLowerCase().contains(BCITBVariousVariables.yes) || 
//										ir.getInfection_risk_exist().toLowerCase().contains(BCITBVariousVariables.unknown) ||
//										ir.getInfection_risk_exist().toLowerCase().contains(BCITBVariousVariables.imported))
//									pat.setNumber_infection_risks(pat.getNumber_infection_risks() + 1);
//						patients_found.add(pat);
//					}
//				}
//			}
//			model.addAttribute("whichPageToShow", "search_patient"); 
//			model.addAttribute("user", user);
//			model.addAttribute("primaryRole", primaryRole);
//			model.addAttribute("user_selected_department", user_selected_department);
//			model.addAttribute("various_actions", various_actions);
//			if(primaryRole.getUserRoleAccess(BCITBVariousVariables.audit).toLowerCase().contains(BCITBVariousVariables.edit)) 
//				model.addAttribute("existing_audit_consents", existing_audit_consents);
//			model.addAttribute("consent_access", primaryRole.getUserRoleAccess(BCITBVariousVariables.consent));
//			model.addAttribute("consent_withdrawn_access", consent_withdrawn_access);
//			model.addAttribute("timeline", primaryRole.getUserRoleAccess(BCITBVariousVariables.timeline));
//			model.addAttribute("search_patient_keyword", search_patient_keyword);
//			model.addAttribute("select_search_criteria", select_search_criteria);
//			model.addAttribute("search_criterias", new AllPatientData().getSearchColumnNames(BCITBVariousVariables.basic_search,
//					primaryRole.getUserRoleAccess(BCITBVariousVariables.consent)));
//			model.addAttribute("search_patient_result", patients_found);
//			
//			return "index";
//		}
//	}	
//	public Scheduler scheduleVariousTask(String whichTask, Scheduler scheduler) {
//	
//	try {
//		if(scheduler.checkExists(JobKey.jobKey(whichTask + "-job"))) { 
//			scheduler.deleteJob(JobKey.jobKey(whichTask + "-job"));
//		}
//		switch (whichTask) {
//		case "MONTHLY-REPORT-TO-OM":
//			JobDetail job = JobBuilder.newJob(MonthlyReportToOM.class).withIdentity(whichTask + "-job").build();
//			CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(whichTask + "-TRIGGER")
//					.withSchedule(CronScheduleBuilder.cronSchedule("0 0 9pm 6 * ?")).build();
//			scheduler.scheduleJob(job, trigger);
//			scheduler.start();
//			break;
//		case "AUDIT-EVERY-SIX-MONTHS":
//			scheduler.scheduleJob(JobBuilder.newJob(SemiAnnualAudit.class).withIdentity(whichTask + "-job").build(), TriggerBuilder.newTrigger()
//	    			.withIdentity(whichTask + "-TRIGGER").withSchedule(CronScheduleBuilder.cronSchedule("0 0 0 1 10/6 ? *")).build());
//			break;
//		}
//	} catch (SchedulerException e) {
//		e.printStackTrace();
//	}
//	return scheduler;
//}
//	public void processConsents(String whatToProcess, String whichDepartment) throws AddressException, ParseException, MessagingException
//	{
//		switch (whatToProcess) {
//	case "CLEAR-CONSENT-FULL-TYPE-EXCLUSIONS-LIST":
//	Consent consent = null;
//	for(Department dept:departmentService.getAllDepartments()) {
//		switch (dept.getDept_acronym().toUpperCase()) {
//		case BCITBVariousVariables.BGTB: case BCITBVariousVariables.HOTB:
//			for(ConsentExclusion ce : consentService.getConsentExclusions(dept.getDept_acronym(), 0)) {
//				consent = consentService.getPatientConsent(dept.getDept_acronym(), ce.getConsent_id(), "", "", null);
//				if(consent.getConsent_type() != null && consent.getConsent_type().trim().equalsIgnoreCase(BCITBVariousVariables.full)) {
//					consentService.deleteConsentExclusion(dept.getDept_acronym(), consent.getConsent_id(), 0);
//				}
//			}
//			break;
//		}
//	}
//	break;
//	case "PATIENT-UPDATE-SECONDARY-IDS":
//	String date_of_consent = "", secondary_id = "";
//	for(Patient pat : patientService.searchPatientDetails(whichDepartment, "", "")) {
//		date_of_consent = consentService.getPatientConsents("", whichDepartment, pat.getPatient_id(), "", "", null, BCITBVariousVariables.ascending).get(0).getDate_of_consent();
//		secondary_id = patientService.generateSecondaryID(pat, date_of_consent);
//		if(!pat.getSecondary_id().equalsIgnoreCase(secondary_id)) {
//			patientService.savePatientVariousColumns(whichDepartment, pat.getPatient_id(), BCITBVariousVariables.secondary_id, secondary_id);
//		}
//	}
//	break;
//
//case "FIND-MISSING-PATIENTS":
//	
//	FileInputStream fis = null;
//	try {
//		fis = new FileInputStream(new File("H:\\Project\\Import\\Haem-Onc_Oct_2021.xlsx"));
//	} catch (FileNotFoundException e2) {
//		e2.printStackTrace();
//	}  
//	//creating workbook instance that refers to .xls file  
//	XSSFWorkbook wb = null;
//	try {
//		wb = new XSSFWorkbook(fis);
//	} catch (IOException e1) {
//		e1.printStackTrace();
//	}   
//	//creating a Sheet object to retrieve the object  
//	XSSFSheet sheet=wb.getSheetAt(0);
//	DataFormatter formatter = new DataFormatter();
//	String hos_nums = "", old_pat_ids = "";
//	for(Row row: sheet) {  
//		for(Cell cell: row) { 
//			if(!formatter.formatCellValue(cell).isEmpty())
//				if(cell.getColumnIndex() == 0)
//					if(hos_nums.isEmpty())
//						hos_nums = "('" + formatter.formatCellValue(cell).toUpperCase() + "',0)";
//					else
//						hos_nums = hos_nums + ",('" + formatter.formatCellValue(cell).toUpperCase() + "',0)";
//				else if(cell.getColumnIndex() == 1)
//					if(old_pat_ids.isEmpty())
//						old_pat_ids = "('" + formatter.formatCellValue(cell).toUpperCase() + "',0)";
//					else
//						old_pat_ids = old_pat_ids + ",('" + formatter.formatCellValue(cell).toUpperCase() + "',0)";
//		}  
//	}  	
//	for(Object obj : patientService.getPatientObjectUsingSQL(BCITBVariousVariables.HOTB, 
//			"SELECT hospital_number FROM HOTB_PATIENT_V WHERE (upper(hospital_number),0) NOT IN (" + hos_nums + ")")) {
//	}
//	break;
//	
//case "CONSENT-CREATION-DATE":
//	
//	Timeline tl = null;
//	List<String> this_dates = new ArrayList<String>();
//	for(Consent con : consentService.getPatientConsents(BCITBVariousVariables.creation_date, 
//			whichDepartment, 0, "", "", null, BCITBVariousVariables.ascending)) {
//		tl = null;
//		if(con.getIs_imported() != null && con.getIs_imported().equalsIgnoreCase(BCITBVariousVariables.yes))
//			tl = timelineService.getTimeline(whichDepartment, BCITBVariousVariables.patient, con.getConsent_id(), BCITBVariousVariables.add);
//		else
//			tl = timelineService.getTimeline(whichDepartment, BCITBVariousVariables.consent, con.getConsent_id(), BCITBVariousVariables.add);
//		if(tl != null) {
//			try {
//				if(tl.getDate_of_entry().substring(0, 10).contains("/"))
//					this_dates.add(String.valueOf(con.getConsent_id()) + "|" + new SimpleDateFormat("yyyy-MM-dd").format(
//							new SimpleDateFormat("dd/MM/yyyy").parse(tl.getDate_of_entry().substring(0, 10))));
//				else if(tl.getDate_of_entry().substring(0, 10).contains("-"))
//					this_dates.add(String.valueOf(con.getConsent_id()) + "|" + new SimpleDateFormat("yyyy-MM-dd").format(
//							new SimpleDateFormat("dd-MM-yyyy").parse(tl.getDate_of_entry().substring(0, 10))));
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//	for(String dt : this_dates)
//		if(validateJavaDate(dt.split("|")[1]))
//			consentService.saveConsentVariousColumns(whichDepartment, Integer.valueOf(dt.split("|")[0]), 
//					BCITBVariousVariables.creation_date,new SimpleDateFormat("yyyy-MM-dd").format(dt.split("|")[1]));
//	break;
//case "PATIENT-REMOVE-DUPLICATES-SECONDARY-IDS":
//	
//	for(Object[] obj : patientService.getPatientObjectArrayUsingSQL(BCITBVariousVariables.BGTB, 
//			"SELECT database_id,count(*) FROM BGTB_PATIENT_V GROUP BY database_id HAVING COUNT(*)>1")) {
//		for(Patient pat : patientService.searchPatientDetails(BCITBVariousVariables.BGTB, "database_id", obj[0].toString())) {
//			patientService.savePatientVariousColumns(BCITBVariousVariables.BGTB, pat.getPatient_id(), 
//					"database_id", patientService.generateDatabaseID(BCITBVariousVariables.BGTB, "G"));
//		}
//	}
//	break;
//  }
//	public void importConsentData(String which_department, String folder_to_search) throws IOException 
//  {
//	consents = new ArrayList<>(consentService.searchConsents(BCITBVariousVariables.date_of_consent, which_department, search_criteria, BCITBVariousVariables.ascending));
//	if(consents != null && consents.size() > 0) {
//		for(Consent con: consents) {
//			patient = patientService.getPatientFromID(which_department, con.getCn_patient_id());
//			if(patient != null && patient.getPatient_firstname() != null && patient.getPatient_firstname().length() >= 1 && 
//					patient.getPatient_surname() != null && patient.getPatient_surname().length() >= 1) {
//				if(file_to_search.split("_")[1].substring(0, 1).equalsIgnoreCase(patient.getPatient_firstname().substring(0, 1))
//						&& file_to_search.split("_")[1].substring(1, 2).equalsIgnoreCase(patient.getPatient_surname().substring(0, 1))) {
//					if(con.getDigital_cf_attachment_id() == null) {
//						con.setDigital_cf_attachment_id(fileService.saveOrUpdateFileAndReturnFileId(which_department, 
//								new ConsentFile(listOfFiles[k].getName(),URLConnection.getFileNameMap().getContentTypeFor(listOfFiles[k].getName()),FileUtils.readFileToByteArray(listOfFiles[k]),
//								BCITBVariousVariables.digital_cf_attachment_file)));
//						consentService.saveConsentVariousColumns(which_department, con.getConsent_id(), 
//								BCITBVariousVariables.digital_cf_attachment_file.replace("_file", "_id"), String.valueOf(con.getDigital_cf_attachment_id()));
//					} else if(con.getAdditional_document_id() == null) {
//						con.setAdditional_document_id(fileService.saveOrUpdateFileAndReturnFileId(which_department, 
//								new ConsentFile(listOfFiles[k].getName(),URLConnection.getFileNameMap().getContentTypeFor(listOfFiles[k].getName()),FileUtils.readFileToByteArray(listOfFiles[k]),
//								BCITBVariousVariables.additional_document_file)));
//						consentService.saveConsentVariousColumns(which_department, con.getConsent_id(), 
//								BCITBVariousVariables.additional_document_file.replace("_file", "_id"), String.valueOf(con.getAdditional_document_id()));
//					}
//				}
//			}
//		}
//	} else {
//		patients = patientService.searchPatientDetails(which_department, "EQUAL-NUMBER", 
//				BCITBVariousVariables.hospital_number,file_to_search.split("_")[0]);
//			
//		}
//	}
//	break;
//case BCITBVariousVariables.BGTB:
//	if(org.apache.commons.lang.NumberUtils.isDigits(file_to_search.replaceAll("\\D+","")) == true && file_to_search.length() >= 8) {
//		search_criteria = file_to_search.substring(0, 4) + "-" + file_to_search.substring(4, 6) + "-" + file_to_search.substring(6, 8);
//		for(Consent con: consentService.searchConsents(BCITBVariousVariables.date_of_consent, which_department, search_criteria, BCITBVariousVariables.ascending)) {
//			if(con.getDigital_cf_attachment_id() == null) {
//				con.setDigital_cf_attachment_id(fileService.saveOrUpdateFileAndReturnFileId(which_department, 
//						new ConsentFile(listOfFiles[k].getName(),URLConnection.getFileNameMap().getContentTypeFor(listOfFiles[k].getName()),FileUtils.readFileToByteArray(listOfFiles[k]),
//						BCITBVariousVariables.digital_cf_attachment_file)));
//				consentService.saveConsentVariousColumns(which_department, con.getConsent_id(), 
//						BCITBVariousVariables.digital_cf_attachment_file.replace("_file", "_id"), String.valueOf(con.getDigital_cf_attachment_id()));
//			} else if(con.getAdditional_document_id() == null) {
//				con.setAdditional_document_id(fileService.saveOrUpdateFileAndReturnFileId(which_department, 
//						new ConsentFile(listOfFiles[k].getName(),URLConnection.getFileNameMap().getContentTypeFor(listOfFiles[k].getName()),FileUtils.readFileToByteArray(listOfFiles[k]),
//						BCITBVariousVariables.additional_document_file)));
//				consentService.saveConsentVariousColumns(which_department, con.getConsent_id(), 
//						BCITBVariousVariables.additional_document_file.replace("_file", "_id"), String.valueOf(con.getAdditional_document_id()));
//			}
//		}
//	} else {
//		if(file_to_search.contains("_") == true)
//			search_criteria = file_to_search.split("_")[0];
//		else
//			search_criteria = file_to_search;
//		for(Patient pat:patientService.searchPatientDetails(which_department, BCITBVariousVariables.old_pat_id, search_criteria)) {
//			for(Consent con:consentService.getPatientConsents("", which_department, pat.getPatient_id(), "", "", null, BCITBVariousVariables.ascending)) {
//				if(con.getDigital_cf_attachment_id() == null) {
//					con.setDigital_cf_attachment_id(fileService.saveOrUpdateFileAndReturnFileId(which_department, 
//							new ConsentFile(listOfFiles[k].getName(),URLConnection.getFileNameMap().getContentTypeFor(listOfFiles[k].getName()),FileUtils.readFileToByteArray(listOfFiles[k]),
//							BCITBVariousVariables.digital_cf_attachment_file)));
//					consentService.saveConsentVariousColumns(which_department, con.getConsent_id(), 
//							BCITBVariousVariables.digital_cf_attachment_file.replace("_file", "_id"), String.valueOf(con.getDigital_cf_attachment_id()));
//				} else if(con.getAdditional_document_id() == null) {
//					con.setAdditional_document_id(fileService.saveOrUpdateFileAndReturnFileId(which_department, 
//							new ConsentFile(listOfFiles[k].getName(),URLConnection.getFileNameMap().getContentTypeFor(listOfFiles[k].getName()),FileUtils.readFileToByteArray(listOfFiles[k]),
//							BCITBVariousVariables.additional_document_file)));
//					consentService.saveConsentVariousColumns(which_department, con.getConsent_id(), 
//							BCITBVariousVariables.additional_document_file.replace("_file", "_id"), String.valueOf(con.getAdditional_document_id()));
//				}
//			}
//		}
//	}
//	break;
//  }
	
	
//	processConsents("CONSENT-CREATION-DATE", BCITBVariousVariables.HOTB);
//	processConsents("PATIENT-REMOVE-DUPLICATES-SECONDARY-IDS", BCITBVariousVariables.BGTB);
//	processConsents("FIND-MISSING-PATIENTS", BCITBVariousVariables.HOTB);
//	processConsents("PATIENT-UPDATE-SECONDARY-IDS", BCITBVariousVariables.HOTB);
//	processConsents("CLEAR-CONSENT-FULL-TYPE-EXCLUSIONS-LIST", "");
//	importConsentData(BCITBVariousVariables.HOTB, "H:\\Project\\Heam-Onc\\Scanned\\2010");
//	searchConsentFilesExists(BCITBVariousVariables.HOTB, "H:\\Project\\Heam-Onc\\Scanned\\2010");
	
	
}
