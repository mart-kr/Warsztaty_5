package pl.coderslab.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.exception.BookNotFoundException;
import pl.coderslab.exception.DuplicatedIsbnException;
import pl.coderslab.model.Book;
import pl.coderslab.repository.BookRepository;
import pl.coderslab.service.BookService;

import java.util.List;

@Service
public class DataBaseBookService implements BookService {

    private final BookRepository bookRepository;

    @Autowired
    public DataBaseBookService(final BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> findAllBooks() {
        return bookRepository.findAllBooks();
    }

    @Override
    public Book findBookById(long id) throws BookNotFoundException {
        return bookRepository.findBookById(id);
    }

    @Override
    public Book addBook(Book book) throws DuplicatedIsbnException {
        return bookRepository.addBook(book);
    }

    @Override
    public Book editBook(Book book) {
        return bookRepository.editBook(book);
    }

    @Override
    public boolean deleteBook(long id) {
        return bookRepository.deleteBook(id);
    }
}
