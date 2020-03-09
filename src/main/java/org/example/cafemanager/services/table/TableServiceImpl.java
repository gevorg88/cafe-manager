package org.example.cafemanager.services.table;

import org.example.cafemanager.domain.CafeTable;
import org.example.cafemanager.domain.User;
import org.example.cafemanager.dto.table.OnlyTableProps;
import org.example.cafemanager.dto.table.SimpleTableProps;
import org.example.cafemanager.dto.table.TableCreate;
import org.example.cafemanager.dto.table.TableCreateRequestBody;
import org.example.cafemanager.repositories.CafeTableRepository;
import org.example.cafemanager.services.exceptions.InstanceNotFoundException;
import org.example.cafemanager.services.exceptions.MustBeUniqueException;
import org.example.cafemanager.services.table.contracts.TableService;
import org.example.cafemanager.services.user.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

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
    public void destroyTable(Long tableId) {
        CafeTable table = tableRepo.findCafeTableById(tableId);
        if (null == table) {
            throw new InstanceNotFoundException("table");
        }
        User user = table.getUser();
        if (null != user) {
            table.getUser().removeTable(table);
        }
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
}
