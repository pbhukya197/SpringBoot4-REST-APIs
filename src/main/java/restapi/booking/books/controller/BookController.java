package restapi.booking.books.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import restapi.booking.books.entity.Book;
import restapi.booking.books.exception.BookNotFoundException;
import restapi.booking.books.request.BookRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * @RestController = @Controller + @ResponseBody
 * It tells Spring: This class will handle HTTP requests and return data directly (JSON/XML),not a view (HTML page)
 *
 * @RequestMapping is the main annotation used to map HTTP requests to controller methods
 * It maps URL + HTTP method to a specific handler method.
 */
@Tag(name = "Books REST API Endpoints", description = "Operations Related to Books")
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final List<Book> books = new ArrayList<>();

    public BookController() {
        initalizeBooks();
    }

    private void initalizeBooks() {
        books.addAll(List.of(
                new Book(1,"CORE JAVA","Bert Bates","Computer Science & Engineering",4),
                new Book(2,"Data Structures & Algorithms","Aditya Chatterjee","Computer Science & Engineering",4),
                new Book(3,"Foundations of Mechanical","Sherwin","Mechanical Engineering",3),
                new Book(4,"Communications","Jaya Reddy","Electrical Engineering",2),
                new Book(5,"Emerging Fintech","Paul Taylor","Fintech",3),
                new Book(6,"BlockChain Essentials","Abhilash","Computer Science & Engineering",5),
                new Book(7,"Python Programming","Nageshwara","Computer Science & Engineering",1),
                new Book(8,"The StartUp","Pramod Meher","Business Marketing",2)
        ));
    }

    /**
     * @GetMapping is used to handle HTTP GET requests in a REST API.
     *
     * @RequestParam is used to get values from the URL query string.
     * It binds a value from query parameters (?key=value) to a method parameter.
     */

    @Operation(summary = "Get all books", description = "Retrieve a list of all available books")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Book> getBooks(@Parameter(description = "Optional Query Parameter")
                                   @RequestParam(required = false) String category) {
        if(category == null) {
            return books;
        }
        return books.stream()
                .filter(book -> book.getCategory().equalsIgnoreCase(category))
                .toList();
        /*List<Book> filteredBooks = new ArrayList<>();
        for (Book book : books) {
            if(book.getCategory().equalsIgnoreCase(category)) {
                filteredBooks.add(book);
            }
        }
        return filteredBooks;*/
    }

    /**
     * @PathVariable is used to extract values from the URL path and pass them into your method.
     * It binds a value from the URL to a method parameter.
     * {title} -> placeholder in url
     * @PathVariable String title -> captures that value
     * Spring automatically maps it
     */
    @Operation(summary = "Get a book by ID", description = "Retrieve a specific book by ID")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Book getBookById(@Parameter(description = "Id of book to be retrieved")
                                    @PathVariable @Min(value = 1) long id) {
        return books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElseThrow( () -> new BookNotFoundException("Book with id " + id + " not found"));
    }

    @Operation(summary = "Get a book by ID/Category", description = "Retrieve a book by Id and Category")
    @GetMapping("/{id}/filter")
    public List<Book> getBookByIdAndCategory(@PathVariable long id,
                                                @RequestParam(required = false) String category) {
        return books.stream()
                .filter(book -> book.getId() == id)
                .filter(book -> category == null || category.equalsIgnoreCase(book.getCategory()))
                .toList();
    }

    /**
     *@RequestBody is used to receive data from the client (JSON) and convert it into a Java object.
     * It binds the HTTP request body (JSON) to a Java object.
     * Spring uses Jackson library internally with @RequestBody to map JSON to Java objects
     */
    @Operation(summary = "Create a new book", description = "Add a new book to the list")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createBook(@Valid @RequestBody BookRequest bookRequest) {
        long id = books.isEmpty() ? 1 : books.get(books.size() - 1).getId()+1;
        Book book = convertToBook(id,bookRequest);
        books.add(book);
        /*boolean isNewBook = books.stream()
                .noneMatch(book -> book.getTitle().equalsIgnoreCase(newBook.getTitle()));
        if(isNewBook) {
            books.add(newBook);
        }*/
        /*for(Book book : books) {
            if(book.getTitle().equalsIgnoreCase(newBook.getTitle())) {
                return;
            }
        }
        books.add(newBook);*/
    }

    @Operation(summary = "Update a book", description = "Update the details of existing book")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public Book updateBook(@Parameter(description = "Id of the book Updated")
                                @PathVariable @Min(value = 1) long id,
                                @Valid @RequestBody BookRequest bookRequest) {
        for(int index = 0; index < books.size(); index++) {
            if(books.get(index).getId() == id) {
                Book uptadeBook = convertToBook(index,bookRequest);
                books.set(index, uptadeBook);
                return uptadeBook;
            }
        }
        throw new BookNotFoundException("Book with id " + id + " not found");
    }

    @Operation(summary = "Delete a book", description = "Delete a book from the list")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteBook(@Parameter(description = "Id of book to be delete")
                               @PathVariable @Min(value = 1) long id) {
        books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElseThrow( () -> new BookNotFoundException("Book with id " + id + " not found"));
        books.removeIf(book -> book.getId() == id);
    }

    private Book convertToBook(long id, BookRequest bookRequest) {
        return new Book(
                id,
                bookRequest.getTitle(),
                bookRequest.getAuthor(),
                bookRequest.getCategory(),
                bookRequest.getRating()
        );
    }

}

