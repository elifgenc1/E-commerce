package com.example.ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.List;

@Entity
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product extends BaseEntity {

    private String name;

    @Positive(message = "Product price must be positive")
    private double price;

    private double stockQuantity;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductPriceHistory> priceHistory;
}
