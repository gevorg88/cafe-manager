package org.example.cafemanager.repositories;

import org.example.cafemanager.domain.CafeTable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CafeTableRepository extends CrudRepository<CafeTable, Long> {
    CafeTable findByName(String name);
}