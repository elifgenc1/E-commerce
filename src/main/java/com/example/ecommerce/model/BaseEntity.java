package com.example.ecommerce.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private boolean deleted = false;

    @Column(name = "creation_time", nullable = false)
    private LocalDateTime creationTime;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @Column(name = "deletion_time")
    private LocalDateTime deletionTime;

    @PrePersist
    public void prePersist() {
        creationTime = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updateTime = LocalDateTime.now();
    }

    @PreRemove
    public void preRemove() {
        deletionTime = LocalDateTime.now();
    }

}
