package arbuz.subscriptionapi.entities.subscription;

import arbuz.subscriptionapi.entities.basket.ReservedProduct;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Subscriber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long number;

    private String address;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "basket_products",
            joinColumns = @JoinColumn(name = "subscriber_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<ReservedProduct> inBasket;

    public Subscriber() {
    }

    public Subscriber(Long number, String address) {
        this.number = number;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<ReservedProduct> getInBasket() {
        return inBasket;
    }

    public void setInBasket(List<ReservedProduct> inBasket) {
        this.inBasket = inBasket;
    }
}
