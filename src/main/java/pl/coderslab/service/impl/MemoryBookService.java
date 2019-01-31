package pl.coderslab.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.coderslab.exception.BookNotFoundException;
import pl.coderslab.model.Book;
import pl.coderslab.service.BookService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class MemoryBookService implements BookService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MemoryBookService.class);

    private List<Book> books;

    public MemoryBookService() {
        books = new ArrayList<>();
        books.add(new Book(1L, "9788324631766", "Thinking in Java", "Bruce Eckel", "Helion", "programming"));
        books.add(new Book(2L, "9788324627738", "Rusz glowa, Java.", "Sierra Kathy, Bates Bert", "Helion", "programming"));
        books.add(new Book(3L, "9780130819338", "Java 2. Podstawy", "Cay Horstmann, Gary Cornell", "Helion", "programming"));
    }

    @Override
    public List<Book> findAllBooks() {
        return books;
    }

    @Override
    public Book findBookById(final long id) throws BookNotFoundException {

        return books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElseThrow(() ->  new BookNotFoundException());
    }

    @Override
    public Book addBook(Book book) {
        book.setId(books.stream().mapToLong(
                p -> p.getId()).max().getAsLong() +1);
        books.add(book);
        return book;
    }

    @Override
    public Book editBook(Book book) throws BookNotFoundException {
        for (int i = 0; i < books.size(); i++) {
            if (Objects.equals(books.get(i).getId(), book.getId())) {
                books.set(i, book);
                return book;
            }
        }
        throw new BookNotFoundException();
    }

    @Override
    public boolean deleteBook(long id) throws BookNotFoundException {
        for (int i = 0; i < books.size(); i++) {
            if (Objects.equals(books.get(i).getId(), id)) {
                books.remove(i);
                return true;
            }
        }
        throw new BookNotFoundException();
    }
}

