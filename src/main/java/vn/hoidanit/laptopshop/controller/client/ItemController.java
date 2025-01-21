package vn.hoidanit.laptopshop.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.ui.Model;

import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.domain.DTO.OrderDTO;
import vn.hoidanit.laptopshop.service.CartDetailService;
import vn.hoidanit.laptopshop.service.OrderService;
import vn.hoidanit.laptopshop.service.ProductService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class ItemController {
    final private ProductService productService;
    final private CartDetailService cartDetailService;
    private final OrderService orderService;

    public ItemController(ProductService productService, CartDetailService cartDetailService,
            OrderService orderService) {
        this.productService = productService;
        this.cartDetailService = cartDetailService;
        this.orderService = orderService;
    }

    @GetMapping("/product/{id}")
    public String getMethodName(Model model, @PathVariable long id) {
        Product product = this.productService.fetchProductById(id).get();
        model.addAttribute("product", product);
        return "client/homepage/detail";
    }

    @PostMapping("/add-product-to-cart/{id}")
    public String addProductToCart(@PathVariable long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        long productId = id;
        this.productService.addProductToCart(session, productId, 1);
        return "redirect:/";
    }

    @PostMapping("/add-product-from-view-detail/{id}")
    public String postAddProductFromViewDetail(@PathVariable long id, @RequestParam("quantity") long quantity,
            HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        long productId = id;
        this.productService.addProductToCart(session, productId, quantity);
        return "redirect:/product/" + id;
    }

    @PostMapping("/cart/del-cart-detail/{id}")
    public String delCartDetailById(@PathVariable long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        this.cartDetailService.RemoveCartDetail(session, id);
        return "redirect:/cart";
    }

    @PostMapping("/place-order")
    public String placeOrder(HttpServletRequest request, @ModelAttribute("orderDTO") OrderDTO orderDTO) {
        HttpSession session = request.getSession(false);
        User user = new User();
        user.setId((long) session.getAttribute("id"));

        this.orderService.placeOrder(session, user, orderDTO);

        return "redirect:/placed-order";
    }

}
