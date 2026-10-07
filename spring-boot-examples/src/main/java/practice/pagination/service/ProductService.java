package practice.pagination.service;

import com.springboot.practice.pagination.dto.PageResponse;
import com.springboot.practice.pagination.dto.ProductRequest;
import com.springboot.practice.pagination.dto.ProductResponse;
import com.springboot.practice.pagination.entity.Product;
import com.springboot.practice.pagination.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly=true)
public class ProductService {
   private final ProductRepository productRepository;

   public ProductService(ProductRepository productRepository){
       this.productRepository = productRepository;
   }

    // --- GET ALL WITH PAGINATION AND SORTING ---
    public PageResponse getAllProducts(int page, int size, String sortBy, String sortDir){
       Sort sort = sortDir.equalsIgnoreCase("desc")?
               Sort.by(sortBy).descending()
               :Sort.by(sortBy).ascending();
       Pageable pageable = PageRequest.of(page,size, sort);
       Page<Product> productPage = productRepository.findAll(pageable);
       return toPageResponse(productPage);
    }
    // --- GET BY CATEGORY WITH PAGINATION ---
    public PageResponse getByCategory(String category, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productRepository.findByCategory(category, pageable);
        return toPageResponse(productPage);
    }

    // --- SEARCH BY NAME ---
    public PageResponse searchByName(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productRepository
                .findByNameContainingIgnoreCase(name, pageable);
        return toPageResponse(productPage);
    }

    // --- GET BY PRICE RANGE ---
    public PageResponse getByPriceRange(Double minPrice, Double maxPrice, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productRepository
                .findByPriceBetween(minPrice, maxPrice, pageable);
        return toPageResponse(productPage);
    }

    // --- GET EXPENSIVE BY CATEGORY --- uses @Query JPQL
    public PageResponse getExpensiveByCategory(Double minPrice, String category,
                                               int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productRepository
                .findExpensiveByCategory(minPrice, category, pageable);
        return toPageResponse(productPage);
    }

    // --- GET OUT OF STOCK --- uses @Query JPQL
    public List<ProductResponse> getOutOfStock() {
        return productRepository.findOutOfStock()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // --- GET ALL CATEGORIES --- uses @Query JPQL
    public List<String> getAllCategories() {
        return productRepository.findAllCategories();
    }

    // --- GET BUDGET PRODUCTS --- uses native @Query
    public List<ProductResponse> getBudgetProducts(Double maxPrice) {
        return productRepository.findBudgetProducts(maxPrice)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // --- CREATE PRODUCT ---
    @Transactional
    public ProductResponse createProduct(ProductRequest request) {
        Product product = toEntity(request);
        return toResponse(productRepository.save(product));
    }

    // --- private helpers ---
   private PageResponse toPageResponse(Page<Product> page) {
       PageResponse response = new PageResponse();

       response.setContent(page.getContent()
               .stream()
               .map(this::toResponse)
               .collect(Collectors.toList()));
       response.setPageNumber(page.getNumber());
       response.setPageSize(page.getSize());
       response.setTotalElements(page.getTotalElements());
       response.setTotalPages(page.getTotalPages());
       response.setLast(page.isLast());
       return response;
   }
    private ProductResponse toResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setCategory(product.getCategory());
        response.setBrand(product.getBrand());
        response.setPrice(product.getPrice());
        response.setStock(product.getStock());
        return response;
    }
    private Product toEntity(ProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setCategory(request.getCategory());
        product.setBrand(request.getBrand());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        return product;
    }


}
