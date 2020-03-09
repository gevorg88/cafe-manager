package org.example.cafemanager.repositories;

import org.example.cafemanager.domain.CafeTable;
import org.example.cafemanager.dto.table.OnlyTableProps;
import org.example.cafemanager.dto.table.SimpleTableProps;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.Collection;

@Repository
public interface CafeTableRepository extends CrudRepository<CafeTable, Long> {
    CafeTable findByName(String name);

    Collection<SimpleTableProps> findAllBy();

    OnlyTableProps findOneByName(String name);

    CafeTable findCafeTableById(Long id);

    CafeTable findCafeTableByNameAndIdIsNot(@NotNull String name, @NotNull Long id);
}