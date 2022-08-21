package ru.alishev.springcourse.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.alishev.springcourse.models.Book;
import ru.alishev.springcourse.services.BooksService;

@Component
public class BookValidator implements Validator {
//    private final BookDAO bookDAO;
//
//    @Autowired
//    public BookValidator(BookDAO bookDAO) {
//        this.bookDAO = bookDAO;
//    }

    private final BooksService booksService;

    @Autowired
    public BookValidator(BooksService booksService) {
        this.booksService = booksService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;

        if (booksService.findOne(book.getName()).isPresent()) {
            errors.rejectValue("name", "", "This name is already taken");
        }
    }
}
