package org.example.cafemanager.services.order;

import org.example.cafemanager.domain.*;
import org.example.cafemanager.dto.order.OrderCreate;
import org.example.cafemanager.dto.order.ProductInOrderReq;
import org.example.cafemanager.repositories.OrderRepository;
import org.example.cafemanager.repositories.ProductsInOrderRepository;
import org.example.cafemanager.services.exceptions.ChooseAtLeastOneException;
import org.example.cafemanager.services.exceptions.InstanceNotFoundException;
import org.example.cafemanager.services.order.contracts.OrderService;
import org.example.cafemanager.services.product.contracts.ProductService;
import org.example.cafemanager.services.table.contracts.TableService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;
    private TableService tableService;
    private ProductService productService;
    private ProductsInOrderRepository productsInOrderRepository;

    public OrderServiceImpl(
            OrderRepository orderRepository,
            ProductsInOrderRepository productsInOrderRepository,
            TableService tableService,
            ProductService productService
    ) {

        this.orderRepository = orderRepository;
        this.tableService = tableService;
        this.productService = productService;
        this.productsInOrderRepository = productsInOrderRepository;
    }

    @Transactional
    @Override
    public Order createOrder(OrderCreate orderCreate) {
        CafeTable table = tableService.getUserAssignedTable(orderCreate.getTableId(), orderCreate.getUser());

        if (null == table) {
            throw new InstanceNotFoundException("table");
        }

        Set<ProductsInOrder> productsInOrderSet = new HashSet<>();
        Set<Product> productsSet = new HashSet<>();
        Order order = new Order();
        order.setCafeTable(table);
        orderRepository.save(order);
        for (ProductInOrderReq pio : orderCreate.getProdsInOrder()) {
            Product product = productService.findOneById(pio.getProductId());

            if (null == product) {
                continue;
            }

            ProductsInOrder productsInOrder = new ProductsInOrder();
            productsInOrder.setAmount(pio.getAmount());
            productsInOrder.setProduct(product);
            productsInOrder.setOrder(order);
            productsInOrderRepository.save(productsInOrder);

            productsInOrderSet.add(productsInOrder);
            productsSet.add(product);
        }

//        productsInOrderRepository.saveAll(productsInOrderSet);
        if (0 == productsSet.size()) {
            throw new ChooseAtLeastOneException("product");
        }

        order.setProductsInOrders(productsInOrderSet);
        order.setProducts(productsSet);
        orderRepository.save(order);
        return order;
    }
}
