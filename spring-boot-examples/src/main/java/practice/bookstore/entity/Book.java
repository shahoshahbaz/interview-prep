package practice.bookstore.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name ="books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column(nullable= false, length = 200)
    private String title;

    @Column(nullable= false)
    private String author;

    private String genre;
    private Integer pages;
    private Double price;

    @Enumerated(EnumType.STRING)
    @Column(nullable= false)
    private BookStatus status = BookStatus.DRAFT;

}
