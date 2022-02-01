package com.tissuebank.service;

import java.util.List;

import com.tissuebank.model.FormVersionConsentTerm;

public interface FormVersionConsentTermService 
{
	List<FormVersionConsentTerm> getAllFormVersionConsentTerms(String dept_acronym); 
}
