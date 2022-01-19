/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.supersight.dao;

import com.sg.supersight.dao.SightingDaoDB.SightingMapper;
import com.sg.supersight.dao.SuperpowerDaoDB.SuperpowerMapper;
import com.sg.supersight.model.Hero;
import com.sg.supersight.model.Location;
import com.sg.supersight.model.Sighting;
import com.sg.supersight.model.Superpower;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Thomas
 */

@Repository
public class HeroDaoDB implements HeroDao{
    
    @Autowired
    JdbcTemplate jdbc;
    
    @Autowired
    SightingDaoDB sightingDaoDB;

    @Override
    public Hero getHeroById(int id) {
        try {
            final String GET_HERO_BY_ID = "SELECT * FROM Hero WHERE hero_id = ?";
            Hero hero = jdbc.queryForObject(GET_HERO_BY_ID, new HeroMapper(), id);
            hero.setSuperpowers(getPowersForHero(id));
            hero.setSightings(getSightingsForHero(id));
            return hero;
        } catch (DataAccessException ex) {
            return null;
        }
    }
    
    private List<Superpower> getPowersForHero(int id) {
        final String GET_POWERS_FOR_HERO = "SELECT s.* FROM Superpower s "
                + "JOIN HeroSuperpower hs ON hs.super_power_id = s.super_power_id WHERE hs.hero_id = ?";
        return jdbc.query(GET_POWERS_FOR_HERO, new SuperpowerMapper(), id);
        //                                          CREATE THIS
    }
    
    private List<Sighting> getSightingsForHero(int id) {
        final String GET_SIGHTINGS_FOR_HERO = "SELECT * FROM Sighting WHERE hero_id = ?";
        List<Sighting> sightings = jdbc.query(GET_SIGHTINGS_FOR_HERO, new SightingMapper(), id);
        sightingDaoDB.connectLocationsForSightings(sightings);
        //              CREATE THIS
        return sightings;
    }

    @Override
    public List<Hero> getAllHeroes() {
        final String GET_ALL_HEROES = "SELECT * FROM Hero";
        List<Hero> heroes = jdbc.query(GET_ALL_HEROES, new HeroMapper());
        connectPowersAndSightings(heroes);
        return heroes;
    }
    
    public void connectPowersAndSightings(List<Hero> heroes) {
        for (Hero hero : heroes) {
            hero.setSuperpowers(getPowersForHero(hero.getId()));
            hero.setSightings(getSightingsForHero(hero.getId()));
        }
    }

    @Override
    @Transactional
    public Hero addHero(Hero hero) {
        final String INSERT_HERO = "INSERT INTO Hero(is_hero, name, description) "
                + "VALUES(?,?,?)";
        jdbc.update(INSERT_HERO,
                hero.isIsHero(),
                hero.getName(),
                hero.getDescription());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        hero.setId(newId);
        insertHeroPower(hero);
       
        return hero;
    }
    
    private void insertHeroPower(Hero hero) {
        final String INSERT_HERO_POWER = "INSERT INTO "
                + "HeroSuperpower(hero_id, super_power_id) VALUES(?,?)";
        for(Superpower power : hero.getSuperpowers()) {
            jdbc.update(INSERT_HERO_POWER,
                    hero.getId(),
                    power.getId());
        }
    }
    
    private void insertSighting(Hero hero) {
        final String INSERT_SIGHTING = "INSERT INTO "
                + "Sighting(hero_id, location_id, date) VALUES(?,?,?)";
        for(Sighting sighting : hero.getSightings()) {
            jdbc.update(INSERT_SIGHTING,
                    hero.getId(),
                    sighting.getLocation().getId(),
                    sighting.getDate());
            int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
            sighting.setId(newId);
        }
    }

    @Override
    public void updateHero(Hero hero) {
        final String UPDATE_HERO = "UPDATE Hero SET is_hero = ?, name = ?, description = ? "
                + "WHERE hero_id = ?";
        jdbc.update(UPDATE_HERO,
                hero.isIsHero(),
                hero.getName(),
                hero.getDescription(),
                hero.getId());
        // These are added to make it easier to update.
        final String DELETE_HERO_SUPERPOWER = "DELETE FROM HeroSuperpower WHERE hero_id = ?";
        jdbc.update(DELETE_HERO_SUPERPOWER, hero.getId());
        insertHeroPower(hero);
        
        //final String DELETE_SIGHTING = "DELETE FROM Sighting WHERE hero_id = ?";
        //jdbc.update(DELETE_SIGHTING, hero.getId());
        //insertSighting(hero);
    }

    @Transactional
    @Override
    public void deleteHeroById(int id) {
        final String DELETE_HERO_SUPERPOWER = "DELETE FROM HeroSuperpower WHERE hero_id = ?";
        jdbc.update(DELETE_HERO_SUPERPOWER, id);
        
        final String DELETE_HERO_ORGANIZATION = "DELETE FROM HeroOrganization WHERE hero_id = ?";
        jdbc.update(DELETE_HERO_ORGANIZATION, id);
        
        final String DELETE_HERO_SIGHTING = "DELETE FROM Sighting WHERE hero_id = ?";
        jdbc.update(DELETE_HERO_SIGHTING, id);
        
        final String DELETE_HERO = "DELETE FROM Hero WHERE hero_id = ?";
        jdbc.update(DELETE_HERO, id);
    }

    @Override
    public List<Hero> getHeroesByPower(Superpower power) {
        final String GET_HEROES_BY_SUPERPOWER = "SELECT h.* FROM Hero h JOIN "
                + "HeroSuperpower hs ON hs.hero_id = h.hero_id WHERE hs.super_power_id = ?";
        List<Hero> heroes = jdbc.query(GET_HEROES_BY_SUPERPOWER, new HeroMapper(),
                power.getId());
        connectPowersAndSightings(heroes);
        return heroes;
    }

    @Override
    public Hero getHeroBySighting(Sighting sighting) {
        final String GET_HEROES_BY_SIGHTING = "SELECT h.* FROM Hero h JOIN "
                + "Sighting s ON s.hero_id = h.hero_id WHERE s.sight_id = ?";
        Hero hero = jdbc.queryForObject(GET_HEROES_BY_SIGHTING, new HeroMapper(),
                sighting.getId());
        hero.setSuperpowers(getPowersForHero(hero.getId()));
        hero.setSightings(getSightingsForHero(hero.getId()));
        return hero;
        
    }

    @Override
    public List<Hero> getHeroesByLocation(Location location) {
        final String GET_HEROES_FOR_LOCATION = "SELECT h.* FROM Hero h "
                + "JOIN Sighting s ON s.hero_id = h.hero_id "
                + "WHERE s.location_id = ?";
        List<Hero> heroes = jdbc.query(GET_HEROES_FOR_LOCATION, new HeroMapper(),
                location.getId());
        connectPowersAndSightings(heroes);
        return heroes;
    }
    
    public static final class HeroMapper implements RowMapper<Hero> {
        
        @Override
        public Hero mapRow(ResultSet rs, int index) throws SQLException {
            Hero hero = new Hero();
            hero.setId(rs.getInt("hero_id"));
            hero.setIsHero(rs.getBoolean("is_hero"));
            hero.setName(rs.getString("name"));
            hero.setDescription(rs.getString("description"));
            return hero;
        }
    }
}
