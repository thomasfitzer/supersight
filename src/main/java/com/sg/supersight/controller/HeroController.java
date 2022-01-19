/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.supersight.controller;

import com.sg.supersight.model.Hero;
import com.sg.supersight.model.Organization;
import com.sg.supersight.model.Superpower;
import com.sg.supersight.service.HeroService;
import com.sg.supersight.service.OrganizationService;
import com.sg.supersight.service.SuperpowerService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Thomas
 */

@Controller
public class HeroController {
    
    Set<ConstraintViolation<Hero>> violations = new HashSet<>();

   @Autowired
   HeroService heroService;

   @Autowired
   SuperpowerService superpowerService;
   
   @Autowired
   OrganizationService organizationService;
   

   
    @GetMapping("heroes")
    public String displayHeroes(Model model) {
        List<Hero> heroes = heroService.getAllHeroes();
        List<Superpower> superpowers = superpowerService.getAllSuperpowers();
        List<Organization> organizations = organizationService.getAllOrgs();
        model.addAttribute("heroes", heroes);
        model.addAttribute("superpowers", superpowers);
        model.addAttribute("organizations", organizations);
        model.addAttribute("errors", violations);
    
        return "heroes";
    }  
    
    @PostMapping("addHero")
    public String addHero(HttpServletRequest request) {
        String name = request.getParameter("heroName");
        boolean isHero = Boolean.parseBoolean(request.getParameter("isHero"));
        String heroDescription = request.getParameter("heroDescription");
        String[] superpowerIds = request.getParameterValues("superpowerId");
        
        List<Superpower> superpowers = new ArrayList<>();
        if(superpowerIds != null) {
            for(String superpowerId : superpowerIds) {
                superpowers.add(superpowerService.getSuperpowerById(Integer.parseInt(superpowerId)));
            }
        }

        Hero hero = heroService.createHero(name, isHero, heroDescription, superpowers);
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(hero);
        if(violations.isEmpty()) {
            heroService.addHero(hero);
        }
       
        
        
        return "redirect:/heroes";
    }
    
    @GetMapping("deleteHero")
    public String deleteHero(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        heroService.deleteHeroById(id);
        
        return "redirect:/heroes";
    }
    
    @GetMapping("editHero")
    public String editHero(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Hero hero = heroService.getHeroById(id);
        List<Superpower> superpowers = superpowerService.getAllSuperpowers();
        
        model.addAttribute("hero", hero);
        model.addAttribute("superpowers", superpowers);
        return "editHero";
    }
    
    @PostMapping("editHero")
    public String editHero(HttpServletRequest request, Hero hero, Model model) {
        
        String[] superpowerIds = request.getParameterValues("superpowerId");
        
        hero.setName(request.getParameter("heroName"));
        hero.setIsHero(Boolean.parseBoolean(request.getParameter("isHero")));
        hero.setDescription(request.getParameter("heroDescription"));
        List<Superpower> superpowers = new ArrayList<>();
        for(String superpowerId : superpowerIds) {
            superpowers.add(superpowerService.getSuperpowerById(Integer.parseInt(superpowerId)));
        }
        hero.setSuperpowers(superpowers);
        

        
        heroService.updateHero(hero);
        
        return "redirect:/heroes";
        
    }
    
    @GetMapping("viewHero")
    public String viewHero(Integer id, Model model) {
        Hero hero = heroService.getHeroById(id);
        //List<Superpower> superpowers = superpowerService.getAllSuperpowers();
        //List<Organization> organization = heroService.getOrgsByHero(hero);
        model.addAttribute("hero", hero);
        //model.addAttribute("superpowers", superpowers);
        //model.addAttribute("organization", organization);
        return "viewHero";
    }

}
