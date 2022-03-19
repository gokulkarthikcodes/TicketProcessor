package com.sync.ticketprocessor.service.impl;

import com.sync.ticketprocessor.constants.ConstantsUtil;
import com.sync.ticketprocessor.conversion.Converter;
import com.sync.ticketprocessor.dto.ProductDTO;
import com.sync.ticketprocessor.entity.Product;
import com.sync.ticketprocessor.exception.RecordAlreadyExistsException;
import com.sync.ticketprocessor.exception.RecordNotFoundException;
import com.sync.ticketprocessor.repository.ProductRepository;
import com.sync.ticketprocessor.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public List<ProductDTO> getMyProducts(String createdBy) {
        List<Product> products = null;
        List<ProductDTO> productDTOList = new ArrayList<>();
        products = productRepository.getProductsByCreatedBy(createdBy);
        products.stream().forEach(product -> productDTOList.add(Converter.covertProductFromEntityToDTO(product)));
        return productDTOList;
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        validateProductForSave(productDTO);
        Product product = Converter.convertProductFromDTOToEntity(productDTO);
        product = productRepository.save(product);
        return Converter.covertProductFromEntityToDTO(product);
    }

    @Override
    public Boolean deleteProduct(ProductDTO productDTO) {
        validateProductForDelete(productDTO);
        Product product = productRepository.deleteByIdAndCreatedBy(productDTO.getId(), productDTO.getCreatedBy());
        return null != product;
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO) {
        validateProductForUpdate(productDTO);
        Product product = Converter.convertProductFromDTOToEntity(productDTO);
        product = productRepository.save(product);
        return Converter.covertProductFromEntityToDTO(product);
    }

    private void validateProductForSave(ProductDTO productDTO) {
        validateSaveValuesForUniqueNess(productDTO);
    }

    private void validateSaveValuesForUniqueNess(ProductDTO productDTO) {
        Product existing = productRepository.findProductsByNameAndCreatedBy(productDTO.getProductName(), productDTO.getCreatedBy());
        if(null != existing)
            throw new RecordAlreadyExistsException(ConstantsUtil.RECORD_ALREADY_EXISTS_FOR + ConstantsUtil.SPACE + productDTO.getProductName());
    }

    public boolean validateProductForDelete(ProductDTO productDTO) {
        Product existing = productRepository.findProductByIdAndCreatedBy(productDTO.getId(), productDTO.getCreatedBy());
        if(null == existing)
            throw new RecordNotFoundException(ConstantsUtil.RECORD_NOT_FOUND_FOR + ConstantsUtil.SPACE + productDTO.getProductName());
        return true;
    }

    public boolean validateProductForUpdate(ProductDTO productDTO) {
        boolean isUnique = validateUpdateValuesForUniqueness(productDTO);
        if (!isUnique) {
            throw new RecordAlreadyExistsException(ConstantsUtil.RECORD_ALREADY_EXISTS_FOR + ConstantsUtil.SPACE + productDTO.getProductName());
        }
        boolean isExists = validateIfUpdateRecordStillExists(productDTO);
        if(!isExists)
            throw new RecordNotFoundException(ConstantsUtil.RECORD_NOT_FOUND_FOR + ConstantsUtil.SPACE + productDTO.getProductName());

        return true;
    }

    private boolean validateUpdateValuesForUniqueness(ProductDTO productDTO) {
        List<Product> existingList = productRepository.findProductsByProductNameAndCreatedBy(productDTO.getProductName(), productDTO.getCreatedBy());
        return existingList.isEmpty() || existingList.get(0).getId().equals(productDTO.getId());
    }

    private boolean validateIfUpdateRecordStillExists(ProductDTO productDTO){
        Product current = productRepository.findProductByIdAndCreatedBy(productDTO.getId(), productDTO.getCreatedBy());
        return null != current;
    }
}
