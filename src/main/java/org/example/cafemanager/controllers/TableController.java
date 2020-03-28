package org.example.cafemanager.controllers;

import org.example.cafemanager.domain.User;
import org.example.cafemanager.dto.table.TableCreate;
import org.example.cafemanager.dto.table.TableCreateRequestBody;
import org.example.cafemanager.dto.ResponseModel;
import org.example.cafemanager.services.exceptions.InstanceNotFoundException;
import org.example.cafemanager.services.table.TableService;
import org.example.cafemanager.services.exceptions.MustBeUniqueException;
import org.example.cafemanager.services.user.UserService;
import org.example.cafemanager.utilities.ValidationMessagesCollector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/tables")
public class TableController {
    private final TableService tableService;
    private final UserService userService;

    @Autowired
    public TableController(TableService tableService, UserService userService) {
        this.tableService = tableService;
        this.userService = userService;
    }

    @GetMapping
    public String index(@AuthenticationPrincipal User user, Model model) {
        if (user.isManager()) {
            model.addAttribute("tables", tableService.getAllTables());
            model.addAttribute("users", userService.getAllWaiters());
            return "table/list";
        }
        model.addAttribute("tables", tableService.getUserAssignedTablesWithOpenStatus(user.getId()));
        return "table/waiter-list";
    }

    @PostMapping("/manager")
    public ResponseEntity<?> store(
            @Valid @RequestBody TableCreateRequestBody requestBody,
            Errors errors
    ) {
        ResponseModel result = new ResponseModel();
        if (errors.hasErrors()) {
            result.setMessage(ValidationMessagesCollector.collectErrorMessages(errors));
            return ResponseEntity.unprocessableEntity().body(result);
        }
        TableCreate createDto = new TableCreate(requestBody.getName());
        try {
            tableService.create(createDto);
            result.setMessage("Table has been successfully created");
        } catch (MustBeUniqueException e) {
            result.setMessage(e.getMessage());
            return ResponseEntity.unprocessableEntity().body(result);
        } catch (Exception e) {
            result.setMessage("Something goes wrong! Try again later");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/manager/assign/{tableId}/{userId}")
    public ResponseEntity<?> assignUser(
            @PathVariable Long tableId,
            @PathVariable Long userId) {
        ResponseModel result = new ResponseModel();
        try {
            String res = tableService.assignUser(tableId, userId);
            result.setMessage(String.format("User has been successfully %s", res));
            return ResponseEntity.ok(result);
        } catch (InstanceNotFoundException e) {
            result.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        } catch (Exception e) {
            result.setMessage("Something goes wrong! Try again later");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    @DeleteMapping("/manager/{tableId}")
    public ResponseEntity<?> destroy(@PathVariable Long tableId) {
        ResponseModel result = new ResponseModel();

        try {
            tableService.delete(tableId);
            result.setMessage("Table has been successfully deleted!");
            return ResponseEntity.ok(result);
        } catch (InstanceNotFoundException e) {
            result.setMessage(e.getMessage());
            return ResponseEntity.unprocessableEntity().body(result);
        } catch (Exception e) {
            result.setMessage("Something goes wrong! Try again later");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    @PutMapping("/manager/{id}")
    public ResponseEntity<?> update(
            @Valid @RequestBody TableCreateRequestBody requestBody,
            @PathVariable Long id,
            Errors errors
    ) {
        ResponseModel result = new ResponseModel();
        if (errors.hasErrors()) {
            result.setMessage(ValidationMessagesCollector.collectErrorMessages(errors));
            return ResponseEntity.unprocessableEntity().body(result);
        }
        try {
            tableService.update(id, requestBody);
            result.setMessage("Table has been successfully updated");
            return ResponseEntity.ok(result);
        } catch (MustBeUniqueException e) {
            result.setMessage(e.getMessage());
            return ResponseEntity.unprocessableEntity().body(result);
        } catch (InstanceNotFoundException e) {
            result.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        } catch (Exception e) {
            result.setMessage("Something goes wrong! Try again later");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }
}

