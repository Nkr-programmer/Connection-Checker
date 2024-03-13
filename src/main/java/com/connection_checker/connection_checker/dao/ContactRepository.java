package com.connection_checker.connection_checker.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.connection_checker.connection_checker.entities.Contact;
import com.connection_checker.connection_checker.entities.User;


public interface ContactRepository extends JpaRepository<Contact,Integer>{


    @Query("from Contact as u where u.user.id=:userId")
    public Page<Contact> findContactsByUser(@Param("userId") int userId, Pageable pageable);
    @Query("from Contact as u where u.user.id=:userId")
    public List<Contact> findContactsByUser(@Param("userId") int userId);

    //for search bar for filters
    public List<Contact> findByNameContainingAndUser(String name, User user);
    public List<Contact> findBySubcompanyContainingAndUser(String subcompany, User user);
    public List<Contact> findByEmailContainingAndUser(String email, User user);
    // @Query("SELECT c FROM Contact c WHERE :company IN elements(c.companies) and c.user.id=:userId")
    // List<Contact> findByCompanyAndUser(@Param("userId") int userId,String company);
    // @Query("SELECT c FROM Contact c WHERE :projects IN elements(c.projects) and c.user.id=:userId")
    // List<Contact> findByProjectsAndUser(@Param("userId") int userId,String projects);
    // @Query("SELECT c FROM Contact c WHERE :teckstack IN elements(c.teckstack) and c.user.id=:userId")
    // List<Contact> findByTechStackAndUser(@Param("userId") int userId,String teckstack);

    @Query(value = "SELECT DISTINCT c.* FROM contact c JOIN user u ON c.user_id = u.id JOIN contact_companies x ON  c.c_id= x.contact_c_id WHERE x.companies LIKE CONCAT('%', :company , '%') and c.user_id=:userId", nativeQuery = true)
    public List<Contact> findByCompanyAndUser(@Param("userId") int userId,String company);
    @Query(value = "SELECT DISTINCT c.* FROM contact c JOIN user u ON c.user_id = u.id JOIN contact_projects x ON  c.c_id= x.contact_c_id WHERE x.projects LIKE CONCAT('%', :projects , '%') and c.user_id=:userId", nativeQuery = true)
    public List<Contact> findByProjectsAndUser(@Param("userId") int userId,String projects);
    @Query(value = "SELECT DISTINCT c.* FROM contact c JOIN user u ON c.user_id = u.id JOIN contact_techstack x ON  c.c_id= x.contact_c_id WHERE x.techstack LIKE CONCAT('%', :techstack , '%') and c.user_id=:userId", nativeQuery = true)
    public List<Contact> findByTechStackAndUser(@Param("userId") int userId,@Param("techstack") String techstack);
}