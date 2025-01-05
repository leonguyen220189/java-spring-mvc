package vn.hoidanit.laptopshop.controller.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.domain.DTO.RegisterDTO;
import vn.hoidanit.laptopshop.service.CartDetailService;
import vn.hoidanit.laptopshop.service.CartService;
import vn.hoidanit.laptopshop.service.ProductService;
import vn.hoidanit.laptopshop.service.UserService;

@Controller
public class HomePageController {
    private final ProductService productService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final CartDetailService cartDetailService;
    private final CartService cartService;

    public HomePageController(ProductService productService, UserService userService, PasswordEncoder passwordEncoder,
            CartDetailService cartDetailService, CartService cartService) {
        this.productService = productService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.cartDetailService = cartDetailService;
        this.cartService = cartService;
    }

    @GetMapping("/")
    public String getHomePage(Model model) {
        List<Product> products = this.productService.fetchProducts();
        model.addAttribute("products", products);
        return "client/homepage/show";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("registerDTO", new RegisterDTO());
        return "client/auth/register";
    }

    @PostMapping("/register")
    public String handleRegister(@ModelAttribute("registerDTO") @Valid RegisterDTO registerDTO,
            BindingResult userBindingResult) {
        List<FieldError> errors = userBindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(">>>>>" + error.getField() + " - " + error.getDefaultMessage());
        }
        if (userBindingResult.hasErrors()) {
            return "client/auth/register";
        }
        User user = this.userService.registerDTOtoUser(registerDTO);
        String hashedPw = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPw);
        user.setRole(this.userService.fetchRoleByName("USER").get());
        this.userService.saveUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "client/auth/login";
    }

    @GetMapping("/access-deny")
    public String getDenyPage() {
        return "client/auth/deny";
    }

    @GetMapping("/cart")
    public String getCartPage(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        float total_price = 0;
        Cart cart = this.cartService
                .fetchCartByUser(userService.fetchUserByEmail((String) session.getAttribute("email")));
        List<CartDetail> cartDetails = cart == null
                ? new ArrayList<CartDetail>()
                : cart.getCartDetails(); // nó sẽ tự join 2 table cho chúng ta

        for (CartDetail cartDetail : cartDetails) {
            total_price += Float.parseFloat(cartDetail.getPrice());
        }

        model.addAttribute("cart", cart);
        model.addAttribute("cartDetails", cartDetails);
        model.addAttribute("total_price", total_price);
        return "client/cart/show";
    }

    @PostMapping("/confirm-checkout")
    public String getCheckoutPage(Model model, @ModelAttribute("cart") Cart cart) {
        List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();
        this.cartDetailService.updateCartDetailsBeforeCheckout(cartDetails);
        return "redirect:/checkout";
    }

    @GetMapping("/checkout")
    public String getCheckoutPage(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        float total_price = 0;
        Cart cart = this.cartService
                .fetchCartByUser(userService.fetchUserByEmail((String) session.getAttribute("email")));
        List<CartDetail> cartDetails = cart == null
                ? new ArrayList<CartDetail>()
                : cart.getCartDetails(); // nó sẽ tự join 2 table cho chúng ta

        for (CartDetail cartDetail : cartDetails) {
            total_price += Float.parseFloat(cartDetail.getPrice());
        }

        model.addAttribute("cartDetails", cartDetails);
        model.addAttribute("total_price", total_price);
        return "client/checkout/show";
    }

}
