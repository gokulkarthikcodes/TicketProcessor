package com.sync.ticketprocessor.service;

import com.sync.ticketprocessor.dto.ProductDTO;

import java.util.List;

public interface ProductService {

    List<ProductDTO> getMyProducts(String createdBy);

    ProductDTO createProduct(ProductDTO productDTO);

    Boolean deleteProduct(ProductDTO productDTO);

    ProductDTO updateProduct(ProductDTO productDTO);
}
