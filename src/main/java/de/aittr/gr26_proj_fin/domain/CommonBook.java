package de.aittr.gr26_proj_fin.domain;

import de.aittr.gr26_proj_fin.domain.interfaces.Book;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "book")
public class CommonBook implements Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    private String name;

    @Column(name = "price")
    private double price;

    @Column(name = "pathimg")
    private String pathimg;

    @Column(name = "author")
    private String author;

    @Column(name = "is_active")
    private boolean is_active;

    @ManyToMany(mappedBy = "books")
    private List<CommonCart> carts = new ArrayList<>();

    @ManyToMany(mappedBy = "listbooks", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CommonOrder> orders = new ArrayList<>();

    public CommonBook() {
    }

    public CommonBook(String name, double price, String pathimg, String author, boolean isActive) {
        this.name = name;
        this.price = price;
        this.pathimg = pathimg;
        this.author = author;
        this.is_active = isActive;
    }

    public CommonBook(String name, String author) {
        this.name = name;
        this.author = author;
    }

    public CommonBook(String name, String author, boolean isActive) {
        this.name = name;
        this.author = author;
        this.is_active = isActive;
    }

    public CommonBook(String name) {
        this.name = name;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String getPathimg() {
        return pathimg;
    }

    @Override
    public void setPathimg(String path) {
        this.pathimg = path;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public boolean isIs_active() {
        return is_active;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommonBook that = (CommonBook) o;
        return Double.compare(that.price, price) == 0 && is_active == that.is_active && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(pathimg, that.pathimg) && Objects.equals(author, that.author) && Objects.equals(carts, that.carts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, pathimg, author, is_active, carts);
    }

    @Override
    public String toString() {
        return "CommonBook{" +
                "id=" + id +
                ", title='" + name +
                ", price=" + price +
                ", pathimg=" + pathimg +
                ", author=" + author +
                ", is_active=" + is_active +
                '}';
    }
}
