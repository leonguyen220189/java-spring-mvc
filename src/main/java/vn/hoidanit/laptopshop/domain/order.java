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
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String totalPrice;
    private String recieverName;
    private String recieverAddress;
    private String recieverMobile;
    private String status;

    // user id: order many to one user
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Order one to many Order Detail
    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getRecieverName() {
        return recieverName;
    }

    public void setRecieverName(String recieverName) {
        this.recieverName = recieverName;
    }

    public String getRecieverAddress() {
        return recieverAddress;
    }

    public void setRecieverAddress(String recieverAddress) {
        this.recieverAddress = recieverAddress;
    }

    public String getRecieverMobile() {
        return recieverMobile;
    }

    public void setRecieverMobile(String recieverMobile) {
        this.recieverMobile = recieverMobile;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    @Override
    public String toString() {
        return "Order [id=" + id + ", totalPrice=" + totalPrice + ", recieverName=" + recieverName
                + ", recieverAddress=" + recieverAddress + ", recieverMobile=" + recieverMobile + ", status=" + status
                + ", user=" + user + ", orderDetails=" + orderDetails + "]";
    }
}
