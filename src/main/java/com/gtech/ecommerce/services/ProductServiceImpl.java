package com.gtech.ecommerce.services;

import com.gtech.ecommerce.domain.Product;
import com.gtech.ecommerce.exceptions.EcResourceNotFoundException;
import com.gtech.ecommerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public Product fetchProductById(Integer productId) throws EcResourceNotFoundException {
        return productRepository.findById(productId);
    }
}
