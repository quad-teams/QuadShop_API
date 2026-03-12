package com.example.QuadShop.controller.dto.Requests;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class AddSize {
    public long productId;
    public String size;
}