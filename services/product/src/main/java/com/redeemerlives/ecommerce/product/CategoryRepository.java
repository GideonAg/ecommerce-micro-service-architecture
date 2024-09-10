package com.redeemerlives.ecommerce.product;

import com.redeemerlives.ecommerce.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
