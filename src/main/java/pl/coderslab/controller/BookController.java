package pl.coderslab.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.exception.BookNotFoundException;
import pl.coderslab.exception.DuplicatedIsbnException;
import pl.coderslab.model.Book;
import pl.coderslab.service.BookService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping(path="/books")
public class BookController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);

    private final BookService bookService;

    @Autowired
    public BookController(@Qualifier("dataBaseBookService") final BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(path = "/")
    public List<Book> findAllBooks() {
        return bookService.findAllBooks();
    }

    @GetMapping(path = "/{id:\\d+}")
    public Book findBookById(@PathVariable final long id) throws BookNotFoundException {
        return bookService.findBookById(id);
    }

    @PostMapping(path = "/")
    public Book addBook(@RequestBody Book book) throws DuplicatedIsbnException {
        return bookService.addBook(book);
    }

    @PutMapping(path = "/{id:\\d+}")
    public Book updateBook(@PathVariable final long id, @RequestBody Book book) throws BookNotFoundException {
        return bookService.editBook(book);
    }

    @DeleteMapping(path = "/{id:\\d+}")
    public boolean deleteBook(@PathVariable final long id) throws BookNotFoundException {
        return bookService.deleteBook(id);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Cannot find requested book id")
    @ExceptionHandler
    public void handleBookNotFoundException(final BookNotFoundException e, HttpServletResponse response) { //tu można np zrobić przekierowanie
        LOGGER.error("Cannot find requested book id", e);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Cannot add book with existing ISBN")
    @ExceptionHandler
    public void handleDuplicatedIsbnException(final DuplicatedIsbnException e, HttpServletResponse response) {
        LOGGER.error("Cannot add book with existing ISBN", e);
    }

}
