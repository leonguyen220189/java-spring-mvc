package vn.hoidanit.laptopshop.controller.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @GetMapping("/products")
    public String getProductsPage(Model model, @RequestParam("page") Optional<String> pageOptional,
            @RequestParam("name") Optional<String> nameOptional,
            @RequestParam("min-price") Optional<String> minOptional,
            @RequestParam("max-price") Optional<String> maxOptional,
            @RequestParam("factory") Optional<String> factoryOptional,
            @RequestParam("price") Optional<String> priceOptional) {
        int page = 1;
        try {
            if (pageOptional.isPresent()) {
                page = Integer.parseInt(pageOptional.get());
            } else {
                // page = 1;
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

        String name = nameOptional.isPresent() ? nameOptional.get() : "";

        Pageable pageable = PageRequest.of(page - 1, 20);

        // test case
        Page<Product> products = this.productService.fetchProductsWithSpec(pageable, name);

        // case 1
        double min = minOptional.isPresent() ? Double.parseDouble(minOptional.get()) : 0;

        // case 2
        double max = maxOptional.isPresent() ? Double.parseDouble(maxOptional.get()) : 0;

        // case 3
        String factory = factoryOptional.isPresent() ? factoryOptional.get() : "";

        // case 4
        List<String> factorys = Arrays.asList(factoryOptional.get().split(","));

        // case 5
        String price = priceOptional.isPresent() ? priceOptional.get() : "";

        // case 6
        List<String> prices = Arrays.asList(priceOptional.get().split(","));

        List<Product> prds = products.getContent();
        model.addAttribute("products", prds);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());
        return "client/product/show";
    }

}
