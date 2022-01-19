/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.supersight.dao;

import com.sg.supersight.model.Location;
import com.sg.supersight.model.Sighting;
import java.util.List;

/**
 *
 * @author Thomas
 */
public interface SightingDao {
    
    Sighting getSightById(int id);
    List<Sighting> getAllSightings();
    Sighting addSighting(Sighting sighting);
    void updateSighting(Sighting sighting);
    void deleteSightingById(int id);
    
    List<Sighting> getSightingsByLocation(Location location);
    
}
