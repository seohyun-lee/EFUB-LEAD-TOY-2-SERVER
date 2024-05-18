package com.efub.leadtoyproject.domain.product.controller;

import com.efub.leadtoyproject.domain.category.domain.ProductType;
import com.efub.leadtoyproject.domain.product.domain.Product;
import com.efub.leadtoyproject.domain.product.dto.AllProductResponseDto;
import com.efub.leadtoyproject.domain.product.dto.ProductResponseDto;
import com.efub.leadtoyproject.domain.product.service.ProductService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    /**상품 전체 조회 기능*/
    @GetMapping
    @ResponseStatus(value=HttpStatus.OK)
    public AllProductResponseDto getAllProducts() {
        List<Product> products = productService.findAllProducts();
        List<ProductResponseDto> productDtos = products.stream().map(product -> {
            return new ProductResponseDto(
                    product.getProductId(),
                    product.getCategory().getCategoryId(),
                    product.getProductName(),
                    product.getOriginalPrice(),
                    product.getInfo(),
                    product.getStockQuantity(),
                    product.getAvgRating()
            );
        }).collect(Collectors.toList());
        return new AllProductResponseDto(productDtos, productDtos.size());
    }

    /**상품 상세 조회 기능*/
    @GetMapping("/{productId}")
    @ResponseStatus(value=HttpStatus.OK)
    public ProductResponseDto getProduct(@PathVariable Long productId) {
        Product product = productService.findProductById(productId);
        return ProductResponseDto.from(product);
    }

    /**카테고리별 상품 전체 조회 기능 (서적)*/
    @GetMapping("/books")
    @ResponseStatus(value=HttpStatus.OK)
    public AllProductResponseDto getBookProducts(){
        List<Product> products = productService.findProductsByType(ProductType.BOOK);
        List<ProductResponseDto> productDtos = products.stream()
                .map(ProductResponseDto::from)
                .collect(Collectors.toList());
        return new AllProductResponseDto(productDtos, productDtos.size());
    }

    /**카테고리별 상품 전체 조회 기능 (음반)*/
    @GetMapping("/records")
    @ResponseStatus(value=HttpStatus.OK)
    public AllProductResponseDto getRecordProducts(){
        List<Product> products = productService.findProductsByType(ProductType.RECORD);
        List<ProductResponseDto> productDtos = products.stream()
                .map(ProductResponseDto::from)
                .collect(Collectors.toList());
        return new AllProductResponseDto(productDtos, productDtos.size());
    }

    /**카테고리별 상품 전체 조회 기능 (굿즈)*/
    @GetMapping("/goods")
    @ResponseStatus(value=HttpStatus.OK)
    public AllProductResponseDto getGoodsProducts(){
        List<Product> products = productService.findProductsByType(ProductType.GOODS);
        List<ProductResponseDto> productDtos = products.stream()
                .map(ProductResponseDto::from)
                .collect(Collectors.toList());
        return new AllProductResponseDto(productDtos, productDtos.size());
    }

    /**상품 검색 기능*/
}
