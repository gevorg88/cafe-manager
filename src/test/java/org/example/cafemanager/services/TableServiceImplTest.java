package org.example.cafemanager.services;

import org.example.cafemanager.EntitiesBuilder;
import org.example.cafemanager.dto.table.SimpleTableProps;
import org.example.cafemanager.repositories.CafeTableRepository;
import org.example.cafemanager.services.table.impls.TableServiceImpl;
import org.example.cafemanager.services.user.impls.UserServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class TableServiceImplTest {

    @InjectMocks
    private TableServiceImpl tableService;

    @Mock
    private CafeTableRepository cafeTableRepository;

    @Mock
    private UserServiceImpl userService;

    @Test
    public void getAllTables() {
        List<SimpleTableProps> tables =
                Arrays.asList(EntitiesBuilder.createSimpleTableProps(1L), EntitiesBuilder.createSimpleTableProps(2L));
        Mockito.when(cafeTableRepository.findAllBy()).thenReturn(tables);
        Assert.assertEquals(tableService.getAllTables().size(), tables.size());
    }

    @Test()
    public void createWithNullableRequest() {
        Assert.assertThrows(IllegalArgumentException.class, () -> tableService.create(null));
    }
}
