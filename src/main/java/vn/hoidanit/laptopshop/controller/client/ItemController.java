package vn.hoidanit.laptopshop.controller.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.ui.Model;

import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.service.CartDetailService;
import vn.hoidanit.laptopshop.service.CartService;
import vn.hoidanit.laptopshop.service.ProductService;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class ItemController {
    final private ProductService productService;
    final private CartDetailService cartDetailService;

    public ItemController(ProductService productService, CartDetailService cartDetailService) {
        this.productService = productService;
        this.cartDetailService = cartDetailService;
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
        this.productService.addProductToCart(session, productId);
        return "redirect:/";
    }

    @PostMapping("/cart/del-cart-detail/{id}")
    public String delCartDetailById(@PathVariable long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        this.cartDetailService.RemoveCartDetail(session, id);
        return "redirect:/cart";
    }
}
