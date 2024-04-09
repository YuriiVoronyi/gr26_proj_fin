package de.aittr.gr26_proj_fin.controllers;

import de.aittr.gr26_proj_fin.domain.CommonBook;
import de.aittr.gr26_proj_fin.services.ImgService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/img")
public class ImgController {

    ImgService service = new ImgService();

    public ImgController(ImgService service) {
        this.service = service;
    }

    @PostMapping("/load")
    @Operation(
            summary = "Uppload image file",
            description = "Uppload image file. Available for any users."
    )
    public ResponseEntity<String> handleFileUpload(@RequestParam("image")MultipartFile file) {
        return service.uploadImage(file);
    }

    @GetMapping("/get/{imageName}")
    @Operation(
            summary = "Getting an image",
            description = "Getting an image from the database. Available for any users."
    )
    public ResponseEntity<Resource> gettingImageFromDB(@PathVariable String imageName) {
        return service.getImage(imageName);
    }

    @PostMapping("/{id}/newpath/{path}")
    @Operation(
            summary = "Changing the path",
            description = "Changing the path to the image. Available for any users."
    )
    public CommonBook changingThePathOfTheImage(@PathVariable Integer id, @PathVariable String path) {
        return service.changingThePath(id,path);
    }
}
