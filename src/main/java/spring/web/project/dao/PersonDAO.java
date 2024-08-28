package spring.web.project.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import spring.web.project.models.Person;

import java.util.List;

@Component
public class PersonDAO {

    static JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public PersonDAO() {}

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }

    public int add(Person person) {
        // Отримуємо максимальне значення id з таблиці person
        Integer maxId = jdbcTemplate.queryForObject("SELECT COALESCE(MAX(id), 0) FROM person", Integer.class);

        // Встановлюємо новий id для персони
        int newId = (maxId != null ? maxId : 0) + 1;
        person.setId(newId);

        // Виконуємо вставку нової персони в таблицю person
        jdbcTemplate.update("INSERT INTO person (id, name, surname, email, password, photo) VALUES (?, ?, ?, ?, ?, ?)",
                person.getId(),
                person.getName(),
                person.getSurname(),
                person.getEmail(),
                person.getPassword(),
                person.getPhoto()
        );

        return person.getId();
    }


    public Person show(int id) {
        return jdbcTemplate.queryForObject("select * from person where id = ?", new BeanPropertyRowMapper<>(Person.class), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE id = ?", id);
    }

    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE person SET name = ?, surname = ?, email = ?, password = ?, photo = ? WHERE id = ?",
                updatedPerson.getName(),
                updatedPerson.getSurname(),
                updatedPerson.getEmail(),
                updatedPerson.getPassword(),
                updatedPerson.getPhoto(),
                id
        );
    }

    public Boolean checkEmail(String email, int id) {
        int count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM person WHERE email = ? AND id <> ?",
                Integer.class,
                email,
                id
        );

        return count == 0;
    }

    public Person checkLogin(String email, String password) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM person WHERE email = ? AND password = ?",
                    new BeanPropertyRowMapper<>(Person.class),
                    email, password
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}