package org.example.cafemanager.services.table.contracts;

import org.example.cafemanager.domain.CafeTable;
import org.example.cafemanager.domain.User;
import org.example.cafemanager.dto.table.*;

import java.util.Collection;

public interface TableService {
    Collection<SimpleTableProps> getAllTables();

    CafeTable createTable(TableCreate createDto);

    String assignUser(Long tableId, Long userId);

    void destroyTable(Long tableId);

    CafeTable update(Long id, TableCreateRequestBody requestBody);

    Collection<OnlyTableProps> getUserAssignedTables(Long id);

    Collection<TableWithOpenOrdersCount> getUserAssignedTablesWithCount(Long id);

    CafeTable getUserAssignedTable(Long tableID, User user);
}
