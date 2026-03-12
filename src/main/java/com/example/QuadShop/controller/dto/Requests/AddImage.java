package com.example.QuadShop.controller.dto.Requests;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
public class AddImage {
    public long ProductId;
    public MultipartFile Image;
}