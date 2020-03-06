package org.example.cafemanager.services.table.contracts;

import org.example.cafemanager.dto.table.SimpleTableProps;

import java.util.Collection;

public interface TableService {
    Collection<SimpleTableProps> getAllTables();
}
