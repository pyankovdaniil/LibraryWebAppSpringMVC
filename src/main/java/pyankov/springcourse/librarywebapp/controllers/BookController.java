package pyankov.springcourse.librarywebapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pyankov.springcourse.librarywebapp.dao.BookDAO;
import pyankov.springcourse.librarywebapp.dao.PersonDAO;
import pyankov.springcourse.librarywebapp.models.Book;
import pyankov.springcourse.librarywebapp.models.Person;
import pyankov.springcourse.librarywebapp.util.BookValidator;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;
    private final BookValidator bookValidator;

    @Autowired
    public BookController(BookDAO booksDAO, PersonDAO personDAO, BookValidator booksValidator) {
        this.bookDAO = booksDAO;
        this.personDAO = personDAO;
        this.bookValidator = booksValidator;
    }

    @GetMapping()
    public String bookList(Model model) {
        List<Book> books = bookDAO.getBookList();
        model.addAttribute("books", books);
        return "/books/bookList";
    }

    @GetMapping("/new")
    public String createNewBookFrom(@ModelAttribute("book") Book book) {
        return "/books/newBookForm";
    }

    @PostMapping()
    public String createNewBook(@ModelAttribute("book") @Valid Book book,
                                  BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()) {
            return "books/newBookForm";
        }
        bookDAO.addBook(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String showBookById(@PathVariable("id") int id, @ModelAttribute("person") Person person,
                               Model model) {
        Optional<Book> book = bookDAO.showBook(id);
        if (book.isPresent()) {
            model.addAttribute("book", book.get());
        } else {
            return "/books/noSuchBookWithId";
        }
        Optional<Person> bookOwner = personDAO.showPerson(bookDAO.getBookOwnerId(id));
        if (bookOwner.isPresent()) {
            model.addAttribute("bookOwner", bookOwner.get());
        } else {
            model.addAttribute("bookOwner", null);
        }
        model.addAttribute("people", personDAO.getPersonList());
        model.addAttribute("person", person);
        return "/books/showBookInfo";
    }

    @PatchMapping("/add_owner/{id}")
    public String addOwner(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
        bookDAO.addOwnerById(id, person.getPersonId());
        return "redirect:/books";
    }

    @PatchMapping("/remove_owner/{id}")
    public String removeOwner(@PathVariable("id") int id) {
        bookDAO.removeOwner(id);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String editBookForm(@PathVariable("id") int id, Model model) {
        Optional<Book> book = bookDAO.showBook(id);
        if (book.isPresent()) {
            model.addAttribute("book", book.get());
            bookValidator.setBookBeforeChanging(book.get());
        } else {
            return "books/noSuchBookWithId";
        }
        return "books/editBookForm";
    }

    @PatchMapping("/{id}")
    public String updateBook(@PathVariable("id") int id,
                               @ModelAttribute("book") @Valid Book book,
                               BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);

        if (bindingResult.hasErrors()) {
            return "books/editBookForm";
        }
        bookDAO.updateBook(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        bookDAO.deleteBook(id);
        return "redirect:/books";
    }

}
