/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.supersight.controller;

import com.sg.supersight.model.Hero;
import com.sg.supersight.model.Superpower;
import com.sg.supersight.service.HeroService;
import com.sg.supersight.service.SuperpowerService;
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
public class SuperpowerController {
    
    @Autowired
    SuperpowerService superpowerService;
    
    @Autowired
    HeroService heroService;
    
    @GetMapping("superpowers")
    public String displaySuperpowers(Model model) {
        List<Superpower> superpowers = superpowerService.getAllSuperpowers();
        model.addAttribute("superpowers", superpowers);
        return "superpowers";
    }
    
    @GetMapping("viewPower")
    public String viewPower(Integer id, Model model) {
        Superpower superpower = superpowerService.getSuperpowerById(id);
       
        model.addAttribute("superpower", superpower);
        return "viewPower";
    }
    
    @PostMapping("addSuperpower")
    public String addSuperpower(HttpServletRequest request) {
        String superpowerName = request.getParameter("superpowerName");
        String superpowerDescription = request.getParameter("superpowerDescription");
        
        Superpower superpower = new Superpower();
        superpower.setName(superpowerName);
        superpower.setDescription(superpowerDescription);
        
        superpowerService.addSuperpower(superpower);
        return "redirect:/superpowers";
    }
    
    @GetMapping("deletePower")
    public String deleteSuperpower(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        superpowerService.deleteSuperpowerById(id);
        return "redirect:/superpowers";
    }
    
    @GetMapping("editPower")
    public String editSuperpower(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Superpower superpower = superpowerService.getSuperpowerById(id);
        
        model.addAttribute("superpower", superpower);
        return "editPower";
    }
    
    @PostMapping("editPower")
    public String editSuperpower(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Superpower superpower = superpowerService.getSuperpowerById(id);
        
        superpower.setName(request.getParameter("superpowerName"));
        superpower.setDescription(request.getParameter("superpowerDescription"));
        
        superpowerService.updateSuperpower(superpower);
        
        return "redirect:/superpowers";
    }
    
    
    
    
}
