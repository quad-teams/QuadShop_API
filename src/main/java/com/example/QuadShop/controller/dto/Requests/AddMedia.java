package com.example.QuadShop.controller.dto.Requests;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class AddMedia {
    public long ProductId;
    public MultipartFile file;
    public String type;
}