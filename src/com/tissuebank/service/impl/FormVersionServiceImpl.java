package com.tissuebank.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tissuebank.dao.FormVersionDao;
import com.tissuebank.model.FormVersion;
import com.tissuebank.service.FormVersionService;

@Service("formVersionService")
@Transactional
public class FormVersionServiceImpl implements FormVersionService
{
	@Autowired
	private FormVersionDao formVersionDao;
	
	@Override
	public List<FormVersion> getAllFormVersions(String dept_acronym) {
		return formVersionDao.getAllFormVersions(dept_acronym);
	}

	@Override
	public FormVersion getFormVersion(String dept_acronym, int form_version_id) {
		return formVersionDao.getFormVersion(dept_acronym, form_version_id);
	}

	@Override
	public FormVersion getFormVersion(String dept_acronym, String form_type, String import_description) {
		return formVersionDao.getFormVersion(dept_acronym, form_type, import_description);
	}
	
}
