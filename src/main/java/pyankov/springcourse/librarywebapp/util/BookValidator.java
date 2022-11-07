package pyankov.springcourse.librarywebapp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pyankov.springcourse.librarywebapp.dao.BookDAO;
import pyankov.springcourse.librarywebapp.models.Book;

import java.util.Optional;

@Component
public class BookValidator implements Validator {
    private Book bookBeforeChanging;
    private final BookDAO bookDAO;

    @Autowired
    public BookValidator(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;

        if (bookBeforeChanging!= null && bookBeforeChanging.getTitle().equals(book.getTitle()) &&
                bookBeforeChanging.getAuthor().equals(book.getAuthor())) {
            return;
        }

        Optional<Book> bookByTitle = bookDAO.getBookByTitleAndAuthor(book.getTitle(), book.getAuthor());
        if (bookByTitle.isPresent()) {
            errors.rejectValue("title", "", "This book " +
                    "is already exists");
        }
    }

    public void setBookBeforeChanging(Book bookBeforeChanging) {
        this.bookBeforeChanging = bookBeforeChanging;
    }
}
