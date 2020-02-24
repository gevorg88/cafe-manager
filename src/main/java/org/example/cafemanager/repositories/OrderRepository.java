package org.example.cafemanager.repositories;

import org.example.cafemanager.domain.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
