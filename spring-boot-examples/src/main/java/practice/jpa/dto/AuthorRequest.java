package practice.jpa.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthorRequest {

    @NotBlank
    private String name;
    @NotBlank
    private String email;
}
