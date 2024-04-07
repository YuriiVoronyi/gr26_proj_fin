package de.aittr.gr26_proj_fin.controllers;

import de.aittr.gr26_proj_fin.domain.CommonOrder;
import de.aittr.gr26_proj_fin.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/add/{userId}")
    @Operation(
            summary = "Adding an order to customer",
            description = "Adding an order to customer. Available for authorized users."
    )
    public String addOrder(@PathVariable Integer userId) {
        return orderService.addNewOrder(userId);
    }

    @GetMapping("/getall")
    @Operation(
            summary = "Displaying all orders",
            description = "Displaying all orders from the database. Available only for administrator."
    )
    public List<CommonOrder> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/getbynum/{number}")
    @Operation(
            summary = "Displaying all orders",
            description = "Displaying all orders from the database. Available only for administrator."
    )
    public CommonOrder getOrderByNumber(@PathVariable Integer number) {
        return orderService.getOrderByNumber(number);
    }

    @PostMapping("/payorder/{number}")
    @Operation(
            summary = "The order has been paid",
            description = "Order payment mark. Available only for administrator."
    )
    public CommonOrder setMarkOfPaymentOfOrder(@PathVariable Integer number) {
        return orderService.setMarkOfPaymentOfOrder(number);
    }

    @DeleteMapping("/delorder/{number}")
    @Operation(
            summary = "Deleting an order",
            description = "Removing an order from the database. Available only for administrator."
    )
    public List<CommonOrder> delTheOrder(@PathVariable Integer number) {
        return orderService.delTheOrder(number);
    }
}
