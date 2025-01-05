package vn.hoidanit.laptopshop.service;

import java.util.List;
import java.util.Optional;

import vn.hoidanit.laptopshop.domain.User;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.repository.CartDetailRepository;
import vn.hoidanit.laptopshop.repository.CartRepository;
import vn.hoidanit.laptopshop.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CartDetailService cartDetailService;
    private final CartService cartService;
    private final UserService userService;

    public ProductService(ProductRepository productRepository, CartDetailService cartDetailService,
            CartService cartService, UserService userService) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.cartDetailService = cartDetailService;
        this.cartService = cartService;
    }

    public Optional<Product> fetchProductById(long id) {
        return this.productRepository.findById(id);
    }

    public Product saveProduct(Product product) {
        return this.productRepository.save(product);
    }

    public void deleteProductById(long id) {
        this.productRepository.deleteById(id);
    }

    public List<Product> fetchProducts() {
        return this.productRepository.findAll();
    }

    public void addProductToCart(HttpSession session, long productId) {
        User user = this.userService.fetchUserByEmail((String) session.getAttribute("email"));
        if (user != null) {
            Cart cart = this.cartService.fetchCartByUser(user);
            if (cart == null) {
                // create new cart
                Cart otherCart = new Cart();
                otherCart.setUser(user);
                otherCart.setTotal_quantity(1);

                cart = this.cartService.saveCart(otherCart);
            }

            Product product = fetchProductById(productId).get();
            Boolean ExistedCartDetail = this.cartDetailService.checkExistedCartDetail(cart, product);

            if (ExistedCartDetail) {
                CartDetail cartDetail = this.cartDetailService.fetchCartDetailByCartAndProduct(cart, product);
                cartDetail.setQuantity(cartDetail.getQuantity() + 1);
                Float price = Float.parseFloat(cartDetail.getPrice()) * cartDetail.getQuantity();
                cartDetail.setPrice(String.valueOf(price));
            } else {
                CartDetail cartDetail = new CartDetail();
                // save cart detail into cart
                cartDetail.setCart(cart);
                cartDetail.setProduct(product);
                cartDetail.setPrice(product.getPrice());
                cartDetail.setQuantity(1);

                this.cartDetailService.saveCartDetail(cartDetail);
            }
            cart.setTotal_quantity(this.cartDetailService.countCartDetailsByCart(cart));
            session.setAttribute("numberOfCartDetails", cart.getTotal_quantity());
        }
    }
}
