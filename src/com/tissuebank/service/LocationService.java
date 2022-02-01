package com.tissuebank.service;

import java.util.List;

import com.tissuebank.model.global.views.Location;
import com.tissuebank.model.global.views.LocationMinimal;

public interface LocationService 
{
	List<Location> getAllLocationsFromIds(String loc_ids); 
	List<LocationMinimal> getAllLocationsMinimalFromIds(String loc_ids); 
	Location getLocation(int location_id);
	Location getLocation(String import_acronym); 
}
