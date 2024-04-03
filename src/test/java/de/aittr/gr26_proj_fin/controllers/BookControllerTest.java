package de.aittr.gr26_proj_fin.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.aittr.gr26_proj_fin.domain.CommonBook;
import de.aittr.gr26_proj_fin.services.BookService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.ResponseBody;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
//@Import(TestConfig.class) // Импортируем класс конфигурации
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    List<CommonBook> mockBooks;
    CommonBook bookToSave;

    @Before
    public void init() {
        // Создание заглушки данных
        mockBooks = Arrays.asList(
                new CommonBook("Book 1", 100, "1900", "sdfasdgsdf dfgfg", "Author1", "2313453245", "Novel", true),
                new CommonBook("Book 2", 200, "2000", "sdfadf dfgfg", "Author2", "2313453245", "Novel", true),
                new CommonBook("Book 3", 300, "2010", "sdf dfgfg", "Author3", "2313453245", "Novel", false),
                new CommonBook("Book 4", 400, "2020", "sdf dfg  fg", "Author4", "2313453245", "Novel", false)
        );
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    public void testGetAllBooks() {

        // Установка поведения заглушки
        when(bookService.getAll()).thenReturn(mockBooks);

        // Вызов метода контроллера
        ResponseEntity<List<CommonBook>> response = bookController.getAll();

        // Проверка результатов
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockBooks, response.getBody());
    }

    @Test
    public void testGetAllActiveBk() {
        // Установка поведения заглушки
        when(bookService.getAllActiveBook()).thenReturn(mockBooks);

        // Вызов метода контроллера
        List<CommonBook> response = bookController.getAllActiveBk();
        int b=0;
        // Проверка результатов
        //assertNotEquals(2, 4);
        for (int i = 0; i < mockBooks.size(); i++) {
            assertEquals(mockBooks.get(i), response.get(i));
            b++;
        }
        System.out.println("i = " + b);
    }

    @ResponseBody
    public String getBookJson() throws JsonProcessingException {
        CommonBook bookToSave = new CommonBook("Book for save", 500, "2024", "sdf dfg r r fg", "Author5", "2313453245", "Novel", true);
        bookToSave.setId(0);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(bookToSave);
    }

    @Test
    public void save() throws Exception {

        String jsonBook = getBookJson();

        // Настройка поведения заглушки
        when(bookService.save(any(CommonBook.class))).thenReturn(bookToSave);

        // Выполнение запроса POST с передачей объекта книги в теле запроса
        mockMvc.perform(post("api/book/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBook))
                .andExpect(status().isOk());
    }

    @Test
    public void updateForBook() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("api/book/update")
                        .content("{ \"id\": 19, \"name\": \"Updated Book Title\", \"author\": \"Updated Author\" }")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(bookService).updateOfBook(any(CommonBook.class));
    }

    @Test
    public void deleteByName() throws Exception {
        String nameToDelete = "BookName";
        mockMvc.perform(MockMvcRequestBuilders.delete("/book/{name}", nameToDelete))
                .andExpect(status().isOk());

        verify(bookService).deleteByName(nameToDelete);
    }

    //    @Test
//    public void getBkByName() throws Exception {
//        String bookName = "BookName";
//        CommonBook book = new CommonBook();
//        book.setId(0);
//        book.setName(bookName);
//
//        when(bookService.getBookByName(bookName)).thenReturn(book);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/book/{name}", bookName))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value(bookName));//ожидаем значение bookName для поля name
//    }
    @Test
    public void getBkByName() {
        // Задаем имя книги, которое мы ожидаем получить
        String expectedBookName = "Example Book";

        // Создаем заглушку для метода getBookByName
        when(bookService.getBookByName(expectedBookName)).thenReturn(new CommonBook(expectedBookName));

        // Вызываем метод контроллера, который должен вызвать метод сервиса
        CommonBook actualBook = bookController.getBkByName(expectedBookName);

        // Проверяем, что полученная книга имеет правильное имя
        assertEquals(expectedBookName, actualBook.getName());
    }

    @Test
    public void getBkByAuthor() {
        // Задаем имя автора, для которого ожидаем получить книги
        String author = "Example Author";

        // Создаем список книг, которые мы ожидаем получить
        List<CommonBook> expectedBooks = new ArrayList<>();
        expectedBooks.add(new CommonBook("Book 1", author));
        expectedBooks.add(new CommonBook("Book 2", author));

        // Создаем заглушку для метода getBooksByAuthor
        when(bookService.getBooksByAuthor(author)).thenReturn(expectedBooks);

        // Вызываем метод контроллера, который должен вызвать метод сервиса
        List<CommonBook> actualBooks = bookController.getBkByAuthor(author);

        // Проверяем, что полученный список книг совпадает с ожидаемым
        assertEquals(expectedBooks, actualBooks);
    }

    @Test
    public void getBkByGenre() {
        // Задаем жанр, для которого ожидаем получить книги
        String genre = "Detective";

        // Создаем список книг, которые мы ожидаем получить
        List<CommonBook> expectedBooks = new ArrayList<>();
        expectedBooks.add(new CommonBook("Book 1", "Example Author 1", genre));
        expectedBooks.add(new CommonBook("Book 2", "Example Author 2", genre));
        //expectedBooks.add(new CommonBook("Book 3", "Example Author 3", "Another Genre")); // этой книги не должно быть в ожидаемом списке
        expectedBooks.add(new CommonBook("Book 4", "Example Author 4", genre));

        // Создаем список книг, которые сервис вернет по запросу
        List<CommonBook> allBooks = new ArrayList<>();
        allBooks.add(new CommonBook("Book 1", "Example Author 1", genre));
        allBooks.add(new CommonBook("Book 2", "Example Author 2", genre));
        allBooks.add(new CommonBook("Book 3", "Example Author 3", "Drama"));
        allBooks.add(new CommonBook("Book 4", "Example Author 4", genre));

        // Создаем заглушку для метода getBooksByGenre
        when(bookService.getBooksByGenre(genre)).thenReturn(allBooks);

        // Вызываем метод контроллера, который должен вызвать метод сервиса
        List<CommonBook> actualBooks = bookController.getBkByGenre(genre);

        System.out.println("================ expectedBooks ==============================");
        expectedBooks.stream().forEach(System.out::println);
        System.out.println("================= actualBooks =============================");
        actualBooks.stream().forEach(System.out::println);

        // Проверяем, что полученный список книг совпадает с ожидаемым
        assertEquals(expectedBooks, actualBooks);
    }
}