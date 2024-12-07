package vn.hoidanit.laptopshop.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "product_detail")
public class ProductDetail {
    @Id
    private long id;

    // ProductDetail One to one Product
    // Ở đây ko sử dụng Generate cho Id nữa mà mapping tới id của Product -> id này
    // vừa là khóa chính vừa là khóa ngoại
    @OneToOne
    @MapsId
    private Product product;

    private String detailDesc;
    private String shortDesc;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getDetailDesc() {
        return detailDesc;
    }

    public void setDetailDesc(String detailDesc) {
        this.detailDesc = detailDesc;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    @Override
    public String toString() {
        return "ProductDetail [id=" + id + ", product=" + product + ", detailDesc=" + detailDesc + ", shortDesc="
                + shortDesc + "]";
    }
}
