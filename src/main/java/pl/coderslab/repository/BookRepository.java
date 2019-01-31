package pl.coderslab.repository;

import pl.coderslab.exception.BookNotFoundException;
import pl.coderslab.exception.DuplicatedIsbnException;
import pl.coderslab.model.Book;

import java.util.List;

public interface BookRepository {

    List<Book> findAllBooks();
    Book findBookById(final long id) throws BookNotFoundException;
    Book addBook(Book book) throws DuplicatedIsbnException;
    Book editBook(Book book);
    boolean deleteBook(final long id);
}
