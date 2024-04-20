package de.aittr.gr26_proj_fin.controllers;

import de.aittr.gr26_proj_fin.domain.CommonBook;
import de.aittr.gr26_proj_fin.domain.CommonCart;
import de.aittr.gr26_proj_fin.services.CartService;
import de.aittr.gr26_proj_fin.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    private UserService userService;
    private CartService cartService;

    public CartController(UserService userService, CartService cartService) {
        this.userService = userService;
        this.cartService = cartService;
    }

    @PostMapping("/{userName}")
    @Operation(
            summary = "Adding a book to cart",
            description = "Adding a book to cart. Available for authorized users. The body needs one field: bookId"
    )
    public ResponseEntity<?> addBookToCart(@PathVariable String userName, @RequestBody Map<String, String> requestBody) {
        String bookId = requestBody.get("bookId");
        if (bookId == null) {
            return ResponseEntity.badRequest().body("Book ID not specified");
        }

        userService.addBookToCart(userName, Integer.valueOf(bookId));
        return ResponseEntity.ok().build();

    }

    @GetMapping("/{userName}")
    @Operation(
            summary = "Displaying the total number and cost of books of cart",
            description = "Displaying the total number and cost of books from the shopping cart. Available for authorized users."
    )
    public CommonCart getCart(@PathVariable String userName) {
        return cartService.getCartOfUser(userName);
    }

    @GetMapping("/books/{userName}")
    @Operation(
            summary = "Displaying a list of books",
            description = "Displaying a list of books from the cart. Available for authorized users."
    )
    public List<CommonBook> getBooksOfCart(@PathVariable String userName) {
        return cartService.getBooksFromCart(userName);
    }

    @DeleteMapping("{userName}")
    @Operation(
            summary = "Removing a book from the cart",
            description = "Removing a book from the cart. Available for authorized users. The body needs one field: bookId"
    )
    public List<CommonBook> delBookFromCart(@PathVariable String userName, @RequestBody Map<String, String> requestBody) {
        String bookId = requestBody.get("bookId");
        return  userService.deleteBookFromCart(userName, Integer.valueOf(bookId));
    }

    @DeleteMapping("/clear/users/{userId}")
    @Operation(
            summary = "Emptying the cart",
            description = "Completly emptying of the book basket. Available for authorized users."
    )
    public List<CommonBook> clrCart(@PathVariable Integer userId) {
        return userService.clearCart(userId);
    }
}
