package org.example.cafemanager.controllers;

import org.example.cafemanager.repositories.CafeTableRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/tables")
public class TableController {
    private final CafeTableRepository cafeTableRepository;

}

