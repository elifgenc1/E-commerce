package com.example.ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends BaseEntity {

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private Cart cart;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Order> orders;

//TODO Spring Security ile password encode işlemi
//    public void setPassword(String password) {
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        this.password = encoder.encode(password);
//    }

}
