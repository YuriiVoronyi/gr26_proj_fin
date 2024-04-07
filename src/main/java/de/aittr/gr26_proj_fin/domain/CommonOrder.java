package de.aittr.gr26_proj_fin.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.aittr.gr26_proj_fin.domain.interfaces.Order;
import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "orderofuser")
public class CommonOrder implements Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "number")
    private Integer number;

    @Column(name = "is_active")
    private boolean is_active;

    // Определение связи многие к одному с пользователем
    @ManyToOne
    @JoinColumn(name = "user_id")
    private CommonUser user;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "order_book",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<CommonBook> listbooks = new ArrayList<>();

    public CommonOrder() {
    }

    public CommonOrder(Integer id, int number, boolean is_active) {
        this.id = id;
        this.number = number;
        this.is_active = is_active;
    }

    @Override
    public List<CommonBook> getBooks() {
        return listbooks;
    }

    @Override
    public void addBook(CommonBook book) {
        listbooks.add(book);
    }

    @Override
    public double getTotalPrice() {
        return listbooks.stream()
                .filter(b -> b.isIs_active())
                .mapToDouble(b -> b.getPrice())
                .sum();
    }

    public void setUser(CommonUser par_user) {
        this.user = par_user;
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
    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public boolean isIs_active() {
        return is_active;
    }

    @Override
    public void setIs_active(boolean active) {
        this.is_active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommonOrder order = (CommonOrder) o;
        return is_active == order.is_active && Objects.equals(id, order.id) && Objects.equals(number, order.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, is_active);
    }

    @Override
    public String toString() {
        return "CommonOrder{" +
                "id=" + id +
                ", number='" + number +
                ", is_active=" + is_active +
                '}';
    }
}
