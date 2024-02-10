package app.controllers;

import app.dao.BookDAO;
import app.model.Book;
import app.model.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BookDAO booksDAO;

    public BooksController(BookDAO booksDAO) {
        this.booksDAO = booksDAO;
    }

    @GetMapping()
    public String showBook(Model model) {
        model.addAttribute("books", booksDAO.index());
        return "books/books-list";
    }

    @GetMapping("/{id}")
    public String showBook(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", booksDAO.show(id));
        return "books/show-books";
    }

    @GetMapping("/new-book")
    public String newBook(Model model) {
        model.addAttribute("book", new Book());
        return "books/new-book";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book) {
        booksDAO.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", booksDAO.show(id));
        return "books/edit-book";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, @PathVariable("id") int id){
        booksDAO.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        booksDAO.delete(id);
        return "redirect:/books";
    }
}
