package org.example.cafemanager.services.table;

import org.example.cafemanager.repositories.CafeTableRepository;
import org.example.cafemanager.services.table.contracts.TableService;

public class TableServiceImpl implements TableService {
    private final CafeTableRepository tableRepo;

    public TableServiceImpl(CafeTableRepository cafeTableRepository) {
        tableRepo = cafeTableRepository;
    }
}
