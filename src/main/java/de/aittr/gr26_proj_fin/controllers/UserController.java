package de.aittr.gr26_proj_fin.controllers;

import de.aittr.gr26_proj_fin.domain.CommonUser;
import de.aittr.gr26_proj_fin.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@Tag(
        name = "Buyer Controller",
        description = "Controller for performing various operations on customer objects"

)
public class UserController {

    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

//    @GetMapping("/{name}")
//    @Operation(
//            summary = "Search for a buyer",
//            description = "Search for a buyer by login. Available only for administrator."
//    )
//    public UserDetails getOneUser(@PathVariable String name) {
//        return service.loadUserByUsername(name);
//    }

    @PostMapping("/reg/items")
    @Operation(
            summary = "Buyer registration",
            description = "New buyer registration. Available for any user."
    )
    public CommonUser register(@RequestBody Map<String, String> requestBody) {
        String name = requestBody.get("name");
        String psw = requestBody.get("psw");
        return service.register(name,psw);
    }

//    @GetMapping
//    @Operation(
//            summary = "Displaying a list of buyers",
//            description = "Display a list of all registered customers. Available only for administrator."
//    )
//    public List<CommonUser> getAll() {
//        return service.getAll();
//    }

//    @GetMapping("/email/{email}")
//    @Operation(
//            summary = "Search for a buyer",
//            description = "Search for a buyer by his email address. Available only for administrator."
//    )
//    public List<CommonUser> getUserByEmail(@PathVariable String email) {
//        return service.getUsersByEmail(email);
//    }


//    @PostMapping("/update/{id}/name/{name}/email/{email}")
//    @Operation(
//            summary = "Updating buyer details",
//            description = "Updating buyer details: login and email. Available only for administrator."
//    )
//    public void updateForUser(@PathVariable Integer id, @PathVariable String name, @PathVariable String email) {
//        service.updateOfUser(id,name,email);
//    }
//
//    @DeleteMapping("/delete/{id}")
//    @Operation(
//            summary = "Delete buyer",
//            description = "Removing a customer from the database. Available only for administrator."
//    )
//    public void delUser(@PathVariable Integer id) {
//        service.deleteUserAndRelatedEntities(id);
//    }

}
