package org.arman.library.controller;

import org.arman.library.model.dao.BookDAO;
import org.arman.library.model.dao.PersonDAO;
import org.arman.library.model.domain.Person;
import org.arman.library.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("people")
public class PersonController {
    private final PersonDAO personDAO;

    private final BookDAO bookDAO;

    private final PersonValidator personValidator;

    @Autowired
    public PersonController(PersonDAO personDAO, BookDAO bookDAO, PersonValidator personValidator) {
        this.personDAO = personDAO;
        this.bookDAO = bookDAO;
        this.personValidator = personValidator;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("people", personDAO.index());
        return "people/index";
    }

    @GetMapping("new")
    public String getAddPage(@ModelAttribute("person") Person person) {

        return "people/new";
    }

    @GetMapping("{id}/edit")
    public String getEditPage(Model model, @PathVariable("id") long id) {
        model.addAttribute("person", personDAO.show(id));
        return "people/edit";
    }

    @GetMapping("{id}")
    public String show(@PathVariable long id, Model model) {
        model.addAttribute("person", personDAO.show(id));
        model.addAttribute("books", bookDAO.findForReaderWithId(id));
        return "people/show";
    }

    @PostMapping
    public String addNewPerson(@ModelAttribute("person") Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if(bindingResult.hasErrors())
            return "people/new";

        personDAO.save(person);

        return "redirect:/people";
    }

    @PatchMapping("{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") long id) {

        if(bindingResult.hasErrors())
            return "people/edit";

        personDAO.update(id, person);

        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        personDAO.delete(id);

        return "redirect:/people";
    }
}
