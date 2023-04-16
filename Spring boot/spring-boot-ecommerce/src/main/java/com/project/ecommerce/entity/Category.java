package com.project.ecommerce.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "product_category")
@Getter
@Setter
public class Category {

    @Id
    private String id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "category")
    private List<Product> products;

    public Category(){
        this.id= UUID.randomUUID().toString();
    }
}
