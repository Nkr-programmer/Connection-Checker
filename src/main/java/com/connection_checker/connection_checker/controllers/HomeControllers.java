package com.connection_checker.connection_checker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.connection_checker.connection_checker.dao.UserRepository;
import com.connection_checker.connection_checker.entities.User;
import com.connection_checker.connection_checker.helper.Message;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;




@Controller
public class HomeControllers {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @RequestMapping("/")
    public String home(Model model) {
        model.addAttribute("title","Home-Smart Contact-Management");
        return "home";
    }
    @RequestMapping("/about")
    public String about(Model model) {
        model.addAttribute("title","About-Smart Contact-Management");
        return "about";
    }

    @RequestMapping("/signin")
    public String customLogin(Model model) {
        model.addAttribute("title","Login-Smart Contact-Management");
        return "login";
    }
    @RequestMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("title","Signup-Smart Contact-Management");
        model.addAttribute("user", new User());
        return "signup";
    }
    @PostMapping("/do-register")
    public String registerUser(@Valid @ModelAttribute("user") User user,BindingResult result1,@RequestParam(value = "agreement", defaultValue = "false") boolean agreement, Model model ,HttpSession httpSession) {

        try {
        if(!agreement){
            System.out.println("You have not agreed the terms and conditions");
            throw new Exception("You have not agreed the terms and conditions");
        }
        if(result1.hasErrors()){
            System.out.println("ERRORS"+result1);
            model.addAttribute("user",user);
            return "signup";}
        user.setRole("ROLE_USER");
        user.setEnabled(true);
        user.setImageUrl("default.png");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println(agreement+"=agreement");
        System.out.println(user+"=user");
        User result=this.userRepository.save(user);
        System.out.println(result);
        model.addAttribute("user", new User());
        httpSession.setAttribute("message", new Message("Successfully Registered!!","alert-success"));
        return "signup";            
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("user", user);
            httpSession.setAttribute("message", new Message("Something went wrong!!"+e.getMessage()+"","alert-danger"));
        }

        return "signup";
    }
    

}
