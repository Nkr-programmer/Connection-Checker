package com.connection_checker.connection_checker.controllers;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.connection_checker.connection_checker.dao.ContactRepository;
import com.connection_checker.connection_checker.dao.UserRepository;
import com.connection_checker.connection_checker.entities.Contact;
import com.connection_checker.connection_checker.entities.User;
import com.connection_checker.connection_checker.helper.Message;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;





@Controller
@RequestMapping("/user")
public class UserControllers {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactRepository contactRepository;
    final String saveFile="C:\\spring connection_checker\\connection_checker\\src\\main\\resources\\static\\img";
    
    @ModelAttribute
    public void addCommonData(Model model,Principal principal){
        String userName=principal.getName();
        System.out.println("userName"+userName);
        User user=userRepository.getUserByUserName(userName);
        System.out.println("current  user"+user);
        model.addAttribute("user", user);
    }

    @RequestMapping("/index")
    public String dashboard(Model model,Principal principal) {
        return "normal/user_dashboard";
    }

    @GetMapping("/add-contact")
    public String openAddContactform(Model model) {
        model.addAttribute("title", "Add Contact");
        model.addAttribute("contact", new Contact());
        return "normal/add_contact_form";
    }
    @PostMapping("/process-contact")
    public String  processContact(@Valid @ModelAttribute Contact contact,BindingResult bindingResult,@RequestParam("image") MultipartFile file,Principal principal,HttpSession httpSession) {
        try{
                        //if(3>2){throw new Exception();}
        if(file.isEmpty()){
 System.out.println("File is Empty");
 contact.setImage("contacts.png");
        }
        else{
            contact.setImage(file.getOriginalFilename());
           // File saveFiles= new ClassPathResource("src/main/resources/static/img").getFile();
           // Path path=Paths.get(saveFiles.getAbsolutePath()+File.separator+file.getOriginalFilename());
            Path path=Paths.get(saveFile+File.separator+file.getOriginalFilename());
            Files.copy(file.getInputStream(), path,StandardCopyOption.REPLACE_EXISTING);
            System.out.println("image is uploaded "+path);
        }
        String userName=principal.getName();
        User user=userRepository.getUserByUserName(userName);
        contact.setUser(user);
        user.getContact().add(contact);
        this.userRepository.save(user);
        System.out.println("Data"+contact);
        System.out.println("ADDed in database");
        httpSession.setAttribute("message", new Message("Successfully Contact Added!!Add More...","alert-success"));

}
        catch(Exception e)
        {
            System.out.println("Error"+e.getMessage());
            e.printStackTrace();
            httpSession.setAttribute("message", new Message("Something went wrong!!"+e.getMessage()+"","alert-danger"));
        }
        return "normal/add_contact_form";
    }

