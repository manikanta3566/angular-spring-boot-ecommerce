package com.project.ecommerce.service.impl;

import com.project.ecommerce.Repository.CategoryRepository;
import com.project.ecommerce.Repository.ProductRepository;
import com.project.ecommerce.dto.CategoryDto;
import com.project.ecommerce.dto.FilesResponseDto;
import com.project.ecommerce.dto.ListingResponse;
import com.project.ecommerce.dto.ProductDto;
import com.project.ecommerce.entity.Category;
import com.project.ecommerce.entity.Product;
import com.project.ecommerce.enums.ExceptionMessage;
import com.project.ecommerce.exception.GlobalException;
import com.project.ecommerce.helper.CommonUtil;
import com.project.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ModelMapper modelMapper;

    @Value("${image.path.product}")
    private String productImagePath;

    private final CategoryRepository categoryRepository;


    @Override
    public Map<String, String> createProduct(ProductDto productDto,String categoryId) {
        Category category= categoryRepository.findById(categoryId).orElseThrow(()->
                new GlobalException(ExceptionMessage.CATEGORY_NOT_FOUND.getMessage(),HttpStatus.EXPECTATION_FAILED,HttpStatus.EXPECTATION_FAILED.value()));
        Product mappedProduct = modelMapper.map(productDto, Product.class);
        mappedProduct.setCategory(category);
        productRepository.save(mappedProduct);
        return Map.of("productId",mappedProduct.getId());
    }

    @Override
    public Map<String, String> updateProduct(String productId, ProductDto productDto) {
        Product product=productRepository.findById(productId).orElseThrow(()->
                new GlobalException(ExceptionMessage.PRODUCT_NOT_FOUND.getMessage(),HttpStatus.EXPECTATION_FAILED,HttpStatus.EXPECTATION_FAILED.value()));
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setSku(productDto.getSku());
        product.setUnitPrice(productDto.getUnitPrice());
        product.setImageUrl(productDto.getImageUrl());
        product.setActive(productDto.isActive());
        product.setUnitsInStock(productDto.getUnitsInStock());
        productRepository.save(product);
        return Map.of("productId",product.getId());
    }

    @Override
    public String deleteProduct(String productId) {
        Product product=productRepository.findById(productId).orElseThrow(()->
                new GlobalException(ExceptionMessage.PRODUCT_NOT_FOUND.getMessage(),HttpStatus.EXPECTATION_FAILED,HttpStatus.EXPECTATION_FAILED.value()));
        productRepository.delete(product);
        return "deleted successfully";
    }

    @Override
    public ProductDto getProductById(String productId) {
        Product product=productRepository.findById(productId).orElseThrow(()->
                new GlobalException(ExceptionMessage.PRODUCT_NOT_FOUND.getMessage(),HttpStatus.EXPECTATION_FAILED,HttpStatus.EXPECTATION_FAILED.value()));
        return modelMapper.map(product,ProductDto.class);
    }

    @Override
    public FilesResponseDto uploadProductImage(String productId, MultipartFile file) {
        try {
            Product product = productRepository.findById(productId).orElseThrow(()->
                    new GlobalException(ExceptionMessage.PRODUCT_NOT_FOUND.getMessage(),HttpStatus.EXPECTATION_FAILED,HttpStatus.EXPECTATION_FAILED.value()));
            Path imagePath=Paths.get(productImagePath).resolve(file.getOriginalFilename());
            if(!Files.exists(imagePath)) {
                Files.createDirectories(imagePath);
                Files.copy(file.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
                product.setImageUrl("http:localhost:8080/api/products/" + product.getId() + "/images");
                product.setFileName(file.getOriginalFilename());
                productRepository.save(product);
            }
            return new FilesResponseDto(file.getSize(),file.getContentType(),file.getOriginalFilename());
        }catch (Exception e){
            log.error("error while uploading image",e);
            throw new RuntimeException("something went wrong while uploading image");
        }
    }

    @Override
    public byte[] getProductImage(String productId) {
        byte[] bytes=null;
        try {
            Product product = productRepository.findById(productId).orElseThrow(()->
                    new GlobalException(ExceptionMessage.PRODUCT_NOT_FOUND.getMessage(),HttpStatus.EXPECTATION_FAILED,HttpStatus.EXPECTATION_FAILED.value()));
            Resource fileResource =new UrlResource(Paths.get(productImagePath).resolve(product.getFileName()).toUri());
            bytes= FileCopyUtils.copyToByteArray(fileResource.getInputStream());
        }catch (Exception e){
            log.error("error while reading product image resources");
            throw new GlobalException(ExceptionMessage.SOMETHING_WENT_WRONG.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return bytes;
    }

    @Override
    public ListingResponse<ProductDto> getProducts(String categoryId,int totalSize,int pageNo) {
        Category category= categoryRepository.findById(categoryId).orElseThrow(()->
                new GlobalException(ExceptionMessage.CATEGORY_NOT_FOUND.getMessage(),HttpStatus.EXPECTATION_FAILED,HttpStatus.EXPECTATION_FAILED.value()));
        Pageable pageable= PageRequest.of(pageNo,totalSize,Sort.by("createdDate"));
        Page<Product> products = productRepository.findByCategory(category, pageable);
        return CommonUtil.convertListingResponse(products,ProductDto.class);
    }
}
