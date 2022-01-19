/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.supersight.dao;

import com.sg.supersight.model.Hero;
import com.sg.supersight.model.Location;
import com.sg.supersight.model.Organization;
import com.sg.supersight.model.Sighting;
import com.sg.supersight.model.Superpower;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 *
 * @author Thomas
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class SuperpowerDaoDBTest {
    
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
    
    public SuperpowerDaoDBTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        List<Hero> heroes = heroDao.getAllHeroes();
        for(Hero hero : heroes){
            heroDao.deleteHeroById(hero.getId());
        }

        List<Location> locations = locationDao.getAllLocations();
        for(Location location : locations){
            locationDao.deleteLocationById(location.getId());
        }

        List<Organization> organizations = organizationDao.getAllOrgs();
        for(Organization organization : organizations){
            organizationDao.deleteOrgById(organization.getId());
        }

        List<Sighting> sightings = sightingDao.getAllSightings();
        for(Sighting sighting : sightings){
            sightingDao.deleteSightingById(sighting.getId());
        }

        List<Superpower> superpowers = superpowerDao.getAllPowers();
        for(Superpower superpower : superpowers){
            superpowerDao.deletePowerById(superpower.getId());
        }        
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getPowerById method, of class SuperpowerDaoDB.
     */
    @Test
    public void testAddGetPower() {
        Superpower superpower = new Superpower();
        superpower.setName("Test name");
        superpower.setDescription("Test description");
        superpower = superpowerDao.addPower(superpower);
        
        Superpower fromDao = superpowerDao.getPowerById(superpower.getId());       
        assertEquals(superpower,fromDao);        
    }

    /**
     * Test of getAllPowers method, of class SuperpowerDaoDB.
     */
    @Test
    public void testGetAllPowers() {
        Superpower superpower = new Superpower();
        superpower.setName("Test name");
        superpower.setDescription("Test description");
        superpower = superpowerDao.addPower(superpower);
        
        Superpower superpower2 = new Superpower();
        superpower2.setName("Test name2");
        superpower2.setDescription("Test description2");
        superpower2 = superpowerDao.addPower(superpower2);
        
        List<Superpower> superpowers = superpowerDao.getAllPowers();
        assertEquals(2, superpowers.size());
        assertTrue(superpowers.contains(superpower));
        assertTrue(superpowers.contains(superpower2));        
    }


    /**
     * Test of updatePower method, of class SuperpowerDaoDB.
     */
    @Test
    public void testUpdatePower() {
        Superpower superpower = new Superpower();
        superpower.setName("Test name");
        superpower.setDescription("Test description");
        superpower = superpowerDao.addPower(superpower);
        
        Superpower fromDao = superpowerDao.getPowerById(superpower.getId());       
        assertEquals(superpower,fromDao);
        
        superpower.setName("Test name2");
        superpower.setDescription("Test description2");
        
        superpowerDao.updatePower(superpower);
        assertNotEquals(superpower,fromDao);
        
        fromDao = superpowerDao.getPowerById(superpower.getId());
        assertEquals(superpower,fromDao);        
    }

    /**
     * Test of deletePowerById method, of class SuperpowerDaoDB.
     */
    @Test
    public void testDeletePowerById() {
        Superpower superpower = new Superpower();
        superpower.setName("Test name");
        superpower.setDescription("Test description");
        superpower = superpowerDao.addPower(superpower);
        
        Superpower fromDao = superpowerDao.getPowerById(superpower.getId());       
        assertEquals(superpower,fromDao);
        
        superpowerDao.deletePowerById(superpower.getId());
        
        fromDao = superpowerDao.getPowerById(superpower.getId());
        assertNull(fromDao);        
    }
    
}
