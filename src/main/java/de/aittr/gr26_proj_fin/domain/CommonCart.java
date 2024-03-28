package de.aittr.gr26_proj_fin.domain;

import de.aittr.gr26_proj_fin.domain.interfaces.Cart;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "cart")
public class CommonCart implements Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "customer_id")
    private int customer_id;

    //private List<CommonBook> books = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommonCart that = (CommonCart) o;
        return id == that.id && customer_id == that.customer_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customer_id);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CommonCart{");
        sb.append("id=").append(id);
        sb.append(", customer_id=").append(customer_id);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setCustomerId(int customerId) {
        this.customer_id = customerId;
    }
}
