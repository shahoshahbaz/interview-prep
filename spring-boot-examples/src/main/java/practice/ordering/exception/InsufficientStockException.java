package practice.ordering.exception;

public class InsufficientStockException  extends RuntimeException{
    public InsufficientStockException(Long bookId, int requested, int available){
        super("Insufficient stock for book id: " + bookId +
                "- requested: "+ requested +
                ", available: " + available);
    }
}
