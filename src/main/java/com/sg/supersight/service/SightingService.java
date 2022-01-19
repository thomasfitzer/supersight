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
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Thomas
 */
@Service
public class SightingService {
    
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
    
    public Sighting createSighting(Hero hero, Location location, Date date) {
        Sighting sighting = new Sighting();
        sighting.setHeroId(hero.getId());
        sighting.setLocation(location);
        sighting.setDate(date);
        
        return sighting;
    }
    
    public boolean isDateValid(String date) {
        try{
            Date.valueOf(date);
            return true; 
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }
    
    public List<Sighting> getRecentSightings(int number) {
        List<Sighting> sightings = sightingDao.getAllSightings();
        Collections.sort(sightings, new SortByDateDesc());
        if(number >= sightings.size()){
            return sightings;
        } else {
            List<Sighting> result = new ArrayList<>();
            for(int i=0; i<sightings.size(); i++) {
                result.add(sightings.get(i));
            }
            return result;
        }
    }
    
    public HashMap<Sighting, Hero> mapHeroSightings(List<Sighting> sightings){
        HashMap<Sighting, Hero> heroSightings = new HashMap<>();
        for(int i=0; i<sightings.size(); i++){
            heroSightings.put(sightings.get(i), getHeroBySighting(sightings.get(i)));
        }
        return heroSightings;
    }    
    
    
    
    public static final class SortByDateDesc implements Comparator<Sighting>{

        @Override
        public int compare(Sighting s1, Sighting s2) {
            return s2.getDate().compareTo(s1.getDate());
        }
        
    }    
    
    public Hero getHeroBySighting(Sighting sighting){
        return heroDao.getHeroBySighting(sighting);
    }
    // LOCAL DAO FUNCTIONS  
    public Sighting getSightById(int id){
        return sightingDao.getSightById(id);      
    }
    public List<Sighting> getAllSightings(){
        return sightingDao.getAllSightings();
    }
    public Sighting addSighting(Sighting sighting){
        return sightingDao.addSighting(sighting);
    }
    public void updateSighting(Sighting sighting){
        sightingDao.updateSighting(sighting);
    }
    public void deleteSightingById(int id){
        sightingDao.deleteSightingById(id);
    }    
    
    
}
