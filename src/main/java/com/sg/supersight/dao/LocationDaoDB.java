/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.supersight.dao;

import com.sg.supersight.model.Hero;
import com.sg.supersight.model.Location;
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
public class LocationDaoDB implements LocationDao{
    
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Location getLocationById(int id) {
        try {
            final String GET_LOCATION_BY_ID = "SELECT * FROM Location WHERE location_id = ?";
            return jdbc.queryForObject(GET_LOCATION_BY_ID, new LocationMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Location> getAllLocations() {
        final String GET_ALL_LOCATIONS = "SELECT * FROM Location";
        return jdbc.query(GET_ALL_LOCATIONS, new LocationMapper());
    }

    @Override
    @Transactional
    public Location addLocation(Location location) {
        final String INSERT_LOCATION = "INSERT INTO Location(name, latitude, longitude, "
                + "description, address) VALUES(?,?,?,?,?)";
        jdbc.update(INSERT_LOCATION,
                location.getName(),
                location.getLatitude(),
                location.getLongitude(),
                location.getDescription(),
                location.getAddress());
        
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        location.setId(newId);
        return location;
    }

    @Override
    public void updateLocation(Location location) {
        final String UPDATE_LOCATION = "UPDATE Location SET name = ?, latitude = ?, "
                + "longitude = ?, description = ?, address = ? WHERE location_id = ?";
        jdbc.update(UPDATE_LOCATION,
                location.getName(),
                location.getLatitude(),
                location.getLongitude(),
                location.getDescription(),
                location.getAddress(),
                location.getId());
    }

    @Override
    @Transactional
    public void deleteLocationById(int id) {
        final String DELETE_SIGHTING = "DELETE FROM Sighting WHERE location_id = ?";
        jdbc.update(DELETE_SIGHTING, id);
        
        final String DELETE_LOCATION = "DELETE FROM Location WHERE location_id = ?";
        jdbc.update(DELETE_LOCATION, id);
    }

    @Override
    public List<Location> getLocationsByHero(Hero hero) {
        final String GET_LOCATIONS_BY_HERO = "SELECT l.* FROM Location l "
                + "JOIN Sighting s ON s.location_id = l.location_id "
                + "WHERE s.hero_id = ?";
        List<Location> locations = jdbc.query(GET_LOCATIONS_BY_HERO, new LocationMapper(), 
        hero.getId());
        return locations;
    }
    
   
    
    public static final class LocationMapper implements RowMapper<Location> {
        
        @Override
        public Location mapRow(ResultSet rs, int index) throws SQLException {
            Location location = new Location();
            location.setId(rs.getInt("location_id"));
            location.setName(rs.getString("name"));
            location.setLatitude(rs.getDouble("latitude"));
            location.setLongitude(rs.getDouble("longitude"));
            location.setDescription(rs.getString("description"));
            location.setAddress(rs.getString("address"));
            return location;
        }
    }
    
}
