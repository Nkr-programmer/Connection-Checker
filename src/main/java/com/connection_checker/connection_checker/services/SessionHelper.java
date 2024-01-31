package com.connection_checker.connection_checker.services;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpSession;
@Component
public class SessionHelper {

    public void removeMessageFromSession(){

    
    try{
        System.out.println("Removing session");
        HttpSession httpSession=((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession();
        httpSession.removeAttribute("message");
    }
    catch(Exception e)
    {
        System.out.println("Error"+e.getMessage());
        e.printStackTrace();
    }
    
}
}