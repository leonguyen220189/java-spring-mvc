package vn.hoidanit.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.Order;
import vn.hoidanit.laptopshop.domain.OrderDetail;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.domain.DTO.OrderDTO;
import vn.hoidanit.laptopshop.repository.OrderRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailService orderDetailService;
    private final CartService cartService;
    private final CartDetailService cartDetailService;

    public OrderService(OrderRepository orderRepository, CartService cartService,
            OrderDetailService orderDetailService, CartDetailService cartDetailService) {
        this.orderDetailService = orderDetailService;
        this.cartService = cartService;
        this.orderRepository = orderRepository;
        this.cartDetailService = cartDetailService;
    }

    public List<Order> fetchAllOrder() {
        return this.orderRepository.findAll();
    }

    public Order fetchOrderById(long id) {
        return this.orderRepository.findById(id);
    }

    public Order saveOrder(Order order) {
        return this.orderRepository.save(order);
    }

    public void deleteOrderById(long id) {
        this.orderRepository.deleteById(id);
    }

    public void placeOrder(HttpSession session, User user, OrderDTO orderDTO) {

        // create order
        Order order = new Order();
        order.setUser(user);
        order.setRecieverAddress(orderDTO.getRecieverAddress());
        order.setRecieverMobile(orderDTO.getRecieverMobile());
        order.setRecieverName(orderDTO.getRecieverName());
        order.setStatus("PENDING");
        order = this.orderRepository.save(order);
        long total_price = 0;

        // create order detail
        Cart cart = this.cartService.fetchCartByUser(user);
        List<CartDetail> cartDetails = cart.getCartDetails();
        if (cartDetails != null) {

            // handle order detail
            for (CartDetail cd : cartDetails) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrder(order);
                orderDetail.setProduct(cd.getProduct());
                orderDetail.setPrice(cd.getPrice());
                orderDetail.setQuantity(cd.getQuantity());
                this.orderDetailService.saveOrderDetail(orderDetail);
                total_price = total_price + Long.parseLong(cd.getPrice());
            }

            order.setTotalPrice(String.valueOf(total_price));
            this.orderRepository.save(order);

            // delete cart detail and update cart
            for (CartDetail cd : cartDetails) {
                this.cartDetailService.RemoveCartDetail(session, cd.getId());
            }

            // update session
            session.setAttribute("numberOfCartDetails", 0);
        }
    }

}
