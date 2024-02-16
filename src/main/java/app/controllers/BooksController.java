package app.controllers;

import app.dao.BookDAO;
import app.dao.PersonDAO;
import app.model.Book;
import app.model.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BookDAO booksDAO;
    private final PersonDAO personDAO;

    public BooksController(BookDAO booksDAO, PersonDAO personDAO) {
        this.booksDAO = booksDAO;
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String showBook(Model model) {
        model.addAttribute("books", booksDAO.index());
        return "books/books-list";
    }

    @GetMapping("/{id}")
    public String showBook(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", booksDAO.show(id));
        Optional<Person> personOwner = booksDAO.getBookOwner(id);
        if (personOwner.isPresent()) {
            model.addAttribute("person_owner", personOwner.get());
        }
        else {
            model.addAttribute("people", personDAO.index());
        }
        return "books/show-books";
    }

    @GetMapping("/new-book")
    public String newBook(Model model) {
        model.addAttribute("book", new Book());
        return "books/new-book";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            return "books/new-book";
        booksDAO.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit-book")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", booksDAO.show(id));
        return "books/edit-book";
    }



    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, @PathVariable("id") int id){
        if(bindingResult.hasErrors())
            return "books/edit-book";

        booksDAO.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        booksDAO.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable int id){
        booksDAO.release(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable int id, @ModelAttribute("person") Person selectPerson){
        booksDAO.assign(id, selectPerson);
        return "redirect:/books";
    }


}
