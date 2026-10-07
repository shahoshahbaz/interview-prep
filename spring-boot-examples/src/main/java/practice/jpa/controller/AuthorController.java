package practice.jpa.controller;

import com.springboot.practice.jpa.dto.AuthorRequest;
import com.springboot.practice.jpa.dto.AuthorResponse;
import com.springboot.practice.jpa.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService){
        this.authorService = authorService;

    }

    // GET all authors — demonstrates N+1 problem
    // watch the SQL console when you call this
    @GetMapping
    public ResponseEntity<List<AuthorResponse>> getAllAuthors(){

        return ResponseEntity.ok(authorService.getAllAuthors());
    }

    // GET all authors — N+1 fixed with JOIN FETCH
    // compare SQL console output with the endpoint above
    @GetMapping("/fixed")
    public ResponseEntity<List<AuthorResponse>> getAllAuthorsFixed(){
        return ResponseEntity.ok(authorService.getAllAuthorsFixed());
    }


    // Get author by ID
    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponse> getAuthorById(@PathVariable Long id){
        return ResponseEntity.ok(authorService.getAuthorById(id));
    }
    // POST Create author
    @PostMapping
    public ResponseEntity<AuthorResponse> createAuthor(
            @RequestBody @Valid AuthorRequest request
            ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authorService.createAuthor(request));
    }


    // DELETE author
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id){
        authorService.deleteAuthor(id);

        return ResponseEntity.noContent().build();
    }

}
