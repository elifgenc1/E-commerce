package com.example.ecommerce.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ProductPriceHistory extends BaseEntity {

    private double oldPrice;
    private double newPrice;
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    Product product;

}
