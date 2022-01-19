/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.supersight.controller;

import com.sg.supersight.model.Hero;
import com.sg.supersight.model.Organization;
import com.sg.supersight.service.HeroService;
import com.sg.supersight.service.OrganizationService;
import java.util.ArrayList;
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
public class OrganizationController {
    
    @Autowired
    OrganizationService organizationService;
    
    @Autowired
    HeroService heroService;
    
    @GetMapping("organizations")
    public String displayOrganizations(Model model) {
        List<Organization> organizations = organizationService.getAllOrgs();
        List<Hero> heroes = heroService.getAllHeroes();
        model.addAttribute("organizations", organizations);
        model.addAttribute("heroes", heroes);
        return "organizations";
    }
    
    @GetMapping("viewOrg")
    public String displayDetailsOrganization(Integer id, Model model){
        
        Organization organization = organizationService.getOrgById(id);
        model.addAttribute("organization", organization);
        
        return "viewOrg";
    }
    
    @PostMapping("addOrg")
    public String addOrganization(Organization organization, HttpServletRequest request) {
        String[] heroIds = request.getParameterValues("heroId");
        String name = request.getParameter("organizationName");
        boolean isHero = Boolean.parseBoolean(request.getParameter("isHero"));
        String organizationDescription = request.getParameter("organizationDescription");
        String address = request.getParameter("address");
        String contact = request.getParameter("contact");
        
        List<Hero> heroes = new ArrayList<>();
        for(String heroId : heroIds) {
            heroes.add(heroService.getHeroById(Integer.parseInt(heroId)));
        }
        
        organization.setMembers(heroes);
        organization = organizationService.createOrganization(name, isHero, organizationDescription, address, contact, heroes);
        organizationService.addOrg(organization);
        
        return "redirect:/organizations";
        
    }
    
    @GetMapping("deleteOrg")
    public String deleteOrganization(Integer id) {
        organizationService.deleteOrganizationById(id);
        return "redirect:/organizations";
    }
    
    @GetMapping("editOrg")
    public String editOrganization(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Organization organization = organizationService.getOrgById(id);
        List<Hero> heroes = heroService.getAllHeroes();
        model.addAttribute("organization", organization);
        model.addAttribute("heroes", heroes);
        return "editOrg";
        
    }
    
    @PostMapping("editOrg")
    public String editOrganization(HttpServletRequest request, Organization organization) {
   
        
        String[] heroIds = request.getParameterValues("heroId");
        
        organization.setName(request.getParameter("organizationName"));
        organization.setIsHero(Boolean.parseBoolean(request.getParameter("isHero")));
        organization.setDescription(request.getParameter("organizationDescription"));
        organization.setAddress(request.getParameter("organizationAddress"));
        organization.setContact(request.getParameter("organizationContact"));
        List<Hero> heroes = new ArrayList<>();
        for(String heroId : heroIds) {
            heroes.add(heroService.getHeroById(Integer.parseInt(heroId)));
        }
        organization.setMembers(heroes);
        
        organizationService.updateOrg(organization);
        
        return "redirect:/organizations";
    }
    
}
