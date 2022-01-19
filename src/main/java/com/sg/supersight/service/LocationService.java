/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.supersight.service;

import com.sg.supersight.dao.HeroDao;
import com.sg.supersight.dao.LocationDao;
import com.sg.supersight.dao.OrganizationDao;
import com.sg.supersight.dao.SightingDao;
import com.sg.supersight.dao.SuperpowerDao;
import com.sg.supersight.model.Hero;
import com.sg.supersight.model.Location;
import com.sg.supersight.model.Sighting;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Thomas
 */
@Service
public class LocationService {
    
    @Autowired
    HeroDao heroDao;
    
    @Autowired
    LocationDao locationDao;
    
    @Autowired
    OrganizationDao organizationDao;
    
    @Autowired
    SightingDao sightingDao;
    
    @Autowired
    SuperpowerDao superpowerDao;
    
    public Location createLocation(String name, double latitude, double longitude,
            String description, String address) {
        Location location = new Location();
        location.setName(name);
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        location.setDescription(description);
        location.setAddress(address);
        
        return location;
    }
    
    public boolean isLatValid(String latitude) {
        try{
            double value = Double.parseDouble(latitude);
            if(value < -90 || value > 90) {
                return false;
            }
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
    
    public boolean isLongValid(String longitude) {
        try{
            double value = Double.parseDouble(longitude);
            if(value < -180 || value > 180) {
                return false;
            }
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
    
    public List<Sighting> getSightingsByLocation(Location location){
        return sightingDao.getSightingsByLocation(location);
    }
    public List<Hero> getHeroesByLocation(Location location){
        return heroDao.getHeroesByLocation(location);
    }
    // LOCAL DAO FUNCTIONS  
    public Location getLocationById(int id){
        return locationDao.getLocationById(id);      
    }
    public List<Location> getAllLocations(){
        return locationDao.getAllLocations();
    }
    public Location addLocation(Location location){
        return locationDao.addLocation(location);
    }
    public void updateLocation(Location location){
        locationDao.updateLocation(location);
    }
    public void deleteLocationById(int id){
        locationDao.deleteLocationById(id);
    }    
    
}
