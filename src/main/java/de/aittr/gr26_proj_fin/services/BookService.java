package de.aittr.gr26_proj_fin.services;

import de.aittr.gr26_proj_fin.domain.CommonBook;
import de.aittr.gr26_proj_fin.repositories.interfaces.BookRepository;
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

    public List<CommonBook> getBookByNameForUser(String name) {
        logger.info(String.format("Requested active book by title"));
        return repository.findAll()
                .stream()
                .filter(x -> (x.getAuthor().toLowerCase().equals(name.toLowerCase()) || x.getName().toLowerCase().equals(name.toLowerCase())) && x.isIs_active() == true)
                .toList();
    }

        public void updateOfBook(CommonBook book) {
        logger.info(String.format("Changing book properties"));
        repository.updateBook(book.getId(),
                book.getName(),
                book.getPrice(),
                book.getPathimg(),
                (book.isIs_active()) ? 1: 0,
                book.getAuthor()
                );
    }
}
