/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.supersight.dao;

import com.sg.supersight.model.Location;
import com.sg.supersight.model.Sighting;
import java.sql.Date;
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
public class SightingDaoDB implements SightingDao{
    
    @Autowired
    JdbcTemplate jdbc;
    

    @Override
    public Sighting getSightById(int id) {
        try {
            final String GET_SIGHTING_BY_ID = "SELECT * FROM Sighting WHERE sight_id = ?";
            Sighting sighting = jdbc.queryForObject(GET_SIGHTING_BY_ID, new SightingMapper(), id);
            sighting.setLocation(getLocationForSighting(id));
            return sighting;
        } catch (DataAccessException ex) {
            return null;
        }
    }
    
    private Location getLocationForSighting(int id) {
        final String GET_LOCATION_FOR_SIGHTING = "SELECT l.* FROM Location l "
                + "JOIN Sighting s ON s.location_id = l.location_id WHERE s.sight_id = ?";
        return jdbc.queryForObject(GET_LOCATION_FOR_SIGHTING, new LocationDaoDB.LocationMapper(), id);
    }

    @Override
    public List<Sighting> getAllSightings() {
        final String GET_ALL_SIGHTING = "SELECT * FROM Sighting";
        List<Sighting> sightings = jdbc.query(GET_ALL_SIGHTING, new SightingMapper());
        connectLocationsForSightings(sightings);
        return sightings;
    }
    
    void connectLocationsForSightings(List<Sighting> sightings) {
        for (Sighting sighting : sightings) {
            sighting.setLocation(getLocationForSighting(sighting.getId()));
        }
    }

    @Override
    @Transactional
    public Sighting addSighting(Sighting sighting) {
        final String ADD_SIGHTING = "INSERT INTO Sighting(hero_id, location_id, date) "
                + "VALUES(?,?,?)";
        jdbc.update(ADD_SIGHTING,
                sighting.getHeroId(),
                sighting.getLocation().getId(),
                sighting.getDate());
        
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        sighting.setId(newId);
        return sighting;
    }

    @Override
    public void updateSighting(Sighting sighting) {
        final String UPDATE_SIGHTING = "UPDATE Sighting SET hero_id = ?, location_id = ?, date = ? "
                + "WHERE sight_id = ?";
        jdbc.update(UPDATE_SIGHTING,
                sighting.getHeroId(),
                sighting.getLocation().getId(),
                sighting.getDate(),
                sighting.getId());
        
    }

    @Override
    @Transactional
    public void deleteSightingById(int id) {
        final String DELETE_SIGHTING = "DELETE FROM Sighting WHERE sight_id = ?";
        jdbc.update(DELETE_SIGHTING, id);
    }

    @Override
    public List<Sighting> getSightingsByLocation(Location location) {
        final String GET_SIGHTINGS_BY_LOCATION = "SELECT * FROM Sighting WHERE location_id = ?";
        List<Sighting> sighting = jdbc.query(GET_SIGHTINGS_BY_LOCATION, new SightingMapper(),
                location.getId());
        connectLocationsForSightings(sighting);
        return sighting;
    }
    
    public static final class SightingMapper implements RowMapper<Sighting> {
        
        @Override
        public Sighting mapRow(ResultSet rs, int index) throws SQLException {
            Sighting sighting = new Sighting();
            sighting.setId(rs.getInt("sight_id"));
            sighting.setHeroId(rs.getInt("hero_id"));
            // FIGURE OUT LATER sighting.setLocation(rs.getObject("location_id"));
            sighting.setDate(Date.valueOf(rs.getDate("date").toString()));
            return sighting;
        }
    }
    
}
