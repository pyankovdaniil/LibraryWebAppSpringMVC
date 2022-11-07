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
import pyankov.springcourse.librarywebapp.util.PersonValidator;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/people")
public class PersonController {
    private final PersonDAO personDAO;
    private final BookDAO bookDAO;
    private final PersonValidator personValidator;

    @Autowired
    public PersonController(PersonDAO personDAO, BookDAO bookDAO, PersonValidator personValidator) {
        this.personDAO = personDAO;
        this.bookDAO = bookDAO;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public String personList(Model model) {
        List<Person> personList = personDAO.getPersonList();
        model.addAttribute("people", personList);
        return "/people/personList";
    }

    @GetMapping("/new")
    public String createNewPersonFrom(@ModelAttribute("person") Person person) {
        return "/people/newPersonForm";
    }

    @PostMapping()
    public String createNewPerson(@ModelAttribute("person") @Valid Person person,
                                  BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "people/newPersonForm";
        }
        personDAO.addPerson(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}")
    public String showPersonById(@PathVariable("id") int id, Model model) {
        Optional<Person> person = personDAO.showPerson(id);
        if (person.isPresent()) {
            model.addAttribute("person", person.get());
        } else {
            return "/people/noSuchPersonWithId";
        }
        List<Book> personBooks = bookDAO.getBooksByPersonId(id);
        model.addAttribute("books", personBooks);
        return "/people/showPersonInfo";
    }

    @GetMapping("/{id}/edit")
    public String editPersonForm(@PathVariable("id") int id, Model model) {
        Optional<Person> person = personDAO.showPerson(id);
        if (person.isPresent()) {
            personValidator.setPersonBeforeChanging(person.get());
            model.addAttribute("person", person.get());
        } else {
            return "people/noSuchPersonWithId";
        }
        return "people/editPersonForm";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@PathVariable("id") int id,
                               @ModelAttribute("person") @Valid Person person,
                               BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            return "people/editPersonForm";
        }
        personDAO.updatePerson(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id) {
        personDAO.deletePerson(id);
        return "redirect:/people";
    }
}
