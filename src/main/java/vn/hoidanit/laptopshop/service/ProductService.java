package vn.hoidanit.laptopshop.service;

import java.util.List;
import java.util.Optional;

import vn.hoidanit.laptopshop.domain.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.Product_;
import vn.hoidanit.laptopshop.repository.CartDetailRepository;
import vn.hoidanit.laptopshop.repository.CartRepository;
import vn.hoidanit.laptopshop.repository.ProductRepository;
import vn.hoidanit.laptopshop.service.specification.ProductSpecs;

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

    public Page<Product> fetchProductsPagination(Pageable pageable) {
        return this.productRepository.findAll(pageable);
    }

    public long countProduct() {
        return this.productRepository.count();
    }

    public Page<Product> fetchProductsWithSpec(Pageable pageable, double min) {
        return this.productRepository.findAll(ProductSpecs.minPrice(min), pageable);
    }

    // case 5
    public Page<Product> fetchProductsWithSpec(Pageable page, String price) {
        // eg: price 10-toi-15-trieu
        if (price.equals("10-toi-15-trieu")) {
            double min = 10000000;
            double max = 15000000;
            return this.productRepository.findAll(ProductSpecs.matchPrice(min, max),
                    page);
        } else if (price.equals("15-toi-30-trieu")) {
            double min = 15000000;
            double max = 30000000;
            return this.productRepository.findAll(ProductSpecs.matchPrice(min, max),
                    page);
        } else
            return this.productRepository.findAll(page);
    }

    // case 6
    public Page<Product> fetchProductsWithSpec(Pageable page, List<String> price) {
        Specification<Product> combinedSpec = (root, query, criteriaBuilder) -> criteriaBuilder.disjunction();
        int count = 0;
        for (String p : price) {
            double min = 0;
            double max = 0;
            // Set the appropriate min and max based on the price range string
            switch (p) {
                case "10-toi-15-trieu":
                    min = 10000000;
                    max = 15000000;
                    count++;
                    break;
                case "15-toi-20-trieu":
                    min = 15000000;
                    max = 20000000;
                    count++;
                    break;
                case "20-toi-30-trieu":
                    min = 20000000;
                    max = 30000000;
                    count++;
                    break;
                // Add more cases as needed
            }
            if (min != 0 && max != 0) {
                Specification<Product> rangeSpec = ProductSpecs.matchMultiplePrice(min, max);
                combinedSpec = combinedSpec.or(rangeSpec);
            }
        }
        // Check if any price ranges were added (combinedSpec is empty)
        if (count == 0) {
            return this.productRepository.findAll(page);
        }
        return this.productRepository.findAll(combinedSpec, page);
    }

    public void addProductToCart(HttpSession session, long productId, long quantity) {
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
                this.cartDetailService.updateCartDetail(cart, product,
                        this.cartDetailService.fetchCartDetailByCartAndProduct(cart, product).getQuantity() + quantity);
            } else {
                CartDetail cartDetail = new CartDetail();
                // save cart detail into cart
                cartDetail.setCart(cart);
                cartDetail.setProduct(product);
                cartDetail.setPrice(product.getPrice());
                cartDetail.setQuantity(quantity);

                this.cartDetailService.saveCartDetail(cartDetail);
            }
            cart.setTotal_quantity(this.cartDetailService.countCartDetailsByCart(cart));
            session.setAttribute("numberOfCartDetails", cart.getTotal_quantity());
        }
    }
}
