package de.aittr.gr26_proj_fin.controllers;

import de.aittr.gr26_proj_fin.domain.CommonBook;
import de.aittr.gr26_proj_fin.services.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
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
            description = "Getting a list of all book objects stored in the database"
    )
//    public List<CommonBook> getAll() {
//        return service.getAll();
//    }
    public ResponseEntity<List<CommonBook>> getAll() {
        return ResponseEntity.ok().body(service.getAll());
    }

    @GetMapping("/active")
    @Operation(
            summary = "Getting all active books",
            description = "Getting all books available for sale"
    )
    public List<CommonBook> getAllActiveBk() {
        return service.getAllActiveBook();
    }

    @PostMapping("/save")
    @Operation(
            summary = "Saving a book",
            description = "Saving a book to a database"
    )
    public CommonBook save(@RequestBody CommonBook book) {
        return service.save(book);
    }

    @PostMapping("/update")
    @Operation(
            summary = "Book update",
            description = "Updating book information"
    )
    public void updateForBook(@RequestBody CommonBook book) {
        service.updateOfBook(book);
    }

    @DeleteMapping("/{name}")
    @Operation(
            summary = "Deleting a book",
            description = "Deleting a book by its name"
    )
    public void deleteByName(@PathVariable String name) {
        service.deleteByName(name);
    }

    @GetMapping("/{name}")
    @Operation(
            summary = "Receiving a book",
            description = "Finding a book by its title"
    )
    public CommonBook getBkByName(@PathVariable String name) {
        return service.getBookByName(name);
    }

    @GetMapping("/author/{author}")
    @Operation(
            summary = "Receiving a book",
            description = "Getting a book by its author"
    )
    public List<CommonBook> getBkByAuthor(@PathVariable String author) {
        return service.getBooksByAuthor(author);
    }

    @GetMapping("/genre/{genre}")
    @Operation(
            summary = "Search books",
            description = "Search books by genre"
    )
    public List<CommonBook> getBkByGenre(@PathVariable String genre) {
        return service.getBooksByGenre(genre);
    }

    @GetMapping("/year/{year}")
    @Operation(
            summary = "Search books",
            description = "Search books by year of publication"
    )
    public List<CommonBook> getBkByYear(@PathVariable String year) {
        return service.getBooksByYear(year);
    }

    @GetMapping("/isbn/{isbn}")
    @Operation(
            summary = "Search books",
            description = "Search books by ISBN code"
    )
    public List<CommonBook> getBkByISBN(@PathVariable String isbn) {
        return service.getBooksByISBN(isbn);
    }

    @GetMapping("/forusername/{name}")
    @Operation(
            summary = "Search for active books by title",
            description = "Search for books available for sale by title"
    )
    public List<CommonBook> getBkByTitleForUser(@PathVariable String name) {
        return service.getBookByNameForUser(name);
    }

    @GetMapping("/foruserauthor/{author}")
    @Operation(
            summary = "Search for active books by author",
            description = "Search for books available for sale by author"
    )
    public List<CommonBook> getBkByAuthorForUser(@PathVariable String author) {
        return service.getBooksByAuthorForUser(author);
    }

    @GetMapping("/forusergenre/{genre}")
    @Operation(
            summary = "Search for active books by genre",
            description = "Search for books available for sale by genre"
    )
    public List<CommonBook> getBkByGenreForUser(@PathVariable String genre) {
        return service.getBooksByGenreForUser(genre);
    }

    @GetMapping("/foruseryear/{year}")
    @Operation(
            summary = "Search for active books by year of publication",
            description = "Search for books available for sale by year of publication"
    )
    public List<CommonBook> getBkByYearForUser(@PathVariable String year) {
        return service.getBooksByYearForUser(year);
    }

    @GetMapping("/foruserisbn/{isbn}")
    @Operation(
            summary = "Search for active books by ISBN code",
            description = "Search for books available for sale by ISBN code"
    )
    public List<CommonBook> getBkByISBNforUser(@PathVariable String isbn) {
        return service.getBooksByISBNforUser(isbn);
    }







}
