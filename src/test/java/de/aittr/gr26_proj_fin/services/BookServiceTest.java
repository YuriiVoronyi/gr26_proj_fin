//package de.aittr.gr26_proj_fin.services;
//
//import de.aittr.gr26_proj_fin.domain.CommonBook;
//import de.aittr.gr26_proj_fin.repositories.interfaces.BookRepository;
//import org.junit.Before;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.mock;
//
//class BookServiceTest {
//    private BookRepository repository;
//    private BookService service;
//
//    @Before
//    public void init() {
//        repository = new BookRepository();
//        service = new BookService(repository);
//    }
//
//    public BookService getBookService() {
//        return bookService;
//    }
//
//    @Test
//    public void testGetAllBooks() {
//        // Создание заглушки данных
//        List<CommonBook> mockBooks = Arrays.asList(
//                //int id, String name, double price, String year, String pathimg, String author, String isbn, String genre, boolean isActive
//                new CommonBook(0,"Book 1", 100, "1900", "sdfasdgsdf dfgfg", "Author1", "2313453245", "Novel", true),
//                new CommonBook(0,"Book 2", 200, "2000", "sdfadf dfgfg", "Author2", "2313453245", "Novel", true),
//                new CommonBook(0,"Book 3", 300, "2100", "sdf dfgfg", "Author3", "2313453245", "Novel", true)
//
//        );
//
//        // Установка поведения заглушки
//        Mockito.when(bookRepository.findAll()).thenReturn(mockBooks);
//
//        // Вызов метода сервиса
//        List<CommonBook> result = bookService.getAll();
//
//        // Проверка результатов
//        assertEquals(mockBooks, result);
//    }
//}