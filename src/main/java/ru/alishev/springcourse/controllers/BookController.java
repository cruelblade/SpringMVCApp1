package ru.alishev.springcourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.alishev.springcourse.dao.BookDAO;
import ru.alishev.springcourse.dao.PersonDAO;
import ru.alishev.springcourse.models.Book;
import ru.alishev.springcourse.models.Person;
import ru.alishev.springcourse.services.BooksService;
import ru.alishev.springcourse.services.PeopleService;
import ru.alishev.springcourse.util.BookValidator;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;
    private final BookValidator bookValidator;
    private final BooksService booksService;
    private final PeopleService peopleService;

    @Autowired
    public BookController(BookDAO bookDAO, PersonDAO personDAO, BookValidator bookValidator, BooksService booksService, PeopleService peopleService) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
        this.bookValidator = bookValidator;
        this.booksService = booksService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String index(Model model, @RequestParam(defaultValue = "0", name = "page") int page,
                        @RequestParam(defaultValue = "10", name = "book_per_page") int bookPerPage,
                        @RequestParam(defaultValue = "false", name = "sort_by_year") boolean sortByYear) {
        model.addAttribute("books", booksService.findAll(page, bookPerPage, sortByYear));
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", booksService.findOne(id));
        model.addAttribute("person", new Person());
        model.addAttribute("people", peopleService.findAll());
        try {
            model.addAttribute("person_id", booksService.extractPersonId(id));
            model.addAttribute("personName", bookDAO.extractPerson(booksService.extractPersonId(id)));
        } catch (Exception e){
            model.addAttribute("person_id", 0);
        }
        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(Model model) {
        model.addAttribute("book", new Book());
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors())
            return "books/new";
        booksService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", booksService.findOne(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if(bindingResult.hasErrors())
            return "books/edit";
        booksService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        booksService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("{id}/assign")
    public String add(@PathVariable("id") int id, @ModelAttribute("person") Person person,
                      @ModelAttribute("book") Book book) {
        booksService.assign(id, person);
        return "redirect:/books/";
    }

    @PatchMapping("{id}/release")
    public String delete(@PathVariable("id") int id, @ModelAttribute("book") Book book) {
        booksService.release(id);
        return "redirect:/books/";
    }

    @GetMapping("/search")
    public String search() {
        return "books/search";
    }

    @PostMapping("/search")
    public String searchByName(Model model, @RequestParam(name="search") @NotEmpty(message = "Строка не должна быть пустой") String search)  {
        if (search.length() > 0)
            model.addAttribute("books", booksService.findBooksByNameStartingWith(search));
        return "books/search";
    }
}
