package com.sync.ticketprocessor.controller;

import com.sync.ticketprocessor.dto.ProductDTO;
import com.sync.ticketprocessor.service.ProductService;
import com.sync.ticketprocessor.service.impl.UserDetailsImpl;
import com.sync.ticketprocessor.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<ProductDTO>> getMyProducts(Authentication authentication) {
        List<ProductDTO> products = null;
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        products = productService.getMyProducts(userPrincipal.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @PostMapping(value = "/save")
    public ResponseEntity<ProductDTO> createProduct(Authentication authentication, @RequestBody ProductDTO productDTO) {
        Validator.validateProductDTO(productDTO);
        productDTO.createAuditForSave(authentication);
        productDTO = productService.createProduct(productDTO);
        return ResponseEntity.status(HttpStatus.OK).body(productDTO);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<ProductDTO> updateProduct(Authentication authentication, @RequestBody ProductDTO productDTO) {
        Validator.validateProductDTO(productDTO);
        productDTO.createAuditForUpdate(authentication);
        productDTO = productService.updateProduct(productDTO);
        return ResponseEntity.status(HttpStatus.OK).body(productDTO);
    }

    @PostMapping(value = "/delete")
    public ResponseEntity<Boolean> deleteProduct(Authentication authentication, @RequestBody ProductDTO productDTO) {
        Boolean flag = productService.deleteProduct(productDTO);
        return ResponseEntity.status(HttpStatus.OK).body(flag);
    }
}
