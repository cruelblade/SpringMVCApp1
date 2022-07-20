package ru.alishev.springcourse.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import ru.alishev.springcourse.models.Book;
import ru.alishev.springcourse.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping()
    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Optional<Person> show(String name) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE name=?", new Object[]{name}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny();
    }

    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO Person (name, age, address) VALUES(?, ?, ?)", person.getName(), person.getAge(), person.getAddress());
    }

    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE Person SET name=?, age=?, address=? WHERE id=?", updatedPerson.getName(), updatedPerson.getAge(), updatedPerson.getAddress(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
    }

//    public int extractBooks(int id) {
//        return jdbcTemplate.queryForObject("SELECT id FROM books WHERE person_id=?",
//                new Object[]{id}, Integer.class);
//    }

    @GetMapping()
    public List<Book> extractBooks(int id) {
        return jdbcTemplate.query("SELECT * FROM books where person_id=?", new BeanPropertyRowMapper<>(Book.class), id);
    }
}
