package com.connection_checker.connection_checker.services;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class CloudinaryImageServiceImpl implements CloudinaryImageService {

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public Map upload( MultipartFile file) {
        try {
            Map data=this.cloudinary.uploader().upload(file.getBytes(), Map.of());
            return data;
        } catch (IOException e) {
            throw new RuntimeException("Image uploading fail");
        }
    }

    @Override
    public Map delete(String image) {
        
        String tempimage=image;
        int slash=tempimage.length(), point=tempimage.length();
        for(int i=tempimage.length()-1;i>=0;i--)
        {
            if(tempimage.charAt(i)=='.')point=slash;
            if(tempimage.charAt(i)=='/')break;
            slash--;
        }
        String public_id=tempimage.substring(slash,point-1);

        try {
            Map data = this.cloudinary.uploader().destroy(public_id, ObjectUtils.emptyMap());
            return data;
        } catch (IOException e) {
            throw new RuntimeException("Image delete fail"+e.toString());
        }
    }

    
    
}
