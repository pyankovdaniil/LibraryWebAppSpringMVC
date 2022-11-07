package pyankov.springcourse.librarywebapp.rowmappers;

import org.springframework.jdbc.core.RowMapper;
import pyankov.springcourse.librarywebapp.models.Book;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Book book = new Book();
        book.setTitle(resultSet.getString("title"));
        book.setAuthor(resultSet.getString("author"));
        book.setYearOfPublishing(resultSet.getInt("yearOfPublishing"));

        return book;
    }
}
