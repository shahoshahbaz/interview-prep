package practice.pagination.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductRequest {

    @NotBlank
    private String name;
    @NotBlank
    private String category;
    @NotBlank
    private String brand;
    @NotNull
    @DecimalMin("0.0")
    private Double price;
    @NotNull
    @Min(0)
    private Integer stock;

}
