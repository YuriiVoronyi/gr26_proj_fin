package de.aittr.gr26_proj_fin.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

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
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.ResponseBody;

@RunWith(MockitoJUnitRunner.class)
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

        // Проверка результатов
        assertNotEquals(2, 4);
        for (int i = 0; i < mockBooks.size(); i++) {
            assertEquals(mockBooks.get(i), response.get(i));
        }
    }

//    @ResponseBody
//    public String getBookJson() {
//        CommonBook bookToSave = new CommonBook("Book for save", 500, "2024", "sdf dfg r r fg", "Author5", "2313453245", "Novel", true);
//        return bookToSave;
//    }
//    @Test
//    public void save() throws Exception {
//        ObjectMapper objectMapper = new ObjectMapper();
//        String jsonBook = getBookJson();
//
//        // Настройка поведения заглушки
//        when(bookService.save(any(CommonBook.class))).thenReturn(bookToSave);
//
//        // Выполнение запроса POST с передачей объекта книги в теле запроса
//        mockMvc.perform(post("/books")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(jsonBook))
//                .andExpect(status().isOk());
//    }
}