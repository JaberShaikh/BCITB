package com.tissuebank.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tissuebank.dao.LocationDao;
import com.tissuebank.model.global.views.Location;
import com.tissuebank.model.global.views.LocationMinimal;

@Transactional
@Repository("locationDao")
public class LocationDaoImp implements LocationDao {

  @Autowired
  private SessionFactory sessionFactory;

  @SuppressWarnings("unchecked")
  @Override
  public List<Location> getAllLocationsFromIds(String loc_ids) {
	if (loc_ids != null)
		return sessionFactory.getCurrentSession().createQuery("FROM Location WHERE loc_id IN (" + loc_ids + ")").list();
	else
		return null;
  }

@Override
public Location getLocation(String import_acronym) {
	return (Location) sessionFactory.getCurrentSession().createQuery("FROM Location WHERE import_acronym='" + import_acronym + "'").uniqueResult();
}

@Override
public Location getLocation(int location_id) {
	return (Location) sessionFactory.getCurrentSession().createQuery("FROM Location WHERE loc_id=" + location_id).list().get(0);
}

@SuppressWarnings("unchecked")
@Override
public List<LocationMinimal> getAllLocationsMinimalFromIds(String loc_ids) {
	if (loc_ids != null)
		return sessionFactory.getCurrentSession().createQuery("FROM LocationMinimal WHERE loc_id IN (" + loc_ids + ")").list();
	else
		return null;
}

}
