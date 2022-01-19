/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.supersight.dao;

import com.sg.supersight.model.Hero;
import com.sg.supersight.model.Organization;
import java.util.List;

/**
 *
 * @author Thomas
 */
public interface OrganizationDao {
    
    Organization getOrgById(int id);
    List<Organization> getAllOrgs();
    Organization addOrg(Organization org);
    void updateOrg(Organization org);
    void deleteOrgById(int id);
    
    List<Organization> getOrgsByHero(Hero hero);
}
