package pyankov.springcourse.librarywebapp.models;

import javax.validation.constraints.*;

public class Person {
    @NotEmpty(message = "Full name should be not empty!")
    @Pattern(regexp = "[А-Я][а-я]+ [А-Я][а-я]+ [А-Я][а-я]+",
            message = "Please enter: Surname, Name, Middle Name")
    @Size(min = 8, max = 50, message = "Full name should be  of 8 <= size <= 100")
    private String fullName;

    @Min(value = 1940, message = "Year of birth should be >= 1940")
    @Max(value = 2022, message = "Year of birth should be <= 2022")
    private int yearOfBirth;

    private int personId;

    public Person() {
    }

    public Person(String fullName, int yearOfBirth) {
        this.fullName = fullName;
        this.yearOfBirth = yearOfBirth;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    @Override
    public String toString() {
        return this.fullName + ", " + this.yearOfBirth;
    }
}
