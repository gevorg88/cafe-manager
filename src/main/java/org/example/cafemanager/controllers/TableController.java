package org.example.cafemanager.controllers;

import org.example.cafemanager.services.table.contracts.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tables")
public class TableController {
    private final TableService tableService;

    @Autowired
    public TableController(TableService tableService) {
        this.tableService = tableService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("tables", tableService.getAllTables());
        return "table/list";
    }
}

