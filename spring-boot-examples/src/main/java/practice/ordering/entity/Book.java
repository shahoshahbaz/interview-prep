package practice.ordering.entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name= "OrderingBook")
@Table(name = "ordering_books")

public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer stock;
}
