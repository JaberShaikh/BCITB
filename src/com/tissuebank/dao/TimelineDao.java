package com.tissuebank.dao;

import java.util.List;

import com.tissuebank.model.Timeline;

public interface TimelineDao
{
    void saveTimeline(String which_department, int data_id, int user_id, int role_id, String status, String notes, 
    		String date_of_entry, String data_type, String description, String is_encrypted, int tl_patient_id);
    List<Timeline> getTimeline(String whatToProcess, String which_department, String whichDataType, int data_id, String asc_or_desc); 
    Timeline getTimeline(String which_department, String whichDataType, int data_id, String status); 
    void deleteTimeline(String which_department, int data_id, String data_type);
}
