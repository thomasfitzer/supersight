/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.supersight.dao;

import com.sg.supersight.model.Hero;
import com.sg.supersight.model.Location;
import com.sg.supersight.model.Sighting;
import com.sg.supersight.model.Superpower;
import java.util.List;

/**
 *
 * @author Thomas
 */
public interface HeroDao {
    
    Hero getHeroById(int id);
    List<Hero> getAllHeroes();
    Hero addHero(Hero hero);
    void updateHero(Hero hero);
    void deleteHeroById(int id);
    
    List<Hero> getHeroesByPower(Superpower power);
    Hero getHeroBySighting(Sighting sighting);
    List<Hero> getHeroesByLocation(Location location);
}
