package org.example.cafemanager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/tables")
public class TableController {

//    private TableRepository tableRepository;
//
//    @Autowired
//    public void setTableRepository(TableRepository tableRepository) {
//        this.tableRepository = tableRepository;
//    }
//
//    @PostMapping(path = "/tables/add")
//    public @ResponseBody String addNewTable(@RequestParam String identificator) {
//        Table table = new Table(identificator);
//        tableRepository.save(table);
//
//        return "";
//    }
//
//    @GetMapping(path = "/all")
//    public @ResponseBody
//    Iterable<Table> getAllUsers() {
//        return tableRepository.findAll();
//    }
}

