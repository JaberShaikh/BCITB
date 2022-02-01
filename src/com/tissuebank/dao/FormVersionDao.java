package com.tissuebank.dao;

import java.util.List;

import com.tissuebank.model.FormVersion;

public interface FormVersionDao
{
	List<FormVersion> getAllFormVersions(String dept_acronym);
	FormVersion getFormVersion(String dept_acronym, int form_version_id);
	FormVersion getFormVersion(String dept_acronym, String form_type, String import_description);
}
