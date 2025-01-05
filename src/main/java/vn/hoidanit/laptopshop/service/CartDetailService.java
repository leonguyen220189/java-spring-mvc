package vn.hoidanit.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.repository.CartDetailRepository;

@Service
public class CartDetailService {
    private final CartDetailRepository cartDetailRepository;

    public CartDetailService(CartDetailRepository cartDetailRepository) {
        this.cartDetailRepository = cartDetailRepository;
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
}
