package com.connection_checker.connection_checker.entities;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "USER")
public class  User{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;



    public User(int id,
            @NotBlank(message = "Name is required") @Size(min = 2, max = 20, message = "Name should be 2-20") String name,
            @Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$") String email,
            @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$") String password,
            String role, String work, boolean enabled, String imageUrl, String about, String subcompany, String phone,
            String linkedin, List<String> companies, List<String> projects, List<String> techstack,
            List<Contact> contact) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.work = work;
        this.enabled = enabled;
        this.imageUrl = imageUrl;
        this.about = about;
        this.subcompany = subcompany;
        this.phone = phone;
        this.linkedin = linkedin;
        this.companies = companies;
        this.projects = projects;
        this.techstack = techstack;
        this.contact = contact;
    }
    @NotBlank(message = "Name is required")
    @Size(min = 2,max =20, message = "Name should be 2-20" )
    private String name;
    @Column(unique = true)
    @Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")
    private String email;
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")
    private String password;
    private String role;
    private String work;
    private boolean enabled;
    private String imageUrl;
    @Column(length = 500)
    private String about;
    private String subcompany;
    private String phone;
    private String linkedin;
    @ElementCollection(targetClass = String.class, fetch = FetchType.LAZY)
    @Column(name = "companies", nullable = false)
    private List<String> companies= new ArrayList<>();
    @ElementCollection(targetClass = String.class, fetch = FetchType.LAZY)
    @Column(name = "projects", nullable = false)
    private List<String> projects= new ArrayList<>();
    @ElementCollection(targetClass = String.class, fetch = FetchType.LAZY)
    @Column(name = "techstack", nullable = false)
    private List<String> techstack= new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY, mappedBy = "user")
    private List<Contact> contact=new ArrayList<>();


    public String getSubcompany() {
        return subcompany;
    }
    public void setSubcompany(String subcompany) {
        this.subcompany = subcompany;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getLinkedin() {
        return linkedin;
    }
    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }
    public List<String> getCompanies() {
        return companies;
    }
    public void setCompanies(List<String> companies) {
        this.companies = companies;
    }
    public List<String> getProjects() {
        return projects;
    }
    public void setProjects(List<String> projects) {
        this.projects = projects;
    }
    public List<String> getTechstack() {
        return techstack;
    }
    public void setTechstack(List<String> techstack) {
        this.techstack = techstack;
    }
    public List<Contact> getContact() {
        return contact;
    }
    public void setContact(List<Contact> contact) {
        this.contact = contact;
    }


    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", role=" + role
                + ", work=" + work + ", enabled=" + enabled + ", imageUrl=" + imageUrl + ", about=" + about
                + ", subcompany=" + subcompany + ", phone=" + phone + ", linkedin=" + linkedin + ", companies="
                + companies + ", projects=" + projects + ", techstack=" + techstack + "]";
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public void setAbout(String about) {
        this.about = about;
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public String getRole() {
        return role;
    }
    public boolean isEnabled() {
        return enabled;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public String getAbout() {
        return about;
    }


    public User() {
    }
    public String getWork() {
        return work;
    }
    public void setWork(String work) {
        this.work = work;
    }
    
}
