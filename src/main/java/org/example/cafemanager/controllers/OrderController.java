package org.example.cafemanager.controllers;

import org.example.cafemanager.domain.CafeTable;
import org.example.cafemanager.domain.Order;
import org.example.cafemanager.domain.User;
import org.example.cafemanager.domain.enums.Status;
import org.example.cafemanager.services.exceptions.InstanceNotFoundException;
import org.example.cafemanager.services.order.contracts.OrderService;
import org.example.cafemanager.services.table.contracts.TableService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@Controller
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final TableService tableService;

    public OrderController(
            OrderService orderService,
            TableService tableService
    ) {
        this.orderService = orderService;
        this.tableService = tableService;
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
        } catch (InstanceNotFoundException e) {
            model.addAttribute("error", e.getMessage());
        }
        return viewPath;
    }
}
