package com.rysh.module.commonService.service;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


public interface ImageUploadService {
    public String upload(@RequestParam("file") MultipartFile file);
}
