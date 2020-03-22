package org.example.cafemanager.services.order.contracts;

import org.example.cafemanager.domain.Order;
import org.example.cafemanager.dto.order.OrderCreate;

public interface OrderService {
    Order createOrder(OrderCreate orderCreate);
}
