package com.gtech.ecommerce.repositories;

import com.gtech.ecommerce.domain.Product;
import com.gtech.ecommerce.exceptions.EcResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private static final String SQL_FIND_BY_ID = "SELECT C.*, D.TITLE PRODUCT_NAME, E.TITLE STORE_NAME, F.TITLE BRAND_NAME, D.CODE PRODUCT_CODE, D.PRICE PRODUCT_PRICE, D.DESCRIPTION PRODUCT_DESCRIPTION FROM (SELECT A.PRODUCT_ID, ARRAY_AGG(B.TITLE) CATEGORY_NAMES FROM EC_PRODUCTS_CATEGORIES A LEFT OUTER JOIN EC_CATEGORIES B ON B.CATEGORY_ID = A.CATEGORY_ID WHERE A.PRODUCT_ID = ? GROUP BY A.PRODUCT_ID) C RIGHT OUTER JOIN EC_PRODUCTS D ON C.PRODUCT_ID = D.PRODUCT_ID LEFT OUTER JOIN EC_STORES E ON E.STORE_ID = D.STORE_ID LEFT OUTER JOIN EC_BRANDS F ON F.BRAND_ID = D.BRAND_ID WHERE D.PRODUCT_ID = ?;";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Product findById(Integer productId) throws EcResourceNotFoundException {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{productId, productId}, productRowMapper);
        } catch (Exception e) {
            throw new EcResourceNotFoundException("Product not found");
        }
    }

    private RowMapper<Product> productRowMapper = ((rs, rowNum) -> {
        return new Product(rs.getInt("PRODUCT_ID"),
                rs.getString("STORE_NAME"),
                rs.getString("BRAND_NAME"),
                rs.getString("CATEGORY_NAMES"),
                rs.getString("PRODUCT_NAME"),
                rs.getString("PRODUCT_CODE"),
                rs.getDouble("PRODUCT_PRICE"),
                rs.getString("PRODUCT_DESCRIPTION"));
    });
}
