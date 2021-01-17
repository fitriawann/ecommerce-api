package com.gtech.ecommerce.services;

import com.gtech.ecommerce.domain.Product;
import com.gtech.ecommerce.exceptions.EcResourceNotFoundException;

public interface ProductService {

    Product fetchProductById(Integer productId) throws EcResourceNotFoundException;
}
