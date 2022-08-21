package ru.alishev.springcourse.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alishev.springcourse.models.Book;
import ru.alishev.springcourse.models.Person;
import ru.alishev.springcourse.repositories.BooksRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;

    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll(int page, int booksPerPage, boolean sortByYear) {
        return booksRepository.findAll(PageRequest.of(page, booksPerPage, sortByYear ? Sort.by("year").descending() : Sort.unsorted())).getContent();
    }

    public Book findOne(int id) {
        Optional<Book> foundBook = booksRepository.findById(id);
        return foundBook.orElse(null);
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        updatedBook.setId(id);
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    @Transactional
    public void assign(int id, Person person) {
        booksRepository.findById(id).ifPresent(
                book -> {
                    book.setOwner(person);
                    book.setTakenAt(LocalDate.now());
                }
        );
    }

    @Transactional
    public void release(int id) {
        booksRepository.findById(id).ifPresent(
                book -> {
                    book.setOwner(null);
                    book.setTakenAt(null);
                }
        );
    }

    public List<Book> findBooksByNameStartingWith(String search) {
        StringBuilder diffLetterSearch = new StringBuilder(search);
        String firstLetter = search.substring(0, 1);

        if (firstLetter.equals(firstLetter.toUpperCase()))
            diffLetterSearch.replace(0,1, search.substring(0, 1).toLowerCase());
        else
            diffLetterSearch.replace(0,1, search.substring(0, 1).toUpperCase());


        return booksRepository.findByNameStartingWithOrNameStartingWith(search, diffLetterSearch.toString());
    }

}
