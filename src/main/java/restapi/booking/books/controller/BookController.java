package restapi.booking.books.controller;

import org.springframework.web.bind.annotation.*;
import restapi.booking.books.entity.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * @RestController = @Controller + @ResponseBody
 * It tells Spring: This class will handle HTTP requests and return data directly (JSON/XML),not a view (HTML page)
 *
 * @RequestMapping is the main annotation used to map HTTP requests to controller methods
 * It maps URL + HTTP method to a specific handler method.
 */
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final List<Book> books = new ArrayList<>();

    public BookController() {
        initalizeBooks();
    }

    private void initalizeBooks() {
        books.addAll(List.of(
                new Book("Title One", "Author One", "Science"),
                new Book("Title Two", "Author Two", "social"),
                new Book("Title three", "Author three", "Telugu"),
                new Book("Title four", "Author four", "social"),
                new Book("Title five", "Author five", "maths"),
                new Book("Title six", "Author six", "English")
        ));
    }

    /**
     * @GetMapping is used to handle HTTP GET requests in a REST API.
     *
     * @RequestParam is used to get values from the URL query string.
     * It binds a value from query parameters (?key=value) to a method parameter.
     */

    @GetMapping
    public List<Book> getBooks(@RequestParam(required = false) String category) {
        if(category == null) {
            return books;
        }
        /*List<Book> filteredBooks = new ArrayList<>();
        for (Book book : books) {
            if(book.getCategory().equalsIgnoreCase(category)) {
                filteredBooks.add(book);
            }
        }
        return filteredBooks;*/
        return books.stream()
                .filter(book -> book.getCategory().equalsIgnoreCase(category))
                .toList();
    }

    /**
     * @PathVariable is used to extract values from the URL path and pass them into your method.
     * It binds a value from the URL to a method parameter.
     * {title} -> placeholder in url
     * @PathVariable String title -> captures that value
     * Spring automatically maps it
     */
    @GetMapping("/{title}")
    public Book getBookByTitle(@PathVariable String title) {
        return books.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);
    }

    @GetMapping("/{title}/filter")
    public List<Book> getBookByTitleAndCategory(@PathVariable String title,
                                                @RequestParam(required = false) String category) {
        return books.stream()
                .filter(book -> book.getTitle() != null &&
                        book.getTitle().trim().equalsIgnoreCase(title.trim()))
                .filter(book -> category == null || category.equalsIgnoreCase(book.getCategory()))
                .toList();
    }

    /**
     *@RequestBody is used to receive data from the client (JSON) and convert it into a Java object.
     * It binds the HTTP request body (JSON) to a Java object.
     * Spring uses Jackson library internally with @RequestBody to map JSON to Java objects
     */
    @PostMapping
    public void createBook(@RequestBody Book newBook) {
        boolean isNewBook = books.stream()
                .noneMatch(book -> book.getTitle().equalsIgnoreCase(newBook.getTitle()));
        if(isNewBook) {
            books.add(newBook);
        }
        /*for(Book book : books) {
            if(book.getTitle().equalsIgnoreCase(newBook.getTitle())) {
                return;
            }
        }
        books.add(newBook);*/
    }

    @PutMapping("/{title}")
    public void updateBook(@PathVariable String title,@RequestBody Book updatedBook) {
        for(int index = 0; index < books.size(); index++) {
            if(books.get(index).getTitle().equalsIgnoreCase(title)) {
                books.set(index, updatedBook);
                return;
            }
        }
    }

    @DeleteMapping("/{title}")
    public void deleteBook(@PathVariable String title) {
        books.removeIf(book -> book.getTitle().equalsIgnoreCase(title));
    }

}

