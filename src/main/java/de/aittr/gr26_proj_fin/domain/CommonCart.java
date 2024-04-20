package de.aittr.gr26_proj_fin.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.aittr.gr26_proj_fin.domain.interfaces.Cart;
import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "cart")
public class CommonCart implements Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private CommonUser customer;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "cart_book",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<CommonBook> books = new ArrayList<>();
    //========================================================================

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommonCart that = (CommonCart) o;
        return Objects.equals(id, that.id) && Objects.equals(customer, that.customer) && Objects.equals(books, that.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customer, books);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CommonCart{");
        sb.append("id=").append(id);
        sb.append(", customer=").append(customer);
        sb.append(", books=").append(books);
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
    public void setCustomer(CommonUser customer) {
        this.customer = customer;
    }

    public CommonUser getCustomer() {
        return customer;
    }

    @Override
    public List<CommonBook> getBooks() {
        return books;
    }

    @Override
    public void addBook(CommonBook book) {
        books.add(book);
    }

    @Override
    public void deleteBookById(int bookId) {
        //books.removeIf(b -> b.getId() == bookId);
        for (CommonBook book : books) {
            if (book.getId() == bookId) {
                books.remove(book);
                break; // Выход из цикла после удаления первого вхождения
            }
        }
    }

    @Override
    public void clear() {
        books.clear();
    }

    @Override
    public double getTotalPrice() {
        return books.stream()
                .filter(b -> b.isIs_active())
                .mapToDouble(b -> b.getPrice())
                .sum();
    }

    @Override
    public int getTotalCount() {
        long count = books.stream()
                .filter(b -> b.isIs_active())
                .count();
        return Math.toIntExact(count);
    }
}
