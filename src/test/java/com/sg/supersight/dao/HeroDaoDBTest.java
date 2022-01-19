/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.supersight.dao;


import com.sg.supersight.model.Hero;
import com.sg.supersight.model.Organization;
import com.sg.supersight.model.Sighting;
import com.sg.supersight.model.Superpower;
import com.sg.supersight.model.Location;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 *
 * @author Thomas
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class HeroDaoDBTest {
    
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
    
    public HeroDaoDBTest() {
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
     * Test of getHeroById method, of class HeroDaoDB.
     */
    @Test
    public void testAddGetHero() {
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
        
        Date date = Date.valueOf("2021-11-13");  
        
        Sighting sighting = new Sighting();
        sighting.setHeroId(hero.getId());
        sighting.setLocation(location);
        sighting.setDate(date);
        sighting = sightingDao.addSighting(sighting);
        
        sightings.add(sighting);
        
        hero.setSightings(sightings);
        
        heroDao.updateHero(hero);

        Hero fromDao = heroDao.getHeroById(hero.getId());
        
        assertNotNull(fromDao);
        
    }

    /**
     * Test of getAllHeroes method, of class HeroDaoDB.
     */
    @Test
    public void testGetAllHeroes() {
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
        
        sightings.add(sighting);
        
        hero.setSightings(sightings);
        
        heroDao.updateHero(hero);
        
        
        // HERO 2
        
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
        
        Date date2 = Date.valueOf("2021-11-14");  
        
        Sighting sighting2 = new Sighting();
        sighting2.setHeroId(hero2.getId());
        sighting2.setLocation(location2);
        sighting2.setDate(date2);
        sighting2 = sightingDao.addSighting(sighting2);
        
        sightings2.add(sighting2);
        
        hero2.setSightings(sightings2);
        
        heroDao.updateHero(hero2);
        
        
        List<Hero> heroes = heroDao.getAllHeroes();
        assertEquals(2, heroes.size());
        assertTrue(heroes.contains(hero));
        assertTrue(heroes.contains(hero2));        
    }


    /**
     * Test of updateHero method, of class HeroDaoDB.
     */
    @Test
    public void testUpdateHero() {
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
        
        sightings.add(sighting);
        
        hero.setSightings(sightings);
        
        heroDao.updateHero(hero);

        Hero fromDao = heroDao.getHeroById(hero.getId());
        
        assertEquals(hero,fromDao);
        
        Date date2 = Date.valueOf("2021-11-15");
        
        Sighting sighting2 = new Sighting();
        sighting2.setHeroId(hero.getId());
        sighting2.setLocation(location);
        sighting2.setDate(date2);
        sighting2 = sightingDao.addSighting(sighting2);
        
        sightings.add(sighting2);
        
        hero.setSightings(sightings);
        
        heroDao.updateHero(hero);
        
        assertNotEquals(hero,fromDao);
        
        fromDao = heroDao.getHeroById(hero.getId());
        
        assertEquals(hero,fromDao);        
    }

    /**
     * Test of deleteHeroById method, of class HeroDaoDB.
     */
    @Test
    public void testDeleteHeroById() {
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
        
        sightings.add(sighting);
        
        hero.setSightings(sightings);
        
        heroDao.updateHero(hero);

        Hero fromDao = heroDao.getHeroById(hero.getId());       
        assertEquals(hero,fromDao);
        
        heroDao.deleteHeroById(hero.getId());
        
        fromDao = heroDao.getHeroById(hero.getId());
        assertNull(fromDao);        
    }

    /**
     * Test of getHeroesByPower method, of class HeroDaoDB.
     */
    @Test
    public void testGetHeroesByPower() {
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
        
        List<Sighting> sightings3 = new ArrayList<>();        
        
        Hero hero3 = new Hero();
        hero3.setIsHero(false);
        hero3.setName("Test name3");
        hero3.setDescription("Test description3");
        hero3.setSuperpowers(superpowers);
        hero3.setSightings(sightings3);
        hero3 = heroDao.addHero(hero3);
        
        List<Hero> heroes = heroDao.getHeroesByPower(superpower);
        assertTrue(heroes.contains(hero));
        assertFalse(heroes.contains(hero2));
        assertTrue(heroes.contains(hero3));        
    }

    /**
     * Test of getHeroBySighting method, of class HeroDaoDB.
     */
    @Test
    public void testGetHeroBySighting() {
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
        
        Date date = Date.valueOf("2021-11-14");  
        
        Sighting sighting = new Sighting();
        sighting.setHeroId(hero.getId());
        sighting.setLocation(location);
        sighting.setDate(date);
        sighting = sightingDao.addSighting(sighting);
        
        sightings.add(sighting);
        
        hero.setSightings(sightings);
        
        heroDao.updateHero(hero);
        
        
        // HERO 2
        
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
        
        Date date2 = Date.valueOf("2021-11-13");  
        
        Sighting sighting2 = new Sighting();
        sighting2.setHeroId(hero2.getId());
        sighting2.setLocation(location2);
        sighting2.setDate(date2);
        sighting2 = sightingDao.addSighting(sighting2);
        
        sightings2.add(sighting2);
        
        hero2.setSightings(sightings2);
        
        heroDao.updateHero(hero2);

        Hero fromDao = heroDao.getHeroBySighting(sighting);
        assertEquals(hero,fromDao);
        assertNotEquals(hero2,fromDao);        
    }

    /**
     * Test of getHeroesByLocation method, of class HeroDaoDB.
     */
    @Test
    public void testGetHeroesByLocation() {
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
        
        sightings.add(sighting);
        
        hero.setSightings(sightings);
        
        heroDao.updateHero(hero);
        
        
        // HERO 2
        
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
        
        Date date2 = Date.valueOf("2021-11-14");  
        
        Sighting sighting2 = new Sighting();
        sighting2.setHeroId(hero2.getId());
        sighting2.setLocation(location2);
        sighting2.setDate(date2);
        sighting2 = sightingDao.addSighting(sighting2);
        
        sightings2.add(sighting2);
        
        hero2.setSightings(sightings2);
        
        heroDao.updateHero(hero2);
        
        List<Hero> heroes = heroDao.getHeroesByLocation(location);

        assertTrue(heroes.contains(hero));
        assertFalse(heroes.contains(hero2));        
    }
    
}
