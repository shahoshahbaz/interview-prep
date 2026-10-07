package practice.pagination.controller;

import com.springboot.practice.pagination.dto.PageResponse;
import com.springboot.practice.pagination.dto.ProductRequest;
import com.springboot.practice.pagination.dto.ProductResponse;
import com.springboot.practice.pagination.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

 @RestController
 @RequestMapping("/api/v1/products")
 public class ProductController {

        private final ProductService productService;

        public ProductController(ProductService productService) {
            this.productService = productService;
        }

        // GET all products with pagination and sorting
        @GetMapping
        public ResponseEntity<PageResponse> getAllProducts(
                @RequestParam(defaultValue = "0") int page,
                @RequestParam(defaultValue = "5") int size,
                @RequestParam(defaultValue = "name") String sortBy,
                @RequestParam(defaultValue = "asc") String sortDir) {
            return ResponseEntity.ok(productService.getAllProducts(page, size, sortBy, sortDir));
        }

        // GET by category with pagination
        @GetMapping("/category/{category}")
        public ResponseEntity<PageResponse> getByCategory(
                @PathVariable String category,
                @RequestParam(defaultValue = "0") int page,
                @RequestParam(defaultValue = "5") int size) {
            return ResponseEntity.ok(productService.getByCategory(category, page, size));
        }

        // GET search by name
        @GetMapping("/search")
        public ResponseEntity<PageResponse> searchByName(
                @RequestParam String name,
                @RequestParam(defaultValue = "0") int page,
                @RequestParam(defaultValue = "5") int size) {
            return ResponseEntity.ok(productService.searchByName(name, page, size));
        }

        // GET by price range
        @GetMapping("/price-range")
        public ResponseEntity<PageResponse> getByPriceRange(
                @RequestParam Double minPrice,
                @RequestParam Double maxPrice,
                @RequestParam(defaultValue = "0") int page,
                @RequestParam(defaultValue = "5") int size) {
            return ResponseEntity.ok(productService.getByPriceRange(minPrice, maxPrice, page, size));
        }

        // GET expensive products by category — uses @Query JPQL
        @GetMapping("/expensive")
        public ResponseEntity<PageResponse> getExpensiveByCategory(
                @RequestParam Double minPrice,
                @RequestParam String category,
                @RequestParam(defaultValue = "0") int page,
                @RequestParam(defaultValue = "5") int size) {
            return ResponseEntity.ok(
                    productService.getExpensiveByCategory(minPrice, category, page, size));
        }

        // GET out of stock — uses @Query JPQL
        @GetMapping("/out-of-stock")
        public ResponseEntity<List<ProductResponse>> getOutOfStock() {
            return ResponseEntity.ok(productService.getOutOfStock());
        }

        // GET all categories — uses @Query JPQL
        @GetMapping("/categories")
        public ResponseEntity<List<String>> getAllCategories() {
            return ResponseEntity.ok(productService.getAllCategories());
        }

        // GET budget products — uses native @Query
        @GetMapping("/budget")
        public ResponseEntity<List<ProductResponse>> getBudgetProducts(
                @RequestParam Double maxPrice) {
            return ResponseEntity.ok(productService.getBudgetProducts(maxPrice));
        }

        // POST create product
        @PostMapping
        public ResponseEntity<ProductResponse> createProduct(
                @RequestBody @Valid ProductRequest request) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(productService.createProduct(request));
        }
}
