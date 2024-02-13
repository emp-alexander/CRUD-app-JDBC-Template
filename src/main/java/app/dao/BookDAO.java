package app.dao;

import app.model.Book;
import app.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index(){
        return jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Optional<Person> getBookOwner(int id){
        return jdbcTemplate.query("SELECT p.id, p.fio, p.year_of_birth FROM Person as p JOIN Book as b on p.id = b.person_id WHERE b.id=?", new BeanPropertyRowMapper<>(Person.class), id).stream().findAny();
    }
    public List<Book> getBooksByPerson(int id){
        return jdbcTemplate.query("SELECT * FROM BOOK WHERE Person_id=?", new BeanPropertyRowMapper<>(Book.class), id);
    }

    public Book show(int id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO Book(name, author, year) VALUES (?, ?, ?)", book.getName(), book.getAuthor(), book.getYear());
    }

    public void update(int id, Book updateBook) {
        jdbcTemplate.update("UPDATE Book SET name=?, author=?, year=? WHERE id=?", updateBook.getName(), updateBook.getAuthor(),
                updateBook.getYear(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Book WHERE id=?", id);
    }

    //освободить книгу
    public void release(int id) {
        jdbcTemplate.update("UPDATE Book SET person_id = NULL WHERE id= ?", id);
    }
    //назначить книгу
    public void assign(int id, Person selectPerson){
        jdbcTemplate.update("UPDATE Book SET person_id = ? WHERE id=?", selectPerson.getId(), id);
    }
}
