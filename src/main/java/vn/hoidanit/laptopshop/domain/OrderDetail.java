package vn.hoidanit.laptopshop.domain;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_detail")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long quantity;
    private long price;

    // order_id
    // OrderDetail many to one order
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    // product_id
    // OrderDetail many to one product
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    // chỉ khai báo quan hệ 1 chiều giữa OrderDetail và Product để chỉ xác nhận
    // trong OrderDatail xuất hiện Product nào
    // Ngược lại chúng ta ko cần quan tâm 1 Product xuất hiển trong bao nhiêu
    // OrderDetail
}