    @GetMapping("/show-contact/{page}")
    public String showContact(@PathVariable("page") Integer page,Model m,Principal principal)
    {
        m.addAttribute("title", "show user contacts");
        String userName=principal.getName();
        User user=userRepository.getUserByUserName(userName);
        Pageable pageable= PageRequest.of(page, 2);
        Page<Contact> contact=this.contactRepository.findContactsByUser(user.getId(),pageable);
        System.out.println("contact"+contact.getContent().get(0).toString());
        m.addAttribute("contact", contact);
        m.addAttribute("currentPage", page);
        m.addAttribute("totalPages", contact.getTotalPages());
        return "normal/show_contact";
    }
    @RequestMapping("/{c_Id}/contact")
    public String showContactDetails(@PathVariable("c_Id") Integer c_Id,Model model,Principal principal) {
        System.out.println("c_Id"+c_Id);
        Optional<Contact> contactopt=this.contactRepository.findById(c_Id);
        Contact contact=contactopt.get();
        String userName=principal.getName();
        User user=userRepository.getUserByUserName(userName);
        if(user.getId()==contact.getUser().getId()){
        model.addAttribute("contact", contact);
        model.addAttribute("title", contact.getName());
        }
        return "normal/contact_detail";
    }
    @GetMapping("/delete/{c_Id}")
    public String deleteContact(@PathVariable("c_Id") Integer c_Id, Model model,HttpSession httpSession,Principal principal) {
        Contact contact=this.contactRepository.findById(c_Id).get();
        String userName=principal.getName();
        User user=userRepository.getUserByUserName(userName);
        if(user.getId()==contact.getUser().getId()){
            model.addAttribute("contact", contact);
            model.addAttribute("title", contact.getName());
        }
        System.out.println("lissssssst"+contact.getUser().getContact().toString());
        contact.getUser().getContact().remove(contact);
        System.out.println("lissssssst"+contact.getUser().getContact().toString());
        String image=contact.getImage();
        boolean defImg=false;
        if(image.equals("contacts.png")){defImg=true;}
        Path path=Paths.get(saveFile+File.separator+image);
            try{if(!defImg)Files.delete(path);}
            catch(Exception e){
                System.out.println("Error"+e.getMessage());
                e.printStackTrace();
                System.out.println("NOOOOO Image");

            }
        contact.setUser(null);
        this.contactRepository.delete(contact);
        httpSession.setAttribute("message", new Message("Contact Deleted Successfully...","alert-success"));
        return "redirect:/user/show-contact/0";
    }    
    @PostMapping("/update-contact/{c_Id}")
    public String updateForm(@PathVariable("c_Id") Integer c_Id, Model model) {
        Contact contact =this.contactRepository.findById(c_Id).get();
        model.addAttribute("contact",contact);
        model.addAttribute("title", "update contact");

        return "normal/update_detail";
    }
    @PostMapping("/process-update")
    public String updateHandler(@Valid @ModelAttribute Contact contact,BindingResult bindingResult,@RequestParam("image") MultipartFile file,Principal principal,HttpSession httpSession,Model m) {
        
        try{
            Contact oldContact= this.contactRepository.findById(contact.getC_Id()).get();
            if(file.isEmpty()){
                System.out.println("File is Empty");
                contact.setImage(oldContact.getImage());
            }
            else{
                
                String image=oldContact.getImage();
                boolean defImg=false;
                if(image.equals("contacts.png")){defImg=true;}
                Path oldpath=Paths.get(saveFile+File.separator+image);
                    try{if(!defImg)Files.delete(oldpath);}
                    catch(Exception e){
                        System.out.println("Error"+e.getMessage());
                        e.printStackTrace();
                        System.out.println("NOOOOO Image");
        
                    }
                // File saveFiles= new ClassPathResource("src/main/resources/static/img").getFile();
                // Path path=Paths.get(saveFiles.getAbsolutePath()+File.separator+file.getOriginalFilename());
                 Path path=Paths.get(saveFile+File.separator+file.getOriginalFilename());
                 Files.copy(file.getInputStream(), path,StandardCopyOption.REPLACE_EXISTING);
                 System.out.println("image is uploaded "+path);
                 contact.setImage(file.getOriginalFilename());
            }
            User user=this.userRepository.getUserByUserName(principal.getName());
            contact.setUser(user);
            // user.getContact().add(contact);
            // this.userRepository.save(user);
            this.contactRepository.save(contact);

            System.out.println(" Contact NAME"+contact.getName()+user.getContact().toString());
            System.out.println("C_ID"+contact.getC_Id());
            httpSession.setAttribute("message", new Message("Successfully Contact Updated!!Add More...","alert-success"));
    
        
        }
        catch(Exception e)
        {
        System.out.println("Error"+e.getMessage());
        e.printStackTrace();
        httpSession.setAttribute("message", new Message("Something went wrong!!"+e.getMessage()+"","alert-danger")); 
        }

        return "redirect:/user/show-contact/0";
    }
    @GetMapping("/profile")
    public String profile(Model model) {
        model.addAttribute("title", "profile page");
        return "normal/profile";
    }
    
    
    
}
