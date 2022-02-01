package com.tissuebank.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tissuebank.dao.FileDao;
import com.tissuebank.model.ConsentFile;
import com.tissuebank.service.FileService;

@Service("fileService")
@Transactional
public class FileServiceImpl implements FileService
{
	@Autowired
	private FileDao fileDao;

	@Override
	public int saveOrUpdateFileAndReturnFileId(String which_department, ConsentFile file) {
		return fileDao.saveOrUpdateFileAndReturnFileId(which_department, file);
	}

	@Override
	public ConsentFile getFile(String which_department, int file_id, String file_description) {
		return fileDao.getFile(which_department, file_id, file_description);
	}

	@Override
	public void deleteFile(String which_department, int file_id) {
		fileDao.deleteFile(which_department, file_id);
	}

	@Override
	public ConsentFile getFile(String which_department, String file_name) {
		return fileDao.getFile(which_department, file_name);
	}

}
