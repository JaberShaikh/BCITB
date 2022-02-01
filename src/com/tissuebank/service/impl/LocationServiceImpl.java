package com.tissuebank.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tissuebank.dao.LocationDao;
import com.tissuebank.model.global.views.Location;
import com.tissuebank.model.global.views.LocationMinimal;
import com.tissuebank.service.LocationService;

@Service("locationService")
@Transactional
public class LocationServiceImpl implements LocationService
{
	@Autowired
	private LocationDao locationDao;
	
	@Override
	public List<Location> getAllLocationsFromIds(String loc_ids) {
		return locationDao.getAllLocationsFromIds(loc_ids);
	}

	@Override
	public Location getLocation(int location_id) {
		return locationDao.getLocation(location_id);
	}

	@Override
	public Location getLocation(String import_acronym) {
		return locationDao.getLocation(import_acronym);
	}

	@Override
	public List<LocationMinimal> getAllLocationsMinimalFromIds(String loc_ids) {
		return locationDao.getAllLocationsMinimalFromIds(loc_ids);
	}
	
}
