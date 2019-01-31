package pl.coderslab.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import pl.coderslab.exception.BookNotFoundException;
import pl.coderslab.exception.DuplicatedIsbnException;
import pl.coderslab.model.Book;
import pl.coderslab.repository.BookRepository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcBookRepository extends NamedParameterJdbcDaoSupport implements BookRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcBookRepository.class);

    private static final String SELECT_ALL_BOOKS = "select * from book";
    private static final String SELECT_BOOK_BY_ID = "select * from book b where b.id = :id";
    private static final String INSERT_BOOK = "insert into book(title, author, publisher, isbn, type) values(:title, :author, :publisher, :isbn, :type)";
    private static final String UPDATE_BOOK = "update book b set b.title = :title, b.author = :author, b.publisher = :publisher, b.isbn = :isbn, b.type = :type where b.id = :id";
    private static final String DELETE_BOOK = "delete from book where id = :id";

    @Autowired
    public JdbcBookRepository(final DataSource dataSource) {
        setDataSource(dataSource);
    }

    @Override
    public List<Book> findAllBooks() {
        try {
            return getJdbcTemplate().query(SELECT_ALL_BOOKS, bookRowMapper);
        } catch (final DataAccessException e) {
            LOGGER.error("Cannot find all books!", e);
            throw e;
        }
    }

    @Override
    public Book findBookById(long id) {
        final Map<String, Long> parameters = Collections.singletonMap("id", id);

        try {
            return getNamedParameterJdbcTemplate().queryForObject(SELECT_BOOK_BY_ID, parameters, bookRowMapper);
        } catch (final DataAccessException e) {
            LOGGER.error("Cannot find book with id: {}", id);
            throw e;
        }
    }

    @Override
    public Book addBook(Book book) throws DuplicatedIsbnException {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameters = getParameters(book);

        try {
            getNamedParameterJdbcTemplate().update(INSERT_BOOK, parameters, keyHolder);

            long id = Optional.ofNullable(keyHolder.getKey())
                    .map(Number::longValue)
                    .orElseThrow(() -> new DataAccessException("Cannot add book to the database") {
                    });
            book.setId(id);
            return book;
        } catch (final DataAccessException e) {
            LOGGER.error("Cannot add book - ISBN already exists - title: {}, author: {}, ISBN: {}", book.getTitle(), book.getAuthor(), book.getIsbn());
            throw new DuplicatedIsbnException();
        }
    }

    @Override
    public Book editBook(Book book) {
        MapSqlParameterSource parameters = getParameters(book);

        try {
            getNamedParameterJdbcTemplate().update(UPDATE_BOOK, parameters);
            return book;
        } catch (final DataAccessException e) {
            LOGGER.error("Cannot edit book with id: {}", book.getId());
            throw e;
        }
    }

    @Override
    public boolean deleteBook(long id) {
        final Map<String, Long> parameters = Collections.singletonMap("id", id);

        try {
            return getNamedParameterJdbcTemplate().update(DELETE_BOOK, parameters) > 0;
        } catch (DataAccessException e) {
            LOGGER.error("Cannot delete book with id: {}", id);
            throw e;
        }
    }

    private MapSqlParameterSource getParameters(Book book) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("title", book.getTitle());
        parameters.addValue("author", book.getAuthor());
        parameters.addValue("publisher", book.getPublisher());
        parameters.addValue("isbn", book.getIsbn());
        parameters.addValue("type", book.getType());
        parameters.addValue("id", book.getId());

        return parameters;
    }

    private final RowMapper<Book> bookRowMapper = new RowMapper<Book>() {
        @Override
        public Book mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
            long id = resultSet.getLong("id");
            String isbn = resultSet.getString("isbn");
            String title = resultSet.getString("title");
            String author = resultSet.getString("author");
            String publisher = resultSet.getString("publisher");
            String type = resultSet.getString("type");

            return new Book(id, isbn, title, author, publisher, type);
        }
    };
}
