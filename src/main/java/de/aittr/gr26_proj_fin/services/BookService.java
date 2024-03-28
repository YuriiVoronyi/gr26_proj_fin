package de.aittr.gr26_proj_fin.services;

import de.aittr.gr26_proj_fin.domain.CommonBook;
import de.aittr.gr26_proj_fin.repositories.interfaces.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public List<CommonBook> getAll() {
        return repository.findAll();
    }

    public CommonBook save(CommonBook book) {
        book.setId(0);
        return repository.save(book);
    }

    public void deleteByName(String name) {
        repository.deleteByName(name);
    }

    public List<CommonBook> getAllActiveBook() {
        return repository.findAll()
                .stream()
                .filter(x -> x.isIs_active() == true)
                .toList();
    }

    public List<CommonBook> getBooksByAuthor(String author) {
        return repository.findAll()
                .stream()
                .filter(x -> x.getAuthor().equals(author) || x.getGenre().contains(author))
                .toList();
    }

    public List<CommonBook> getBooksByGenre(String genre) {
        return repository.findAll()
                .stream()
                .filter(x -> x.getGenre().equals(genre) || x.getGenre().contains(genre))
                .toList();
    }

    public List<CommonBook> getBooksByYear(String year) {
        return repository.findAll()
                .stream()
                .filter(x -> x.getYear().equals(year))
                .toList();
    }

    public List<CommonBook> getBooksByISBN(String isbn) {
        return repository.findAll()
                .stream()
                .filter(x -> x.getIsbn().equals(isbn))
                .toList();
    }

    public CommonBook getBookByName(String name) {
        return repository.findByName(name);
    }

    public List<CommonBook> getBookByNameForUser(String name) {
        return repository.findAll()
                .stream()
                .filter(x -> x.getName().equals(name) && x.isIs_active() == true)
                .toList();
    }

    public List<CommonBook> getBooksByAuthorForUser(String author) {
        return repository.findAll()
                .stream()
                .filter(x -> (x.getAuthor().equals(author) || x.getAuthor().contains(author)) && x.isIs_active())
                .toList();
    }

    public List<CommonBook> getBooksByGenreForUser(String genre) {
        return repository.findAll()
                .stream()
                .filter(x -> (x.getGenre().equals(genre) || x.getGenre().contains(genre)) && x.isIs_active())
                .toList();
    }

    public List<CommonBook> getBooksByYearForUser(String year) {
        return repository.findAll()
                .stream()
                .filter(x -> x.getYear().equals(year) && x.isIs_active())
                .toList();
    }

    public List<CommonBook> getBooksByISBNforUser(String isbn) {
        return repository.findAll()
                .stream()
                .filter(x -> x.getIsbn().equals(isbn) && x.isIs_active())
                .toList();
    }

        public void updateOfBook(CommonBook book) {
//            System.out.println("book.isIs_active() == " + book.isIs_active());
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
