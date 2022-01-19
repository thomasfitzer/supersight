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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
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
public class LocationDaoDBTest {
  
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
    
    public LocationDaoDBTest() {
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
     * Test of getLocationById method, of class LocationDaoDB.
     */
    @Test
    public void testAddGetLocation() {
        Location location = new Location();
        location.setName("Test name");
        location.setLatitude(12.3);
        location.setLongitude(-5.36);
        location.setDescription("Test description");
        location.setAddress("Test address info");
        location = locationDao.addLocation(location);
        
        Location fromDao = locationDao.getLocationById(location.getId());        
        assertEquals(location,fromDao);        
    }

    /**
     * Test of getAllLocations method, of class LocationDaoDB.
     */
    @Test
    public void testGetAllLocations() {
        Location location = new Location();
        location.setName("Test name");
        location.setLatitude(12.3);
        location.setLongitude(-5.36);
        location.setDescription("Test description");
        location.setAddress("Test address info");
        location = locationDao.addLocation(location);
        
        Location location2 = new Location();
        location2.setName("Test name2");
        location2.setLatitude(1.3);
        location2.setLongitude(5.36);
        location2.setDescription("Test description2");
        location2.setAddress("Test address info2");
        location2 = locationDao.addLocation(location2);
        
        List<Location> locations = locationDao.getAllLocations();
        assertEquals(2, locations.size());
        assertTrue(locations.contains(location));
        assertTrue(locations.contains(location2));         
    }



    /**
     * Test of updateLocation method, of class LocationDaoDB.
     */
    @Test
    public void testUpdateLocation() {
        Location location = new Location();
        location.setName("Test name");
        location.setLatitude(12.3);
        location.setLongitude(-5.36);
        location.setDescription("Test description");
        location.setAddress("Test address info");
        location = locationDao.addLocation(location);
        
        Location fromDao = locationDao.getLocationById(location.getId());        
        assertEquals(location,fromDao);
        
        location.setName("Test name2");
        location.setLatitude(12.33);
        location.setLongitude(-5.365);
        location.setDescription("Test description2");
        
        locationDao.updateLocation(location);
        assertNotEquals(location,fromDao);
        
        fromDao = locationDao.getLocationById(location.getId());
        assertEquals(location,fromDao);        
    }

    /**
     * Test of deleteLocationById method, of class LocationDaoDB.
     */
    @Test
    public void testDeleteLocationById() {
        Location location = new Location();
        location.setName("Test name");
        location.setLatitude(12.3);
        location.setLongitude(-5.36);
        location.setDescription("Test description");
        location.setAddress("Test address info");
        location = locationDao.addLocation(location);
        
        Location fromDao = locationDao.getLocationById(location.getId());        
        assertEquals(location,fromDao);
        
        locationDao.deleteLocationById(location.getId());
        
        fromDao = locationDao.getLocationById(location.getId());
        assertNull(fromDao);        
    }


    
}
