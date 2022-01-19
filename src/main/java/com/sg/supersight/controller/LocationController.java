/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.supersight.controller;

import com.sg.supersight.model.Location;
import com.sg.supersight.service.LocationService;
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
public class LocationController {

    @Autowired
    LocationService locationService;
    
    String latError = null;
    String longError = null;
    
    @GetMapping("locations")
    public String displayLocations(Model model) {
        List<Location> locations = locationService.getAllLocations();
        model.addAttribute("locations", locations);
        return "locations";
    }
    
    @PostMapping("addLocation")
    public String addLocation(HttpServletRequest request) {
        
        latError = null;
        longError = null;
        
        String name = request.getParameter("locationName");
        String latString = request.getParameter("latitude");
        String longString = request.getParameter("longitude");
        String description = request.getParameter("locationDescription");
        String address = request.getParameter("address");
        
        double latitude = Double.parseDouble(request.getParameter("latitude"));
        double longitude = Double.parseDouble(request.getParameter("longitude"));
        
        Location location = locationService.createLocation(name, latitude, longitude, description, address);
        location = locationService.addLocation(location);
        
        return "redirect:/locations";
        
    }
    
    @GetMapping("deleteLocation")
    public String deleteLocation(Integer id) {
        locationService.deleteLocationById(id);
        return "redirect:/locations";
    }
    
    @GetMapping("editLocation")
    public String editLocation(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Location location = locationService.getLocationById(id);
        
        model.addAttribute("location", location);
        return "editLocation";
    }
    
    @PostMapping("editLocation")
    public String editLocation(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Location location = locationService.getLocationById(id);
        double latitude = Double.parseDouble(request.getParameter("latitude"));
        double longitude = Double.parseDouble(request.getParameter("longitude"));
        
        location.setName(request.getParameter("locationName"));
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        location.setDescription(request.getParameter("locationDescription"));
        location.setAddress(request.getParameter("locationAddress"));
        
        locationService.updateLocation(location);
        
        return "redirect:/locations";
        
    }
    
    
    @GetMapping("viewLocation")
    public String viewLocation(Integer id, Model model) {
        Location location = locationService.getLocationById(id);
        model.addAttribute("location", location);
        return "viewLocation";
    }
    
    private boolean isValidLatitude(String latitude){
        try{
            double value = Double.parseDouble(latitude);
            if(value<-90 || value>90){
                return false;
            }
            return true;
        } catch (Exception e){
            return false;
        }
    }
    
    public boolean isValidLongitude(String longitude){
        try{
            double value = Double.parseDouble(longitude);
            if(value<-180 || value>180){
                return false;
            }
            return true;
        } catch (Exception e){
            return false;
        }
    }     
}
