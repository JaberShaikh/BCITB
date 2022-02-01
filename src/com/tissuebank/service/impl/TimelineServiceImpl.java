package com.tissuebank.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tissuebank.dao.TimelineDao;
import com.tissuebank.model.Timeline;
import com.tissuebank.service.TimelineService;

@Service("timelineService")
@Transactional
public class TimelineServiceImpl implements TimelineService
{
	@Autowired
	private TimelineDao timelineDao;

	@Override
	public void saveTimeline(String dept_acronym, int data_id, int user_id, int role_id, String status,
			String notes, String date_of_entry, String data_type, String description, String is_encrypted, int tl_patient_id) {
		timelineDao.saveTimeline(dept_acronym, data_id, user_id, role_id, status, notes, date_of_entry, data_type, description, is_encrypted, tl_patient_id);
	}

	@Override
	public List<Timeline> getTimeline(String whatToProcess, String dept_acronym, String whichDataType, int data_id, String asc_or_desc) {
		return timelineDao.getTimeline(whatToProcess, dept_acronym, whichDataType, data_id, asc_or_desc);
	}

	@Override
	public void deleteTimeline(String which_department, int data_id, String data_type) {
		timelineDao.deleteTimeline(which_department, data_id, data_type);
	}

	@Override
	public Timeline getTimeline(String which_department, String whichDataType, int data_id, String status) {
		return timelineDao.getTimeline(which_department, whichDataType, data_id, status);
	}

}
