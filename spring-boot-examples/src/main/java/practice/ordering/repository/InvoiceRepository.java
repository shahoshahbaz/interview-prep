package practice.ordering.repository;

import com.springboot.practice.ordering.entity.Invoice;
import com.springboot.practice.ordering.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    Optional<Invoice> findByOrder(Order order);
}
