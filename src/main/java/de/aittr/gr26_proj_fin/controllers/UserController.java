package de.aittr.gr26_proj_fin.controllers;

import de.aittr.gr26_proj_fin.domain.CommonUser;
import de.aittr.gr26_proj_fin.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
@Tag(
        name = "Buyer Controller",
        description = "Controller for performing various operations on customer objects"

)
public class UserController {

    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/reg")
    @Operation(
            summary = "Buyer registration",
            description = "New buyer registration. Available for any user. The body needs two fields: name, psw"
    )
    public ResponseEntity<CommonUser> register(@RequestBody Map<String, String> requestBody) {
        String name = requestBody.get("name");
        String psw = requestBody.get("psw");
        return ResponseEntity.ok(service.register(name, psw));
    }
}
