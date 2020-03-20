package org.example.cafemanager.controllers;

import org.example.cafemanager.domain.CafeTable;
import org.example.cafemanager.domain.Order;
import org.example.cafemanager.domain.User;
import org.example.cafemanager.domain.enums.Status;
import org.example.cafemanager.dto.CreateAjaxResponse;
import org.example.cafemanager.dto.table.TableCreate;
import org.example.cafemanager.dto.table.TableCreateRequestBody;
import org.example.cafemanager.services.exceptions.InstanceNotFoundException;
import org.example.cafemanager.services.exceptions.MustBeUniqueException;
import org.example.cafemanager.services.order.contracts.OrderService;
import org.example.cafemanager.services.product.contracts.ProductService;
import org.example.cafemanager.services.table.contracts.TableService;
import org.example.cafemanager.utilities.ValidationMessagesCollector;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@Controller
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final TableService tableService;
    private final ProductService productService;

    public OrderController(
            OrderService orderService,
            TableService tableService,
            ProductService productService
    ) {
        this.orderService = orderService;
        this.tableService = tableService;
        this.productService = productService;
    }

    @GetMapping("/manage/{tableId}")
    public String index(
            @PathVariable("tableId") Long tableId,
            @AuthenticationPrincipal User user,
            Model model
    ) {
        String viewPath = "order/list";
        try {
            CafeTable table = tableService.getUserAssignedTable(tableId, user);
            Set<Order> orders = table.getOrders();
            model.addAttribute("table", tableService.getUserAssignedTable(tableId, user));
            model.addAttribute("orders", orders);
            model.addAttribute("statuses", Status.getEnumMapValues());
            model.addAttribute("products", productService.getAllProducts());
        } catch (InstanceNotFoundException e) {
            model.addAttribute("error", e.getMessage());
        }
        return viewPath;
    }

//    @PostMapping("/create/{tableId}")
//    public ResponseEntity<?> store(
//            @PathVariable("tableId")
//            @Valid @RequestBody OrderCreateRequest requestBody,
//            Errors errors
//    ) {
//        CreateAjaxResponse result = new CreateAjaxResponse();
//        if (errors.hasErrors()) {
//            result.setMessage(ValidationMessagesCollector.collectErrorMessages(errors));
//            return ResponseEntity.unprocessableEntity().body(result);
//        }
//        TableCreate createDto = new TableCreate(requestBody.getName());
//        try {
//            tableService.createTable(createDto);
//            result.setMessage("Table has been successfully created");
//        } catch (MustBeUniqueException e) {
//            result.setMessage(e.getMessage());
//            return ResponseEntity.unprocessableEntity().body(result);
//        } catch (Exception e) {
//            result.setMessage("Something goes wrong! Try again later");
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
//        }
//        return ResponseEntity.ok(result);
//    }
}
