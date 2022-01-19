/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.supersight.controller;

import com.sg.supersight.model.Hero;
import com.sg.supersight.model.Location;
import com.sg.supersight.model.Sighting;
import com.sg.supersight.service.HeroService;
import com.sg.supersight.service.LocationService;
import com.sg.supersight.service.SightingService;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Thomas
 */
@Controller
public class SightingController {
    
    @Autowired
    SightingService sightingService;
    
    @Autowired
    HeroService heroService;
    
    @Autowired
    LocationService locationService;
    
    @GetMapping("sightings")
    public String displaySightings(Model model) {
        List<Sighting> sightings = sightingService.getAllSightings();
        HashMap<Sighting,Hero> heroSightings = sightingService.mapHeroSightings(sightings);
        List<Hero> heroes = heroService.getAllHeroes();
        List<Location> locations = locationService.getAllLocations();
        model.addAttribute("sightings", sightings);
        model.addAttribute("heroSightings", heroSightings);
        model.addAttribute("heroes", heroes);
        model.addAttribute("locations", locations);
        return "sightings";
    }

    @GetMapping("viewSighting")
    public String viewSighting(Integer id, Model model) {
        
        Sighting sighting = sightingService.getSightById(id);
        Hero hero = heroService.getHeroById(sighting.getHeroId());
        
  
        model.addAttribute("sighting", sighting);
        model.addAttribute("hero", hero);
        
        
        
        
        return "viewSighting";
    }
    
    @PostMapping("addSighting")
    public String addSighting(HttpServletRequest request) {
        String dateString = request.getParameter("date");
        int heroId = Integer.parseInt(request.getParameter("heroId"));
        int locationId = Integer.parseInt(request.getParameter("locationId"));
        
        Date date = Date.valueOf(dateString);
        Hero hero = heroService.getHeroById(heroId);
        Location location = locationService.getLocationById(locationId);
        
        Sighting sighting = sightingService.createSighting(hero, location, date);
        sighting = sightingService.addSighting(sighting);
        
        return "redirect:/sightings";
    }
    
    @GetMapping("deleteSighting")
    public String deleteSighting(Integer id) {
        sightingService.deleteSightingById(id);
        return "redirect:/sightings";
    }
    
    @GetMapping("editSighting")
    public String editSighting(Integer id, Model model) {
        Sighting sighting = sightingService.getSightById(id);
        List<Hero> heroes = heroService.getAllHeroes();
        List<Location> locations = locationService.getAllLocations();
        model.addAttribute("sighting", sighting);
        model.addAttribute("heroes", heroes);
        model.addAttribute("locations", locations);
        
        return "editSighting";
    }
    
    @PostMapping("editSighting")
    public String editSighting(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Sighting sighting = sightingService.getSightById(id);
        String dateString = request.getParameter("date");
        Date date = Date.valueOf(dateString);
        System.out.println(date);
        int locationId = Integer.parseInt(request.getParameter("locationId"));
        Location location = locationService.getLocationById(locationId);
        int heroId = Integer.parseInt(request.getParameter("heroId"));
        
        sighting.setDate(date);
        sighting.setLocation(location);
        sighting.setHeroId(heroId);
        
        sightingService.updateSighting(sighting);
        return "redirect:/sightings";
    } 
    
    
}
