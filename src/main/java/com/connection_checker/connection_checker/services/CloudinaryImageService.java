package com.connection_checker.connection_checker.services;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryImageService {
    public Map upload(MultipartFile file);
    public Map delete(String image);
}
