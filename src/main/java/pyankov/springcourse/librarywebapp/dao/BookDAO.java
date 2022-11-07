package pyankov.springcourse.librarywebapp.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import pyankov.springcourse.librarywebapp.models.Book;

import java.sql.Types;
import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getBookList() {
        return jdbcTemplate.query("select * from book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Optional<Book> showBook(int id) {
        return jdbcTemplate.query("select * from book where book_id=?", new Object[]{id}, new int[]{Types.INTEGER},
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny();
    }

    public Optional<Book> getBookByTitleAndAuthor(String title, String author) {
        return jdbcTemplate.query("select * from book where title=? and author=?", new Object[]{title, author},
                new int[]{Types.VARCHAR, Types.VARCHAR},
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny();
    }

    public List<Book> getBooksByPersonId(int id) {
        return jdbcTemplate.query("select * from book where person_id=?", new Object[]{id},
                new int[]{Types.INTEGER}, new BeanPropertyRowMapper<>(Book.class));
    }

    public void addBook(Book book) {
        jdbcTemplate.update("insert into book(title, author, yearOfPublishing) values(?, ?, ?)",
                book.getTitle(), book.getAuthor(), book.getYearOfPublishing());
    }

    public void updateBook(int id, Book book) {
        jdbcTemplate.update("update book set title=?, author=?, yearOfPublishing=? where book_id=?",
                book.getTitle(), book.getAuthor(), book.getYearOfPublishing(), id);
    }

    public void deleteBook(int id) {
        jdbcTemplate.update("delete from book where book_id=?", id);
    }

    public int getBookOwnerId(int id) {
        return jdbcTemplate.query("select person_id from book where book_id=?", new Object[]{id},
                new int[]{Types.INTEGER}, resultSet -> {
                    if (resultSet.next()) {
                        return resultSet.getInt("person_id");
                    }
                    return Integer.parseInt("0");
                });
    }

    public void addOwnerById(int id, int personId) {
        jdbcTemplate.update("update book set person_id=? where book_id=?", personId, id);
    }

    public void removeOwner(int id) {
        jdbcTemplate.update("update book set person_id=null where book_id=?", id);
    }
}
