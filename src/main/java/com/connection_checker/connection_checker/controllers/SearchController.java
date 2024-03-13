package com.connection_checker.connection_checker.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.connection_checker.connection_checker.dao.ContactRepository;
import com.connection_checker.connection_checker.dao.UserRepository;
import com.connection_checker.connection_checker.entities.Contact;
import com.connection_checker.connection_checker.entities.User;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
public class SearchController {
    
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ContactRepository contactRepository;

    @GetMapping("/search/{query}/{filter}")
    public ResponseEntity<?> search(@PathVariable("query") String query,@PathVariable("filter") String filter, Principal principal) {
        System.out.println(query);
        User user=this.userRepository.getUserByUserName(principal.getName());
        List<Contact> contact= new ArrayList<>();
        if(filter.equals("Name")){ contact= this.contactRepository.findByNameContainingAndUser(query, user);}
        else if(filter.equals("SubCompany")){ contact= this.contactRepository.findBySubcompanyContainingAndUser(query, user);}
        else if(filter.equals("Email")){ contact= this.contactRepository.findByEmailContainingAndUser(query, user);}
        else if(filter.equals("Company")){ contact= this.contactRepository.findByCompanyAndUser(user.getId(),query);}
        else if(filter.equals("Projects")){ contact= this.contactRepository.findByProjectsAndUser(user.getId(),query);}
        else if(filter.equals("TechStack")){ contact= this.contactRepository.findByTechStackAndUser(user.getId(),query);}
        return ResponseEntity.ok(contact);
    }
    

}
