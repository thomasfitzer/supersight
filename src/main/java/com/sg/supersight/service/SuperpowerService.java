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
import com.sg.supersight.model.Superpower;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Thomas
 */
@Service
public class SuperpowerService {
    
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
    
    public Superpower createSuperpower(String name, String description) {
        Superpower superpower = new Superpower();
        superpower.setName(name);
        superpower.setDescription(description);
        
        return superpower;
    }
    
    public List<Hero> getHeroesForSuperpower(Superpower superpower){
        return heroDao.getHeroesByPower(superpower);
    }
    // LOCAL DAO FUNCTIONS  
    public Superpower getSuperpowerById(int id){
        return superpowerDao.getPowerById(id);      
    }
    public List<Superpower> getAllSuperpowers(){
        return superpowerDao.getAllPowers();
    }
    public Superpower addSuperpower(Superpower superpower){
        return superpowerDao.addPower(superpower);
    }
    public void updateSuperpower(Superpower superpower){
        superpowerDao.updatePower(superpower);
    }
    public void deleteSuperpowerById(int id){
        superpowerDao.deletePowerById(id);
    }    
    
}
