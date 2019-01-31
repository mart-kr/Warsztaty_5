package pl.coderslab.service;

import pl.coderslab.exception.BookNotFoundException;
import pl.coderslab.exception.DuplicatedIsbnException;
import pl.coderslab.model.Book;

import java.util.List;

public interface BookService {

    List<Book> findAllBooks();
    Book findBookById(final long id) throws BookNotFoundException;
    Book addBook(Book book) throws DuplicatedIsbnException;
    Book editBook(Book book) throws BookNotFoundException;
    boolean deleteBook(final long id) throws BookNotFoundException;

}
