package org.example.cafemanager.services.order.contracts;

import org.example.cafemanager.domain.Order;
import org.example.cafemanager.domain.ProductsInOrder;
import org.example.cafemanager.domain.User;
import org.example.cafemanager.domain.enums.Status;
import org.example.cafemanager.dto.order.OrderDetails;
import org.example.cafemanager.dto.order.UpdateProductInOrderDto;

public interface OrderService {
    Order createOrder(OrderDetails orderDetails);

    void destroyProductInOrder(Long orderId, Long pioId, User user);

    ProductsInOrder updateProductInOrder(UpdateProductInOrderDto productUpdate);

    Order updateStatus(Long orderId, Status status, User user);

    void deleteOrder(Long orderId, User user);
}
