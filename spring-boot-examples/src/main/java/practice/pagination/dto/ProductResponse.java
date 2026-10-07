package practice.pagination.dto;

import lombok.Data;

@Data
public class ProductResponse {

    private Long id;
    private String name;
    private String category;
    private String brand;
    private Double price;
    private Integer stock;
}
