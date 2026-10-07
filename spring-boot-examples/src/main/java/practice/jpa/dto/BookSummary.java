package practice.jpa.dto;

import lombok.Data;

@Data
public class BookSummary {

    private Long id;
    private String title;
    private String genre;
    private Double price;

}
