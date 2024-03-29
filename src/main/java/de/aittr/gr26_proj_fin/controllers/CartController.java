package de.aittr.gr26_proj_fin.controllers;

import de.aittr.gr26_proj_fin.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/cart")
public class CartController {

    private UserService userService;

    public CartController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users/{userId}/cart/items")
    @Operation(
            summary = "Adding a book to cart",
            description = "Adding a book to an authorized customer's cart"
    )

    public ResponseEntity<?> addBookToCart(@PathVariable Integer userId, @RequestBody Map<String, String> requestBody) {
        String bookId = requestBody.get("bookId");
        if (bookId == null) {
            return ResponseEntity.badRequest().body("Book ID not specified");
        }

        userService.addBookToCart(userId, Integer.valueOf(bookId));
        return ResponseEntity.ok().build();

    }
}
