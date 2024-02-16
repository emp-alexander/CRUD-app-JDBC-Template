package app.model;


import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;


public class Person {
    private int id;
    @NotEmpty(message = "FIO should not be empty")
    @Size(min = 10, max = 150, message = "FIO should be between 10 and 150 characters")
    private String fio;
   // @Min(value = 1900, message = "Year of birth should be greater than 1900")
    private String yearOfBirth;

    public Person() {
    }

    public Person(int id, String fio, String yearOfBirth) {
        this.id = id;
        this.fio = fio;
        this.yearOfBirth = yearOfBirth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(String yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

}
