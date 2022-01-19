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
import com.sg.supersight.model.Organization;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Thomas
 */
@Service
public class OrganizationService {
    
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
    
    public Organization createOrganization(String name, boolean isHero, String description,
            String address, String contact, List<Hero> heroes) {
        Organization org = new Organization();
        org.setName(name);
        org.setIsHero(isHero);
        org.setDescription(description);
        org.setAddress(address);
        org.setContact(contact);
        org.setMembers(heroes);
        
        return org;
    }
    
    public Organization getOrgById(int id){
        return organizationDao.getOrgById(id);      
    }
    public List<Organization> getAllOrgs(){
        return organizationDao.getAllOrgs();
    }
    public Organization addOrg(Organization org){
        return organizationDao.addOrg(org);
    }
    public void updateOrg(Organization org){
        organizationDao.updateOrg(org);
    }
    public void deleteOrganizationById(int id){
        organizationDao.deleteOrgById(id);
    }    
    
}
