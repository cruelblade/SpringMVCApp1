package ru.alishev.springcourse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alishev.springcourse.models.Book;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
}
