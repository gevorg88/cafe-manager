package org.example.cafemanager.services;

import org.example.cafemanager.EntitiesBuilder;
import org.example.cafemanager.Util;
import org.example.cafemanager.domain.CafeTable;
import org.example.cafemanager.domain.User;
import org.example.cafemanager.dto.table.OnlyTableProps;
import org.example.cafemanager.dto.table.SimpleTableProps;
import org.example.cafemanager.dto.table.TableCreate;
import org.example.cafemanager.dto.table.TableCreateRequestBody;
import org.example.cafemanager.repositories.CafeTableRepository;
import org.example.cafemanager.services.exceptions.InstanceNotFoundException;
import org.example.cafemanager.services.exceptions.MustBeUniqueException;
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

    @Test
    public void createWithNullableRequest() {
        Assert.assertThrows(IllegalArgumentException.class, () -> tableService.create(null));
    }

    @Test
    public void createWithNullableTableName() {
        Assert.assertThrows(IllegalArgumentException.class, () -> tableService.create(new TableCreate(null)));
    }

    @Test
    public void createWitDuplicateTableName() {
        OnlyTableProps tableProps = EntitiesBuilder.createOnlyTableProps(1L);
        TableCreate createReq = new TableCreate(tableProps.getName());
        Mockito.when(cafeTableRepository.findOneByName(tableProps.getName())).thenReturn(tableProps);
        Assert.assertThrows(MustBeUniqueException.class, () -> tableService.create(createReq));
    }

    @Test
    public void create() {
        CafeTable table = EntitiesBuilder.createCafeTable();
        TableCreate createReq = new TableCreate(table.getName());
        Mockito.when(cafeTableRepository.save(table)).thenReturn(table);
        CafeTable t = tableService.create(createReq);
        Assert.assertEquals(table.getName(), tableService.create(createReq).getName());
    }

    @Test
    public void assignUserNotFoundTable() {
        Long tableId = Util.randomLong();
        Long userId = Util.randomLong();
        Mockito.when(cafeTableRepository.findCafeTableById(tableId)).thenReturn(null);
        Assert.assertThrows(InstanceNotFoundException.class, () -> tableService.assignUser(tableId, userId));
    }

    @Test
    public void assignUserNotFoundUser() {
        CafeTable t = EntitiesBuilder.createCafeTable();
        Long userId = Util.randomLong();
        Mockito.when(cafeTableRepository.findCafeTableById(t.getId())).thenReturn(t);
        Mockito.when(userService.getUserById(userId)).thenReturn(null);
        Assert.assertThrows(InstanceNotFoundException.class, () -> tableService.assignUser(t.getId(), userId));
    }

    @Test
    public void assignUserDetaching() {
        CafeTable t = EntitiesBuilder.createCafeTable();
        Long userId = -1L;
        Mockito.when(cafeTableRepository.findCafeTableById(t.getId())).thenReturn(t);
        Assert.assertEquals(CafeTable.DETACHED, tableService.assignUser(t.getId(), userId));
    }

    @Test
    public void assignUser() {
        CafeTable t = EntitiesBuilder.createCafeTable();
        User u = EntitiesBuilder.createUser();
        Mockito.when(cafeTableRepository.findCafeTableById(t.getId())).thenReturn(t);
        Mockito.when(userService.getUserById(u.getId())).thenReturn(u);
        Assert.assertEquals(CafeTable.ATTACHED, tableService.assignUser(t.getId(), u.getId()));
    }

    @Test
    public void updateWithNullableRequest() {
        Assert.assertThrows(IllegalArgumentException.class, () -> tableService.update(1L, null));
    }

    @Test
    public void updateWithNullableTableName() {
        Assert.assertThrows(IllegalArgumentException.class, () -> tableService.update(1L, new TableCreateRequestBody()));
    }

    @Test
    public void updateWithNotFoundTable() {
        Mockito.when(cafeTableRepository.findCafeTableById(1L)).thenReturn(null);
        Assert.assertThrows(InstanceNotFoundException.class, ()->tableService.update(1L, EntitiesBuilder.createTableCreateRequestBody()));
    }

    @Test
    public void updateWitDuplicateTableName() {
        TableCreateRequestBody tb = EntitiesBuilder.createTableCreateRequestBody();
        CafeTable t = EntitiesBuilder.createCafeTable();
        t.setName(tb.getName());
        Mockito.when(cafeTableRepository.findCafeTableById(t.getId())).thenReturn(t);
        Mockito.when(cafeTableRepository.findCafeTableByNameAndIdIsNot(tb.getName(), t.getId())).thenReturn(t);
        Assert.assertThrows(MustBeUniqueException.class, () -> tableService.update(t.getId(), tb));
    }

    @Test
    public void update() {
        CafeTable table = EntitiesBuilder.createCafeTable();
        TableCreate createReq = new TableCreate(table.getName());
        Mockito.when(cafeTableRepository.save(table)).thenReturn(table);
        CafeTable t = tableService.create(createReq);
        Assert.assertEquals(table.getName(), tableService.create(createReq).getName());
    }
}
