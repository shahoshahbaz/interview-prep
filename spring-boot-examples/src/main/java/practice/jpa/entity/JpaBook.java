package practice.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "jpa_books")
public class JpaBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;

    private Double price;

    private String genre;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

}
