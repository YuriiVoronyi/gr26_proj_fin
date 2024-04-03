package de.aittr.gr26_proj_fin.services;

import de.aittr.gr26_proj_fin.domain.CommonBook;
import de.aittr.gr26_proj_fin.repositories.interfaces.BookRepository;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private BookRepository repository;
    private Logger logger = LogManager.getLogger(BookService.class);

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public List<CommonBook> getAll() {
        logger.info(String.format("All books from the database are requested"));
        return repository.findAll();
    }

    public CommonBook save(CommonBook book) {
        logger.info(String.format("Book saved in database"));
        book.setId(0);
        return repository.save(book);
    }

    public void deleteByName(String name) {
        logger.info(String.format("A book has been deleted by its title"));
        repository.deleteByName(name);
    }

    public List<CommonBook> getAllActiveBook() {
        logger.info(String.format("Querying all active books from the database"));
        return repository.findAll()
                .stream()
                .filter(x -> x.isIs_active() == true)
                .toList();
    }

    public List<CommonBook> getBooksByAuthor(String author) {
        logger.info(String.format("Books requested by author"));
        return repository.findAll()
                .stream()
                .filter(x -> x.getAuthor().equals(author) || x.getGenre().contains(author))
                .toList();
    }

    public List<CommonBook> getBooksByGenre(String genre) {
        logger.info(String.format("Books requested by genre"));
        return repository.findAll()
                .stream()
                .filter(x -> x.getGenre().equals(genre) || x.getGenre().contains(genre))
                .toList();
    }

    public List<CommonBook> getBooksByYear(String year) {
        logger.info(String.format("Books requested by year of publication"));
        return repository.findAll()
                .stream()
                .filter(x -> x.getYear().equals(year))
                .toList();
    }

    public List<CommonBook> getBooksByISBN(String isbn) {
        logger.info(String.format("Book requested by ISBN code"));
        return repository.findAll()
                .stream()
                .filter(x -> x.getIsbn().equals(isbn))
                .toList();
    }

    public CommonBook getBookByName(String name) {
        logger.info(String.format("Book requested by title"));
        return repository.findByName(name);
    }

    public List<CommonBook> getBookByNameForUser(String name) {
        logger.info(String.format("Requested active book by title"));
        return repository.findAll()
                .stream()
                .filter(x -> x.getName().equals(name) && x.isIs_active() == true)
                .toList();
    }

    public List<CommonBook> getBooksByAuthorForUser(String author) {
        logger.info(String.format("Requested active books by author"));
        return repository.findAll()
                .stream()
                .filter(x -> (x.getAuthor().equals(author) || x.getAuthor().contains(author)) && x.isIs_active())
                .toList();
    }

    public List<CommonBook> getBooksByGenreForUser(String genre) {
        logger.info(String.format("Requested active books by genre"));
        return repository.findAll()
                .stream()
                .filter(x -> (x.getGenre().equals(genre) || x.getGenre().contains(genre)) && x.isIs_active())
                .toList();
    }

    public List<CommonBook> getBooksByYearForUser(String year) {
        logger.info(String.format("Active books requested by year of publication"));
        return repository.findAll()
                .stream()
                .filter(x -> x.getYear().equals(year) && x.isIs_active())
                .toList();
    }

    public List<CommonBook> getBooksByISBNforUser(String isbn) {
        logger.info(String.format("Active book requested by ISBN code"));
        return repository.findAll()
                .stream()
                .filter(x -> x.getIsbn().equals(isbn) && x.isIs_active())
                .toList();//jkjjkjhkjhkjhkjhk
    }

        public void updateOfBook(CommonBook book) {
        logger.info(String.format("Active book requested by ISBN code"));
        repository.updateBook(book.getId(),
                book.getName(),
                book.getPrice(),
                book.getYear(),
                book.getPathimg(),
                (book.isIs_active()) ? 1: 0,
                book.getIsbn(),
                book.getAuthor(),
                book.getGenre()
                );
    }

}
