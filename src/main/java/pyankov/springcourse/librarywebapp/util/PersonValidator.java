package pyankov.springcourse.librarywebapp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pyankov.springcourse.librarywebapp.dao.PersonDAO;
import pyankov.springcourse.librarywebapp.models.Person;

@Component
public class PersonValidator implements Validator {
    private Person personBeforeChanging;
    private final PersonDAO personDAO;

    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        if (personBeforeChanging != null && personBeforeChanging.getFullName().equals(person.getFullName())) {
            return;
        }

        if (personDAO.showPerson(person.getFullName()).isPresent()) {
            errors.rejectValue("fullName", "", "This full name " +
                    "is already taken");
        }
    }

    public void setPersonBeforeChanging(Person personBeforeChanging) {
        this.personBeforeChanging = personBeforeChanging;
    }
}
