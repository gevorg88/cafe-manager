package org.example.cafemanager.domain;

import org.example.cafemanager.domain.enums.ProdInOrderStatus;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "products_in_order")
public class ProductsInOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status")
    private ProdInOrderStatus status;

    private Integer amount = 0;

    @JoinColumn(name = "order_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    @JoinColumn(name = "product_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    public ProductsInOrder() {
        this.status = ProdInOrderStatus.ORDERED;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductsInOrder that = (ProductsInOrder) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
