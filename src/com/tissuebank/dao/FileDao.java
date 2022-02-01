package com.tissuebank.dao;

import com.tissuebank.model.ConsentFile;

public interface FileDao
{
	int saveOrUpdateFileAndReturnFileId(String which_department, ConsentFile file);
	ConsentFile getFile(String which_department, int file_id, String file_description);
	ConsentFile getFile(String which_department, String file_name);
    void deleteFile(String which_department, int file_id);
}
