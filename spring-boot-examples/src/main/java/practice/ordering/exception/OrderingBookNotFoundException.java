package practice.ordering.exception;

public class OrderingBookNotFoundException  extends RuntimeException{
    public OrderingBookNotFoundException(Long id){
        super("Book not found with id: " + id);
    }
}
