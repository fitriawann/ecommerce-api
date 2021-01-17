package com.gtech.ecommerce.resources;

import com.gtech.ecommerce.domain.Product;
import com.gtech.ecommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/product")
public class ProductResource {

    @Autowired
    ProductService productService;

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(HttpServletRequest request,
                                                  @PathVariable("productId") Integer productId) {
        Product product = productService.fetchProductById(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

}
