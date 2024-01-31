package com.connection_checker.connection_checker.controllers;

import java.security.Principal;
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

    @GetMapping("/search/{query}")
    public ResponseEntity<?> search(@PathVariable("query") String query, Principal principal) {
        System.out.println(query);
        User user=this.userRepository.getUserByUserName(principal.getName());
        List<Contact> contact= this.contactRepository.findByNameContainingAndUser(query, user);
        return ResponseEntity.ok(contact);
    }
    

}
