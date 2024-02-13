package app.dao;

import app.model.Book;
import app.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;


    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {

        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    //получения списка книг взятых определенным человеком
    public List<Book> getBooksByPerson(int id){
        return jdbcTemplate.query("SELECT * FROM BOOK WHERE Person_id=?", new BeanPropertyRowMapper<>(Book.class), id);
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO Person(fio, year_of_birth) VALUES (?, ?)", person.getFio(),
                person.getYearOfBirth());
    }

    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE Person SET fio=?, year_of_birth=? WHERE id=?", updatedPerson.getFio(),
                updatedPerson.getYearOfBirth(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
    }
}
