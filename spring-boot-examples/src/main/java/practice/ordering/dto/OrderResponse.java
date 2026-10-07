package practice.ordering.dto;

import com.springboot.practice.ordering.entity.OrderStatus;
import lombok.Data;

@Data
public class OrderResponse {
    private Long orderId;
    private String bookTitle;
    private Integer quantity;
    private Double totalPrice;
    private OrderStatus status;

    // invoice details embedded in the response
    private Long invoiceId;
    private Double amount;
    private Double tax;
    private Double grandTotal;
}
