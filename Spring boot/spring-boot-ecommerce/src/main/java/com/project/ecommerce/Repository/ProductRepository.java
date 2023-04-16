package com.project.ecommerce.Repository;

import com.project.ecommerce.entity.Category;
import com.project.ecommerce.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,String> {

    Page<Product> findByCategory(Category category, Pageable pageable);
}
