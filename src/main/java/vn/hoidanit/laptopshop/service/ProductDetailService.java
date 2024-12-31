package vn.hoidanit.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.ProductDetail;
import vn.hoidanit.laptopshop.repository.ProductDetailRepository;

@Service
public class ProductDetailService {
    private final ProductDetailRepository productDetailRepository;

    public ProductDetailService(ProductDetailRepository productDetailRepository) {
        this.productDetailRepository = productDetailRepository;
    }

    public ProductDetail saveProductDetail(ProductDetail productDetail) {
        return this.productDetailRepository.save(productDetail);
    }

    public void deleteProductDetailById(long id) {
        this.productDetailRepository.deleteById(id);
    }

    public List<ProductDetail> fetchProductDetails() {
        return this.productDetailRepository.findAll();
    }
}
