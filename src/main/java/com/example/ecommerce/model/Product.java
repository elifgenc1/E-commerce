package com.example.ecommerce.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Product extends BaseEntity {

    private String name;
    private double price;
    private double stockQuantity;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductPriceHistory> priceHistory;

}
