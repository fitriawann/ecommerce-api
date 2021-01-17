package com.gtech.ecommerce.repositories;

import com.gtech.ecommerce.domain.Product;
import com.gtech.ecommerce.exceptions.EcResourceNotFoundException;

public interface ProductRepository {

    Product findById(Integer productId) throws EcResourceNotFoundException;

}
