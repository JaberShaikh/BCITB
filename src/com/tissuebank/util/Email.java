package com.tissuebank.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.tissuebank.model.Action;
import com.tissuebank.model.Consent;
import com.tissuebank.model.ConsentAudit;
import com.tissuebank.model.Patient;
import com.tissuebank.model.global.tables.AuditProcess;
import com.tissuebank.model.global.tables.AuditTeam;
import com.tissuebank.service.ActionService;
import com.tissuebank.service.ConsentService;
import com.tissuebank.service.PatientService;
import com.tissuebank.service.UserService;

public class Email {

	public void sendEmail(String whatToProcess, String which_department, List<AuditTeam> audit_teams, 
			AuditProcess audit_process, MimeMessage mimeMessage, UserService userService, PatientService patientService,
			ConsentService consentService, ActionService actionService) {
		
		Multipart multiPart = new MimeMultipart();
		MimeBodyPart messageText = new MimeBodyPart();
		String message_body = "";
		int this_count = 0;
		
		try {
			switch (whatToProcess) {
			case "SEND-AUDIT-REPORT-ON-COMPLETION":
				Patient this_patient = new Patient();
				Consent this_consent = new Consent();
				for(ConsentAudit ca : consentService.getConsentAudits(which_department, 
						BCITBVariousVariables.audit_triggered_date, audit_process.getAudit_process_date())) {
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
							audit_process.getAudit_process_date())));
					messageText.setContent(message_body
							+ "Kind Regards<br>BCI Tissue Bank Team<br><br>P.S. Please do not reply to this message. Replies to this message are routed to an "
							+ "unmonitored mailbox. If you have questions please contact the BCI Tissue Bank Team bcitissuebankmgmt@qmul.ac.uk", 
							"text/html; charset=UTF-8");
					mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(userService.findUserById(audit_process.getReport_user_id()).getEmail()));
					multiPart.addBodyPart(messageText);
					mimeMessage.setContent(multiPart);
					Transport.send(mimeMessage);
				}
				break;
			case "MONTHLY-REPORT-TO-OM":
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
				System.out.println("consent_validate_count = " + consent_validate_count);
				System.out.println("non_validate_consents size = " + non_validate_consents.size());
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
				System.out.println("this_actions size = " + this_actions.size());				
				if(!message_body.isEmpty()) {
					System.out.println("messageText = " + messageText);
					mimeMessage.setFrom(new InternetAddress("noreply-BCITB@qmul.ac.uk"));
					mimeMessage.setSubject(which_department + " monthly report");
					messageText.setContent(message_body
							+ "<br>Kind Regards<br>BCI Tissue Bank Team<br><br>P.S. Please do not reply to this message. Replies to this message are routed to an "
							+ "unmonitored mailbox. If you have questions please contact the BCI Tissue Bank Team bcitissuebankmgmt@qmul.ac.uk", 
							"text/html; charset=UTF-8");
					mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(userService.findUserById(audit_process.getReport_user_id()).getEmail()));
					multiPart.addBodyPart(messageText);
					mimeMessage.setContent(multiPart);
					Transport.send(mimeMessage);
				}
				break;
			case "SEMI-ANNUAL-AUDIT-TRIGGERED":
				mimeMessage.setFrom(new InternetAddress("noreply-BCITB@qmul.ac.uk"));
				mimeMessage.setSubject("Consents To Audit");
				for(AuditTeam at : audit_teams) {
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
		} catch (MessagingException | ParseException e) {
			e.printStackTrace();
		}
		
	}
	
}
