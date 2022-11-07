package pyankov.springcourse.librarywebapp.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import pyankov.springcourse.librarywebapp.models.Person;

import java.sql.Types;
import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;


    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> getPersonList() {
        return jdbcTemplate.query("select * from person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Optional<Person> showPerson(int id) {
        return jdbcTemplate.query("select * from person where person_id=?", new Object[]{id}, new int[]{Types.INTEGER},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public Optional<Person> showPerson(String fullName) {
        return jdbcTemplate.query("select * from person where fullName=?", new Object[]{fullName},
                new int[]{Types.VARCHAR}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public void addPerson(Person person) {
        jdbcTemplate.update("insert into person(fullName, yearOfBirth) values(?, ?)",
                person.getFullName(), person.getYearOfBirth());
    }

    public void updatePerson(int id, Person updatedPerson) {
        jdbcTemplate.update("update person set fullName=?, yearOfBirth=? where person_id=?",
                updatedPerson.getFullName(), updatedPerson.getYearOfBirth(), id);
    }

    public void deletePerson(int id) {
        jdbcTemplate.update("delete from person where person_id=?", id);
    }
}
