package com.tissuebank.model.global.tables;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;

@Entity
@Table(name = "TB_AUDIT_PROCESS")
public class AuditProcess 
{
  @Id
  @Column(name = "AUDIT_PROCESS_ID")
  private int audit_process_id;

  @Column(name = "AUDIT_PROCESS_DATE")
  private String audit_process_date;

  @Column(name = "MONTHLY_REPORT_SENT_DATE")
  private String monthly_report_sent_date;
  
  @Column(name = "NUMBER_OF_CONSENTS")
  private int number_of_consents;

  @Column(name = "SEND_REPORT_BGTB")
  private String send_report_bgtb;

  @Column(name = "SEND_REPORT_HOTB")
  private String send_report_hotb;

  @Column(name = "REPORT_USER_ID")
  private int report_user_id;

  @Column(name = "MONTHLY_CYCLE")
  private String monthly_cycle;
  
public String getMonthly_cycle() {
	return monthly_cycle;
}

public void setMonthly_cycle(String monthly_cycle) {
	this.monthly_cycle = monthly_cycle;
}

public String getMonthly_report_sent_date() {
	return monthly_report_sent_date;
}

public void setMonthly_report_sent_date(String monthly_report_sent_date) {
	this.monthly_report_sent_date = monthly_report_sent_date;
}

public String getAudit_process_date() {
	return audit_process_date;
}

public void setAudit_process_date(String audit_process_date) {
	this.audit_process_date = audit_process_date;
}

public String getSend_report_bgtb() {
	return send_report_bgtb;
}

public void setSend_report_bgtb(String send_report_bgtb) {
	this.send_report_bgtb = send_report_bgtb;
}

public String getSend_report_hotb() {
	return send_report_hotb;
}

public void setSend_report_hotb(String send_report_hotb) {
	this.send_report_hotb = send_report_hotb;
}

public int getReport_user_id() {
	return report_user_id;
}

public void setReport_user_id(int report_user_id) {
	this.report_user_id = report_user_id;
}

public int getNumber_of_consents() {
	return number_of_consents;
}

public void setNumber_of_consents(int number_of_consents) {
	this.number_of_consents = number_of_consents;
}

public int getAudit_process_id() {
	return audit_process_id;
}

public void setAudit_process_id(int audit_process_id) {
	this.audit_process_id = audit_process_id;
}

@Override
public String toString() {
	return "AuditProcess [audit_process_id=" + audit_process_id + ", audit_process_date=" + audit_process_date
			+ ", monthly_report_sent_date=" + monthly_report_sent_date + ", number_of_consents=" + number_of_consents
			+ ", send_report_bgtb=" + send_report_bgtb + ", send_report_hotb=" + send_report_hotb + ", report_user_id="
			+ report_user_id + "]";
}

}