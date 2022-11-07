package pyankov.springcourse.librarywebapp.rowmappers;

import org.springframework.jdbc.core.RowMapper;
import pyankov.springcourse.librarywebapp.models.Person;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Person person = new Person();
        person.setFullName(resultSet.getString("fullName"));
        person.setYearOfBirth(resultSet.getInt("yearOfBirth"));
        return person;
    }
}
