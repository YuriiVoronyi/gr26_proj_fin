package de.aittr.gr26_proj_fin.services;

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

@Service
public class ImgService {

    private String uploadPath = "src/main/resources/img"; // Путь для сохранения загруженных файлов

    public ResponseEntity<String> uploadImage(MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Failed to upload empty file", HttpStatus.BAD_REQUEST);
        }

        try {
            // Генерируем уникальное имя для файла
            String fileName = file.getOriginalFilename();
            // Создаем путь для сохранения файла
            Path path = Paths.get(uploadPath + File.separator + fileName);
            // Сохраняем файл
            Files.write(path, file.getBytes());
            // Возвращаем URL загруженного файла
            return ResponseEntity.ok(uploadPath + "/" + fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to upload file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Resource> getImage(String imageName) {
        Path imagePath = Paths.get(uploadPath).resolve(imageName); // Формирование пути к изображению
        System.out.println("imagePath = " + imagePath);
        Resource imageResource;
        try {
            imageResource = new org.springframework.core.io.UrlResource(imagePath.toUri()); // Создание ресурса для изображения
            System.out.println(imageResource.exists());
            System.out.println(imageResource.isReadable());
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
}
