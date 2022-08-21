package ru.alishev.springcourse.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.alishev.springcourse.models.Book;
import ru.alishev.springcourse.models.Person;

import java.util.*;

@Component
public class BookDAO {
    JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) { this.jdbcTemplate = jdbcTemplate;}

//    @GetMapping()
//    public List<Book> index() {
//        return jdbcTemplate.query("SELECT * FROM books", new BeanPropertyRowMapper<>(Book.class));
//    }
//
//    public Book show(int id) {
//        return jdbcTemplate.query("SELECT * FROM books WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class))
//                .stream().findAny().orElse(null);
//    }
//
    public Optional<Book> show(String name) {
        return jdbcTemplate.query("SELECT * FROM books WHERE name=?", new Object[]{name}, new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny();
    }
//
//    public void save(Book book) {
//        jdbcTemplate.update("INSERT INTO books (name, author, year) VALUES(?, ?, ?)", book.getName(), book.getAuthor(), book.getYear());
//    }
//
//    public void update(int id, Book updatedBook) {
//        jdbcTemplate.update("UPDATE books SET name=?, author=?, year=? WHERE id=?", updatedBook.getName(), updatedBook.getAuthor(), updatedBook.getYear(), id);
//    }
//
//    public void delete(int id) {jdbcTemplate.update("DELETE FROM books WHERE id =?", id);}
//
//    public void assign(int id, Person person) {
//        jdbcTemplate.update("UPDATE books set person_id=? WHERE id=?", person.getId(), id);
//    }
//
//    public void release(int id) {
//        jdbcTemplate.update("UPDATE books set person_id=null WHERE id=?", id);
//    }

    public int extractPersonId(int id) {
        return jdbcTemplate.queryForObject("SELECT person_id FROM books WHERE id=?",
                new Object[]{id}, Integer.class);
    }

    public String extractPerson(int id) {
        return jdbcTemplate.queryForObject("SELECT name FROM Person WHERE id=?",
                new Object[]{id}, String.class);
    }
}
