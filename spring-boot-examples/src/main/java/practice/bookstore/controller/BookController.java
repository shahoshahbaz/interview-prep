package practice.bookstore.controller;

import com.springboot.practice.bookstore.dto.BookRequest;
import com.springboot.practice.bookstore.dto.BookResponse;
import com.springboot.practice.bookstore.entity.Book;
import com.springboot.practice.bookstore.service.BookService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookResponse>> getAllBooks(@RequestParam(required = false) String genre,
                                                          @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size){

        return ResponseEntity.ok(bookService.getAllBooks(genre,page, size));
    }


    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable @Min(1) Long id){
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @GetMapping("/author/{authorName}")
    public ResponseEntity<List<BookResponse>> getBooksByAuthor(@PathVariable  String authorName){

        return ResponseEntity.ok(bookService.getBooksByAuthor(authorName));

    }
    @PostMapping
    public ResponseEntity<BookResponse> createBook(@RequestBody @Valid BookRequest bookRequest){

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bookService.createBook(bookRequest));

    }
    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(@PathVariable Long id, @RequestBody @Valid BookRequest request){
        return ResponseEntity.ok(bookService.updateBook(id, request));
    }

    @PatchMapping("/{id}/title")
    public ResponseEntity<BookResponse> updateTitle(@PathVariable Long id, @RequestParam String newTitle){

        return ResponseEntity.ok(bookService.updateTitle(id, newTitle));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/publish")
    public ResponseEntity<BookResponse> publishBook(@PathVariable Long id){

        return ResponseEntity.ok(bookService.publishBook(id));
    }




}
