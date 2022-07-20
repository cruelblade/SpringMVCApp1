package ru.alishev.springcourse.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.alishev.springcourse.dao.BookDAO;
import ru.alishev.springcourse.models.Book;

@Component
public class BookValidator implements Validator {
    private final BookDAO bookDAO;

    @Autowired
    public BookValidator(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;

        if(bookDAO.show(book.getName()).isPresent()) {
            errors.rejectValue("name", "", "This name is already taken");
        }
    }
}
