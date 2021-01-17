package com.gtech.ecommerce.domain;

public class Product {

    private Integer productId;
    private String storeName;
    private String brandName;
    private String categoryNames;
    private String title;
    private String code;
    private Double price;
    private String description;

    public Product(Integer productId, String storeName, String brandName, String categoryNames, String title, String code, Double price, String description) {
        this.productId = productId;
        this.storeName = storeName;
        this.brandName = brandName;
        this.categoryNames = categoryNames;
        this.title = title;
        this.code = code;
        this.price = price;
        this.description = description;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCategoryNames() {
        return categoryNames;
    }

    public void setCategoryNames(String categoryNames) {
        this.categoryNames = categoryNames;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
