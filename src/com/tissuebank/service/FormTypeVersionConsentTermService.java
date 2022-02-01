package com.tissuebank.service;

import java.util.List;

import com.tissuebank.model.FormTypeVersionConsentTerm;

public interface FormTypeVersionConsentTermService 
{
	List<FormTypeVersionConsentTerm> getAllFormTypeVersionConsentTerms(String dept_acronym); 
}
