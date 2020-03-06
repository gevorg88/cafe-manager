package org.example.cafemanager.repositories;

import org.example.cafemanager.domain.CafeTable;
import org.example.cafemanager.dto.table.SimpleTableProps;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CafeTableRepository extends CrudRepository<CafeTable, Long> {
    CafeTable findByName(String name);

    Collection<SimpleTableProps> findAllBy();
}