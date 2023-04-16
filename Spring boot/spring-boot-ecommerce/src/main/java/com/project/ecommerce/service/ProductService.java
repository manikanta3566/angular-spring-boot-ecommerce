package com.project.ecommerce.service;

import com.project.ecommerce.dto.FilesResponseDto;
import com.project.ecommerce.dto.ListingResponse;
import com.project.ecommerce.dto.ProductDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface ProductService {
    Map<String,String> createProduct(ProductDto productDto,String categoryId);

    Map<String,String> updateProduct(String productId,ProductDto productDto);

    String deleteProduct(String productId);

    ProductDto getProductById(String productId);

    FilesResponseDto uploadProductImage(String productId, MultipartFile file);

    byte[] getProductImage(String productId);

    ListingResponse<ProductDto> getProducts(String categoryId, int totalSize, int pageNo);
 }
