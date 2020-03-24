package org.example.cafemanager.services.table;

import org.example.cafemanager.domain.CafeTable;
import org.example.cafemanager.domain.Order;
import org.example.cafemanager.domain.User;
import org.example.cafemanager.dto.table.*;
import org.example.cafemanager.repositories.CafeTableRepository;
import org.example.cafemanager.services.exceptions.InstanceNotFoundException;
import org.example.cafemanager.services.exceptions.MustBeUniqueException;
import org.example.cafemanager.services.table.contracts.TableService;
import org.example.cafemanager.services.user.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class TableServiceImpl implements TableService {

    private final CafeTableRepository tableRepo;

    private final UserService userService;

    @Autowired
    public TableServiceImpl(
            CafeTableRepository cafeTableRepository,
            UserService userService
    ) {
        tableRepo = cafeTableRepository;
        this.userService = userService;
    }

    @Override
    public Collection<SimpleTableProps> getAllTables() {
        return tableRepo.findAllBy();
    }

    @Override
    public CafeTable createTable(TableCreate createDto) {
        OnlyTableProps existingTable = tableRepo.findOneByName(createDto.getName());
        if (null != existingTable) {
            throw new MustBeUniqueException("name");
        }

        CafeTable table = new CafeTable();
        table.setName(createDto.getName());
        tableRepo.save(table);

        return table;
    }

    @Override
    @Transactional
    public String assignUser(Long tableId, Long userId) {
        CafeTable table = tableRepo.findCafeTableById(tableId);
        if (null == table) {
            throw new InstanceNotFoundException("table");
        }
        if (-1 == userId) {
            table.setUser(null);
            tableRepo.save(table);
            return "detached";
        }

        User user = userService.getUserById(userId);
        if (null == user) {
            throw new InstanceNotFoundException("user");
        }

        table.setUser(user);
        user.addTable(table);
        tableRepo.save(table);
        return "attached";
    }

    @Override
    @Transactional
    public void destroyTable(Long tableId) {
        CafeTable table = tableRepo.findCafeTableById(tableId);
        if (null == table) {
            throw new InstanceNotFoundException("table");
        }
        User user = table.getUser();
        if (null != user) {
            table.getUser().removeTable(table);
        }

        for (Order order : table.getOrders()) {
            order.setProductsInOrders(new HashSet<>());
        }

        table.setOrders(new HashSet<>());
        tableRepo.delete(table);
    }

    @Override
    public CafeTable update(Long id, TableCreateRequestBody requestBody) {
        CafeTable table = tableRepo.findCafeTableById(id);
        if (null == table) {
            throw new InstanceNotFoundException("table");
        }

        CafeTable existingTable = tableRepo.findCafeTableByNameAndIdIsNot(requestBody.getName(), id);
        if (null != existingTable) {
            throw new MustBeUniqueException("name");
        }

        if (!table.getName().equals(requestBody.getName())) {
            table.setName(requestBody.getName());
            tableRepo.save(table);
        }

        return table;
    }

    @Override
    public Collection<OnlyTableProps> getUserAssignedTables(Long id) {
        return tableRepo.getAllByUserIdIs(id);
    }

    @Override
    public Collection<TableWithOpenOrdersCount> getUserAssignedTablesWithCount(Long id) {
        return this.tableRepo.userTablesWithOrders(id);
    }

    @Override
    public CafeTable getUserAssignedTable(Long tableID, User user) {
        CafeTable table = tableRepo.findCafeTableByIdAndUser(tableID, user);
        if (null == table) {
            throw new InstanceNotFoundException("table");
        }
        return table;
    }
}
