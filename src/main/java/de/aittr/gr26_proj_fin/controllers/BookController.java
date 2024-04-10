package de.aittr.gr26_proj_fin.controllers;

import de.aittr.gr26_proj_fin.domain.CommonBook;
import de.aittr.gr26_proj_fin.services.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@Tag(
        name = "Book controller",
        description = "Controller for performing various operations on book objects"
)
public class BookController {

    private BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(
            summary = "Getting all books",
            description = "Getting a list of all book objects stored in the database. Available only for administrator."
    )

    public ResponseEntity<List<CommonBook>> getAll() {
        return ResponseEntity.ok().body(service.getAll());
    }

    @GetMapping("/active")
    @Operation(
            summary = "Getting all active books",
            description = "Getting all books available for sale. Available for any user."
    )
    public List<CommonBook> getAllActiveBk() {
        return service.getAllActiveBook();
    }

    @PostMapping("/save")
    @Operation(
            summary = "Saving a book",
            description = "Saving a book to a database. Available only for administrator."
    )
    public CommonBook save(@RequestBody CommonBook book) {
        return service.save(book);
    }

    @PostMapping("/update")
    @Operation(
            summary = "Book update",
            description = "Updating book information. Available only for administrator."
    )
    public void updateForBook(@RequestBody CommonBook book) {
        service.updateOfBook(book);
    }

    @DeleteMapping("/{name}")
    @Operation(
            summary = "Deleting a book",
            description = "Deleting a book by its name. Available only for administrator."
    )
    public void deleteByName(@PathVariable String name) {
        service.deleteByName(name);
    }

    @GetMapping("/{name}")
    @Operation(
            summary = "Search for active books by title",
            description = "Search for books available for sale by title. Available for any user."
    )
    public List<CommonBook> getBkByTitleForUser(@PathVariable String name) {
        return service.getBookByNameForUser(name);
    }
}
