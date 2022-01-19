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
import com.sg.supersight.model.Organization;
import com.sg.supersight.model.Sighting;
import com.sg.supersight.model.Superpower;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Thomas
 */

@Service
public class HeroService {
    
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
    
    public Hero createHero(String name, boolean isHero, String description, List<Superpower> superpowers) {
        Hero hero = new Hero();
        hero.setName(name);
        hero.setIsHero(isHero);
        hero.setDescription(description);
        hero.setSuperpowers(superpowers);
        
        
        return hero;
    }
    
     public List<Organization> getOrgsByHero(Hero hero){
        return organizationDao.getOrgsByHero(hero);
    }
    public List<Location> getLocationsByHero(Hero hero){
        return locationDao.getLocationsByHero(hero);
    }
    // LOCAL DAO FUNCTIONS  
    public Hero getHeroById(int id){
        return heroDao.getHeroById(id);      
    }
    public List<Hero> getAllHeroes(){
        return heroDao.getAllHeroes();
    }
    public Hero addHero(Hero hero){
        return heroDao.addHero(hero);
    }
    public void updateHero(Hero hero){
        heroDao.updateHero(hero);
    }
    public void deleteHeroById(int id){
        heroDao.deleteHeroById(id);
    }   
    
}
