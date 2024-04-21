package de.aittr.gr26_proj_fin.controllers;

import de.aittr.gr26_proj_fin.domain.CommonOrder;
import de.aittr.gr26_proj_fin.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/{userName}")
    @Operation(
            summary = "Adding an order to customer",
            description = "Adding an order to customer. Available for authorized users."
    )
    public String addOrder(@PathVariable String userName) {
        return orderService.addNewOrder(userName);
    }

    @GetMapping()
    @Operation(
            summary = "Displaying all orders",
            description = "Displaying all orders from the database. Available only for administrator."
    )
    public List<CommonOrder> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{number}")
    @Operation(
            summary = "Search for an order by number",
            description = "Search for an order by number. Available only for administrator."
    )
    public CommonOrder getOrderByNumber(@PathVariable Integer number) {
        return orderService.getOrderByNumber(number);
    }

    @PostMapping("/{number}")
    @Operation(
            summary = "The order has been paid",
            description = "Order payment mark. Available only for administrator."
    )
    public CommonOrder setMarkOfPaymentOfOrder(@PathVariable Integer number) {
        return orderService.setMarkOfPaymentOfOrder(number);
    }

    @DeleteMapping("/{number}")
    @Operation(
            summary = "Deleting an order",
            description = "Removing an order from the database. Available only for administrator."
    )
    public List<CommonOrder> delTheOrder(@PathVariable Integer number) {
        return orderService.delTheOrder(number);
    }
}
