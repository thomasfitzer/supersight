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
import java.sql.Date;
import java.util.ArrayList;
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
public class OrganizationDaoDBTest {
    
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
    
    public OrganizationDaoDBTest() {
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

    @Test
    public void testAddGetOrg() {
        Superpower superpower = new Superpower();
        superpower.setName("Test name");
        superpower.setDescription("Test description");
        superpower = superpowerDao.addPower(superpower);
        
        List<Superpower> superpowers = new ArrayList<>();
        superpowers.add(superpower);
        
        List<Sighting> sightings = new ArrayList<>();        
        
        Hero hero = new Hero();
        hero.setIsHero(true);
        hero.setName("Test name");
        hero.setDescription("Test description");
        hero.setSuperpowers(superpowers);
        hero.setSightings(sightings);
        hero = heroDao.addHero(hero);
        
        Location location = new Location();
        location.setName("Test name");
        location.setLatitude(12.3);
        location.setLongitude(-5.36);
        location.setDescription("Test description");
        location.setAddress("Test address info");
        location = locationDao.addLocation(location);
        
        Date date = Date.valueOf("2021-11-13");  
        
        Sighting sighting = new Sighting();
        sighting.setHeroId(hero.getId());
        sighting.setLocation(location);
        sighting.setDate(date);
        sighting = sightingDao.addSighting(sighting);
        
        hero.setSightings(sightings);
        
        heroDao.updateHero(hero);
        
        List<Hero> heroes = new ArrayList<>();        
        heroes.add(hero);
        
        Organization org = new Organization();
        org.setName("Test name");
        org.setIsHero(true);
        org.setDescription("Test description");
        org.setAddress("Test address");
        org.setContact("Test contact");
        org.setMembers(heroes);
        org = organizationDao.addOrg(org);

        Organization fromDao = organizationDao.getOrgById(org.getId());
        assertEquals(org.getName(),fromDao.getName());
        assertEquals(org.isIsHero(),fromDao.isIsHero()); 
        assertEquals(org.getDescription(),fromDao.getDescription()); 
        assertEquals(org.getAddress(),fromDao.getAddress()); 
        assertEquals(org.getContact(),fromDao.getContact()); 
        assertEquals(org.getId(),fromDao.getId());
        
    }

/*
    @Test
    public void testGetAllOrgs() {
        
        Superpower power = new Superpower();
        power.setName("Test name");
        power.setDescription("Test description");
        power = superpowerDao.addPower(power);
        
        List<Superpower> superpowers = new ArrayList<>();
        superpowers.add(power);
        
        List<Sighting> sightings = new ArrayList<>();        
        
        Hero hero = new Hero();
        hero.setIsHero(true);
        hero.setName("Test name");
        hero.setDescription("Test description");
        hero.setSuperpowers(superpowers);
        hero.setSightings(sightings);
        hero = heroDao.addHero(hero);
        
        Location location = new Location();
        location.setName("Test name");
        location.setLatitude(12.3);
        location.setLongitude(-5.36);
        location.setDescription("Test description");
        location.setAddress("Test address info");
        location = locationDao.addLocation(location);
        
        Date date = Date.valueOf("2021-10-31");  
        
        Sighting sighting = new Sighting();
        sighting.setHeroId(hero.getId());
        sighting.setLocation(location);
        sighting.setDate(date);
        sighting = sightingDao.addSighting(sighting);
        
        hero.setSightings(sightings);
        
        heroDao.updateHero(hero);
        
        List<Hero> heroes = new ArrayList<>();        
        heroes.add(hero);
        
        Organization org = new Organization();
        org.setName("Test name");
        org.setIsHero(true);
        org.setDescription("Test description");
        org.setAddress("Test address");
        org.setContact("Test contact");
        org.setMembers(heroes);
        org = organizationDao.addOrg(org);
        
        // Organization 2
        
        Superpower superpower2 = new Superpower();
        superpower2.setName("Test name2");
        superpower2.setDescription("Test description2");
        superpower2 = superpowerDao.addPower(superpower2);
        
        List<Superpower> superpowers2 = new ArrayList<>();
        superpowers2.add(superpower2);
        
        List<Sighting> sightings2 = new ArrayList<>();        
        
        Hero hero2 = new Hero();
        hero2.setIsHero(false);
        hero2.setName("Test name2");
        hero2.setDescription("Test description2");
        hero2.setSuperpowers(superpowers2);
        hero2.setSightings(sightings2);
        hero2 = heroDao.addHero(hero2);
        
        Location location2 = new Location();
        location2.setName("Test name2");
        location2.setLatitude(1.3);
        location2.setLongitude(5.36);
        location2.setDescription("Test description2");
        location2.setAddress("Test address info2");
        location2 = locationDao.addLocation(location2);
        
        Date date2 = Date.valueOf("2021-11-18");  
        
        Sighting sighting2 = new Sighting();
        sighting2.setHeroId(hero2.getId());
        sighting2.setLocation(location2);
        sighting2.setDate(date2);
        sighting2 = sightingDao.addSighting(sighting2);
        
        sightings2.add(sighting2);
        
        hero2.setSightings(sightings2);
        
        heroDao.updateHero(hero2);
        
        List<Hero> heroes2 = new ArrayList<>();        
        heroes2.add(hero2);
        
        Organization org2 = new Organization();
        org2.setName("Test name2");
        org2.setIsHero(false);
        org2.setDescription("Test description2");
        org2.setAddress("Test address2");
        org2.setContact("Test contact2");
        org2.setMembers(heroes2);
        org2 = organizationDao.addOrg(org2);
        
        
        List<Organization> orgs = organizationDao.getAllOrgs();
        assertEquals(2, orgs.size());
        assertTrue(orgs.contains(org));
        assertTrue(orgs.contains(org2));        
    }



    @Test
    public void testUpdateOrg() {
        Superpower superpower = new Superpower();
        superpower.setName("Test name");
        superpower.setDescription("Test description");
        superpower = superpowerDao.addPower(superpower);
        
        List<Superpower> superpowers = new ArrayList<>();
        superpowers.add(superpower);
        
        List<Sighting> sightings = new ArrayList<>();        
        
        Hero hero = new Hero();
        hero.setIsHero(true);
        hero.setName("Test name");
        hero.setDescription("Test description");
        hero.setSuperpowers(superpowers);
        hero.setSightings(sightings);
        hero = heroDao.addHero(hero);
        
        Location location = new Location();
        location.setName("Test name");
        location.setLatitude(12.3);
        location.setLongitude(-5.36);
        location.setDescription("Test description");
        location.setAddress("Test address info");
        location = locationDao.addLocation(location);
        
        Date date = Date.valueOf("2021-10-31");  
        
        Sighting sighting = new Sighting();
        sighting.setHeroId(hero.getId());
        sighting.setLocation(location);
        sighting.setDate(date);
        sighting = sightingDao.addSighting(sighting);
        
        hero.setSightings(sightings);
        
        heroDao.updateHero(hero);
        
        List<Hero> heroes = new ArrayList<>();        
        heroes.add(hero);
        
        Organization org = new Organization();
        org.setName("Test name");
        org.setIsHero(true);
        org.setDescription("Test description");
        org.setAddress("Test address");
        org.setContact("Test contact");
        org.setMembers(heroes);
        org = organizationDao.addOrg(org);
        
        Organization fromDao = organizationDao.getOrgById(org.getId());
        assertEquals(org,fromDao);

        org.setName("Test name2");
        org.setIsHero(false);
        org.setDescription("Test description2");
        org.setAddress("Test address2");
        org.setContact("Test contact2");
        
        organizationDao.updateOrg(org);
        assertNotEquals(org,fromDao);
        
        fromDao = organizationDao.getOrgById(org.getId());
        assertEquals(org,fromDao);        
    }


    @Test
    public void testDeleteOrgById() {
        Superpower superpower = new Superpower();
        superpower.setName("Test name");
        superpower.setDescription("Test description");
        superpower = superpowerDao.addPower(superpower);
        
        List<Superpower> superpowers = new ArrayList<>();
        superpowers.add(superpower);
        
        List<Sighting> sightings = new ArrayList<>();        
        
        Hero hero = new Hero();
        hero.setIsHero(true);
        hero.setName("Test name");
        hero.setDescription("Test description");
        hero.setSuperpowers(superpowers);
        hero.setSightings(sightings);
        hero = heroDao.addHero(hero);
        
        Location location = new Location();
        location.setName("Test name");
        location.setLatitude(12.3);
        location.setLongitude(-5.36);
        location.setDescription("Test description");
        location.setAddress("Test address info");
        location = locationDao.addLocation(location);
        
        Date date = Date.valueOf("2021-03-31");  
        
        Sighting sighting = new Sighting();
        sighting.setHeroId(hero.getId());
        sighting.setLocation(location);
        sighting.setDate(date);
        sighting = sightingDao.addSighting(sighting);
        
        hero.setSightings(sightings);
        
        heroDao.updateHero(hero);
        
        List<Hero> heroes = new ArrayList<>();        
        heroes.add(hero);
        
        Organization org = new Organization();
        org.setName("Test name");
        org.setIsHero(true);
        org.setDescription("Test description");
        org.setAddress("Test address");
        org.setContact("Test contact");
        org.setMembers(heroes);
        org = organizationDao.addOrg(org);
        
        Organization fromDao = organizationDao.getOrgById(org.getId());
        assertEquals(org,fromDao);
        
        organizationDao.deleteOrgById(org.getId());
        
        fromDao = organizationDao.getOrgById(org.getId());
        assertNull(fromDao);        
    }


    @Test
    public void testGetOrgsByHero() {
        Superpower superpower = new Superpower();
        superpower.setName("Test name");
        superpower.setDescription("Test description");
        superpower = superpowerDao.addPower(superpower);
        
        List<Superpower> superpowers = new ArrayList<>();
        superpowers.add(superpower);
        
        List<Sighting> sightings = new ArrayList<>();        
        
        Hero hero = new Hero();
        hero.setIsHero(true);
        hero.setName("Test name");
        hero.setDescription("Test description");
        hero.setSuperpowers(superpowers);
        hero.setSightings(sightings);
        hero = heroDao.addHero(hero);
        
        Location location = new Location();
        location.setName("Test name");
        location.setLatitude(12.3);
        location.setLongitude(-5.36);
        location.setDescription("Test description");
        location.setAddress("Test address info");
        location = locationDao.addLocation(location);
        
        Date date = Date.valueOf("2021-03-31");  
        
        Sighting sighting = new Sighting();
        sighting.setHeroId(hero.getId());
        sighting.setLocation(location);
        sighting.setDate(date);
        sighting = sightingDao.addSighting(sighting);
        
        hero.setSightings(sightings);
        
        heroDao.updateHero(hero);
        
        List<Hero> heroes = new ArrayList<>();        
        heroes.add(hero);
        
        Organization org = new Organization();
        org.setName("Test name");
        org.setIsHero(true);
        org.setDescription("Test description");
        org.setAddress("Test address");
        org.setContact("Test contact");
        org.setMembers(heroes);
        org = organizationDao.addOrg(org);
        
        // Organization 2
        
        Superpower superpower2 = new Superpower();
        superpower2.setName("Test name2");
        superpower2.setDescription("Test description2");
        superpower2 = superpowerDao.addPower(superpower2);
        
        List<Superpower> superpowers2 = new ArrayList<>();
        superpowers2.add(superpower2);
        
        List<Sighting> sightings2 = new ArrayList<>();        
        
        Hero hero2 = new Hero();
        hero2.setIsHero(false);
        hero2.setName("Test name2");
        hero2.setDescription("Test description2");
        hero2.setSuperpowers(superpowers2);
        hero2.setSightings(sightings2);
        hero2 = heroDao.addHero(hero2);
        
        Location location2 = new Location();
        location2.setName("Test name2");
        location2.setLatitude(1.3);
        location2.setLongitude(5.36);
        location2.setDescription("Test description2");
        location2.setAddress("Test address info2");
        location2 = locationDao.addLocation(location2);
        
        Date date2 = Date.valueOf("2020-03-31");  
        
        Sighting sighting2 = new Sighting();
        sighting2.setHeroId(hero2.getId());
        sighting2.setLocation(location2);
        sighting2.setDate(date2);
        sighting2 = sightingDao.addSighting(sighting2);
        
        sightings2.add(sighting2);
        
        hero2.setSightings(sightings2);
        
        heroDao.updateHero(hero2);
        
        List<Hero> heroes2 = new ArrayList<>();        
        heroes2.add(hero2);
        
        Organization org2 = new Organization();
        org2.setName("Test name2");
        org2.setIsHero(false);
        org2.setDescription("Test description2");
        org2.setAddress("Test address2");
        org2.setContact("Test contact2");
        org2.setMembers(heroes2);
        org2 = organizationDao.addOrg(org2);
        
        List<Organization> fromDao = organizationDao.getOrgsByHero(hero);
        assertTrue(fromDao.contains(org));
        assertFalse(fromDao.contains(org2));        
    }

    */
}
