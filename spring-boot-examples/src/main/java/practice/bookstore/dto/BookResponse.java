package practice.bookstore.dto;

import com.springboot.practice.bookstore.entity.BookStatus;
import lombok.Data;

@Data

public class BookResponse {

    private Long id;
    private String title;
    private String author;
    private String genre;
    private Integer pages;
    private Double price;
    private BookStatus status;
}
