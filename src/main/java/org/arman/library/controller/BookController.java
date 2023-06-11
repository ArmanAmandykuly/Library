package org.arman.library.controller;

import org.arman.library.model.dao.BookDAO;
import org.arman.library.model.dao.PersonDAO;
import org.arman.library.model.domain.Book;
import org.arman.library.model.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("books")
public class BookController {

    private final PersonDAO personDAO;

    private final BookDAO bookDAO;

//    private final PersonValidator personValidator;

    @Autowired
    public BookController(PersonDAO personDAO, BookDAO bookDAO) {
        this.personDAO = personDAO;
        this.bookDAO = bookDAO;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("books", bookDAO.index());

        return "books/index";
    }

    @GetMapping("new")
    public String getAddPage(@ModelAttribute("book") Book book) {

        return "books/new";
    }

    @GetMapping("{id}/edit")
    public String getEditPage(Model model, @PathVariable("id") long id) {
        model.addAttribute("person", bookDAO.show(id));
        return "books/edit";
    }

    @GetMapping("{id}")
    public String show(@PathVariable long id, Model model, @ModelAttribute("newReader") Person person) {
        Book book = bookDAO.show(id);
        model.addAttribute("book", book);
        model.addAttribute("person", personDAO.show(book.getReaderId()));
        model.addAttribute("people", personDAO.index());
        return "books/show";
    }

    @PostMapping
    public String addNewBook(@ModelAttribute("books") Book book, BindingResult bindingResult) {
        book.setReaderId(null);
//        bookValidator.validate(book, bindingResult);
        if(bindingResult.hasErrors())
            return "books/new";

        bookDAO.save(book);

        return "redirect:/books";
    }

    @PatchMapping("{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("id") long id) {

        if(bindingResult.hasErrors())
            return "books/edit";

        bookDAO.update(id, book);

        return "redirect:/books";
    }

    @PatchMapping("{id}/assign")
    public String assign(@PathVariable("id") long id, @ModelAttribute("person") Person selectedPerson) {
        bookDAO.assign(id, selectedPerson);
        return "redirect:/books/" + String.valueOf(id);
    }

    @PatchMapping("{id}/free")
    public String free(@PathVariable("id") long id) {
        bookDAO.release(id);
        return "redirect:/books/" + String.valueOf(id);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        bookDAO.delete(id);

        return "redirect:/books";
    }

}
