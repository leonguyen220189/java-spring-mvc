package vn.hoidanit.laptopshop.controller.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import vn.hoidanit.laptopshop.domain.Order;
import vn.hoidanit.laptopshop.domain.OrderDetail;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.OrderService;

@Controller
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/admin/order")
    public String getOrderPage(Model model) {
        List<Order> orders = this.orderService.fetchOrders();
        List<User> users = new ArrayList();
        for (Order order : orders) {
            users.add(order.getUser());
        }
        model.addAttribute("orders", orders);
        model.addAttribute("users", users);
        return "admin/order/show";
    }

    @GetMapping("/admin/order/{id}")
    public String getOrderDetailPage(Model model, @PathVariable long id) {
        Order order = this.orderService.fetchOrderById(id);
        List<OrderDetail> orderDetails = order.getOrderDetails();

        model.addAttribute("orderDetails", orderDetails);
        model.addAttribute("order", order);
        return "admin/order/detail";
    }

    @GetMapping("/admin/order/update/{id}")
    public String getUpdateOrderPage(Model model, @PathVariable long id) {
        Order order = this.orderService.fetchOrderById(id);

        model.addAttribute("order", order);
        return "admin/order/update";
    }

    @PostMapping("/admin/order/update")
    public String postUpdateOrder(@ModelAttribute("order") Order order) {
        Order adjustedOrder = this.orderService.fetchOrderById(order.getId());
        adjustedOrder.setStatus(order.getStatus());
        this.orderService.saveOrder(adjustedOrder);
        return "redirect:/admin/order";
    }

    @PostMapping("/admin/order/delete/{id}")
    public String postDeleteOrder(@PathVariable long id) {
        this.orderService.deleteOrderById(id);
        return "redirect:/admin/order";
    }
}
