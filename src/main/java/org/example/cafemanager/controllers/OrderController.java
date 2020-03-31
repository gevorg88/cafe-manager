package org.example.cafemanager.controllers;

import org.example.cafemanager.domain.CafeTable;
import org.example.cafemanager.domain.Order;
import org.example.cafemanager.domain.User;
import org.example.cafemanager.domain.enums.Status;
import org.example.cafemanager.dto.ResponseModel;
import org.example.cafemanager.dto.order.*;
import org.example.cafemanager.services.exceptions.ChooseAtLeastOneException;
import org.example.cafemanager.services.exceptions.InstanceNotFoundException;
import org.example.cafemanager.services.order.OrderService;
import org.example.cafemanager.services.product.ProductService;
import org.example.cafemanager.services.table.TableService;
import org.example.cafemanager.utilities.ValidationMessagesCollector;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final TableService tableService;
    private final ProductService productService;

    public OrderController(OrderService orderService, TableService tableService, ProductService productService) {
        this.orderService = orderService;
        this.tableService = tableService;
        this.productService = productService;
    }

    @GetMapping("/manage/{tableId}")
    public String index(@PathVariable("tableId") Long tableId, @AuthenticationPrincipal User user, Model model) {
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

    @PostMapping(path = "/create/{tableId}")
    public ResponseEntity<?> store(
            @AuthenticationPrincipal User user,
            @PathVariable("tableId") Long tableId,
            @RequestBody CreateOrderRequest requestBody,
            Errors errors) {
        ResponseModel result = new ResponseModel();

        if (errors.hasErrors()) {
            result.setMessage(ValidationMessagesCollector.collectErrorMessages(errors));
            return ResponseEntity.unprocessableEntity().body(result);
        }
        try {
            orderService.createOrder(new OrderDetails(tableId, requestBody.getProducts(), user));
            result.setMessage("Order has been successfully created");
        } catch (InstanceNotFoundException e) {
            result.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        } catch (ChooseAtLeastOneException e) {
            result.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        } catch (Exception e) {
            result.setMessage("Something goes wrong! Try again later");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<?> delete(@PathVariable("orderId") Long orderId, @AuthenticationPrincipal User user) {
        ResponseModel result = new ResponseModel();
        try {
            orderService.deleteOrder(orderId, user);
            result.setMessage("Order has been successfully deleted");
        } catch (InstanceNotFoundException e) {
            result.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        } catch (Exception e) {
            result.setMessage("Something goes wrong! Try again later");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PutMapping("/change-status/{orderId}")
    public ResponseEntity<?> changeOrderStatus(
            @PathVariable("orderId") Long orderId,
            @AuthenticationPrincipal User user,
            @RequestBody OrderStatusUpdate orderStatus,
            Errors errors) {
        ResponseModel result = new ResponseModel();

        if (errors.hasErrors()) {
            result.setMessage(ValidationMessagesCollector.collectErrorMessages(errors));
            return ResponseEntity.unprocessableEntity().body(result);
        }
        try {
            orderService.updateOrderStatus(orderId, orderStatus.getStatus(), user);
            result.setMessage("Order status has been successfully updated");
        } catch (InstanceNotFoundException e) {
            result.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        } catch (Exception e) {
            result.setMessage("Something goes wrong! Try again later");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{orderId}/pio/{pioId}")
    public ResponseEntity<?> updateProductInOrder(
            @PathVariable("orderId") Long orderId,
            @PathVariable("pioId") Long productInOrderId,
            @AuthenticationPrincipal User user,
            @RequestBody PioUpdateReq requestBody,
            Errors errors) {
        ResponseModel result = new ResponseModel();

        if (errors.hasErrors()) {
            result.setMessage(ValidationMessagesCollector.collectErrorMessages(errors));
            return ResponseEntity.unprocessableEntity().body(result);
        }
        try {
            orderService.updateProductInOrder(
                    new UpdateProductInOrderDto(productInOrderId, orderId, user, requestBody.getAmount()));

            result.setMessage("Product In Order has been successfully updated");
        } catch (InstanceNotFoundException e) {
            result.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        } catch (Exception e) {
            result.setMessage("Something goes wrong! Try again later");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{orderId}/pio/{pioId}")
    public ResponseEntity<?> deleteProductInOrder(
            @PathVariable("orderId") Long orderId,
            @PathVariable("pioId") Long productInOrderId,
            @AuthenticationPrincipal User user) {
        ResponseModel result = new ResponseModel();
        try {
            orderService.deleteProductInOrder(orderId, productInOrderId, user);
            result.setMessage("Product In Order has been successfully deleted");
        } catch (InstanceNotFoundException e) {
            result.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        } catch (Exception e) {
            result.setMessage("Something goes wrong! Try again later");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
        return ResponseEntity.ok(result);
    }
}
