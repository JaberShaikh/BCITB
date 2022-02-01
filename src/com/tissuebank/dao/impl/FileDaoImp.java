package com.tissuebank.dao.impl;

import java.math.BigDecimal;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tissuebank.dao.FileDao;
import com.tissuebank.model.ConsentFile;
import com.tissuebank.util.BCITBEncryptDecrypt;

@Transactional
@Repository("fileDao")
public class FileDaoImp implements FileDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public int saveOrUpdateFileAndReturnFileId(String which_department, ConsentFile file) {
		if (file.getFile_id() > 0)
			sessionFactory.getCurrentSession().createSQLQuery(
					"UPDATE " + which_department + "_File SET file_name=:filename_val,description=:description_val,content_type=:content_type_val,"  
					+ "file_data=:file_data_val WHERE file_id=:file_id_val")
					.setParameter("filename_val", file.getFile_name()).setParameter("description_val", file.getDescription())
					.setParameter("content_type_val", file.getContent_type())
					.setParameter("file_data_val", BCITBEncryptDecrypt.encrypt(file.getFile_data(),file.getFile_name()))
					.setParameter("file_id_val", file.getFile_id()).executeUpdate();
		else
		{
			file.setFile_id((int) (long)((sessionFactory.getCurrentSession().createSQLQuery("SELECT MAX(file_id) FROM " + which_department + "_File").uniqueResult() != null) ?
					((BigDecimal) sessionFactory.getCurrentSession().createSQLQuery("SELECT MAX(file_id) FROM " + which_department + "_File").uniqueResult()).longValue() + 1 : 1));
			sessionFactory.getCurrentSession().createSQLQuery(
					"INSERT into " + which_department + "_File(file_id,file_name,description,content_type,file_data) " +
					"VALUES (:file_id_val,:filename_val,:description_val,:content_type_val,:file_data_val)")
					.setParameter("file_id_val", file.getFile_id()).setParameter("filename_val", file.getFile_name())
					.setParameter("description_val", file.getDescription()).setParameter("content_type_val", file.getContent_type())
					.setParameter("file_data_val", BCITBEncryptDecrypt.encrypt(file.getFile_data(),file.getFile_name())).executeUpdate();
		}
		return file.getFile_id();
	}

	@Override
	public ConsentFile getFile(String which_department, int file_id, String file_description) {
		if(file_description.trim() != "")
			return (ConsentFile) sessionFactory.getCurrentSession().createQuery(
					"FROM " + which_department + "_File_V WHERE file_id=" + file_id + " AND upper(description)='" + 
					file_description.toUpperCase() + "'").uniqueResult();
		else
			return (ConsentFile) sessionFactory.getCurrentSession().createQuery(
					"FROM " + which_department + "_File_V WHERE file_id=" + file_id).uniqueResult();
	}

	@Override
	public void deleteFile(String which_department, int file_id) {
		sessionFactory.getCurrentSession().createSQLQuery(
				"DELETE " + which_department + "_File_V WHERE file_id = " + file_id).executeUpdate();
	}

	@Override
	public ConsentFile getFile(String which_department, String file_name) {
		return (ConsentFile) sessionFactory.getCurrentSession().createQuery(
				"FROM " + which_department + "_File_V WHERE upper(file_name)='" + file_name.toUpperCase() + "'").setMaxResults(1).uniqueResult();
	}

}
