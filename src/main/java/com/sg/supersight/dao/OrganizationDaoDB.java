/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.supersight.dao;

import com.sg.supersight.dao.HeroDaoDB.HeroMapper;
import com.sg.supersight.model.Hero;
import com.sg.supersight.model.Organization;
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
public class OrganizationDaoDB implements OrganizationDao{
    
    @Autowired
    JdbcTemplate jdbc;
    
    @Autowired
    HeroDaoDB heroDaoDB;

    @Override
    public Organization getOrgById(int id) {
        try {
            final String GET_ORG_BY_ID = "SELECT * FROM Organization WHERE org_id = ?";
            Organization org = jdbc.queryForObject(GET_ORG_BY_ID, new OrgMapper(), id);
            org.setMembers(getHeroesForOrg(id));
            return org;
        } catch (DataAccessException ex) {
            return null;
        }
    }
    
    private List<Hero> getHeroesForOrg(int id) {
        final String GET_HEROES_FOR_ORG = "SELECT h.* FROM Hero h "
                + "JOIN HeroOrganization ho ON h.hero_id = ho.hero_id WHERE ho.org_id = ?";
        List<Hero> heroes = jdbc.query(GET_HEROES_FOR_ORG, new HeroMapper(), id);
        heroDaoDB.connectPowersAndSightings(heroes);
        return heroes;
    }

    @Override
    public List<Organization> getAllOrgs() {
        final String GET_ALL_ORGS = "SELECT * FROM Organization";
        List<Organization> orgs = jdbc.query(GET_ALL_ORGS, new OrgMapper());
        connectHeroes(orgs);
        return orgs;
        
    }
    
    private void connectHeroes(List<Organization> orgs) {
        for(Organization org : orgs) {
            org.setMembers(getHeroesForOrg(org.getId()));
        }
    }
 
    @Override
    @Transactional
    public Organization addOrg(Organization org) {
        final String ADD_ORG = "INSERT INTO Organization(name, is_hero, description, address, contact) "
                + "VALUES(?,?,?,?,?)";
        jdbc.update(ADD_ORG,
                        org.getName(),
                        org.isIsHero(),
                        org.getDescription(),
                        org.getAddress(),
                        org.getContact());
         int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
         org.setId(newId);
         insertHeroOrg(org);
         return org;
         
                
    }
    
    private void insertHeroOrg(Organization org) {
        final String INSERT_HERO_ORG = "INSERT INTO HeroOrganization(hero_id, org_id) "
                + "VALUES(?,?)";
        for(Hero hero : org.getMembers()) {
            jdbc.update(INSERT_HERO_ORG,
                    hero.getId(),
                    org.getId());
        }
    }

    @Override
    public void updateOrg(Organization org) {
        final String UPDATE_ORG = "UPDATE Organization SET name = ?, is_hero = ?, "
                + "description = ?, address = ?, contact = ? WHERE org_id = ?";
        jdbc.update(UPDATE_ORG,
                org.getName(),
                org.isIsHero(),
                org.getDescription(),
                org.getAddress(),
                org.getContact(),
                org.getId());
        
        final String DELETE_HERO_ORG = "DELETE FROM HeroOrganization WHERE org_id = ?";
        jdbc.update(DELETE_HERO_ORG, org.getId());
        insertHeroOrg(org);
    }

    @Override
    @Transactional
    public void deleteOrgById(int id) {
        final String DELETE_HERO_ORG = "DELETE FROM HeroOrganization WHERE org_id = ?";
        jdbc.update(DELETE_HERO_ORG, id);
        
        final String DELETE_ORG = "DELETE FROM Organization WHERE org_id = ?";
        jdbc.update(DELETE_ORG, id);
    }

    @Override
    public List<Organization> getOrgsByHero(Hero hero) {
        final String GET_ORGS_BY_HERO = "SELECT o.* FROM Organization o JOIN "
                + "HeroOrganization ho ON ho.org_id = o.org_id WHERE ho.hero_id = ?";
        List<Organization> orgs = jdbc.query(GET_ORGS_BY_HERO, new OrgMapper(), hero.getId());
        connectHeroes(orgs);
        return orgs;
                
    }
    
    public static final class OrgMapper implements RowMapper<Organization> {
        
        @Override
        public Organization mapRow(ResultSet rs, int index) throws SQLException {
            Organization org = new Organization();
            org.setId(rs.getInt("org_id"));
            org.setName(rs.getString("name"));
            org.setIsHero(rs.getBoolean("is_hero"));
            org.setDescription(rs.getString("description"));
            org.setAddress(rs.getString("address"));
            org.setContact(rs.getString("contact"));
            return org;
        }
    }
    
}
