package practice.bookstore.service;

import com.springboot.practice.bookstore.dto.BookRequest;
import com.springboot.practice.bookstore.dto.BookResponse;
import com.springboot.practice.bookstore.entity.Book;
import com.springboot.practice.bookstore.entity.BookStatus;
import com.springboot.practice.bookstore.exception.BookNotFoundException;
import com.springboot.practice.bookstore.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service

public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository){
        this.bookRepository = bookRepository;

    }

    public List<BookResponse> getAllBooks(String genre, int page, int size){
        List<Book> books = (genre != null)?
                bookRepository.findByGenre(genre):
                bookRepository.findAll();

        return books.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }


    public BookResponse getBookById(Long id){

        Book book = bookRepository.findById(id).orElseThrow( () -> new BookNotFoundException(id));

        return toResponse(book);

    }
    public List<BookResponse> getBooksByAuthor(String author){

        return  bookRepository.findByAuthorIgnoreCase(author)
                .stream().map(this::toResponse)
                .collect(Collectors.toList());

    }
    public BookResponse createBook(BookRequest request){

        Book book = toEntity(request);
        Book saved = bookRepository.save(book);
        return toResponse(saved);
    }

    public BookResponse updateBook(Long id, BookRequest request){
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setGenre(request.getGenre());
        book.setPages(request.getPages());
        book.setPrice(request.getPrice());

        return toResponse(bookRepository.save(book));
    }
    public BookResponse updateTitle(Long id, String newTitle){

        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));

        book.setTitle(newTitle);

        return toResponse(bookRepository.save(book));
    }
    public void deleteBook(Long id){

        if(!bookRepository.existsById(id)){
            throw new BookNotFoundException(id);
        }

        bookRepository.deleteById(id);

    }
    public BookResponse publishBook(Long id){
        Book book = bookRepository.findById(id).
                orElseThrow(()-> new BookNotFoundException(id));

        book.setStatus(BookStatus.PUBLISHED);
        return toResponse(bookRepository.save(book ));
    }





    private BookResponse toResponse(Book book) {
        BookResponse response = new BookResponse();
        response.setId(book.getId());
        response.setTitle(book.getTitle());
        response.setAuthor(book.getAuthor());
        response.setGenre(book.getGenre());
        response.setPages(book.getPages());
        response.setPrice(book.getPrice());
        response.setStatus(book.getStatus());
        return response;
    }
    private Book toEntity(BookRequest request) {
        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setGenre(request.getGenre());
        book.setPages(request.getPages());
        book.setPrice(request.getPrice());
        return book;
    }

}
