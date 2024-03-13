package com.connection_checker.connection_checker.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "CONTACT")
public class Contact {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int c_Id;
    private String name;
    private String secondName;
    private String work;
    @Column(unique = true)
    private String email;
    private String phone;
    private String subcompany;
    private String linkedin;
    private String image;
    @Column(length = 1000)
    private String description;
    @ElementCollection(targetClass = String.class, fetch = FetchType.LAZY)
    @Column(name = "companies", nullable = false)
    private List<String> companies= new ArrayList<>();
    @ElementCollection(targetClass = String.class, fetch = FetchType.LAZY)
    @Column(name = "projects", nullable = false)
    private List<String> projects= new ArrayList<>();
    @ElementCollection(targetClass = String.class, fetch = FetchType.LAZY)
    @Column(name = "techstack", nullable = false)
    private List<String> techstack= new ArrayList<>();

    @ManyToOne
    @JsonIgnore
    private User user;

    @Override
    public String toString() {
        return "Contact [c_Id=" + c_Id + ", name=" + name + ", secondName=" + secondName + ", work=" + work + ", email="
                + email + ", phone=" + phone + ", subcompany=" + subcompany + ", linkedin=" + linkedin + ", image="
                + image + ", description=" + description + ", companies=" + companies + ", projects=" + projects
                + ", techstack=" + techstack + ", user=" + user.getName() + "]";
    }

    public Contact() {
    }

    public Contact(int c_Id, String name, String secondName, String work, String email, String phone, String subcompany,
            String linkedin, String image, String description, List<String> companies, List<String> projects,
            List<String> techstack, User user) {
        this.c_Id = c_Id;
        this.name = name;
        this.secondName = secondName;
        this.work = work;
        this.email = email;
        this.phone = phone;
        this.subcompany = subcompany;
        this.linkedin = linkedin;
        this.image = image;
        this.description = description;
        this.companies = companies;
        this.projects = projects;
        this.techstack = techstack;
        this.user = user;
    }

    public int getC_Id() {
        return c_Id;
    }

    public void setC_Id(int c_Id) {
        this.c_Id = c_Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSubcompany() {
        return subcompany;
    }

    public void setSubcompany(String subcompany) {
        this.subcompany = subcompany;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }


}
