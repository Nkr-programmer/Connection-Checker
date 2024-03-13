package com.connection_checker.connection_checker.controllers;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.connection_checker.connection_checker.dao.UserRepository;
import com.connection_checker.connection_checker.entities.Contact;
import com.connection_checker.connection_checker.entities.User;
import com.connection_checker.connection_checker.helper.Message;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;




@Controller
public class HomeControllers {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    final String saveFile="C:\\spring connection_checker\\connection_checker\\src\\main\\resources\\static\\img";

    @ModelAttribute
    public void addCommonData(Model model,Principal principal){

        if(principal==null){model.addAttribute("login","Login");}
        else{     
        String userName=principal.getName();
        System.out.println("USEEEER PAGE userName"+userName);
        User user=userRepository.getUserByUserName(userName);
        System.out.println("USER ====== PAGEcurrent  user"+user);
        System.out.println("userrrrrrr page"+user.getContact());
        model.addAttribute("login","Connections");}
    }
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
            return "signup";
        }
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
        } 
        catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("user", user);
            httpSession.setAttribute("message", new Message("Something went wrong!!"+e.getMessage()+"","alert-danger"));
        }

        return "signup";
    }
    @PostMapping("/user/update-user/{id}")
    public String updateUserForm(@PathVariable("id") Integer id, Model model) {
        User user =this.userRepository.findById(id).get();
        model.addAttribute("user",user);
        model.addAttribute("title", "update user");

        return "normal/user_update";
    }

        @PostMapping("/user/process-user-update")
    public String updateHandler(@Valid @ModelAttribute User user,BindingResult bindingResult,@RequestParam("imageUrl") MultipartFile file,Principal principal,HttpSession httpSession,Model m) {
        
        try{
            User oldUser= this.userRepository.findById(user.getId()).get();
            user.setContact(oldUser.getContact());
            System.out.println("inputtttttttttt user "+user+" "+user.getContact()+user.getRole());
            System.out.println("olllllllld user"+oldUser+" "+oldUser.getContact());
            if(file.isEmpty()){
                System.out.println("File is Empty");
                user.setImageUrl(oldUser.getImageUrl());
            }
            else{
                
                String image=oldUser.getImageUrl();
                boolean defImg=false;
                if(image.equals("default.png")){defImg=true;}
                Path oldpath=Paths.get(saveFile+File.separator+user.getId()+"_"+image);
                try{if(!defImg)Files.delete(oldpath);}
                catch(Exception e){
                    System.out.println("Error"+e.getMessage());
                    e.printStackTrace();
                    System.out.println("NOOOOO Image");
                }
                // File saveFiles= new ClassPathResource("src/main/resources/static/img").getFile();
                // Path path=Paths.get(saveFiles.getAbsolutePath()+File.separator+file.getOriginalFilename());
                 Path path=Paths.get(saveFile+File.separator+user.getId()+"_"+file.getOriginalFilename());
                 Files.copy(file.getInputStream(), path,StandardCopyOption.REPLACE_EXISTING);
                 System.out.println("image is uploaded "+path);
                 user.setImageUrl(file.getOriginalFilename());
            }
            List<Contact> contact=user.getContact();
            System.out.println(user);
            System.out.println(contact);
            for(Contact c:contact)
            {
                c.setUser(user);
            }
            // user.getContact().add(contact);
            // this.userRepository.save(user);
            this.userRepository.save(user);
            System.out.println(user);
            System.out.println(user.getContact().toString());
            System.out.println(" user NAME"+user.getName()+user.getContact().toString());
            System.out.println("id"+user.getId());
            httpSession.setAttribute("message", new Message("Successfully User Updated!!Add More...","alert-success"));
    
        
        }
        catch(Exception e)
        {
            System.out.println("Error"+e.getMessage());
            e.printStackTrace();
            httpSession.setAttribute("message", new Message("Something went wrong!!"+e.getMessage()+"","alert-danger")); 
        }

        return "about";
    }
}
