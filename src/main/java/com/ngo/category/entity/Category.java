package com.ngo.category.entity;

import com.ngo.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
    name = "categories",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_category_name",
            columnNames = "name"
        )
    }
)
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
        name = "name",
        nullable = false,
        length = 100
    )
    private String name;

    @Column(
        name = "description",
        length = 500
    )
    private String description;

    @Column(
        name = "is_active",
        nullable = false
    )
    private Boolean isActive = true;
}