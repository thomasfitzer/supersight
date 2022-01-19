/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.supersight.dao;

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
public class SuperpowerDaoDB implements SuperpowerDao{
    
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Superpower getPowerById(int id) {
        try {
            final String GET_POWER_BY_ID = "SELECT * FROM Superpower WHERE super_power_id = ?";
            return jdbc.queryForObject(GET_POWER_BY_ID, new SuperpowerMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Superpower> getAllPowers() {
        final String GET_ALL_POWERS = "SELECT * FROM Superpower";
        return jdbc.query(GET_ALL_POWERS, new SuperpowerMapper());
    }

    @Override
    @Transactional
    public Superpower addPower(Superpower power) {
        final String ADD_POWER = "INSERT INTO Superpower(name, description) "
                + "VALUES(?,?)";
        jdbc.update(ADD_POWER,
                power.getName(),
                power.getDescription());
        
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        power.setId(newId);
        return power;
    }

    @Override
    public void updatePower(Superpower power) {
        final String UPDATE_POWER = "UPDATE Superpower SET name = ?, description = ? "
                + "WHERE super_power_id = ?";
        jdbc.update(UPDATE_POWER,
                power.getName(),
                power.getDescription(),
                power.getId());
    }

    @Override
    @Transactional
    public void deletePowerById(int id) {
        final String DELETE_HERO_POWER = "DELETE FROM HeroSuperpower WHERE super_power_id = ?";
        jdbc.update(DELETE_HERO_POWER, id);
        
        final String DELETE_POWER = "DELETE FROM Superpower WHERE super_power_id = ?";
        jdbc.update(DELETE_POWER, id);
    }
    
    public static final class SuperpowerMapper implements RowMapper<Superpower> {
        
        @Override
        public Superpower mapRow(ResultSet rs, int index) throws SQLException {
            Superpower power = new Superpower();
            power.setId(rs.getInt("super_power_id"));
            power.setName(rs.getString("name"));
            power.setDescription(rs.getString("description"));
            return power;
        }
    }
    
    
    
}
