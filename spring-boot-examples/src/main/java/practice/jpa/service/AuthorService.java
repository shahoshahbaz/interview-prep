package practice.jpa.service;


import com.springboot.practice.jpa.dto.AuthorRequest;
import com.springboot.practice.jpa.dto.AuthorResponse;
import com.springboot.practice.jpa.dto.BookSummary;
import com.springboot.practice.jpa.entity.Author;
import com.springboot.practice.jpa.entity.JpaBook;
import com.springboot.practice.jpa.exception.AuthorNotFoundException;
import com.springboot.practice.jpa.repository.AuthorRepository;
import com.springboot.practice.jpa.repository.JpaBookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly= true)
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final JpaBookRepository bookRepository;

    public AuthorService(AuthorRepository authorRepository, JpaBookRepository bookRepository){
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    // --- GET ALL AUTHORS ---
    // demonstrates N+1 problem — watch the SQL in console
    public List<AuthorResponse> getAllAuthors(){
        List<Author> authors = authorRepository.findAll();
        // for each author, accessing books trigger a seperate sqsl query N+1

        return authors.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // --- GET ALL AUTHORS — N+1 FIXED ---
    // uses JOIN FETCH — loads everything in one single query
    public List<AuthorResponse> getAllAuthorsFixed()
    {
        return bookRepository.findAllWithAuthor()
                .stream()
                .map(book -> book.getAuthor())
                .distinct()
                .map(this:: toResponse)
                .collect(Collectors.toList());
    }

    // --- GET AUTHOR BY ID ---
    public AuthorResponse getAuthorById(Long id){

        Author author = authorRepository.findById(id)
                .orElseThrow(() ->new AuthorNotFoundException(id));

        return toResponse(author);
    }

    @Transactional
    public AuthorResponse createAuthor(AuthorRequest request) {

        Author author = new Author();
        author.setName(request.getName());
        author.setEmail(request.getEmail());
        Author saved = authorRepository.save(author);

        return toResponse(saved);
    }

    @Transactional
    public void deleteAuthor(Long id){
        if(!authorRepository.existsById(id)){
            throw new AuthorNotFoundException(id);
        }

        authorRepository.deleteById(id);
    }

    private AuthorResponse toResponse(Author author) {
        AuthorResponse response = new AuthorResponse();
        response.setId(author.getId());
            response.setEmail(author.getEmail());
        response.setName(author.getName());
        List<BookSummary>  books = author.getJpaBooks() == null ? List.of() :
                author.getJpaBooks().stream()
                        .map(this:: toBookSummary)
                                .collect(Collectors.toList());

        response.setBooks(books);
        return response;
    }
    private BookSummary toBookSummary(JpaBook jpaBook){
        BookSummary summary = new BookSummary();
        summary.setId(jpaBook.getId());
        summary.setTitle(jpaBook.getTitle());
        summary.setGenre(jpaBook.getGenre());
        summary.setPrice(jpaBook.getPrice());
        return summary;
    }

}
