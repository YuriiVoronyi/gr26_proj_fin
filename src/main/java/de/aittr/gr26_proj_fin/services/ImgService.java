package de.aittr.gr26_proj_fin.services;

import de.aittr.gr26_proj_fin.domain.CommonBook;
import de.aittr.gr26_proj_fin.repositories.interfaces.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImgService {

    @Autowired
    private BookRepository bookRepository;

    private String uploadPath = "src/main/resources/img"; // Путь для сохранения загруженных файлов

    public ResponseEntity<String> uploadImage(MultipartFile file, Integer id) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Failed to upload empty file", HttpStatus.BAD_REQUEST);
        }

        try {
            // Генерируем уникальное имя для файла
            String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
            // Создаем путь для сохранения файла
            Path path = Paths.get(uploadPath + File.separator + fileName);
            // Сохраняем файл
            Files.write(path, file.getBytes());
            CommonBook book = new CommonBook();
            book = bookRepository.findById(id).orElse(null);
            if (book != null) {
                book.setPathimg(fileName);
                bookRepository.save(book);
            }
            // Возвращаем URL загруженного файла
            return ResponseEntity.ok(uploadPath + "/" + fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to upload file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Resource> getImage(String imageName) {
        Path imagePath = Paths.get(uploadPath).resolve(imageName); // Формирование пути к изображению
        Resource imageResource;
        try {
            imageResource = new org.springframework.core.io.UrlResource(imagePath.toUri()); // Создание ресурса для изображения
            if (imageResource.exists() && imageResource.isReadable()) {
                // Если изображение существует и доступно для чтения, возвращаем его клиенту
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + imageName + "\"")
                        .body(imageResource);
            } else {
                // Если изображение не найдено или не доступно для чтения, возвращаем ошибку 404
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            e.printStackTrace();
            // В случае ошибки возвращаем ошибку сервера
            return ResponseEntity.status(500).build();
        }
    }

    public CommonBook changingThePath(Integer id, String path) {
        CommonBook book = new CommonBook();
        book = bookRepository.findById(id).orElse(null);
        if (book != null) {
            book.setPathimg(path.trim());
            bookRepository.save(book);
            return book;
        }
        return null;
    }
}
