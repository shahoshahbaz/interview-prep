package practice.bookstore.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookRequest {

    @NotBlank(message = "Title is required")

    private String title;
    @NotBlank(message = "Author is required")
    private String author;
    @NotBlank(message = "Genre is required")
    private String genre;
    @NotNull(message = "Pages is required")
    @Min(value = 1, message = "Pages must be at least 1")
    private Integer pages;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")

    private Double price;
}
