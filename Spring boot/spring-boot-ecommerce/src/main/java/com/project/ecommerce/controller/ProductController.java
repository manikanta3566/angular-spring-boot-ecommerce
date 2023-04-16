package com.project.ecommerce.controller;

import com.project.ecommerce.dto.FilesResponseDto;
import com.project.ecommerce.dto.ListingResponse;
import com.project.ecommerce.dto.ProductDto;
import com.project.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
@CrossOrigin("*")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/category/{categoryId}")
    public ResponseEntity<Map<String, String>> createProduct(@RequestBody ProductDto productDto, @PathVariable("categoryId")
    String categoryId) {
        return new ResponseEntity<>(productService.createProduct(productDto, categoryId), HttpStatus.CREATED);
    }

    @PutMapping("{productId}")
    public ResponseEntity<Map<String, String>> updateProduct(@RequestBody ProductDto productDto, String productId) {
        return new ResponseEntity<>(productService.updateProduct(productId, productDto), HttpStatus.OK);
    }

    @GetMapping("{productId}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable("productId") String productId) {
        return new ResponseEntity<>(productService.getProductById(productId), HttpStatus.OK);
    }

    @DeleteMapping("{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable("productId") String productId) {
        return new ResponseEntity<>(productService.deleteProduct(productId), HttpStatus.OK);
    }

    @PostMapping("{productId}/images")
    public ResponseEntity<FilesResponseDto> uploadProductImage(@PathVariable("productId") String productId
            , @RequestParam("image") MultipartFile file) {
        return new ResponseEntity<>(productService.uploadProductImage(productId, file), HttpStatus.OK);
    }

    @GetMapping(value = "{productId}/images",produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getProductImage(@PathVariable("productId") String productId) {
        return productService.getProductImage(productId);
    }

    @GetMapping()
    public ResponseEntity<ListingResponse<ProductDto>> getAllProducts(@RequestParam(name = "categoryId",required = true) String categoryId,
                                                                      @RequestParam(name = "totalSize",required = false,defaultValue = "10") int totalSize,
                                                                      @RequestParam(name = "pageNo",required = false,defaultValue = "0") int pageNo){
        return new ResponseEntity<>(productService.getProducts(categoryId,totalSize,pageNo),HttpStatus.OK);
    }

}
