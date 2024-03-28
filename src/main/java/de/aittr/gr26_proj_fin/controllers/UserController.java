package de.aittr.gr26_proj_fin.controllers;

import de.aittr.gr26_proj_fin.domain.CommonUser;
import de.aittr.gr26_proj_fin.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Tag(
        name = "Buyer Controller",
        description = "Controller for performing various operations on customer objects"

)
public class UserController {

    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/{name}")
    @Operation(
            summary = "Search for a buyer",
            description = "Search for a buyer by login"
    )
    public UserDetails getOneUser(@PathVariable String name) {
        return service.loadUserByUsername(name);
    }

    @PostMapping("/reg")
    @Operation(
            summary = "Buyer registration",
            description = "New buyer registration"
    )
    public CommonUser register(@RequestBody CommonUser user) {
        return service.register(user);
    }

    @GetMapping
    @Operation(
            summary = "Displaying a list of buyers",
            description = "Display a list of all registered customers"
    )
    public List<CommonUser> getAll() {
        return service.getAll();
    }

    @GetMapping("email/{email}")
    @Operation(
            summary = "Search for a buyer",
            description = "Search for a buyer by his email address"
    )
    public List<CommonUser> getUserByEmail(@PathVariable String email) {
        return service.getUsersByEmail(email);
    }

    @PostMapping("/update")
    @Operation(
            summary = "Updating buyer details",
            description = "Updating buyer details"
    )
    public void updateForUser(@RequestBody CommonUser user) {
        service.updateOfUser(user);
    }
}
