package practice.pagination.repository;

import com.springboot.practice.pagination.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // --- DERIVED QUERY METHODS ---
    // Spring generates SQL automatically from method name

    Page<Product> findByCategory(String category, Pageable pageable);
    Page<Product> findByCategoryAndBrand(String category, String brand, Pageable pageable);
    Page<Product> findByPriceBetween(Double minPrice, Double maxPrice, Pageable pageable);
    Page<Product> findByStockGreaterThan(Integer stock, Pageable pageable);
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);


    // --- JPQL @Query ---
    // uses entity/field names, DB independent
    @Query("SELECT p FROM Product p WHERE p.price >:minPrice AND p.category = :category")
    Page<Product> findExpensiveByCategory(
            @Param("minPrice") double minPrice,
            @Param("category") String category,
            Pageable pageable

    );

    @Query("SELECT p FROM Product p WHERE p.stock = 0")
    List<Product> findOutOfStock();

    @Query("SELECT DISTINCT p.category FROM Product p")
    List<String> findAllCategories();

    // --- Native SQL @Query ---
    // uses actual table/column names
    @Query(value = "SELECT * FROM products WHERE price < :maxPrice ORDER BY price ASC",
            nativeQuery = true)
    List<Product> findBudgetProducts(@Param("maxPrice") Double maxPrice);


}
