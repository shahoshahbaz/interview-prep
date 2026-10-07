package practice.ordering.repository;

import com.springboot.practice.ordering.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderingBookRepository extends JpaRepository<Book, Long> {
}
