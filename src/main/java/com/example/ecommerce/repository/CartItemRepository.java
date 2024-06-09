package com.example.ecommerce.repository;

import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.CartItem;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Page<CartItem> findByCartAndProduct_Id(Cart cart, Long productId, Pageable pageable);
    List<CartItem> findByCart(Cart cart);

    @Transactional
    @Modifying
    @Query("UPDATE CartItem e SET e.deleted = true, e.deletionTime = CURRENT_TIMESTAMP WHERE e.id = :id")
    void softDelete(Long id);



}
