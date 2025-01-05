package vn.hoidanit.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.repository.CartDetailRepository;

@Service
public class CartDetailService {
    private final CartDetailRepository cartDetailRepository;
    private final CartService cartService;

    public CartDetailService(CartDetailRepository cartDetailRepository, CartService cartService) {
        this.cartDetailRepository = cartDetailRepository;
        this.cartService = cartService;
    }

    public CartDetail saveCartDetail(CartDetail cartDetail) {
        return this.cartDetailRepository.save(cartDetail);
    }

    public Boolean checkExistedCartDetail(Cart cart, Product product) {
        return this.cartDetailRepository.existsByCartAndProduct(cart, product);
    }

    public CartDetail fetchCartDetailByCartAndProduct(Cart cart, Product product) {
        return this.cartDetailRepository.findByCartAndProduct(cart, product);
    }

    public long countCartDetailsByCart(Cart cart) {
        return this.cartDetailRepository.countByCart(cart);
    }

    public List<CartDetail> fetchCartDetailsByCart(Cart cart) {
        return this.cartDetailRepository.findAllByCart(cart);
    }

    public CartDetail fetchCartDetailById(long id) {
        return this.cartDetailRepository.findById(id);
    }

    public void delCartDetailById(long id) {
        this.cartDetailRepository.deleteById(id);
    }

    public void RemoveCartDetail(HttpSession session, long cartDetailId) {
        CartDetail cartDetail = this.fetchCartDetailById(cartDetailId);
        Cart cart = this.cartService.fetchCartById(cartDetail.getCart().getUser().getId());
        this.delCartDetailById(cartDetailId);
        if (cart.getTotal_quantity() > 0) {
            cart.setTotal_quantity(cart.getTotal_quantity() - 1);
        }
        session.setAttribute("numberOfCartDetails", cart.getTotal_quantity());
    }
}
