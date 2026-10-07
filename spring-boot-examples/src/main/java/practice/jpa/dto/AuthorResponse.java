package practice.jpa.dto;

import lombok.Data;

import java.util.List;
@Data
public class AuthorResponse {

    private Long id;
    private String name;
    private String email;
    private List<BookSummary> books;
}
