package org.example.cafemanager.services.order.contracts;

import org.example.cafemanager.domain.Order;
import org.example.cafemanager.domain.ProductsInOrder;
import org.example.cafemanager.domain.User;
import org.example.cafemanager.domain.enums.Status;
import org.example.cafemanager.dto.order.OrderCreate;
import org.example.cafemanager.dto.order.ProductInOrderUpdate;

public interface OrderService {
    Order createOrder(OrderCreate orderCreate);

    void destroyProductInOrder(Long orderId, Long pioId, User user);

    ProductsInOrder updateProductInOrder(ProductInOrderUpdate productUpdate);

    Order updateStatus(Long orderId, Status status, User user);

    void destroyOrder(Long orderId, User user);
}
