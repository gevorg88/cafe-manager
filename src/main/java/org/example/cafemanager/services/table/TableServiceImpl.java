package org.example.cafemanager.services.table;

import org.example.cafemanager.dto.table.SimpleTableProps;
import org.example.cafemanager.repositories.CafeTableRepository;
import org.example.cafemanager.services.table.contracts.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class TableServiceImpl implements TableService {
    private final CafeTableRepository tableRepo;

    @Autowired
    public TableServiceImpl(CafeTableRepository cafeTableRepository) {
        tableRepo = cafeTableRepository;
    }

    @Override
    public Collection<SimpleTableProps> getAllTables() {
        return tableRepo.findAllBy();
    }
}
