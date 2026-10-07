package practice.ordering.service;

import com.springboot.practice.bookstore.exception.BookNotFoundException;
import com.springboot.practice.ordering.dto.OrderRequest;
import com.springboot.practice.ordering.dto.OrderResponse;
import com.springboot.practice.ordering.entity.Book;
import com.springboot.practice.ordering.entity.Invoice;
import com.springboot.practice.ordering.entity.Order;
import com.springboot.practice.ordering.entity.OrderStatus;
import com.springboot.practice.ordering.exception.InsufficientStockException;
import com.springboot.practice.ordering.exception.OrderNotFoundException;
import com.springboot.practice.ordering.repository.OrderingBookRepository;
import com.springboot.practice.ordering.repository.InvoiceRepository;
import com.springboot.practice.ordering.repository.OrderRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service

@Transactional(readOnly = true)
public class OrderService {

    private static final double TAX_RATE = 0.13;

    private final OrderingBookRepository bookRepository;
    private final OrderRepository orderRepository;
    private final InvoiceRepository invoiceRepository;

    public OrderService(OrderingBookRepository bookRepository,
                        OrderRepository orderRepository,
                        InvoiceRepository invoiceRepository){
        this.bookRepository = bookRepository;
        this.orderRepository = orderRepository;
        this.invoiceRepository = invoiceRepository;
    }

    public OrderResponse placeOrder(OrderRequest request){

        // step 1: find the book or throw

        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() ->new BookNotFoundException(request.getBookId()));

        // step 2: check stock
        if(book.getStock()< request.getQuantity()){
            throw new InsufficientStockException(
                    request.getBookId(),
                    request.getQuantity(),
                    book.getStock());
        }

        // step 3: deduct stock

        book.setStock(book.getStock() - request.getQuantity());
        bookRepository.save(book);

        // step 4: create order

        Order order = new Order();
        order.setBook(book);
        order.setQuantity(request.getQuantity());
        order.setTotalPrice(book.getPrice() * request.getQuantity());
        order.setStatus(OrderStatus.CONFIRMED);
        Order savedOrder = orderRepository.save(order);

        // step 5- create invoice
        Invoice invoice = createInvoice(savedOrder, request.isSimulateInvoiceFailure());

        return toResponse(savedOrder, invoice);
    }

    public Invoice createInvoice(Order order, boolean simulationFlag){
        double tax = order.getTotalPrice() * TAX_RATE;
        double grandTotal = order.getTotalPrice() + tax;
        Invoice invoice = new Invoice();

        invoice.setOrder(order);
        invoice.setAmount(order.getTotalPrice());
        invoice.setTax(tax);
        invoice.setGrandTotal(grandTotal);

        // SIMULATE FAILURE
         if (simulationFlag) throw new RuntimeException("Invoice service crashed!");

        return invoiceRepository.save(invoice);

    }


    public OrderResponse getOrder(Long orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()->new OrderNotFoundException(orderId));
        Invoice invoice = invoiceRepository.findByOrder(order)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
        return toResponse(order, invoice);
    }

    private OrderResponse toResponse(Order order, Invoice invoice){
        OrderResponse response = new OrderResponse();

        response.setOrderId(order.getId());
        response.setBookTitle(order.getBook().getTitle());
        response.setQuantity(order.getQuantity());
        response.setTotalPrice(order.getTotalPrice());
        response.setStatus(order.getStatus());
        response.setInvoiceId(invoice.getId());
        response.setAmount(invoice.getAmount());
        response.setTax(invoice.getTax());
        response.setGrandTotal(invoice.getGrandTotal());
        return response;


    }


}
