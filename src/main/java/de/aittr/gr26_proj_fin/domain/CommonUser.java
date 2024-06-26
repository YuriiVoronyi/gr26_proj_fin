package de.aittr.gr26_proj_fin.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.aittr.gr26_proj_fin.domain.interfaces.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Schema(description = "CommonUser entity")
@Entity
@Table(name = "customer")
public class CommonUser implements User, UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Schema(description = "Username that use for logging in", example = "Bob")
    @Column(name = "username")
    private String name;

    @Schema(description = "User's raw password for logging in", example = "qwerty")
    @Column(name = "password")
    private String password;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @JsonIgnore
    @OneToOne(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private CommonCart cart;

    // Определение связи один ко многим с заказами
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<CommonOrder> orders = new HashSet<>();

    public CommonUser() {
    }

    public CommonUser(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public void addToCart(CommonBook book) {
        if (cart == null) {
            cart = new CommonCart();
            cart.setCustomer(this);
        }

        // Проверка наличия книги в корзине
        boolean bookFound = false;
        for (CommonBook item : cart.getBooks()) {
            if (item.getId() == book.getId()) {
                // Если книга уже есть в корзине, увеличиваем количество
                //cart.setQuantity(cart.getQuantity() + 1);
                bookFound = true;
                break;
            }
        }

        cart.addBook(book);
    }

    public void delFromCart(CommonBook book) {
        int id = book.getId();
        if (cart == null) {
            cart = new CommonCart();
            cart.setCustomer(this);
        }
        for (CommonBook item : cart.getBooks()) {
            if (item.getId() == id) {
                cart.deleteBookById(id);
                break;
            }
        }
    }
    public void addOrder(CommonOrder order) {
        orders.add(order);
    }

    public Set<CommonOrder> getOrders() {
        return orders;
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    public void clearRoles() {
        roles.clear();
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public CommonCart getCart() {
        return cart;
    }

    public void setCart(CommonCart cart) {
        this.cart = cart;
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
    public String getUsername() {
        return name;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {

        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getAuthority()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommonUser that = (CommonUser) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(password, that.password) && Objects.equals(roles, that.roles) && Objects.equals(cart, that.cart) && Objects.equals(orders, that.orders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, password, roles, cart, orders);
    }

    @Override
    public String toString() {
        return "CommonUser{" +
                "id=" + id +
                ", name=" + name +
                ", roles=" + roles +
                ", orders=" + orders +
                '}';
    }
}
