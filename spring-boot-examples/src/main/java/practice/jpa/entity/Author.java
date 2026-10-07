package practice.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name="jpa_authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    @OneToMany(mappedBy ="author", fetch = FetchType.LAZY, cascade= CascadeType.ALL, orphanRemoval = true )
    private List<JpaBook> JpaBooks;
}
