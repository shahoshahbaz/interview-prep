package practice.jpa.repository;

import com.springboot.practice.jpa.entity.JpaBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JpaBookRepository extends JpaRepository<JpaBook, Long> {
    List<JpaBook> findByGenre(String genre);

    // fix N+1 -load books and authors in one single Query
    @Query("SELECT b FROM JpaBook b JOIN FETCH b.author")
    List<JpaBook> findAllWithAuthor();
}
