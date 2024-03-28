package de.aittr.gr26_proj_fin.domain;

import de.aittr.gr26_proj_fin.domain.interfaces.Book;
import jakarta.persistence.*;

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

    @Column(name = "year")
    private String year;

    @Column(name = "pathimg")
    private String pathimg;

    @Column(name = "author")
    private String author;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "genre")
    private String genre;

    @Column(name = "is_active")
    private boolean is_active;

    public CommonBook() {
    }

    public CommonBook(int id, String name, double price, String year, String pathimg, String author, String isbn, String genre, boolean isActive) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.year = year;
        this.pathimg = pathimg;
        this.author = author;
        this.isbn = isbn;
        this.genre = genre;
        this.is_active = isActive;
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
    public double getPrice() {
        return price;
    }

    @Override
    public String getYear() {
        return year;
    }

    @Override
    public String getPathimg() {
        return pathimg;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public String getIsbn() {
        return isbn;
    }

    @Override
    public String getGenre() {
        return genre;
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
        return id == that.id && Double.compare(that.price, price) == 0 && is_active == that.is_active && Objects.equals(name, that.name) && Objects.equals(year, that.year) && Objects.equals(pathimg, that.pathimg) && Objects.equals(author, that.author) && Objects.equals(isbn, that.isbn) && Objects.equals(genre, that.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, year, pathimg, author, isbn, genre, is_active);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CommonBook{");
        sb.append("id=").append(id);
        sb.append(", title='").append(name).append('\'');
        sb.append(", price=").append(price);
        sb.append(", year='").append(year).append('\'');
        sb.append(", pathimg='").append(pathimg).append('\'');
        sb.append(", author='").append(author).append('\'');
        sb.append(", isbn='").append(isbn).append('\'');
        sb.append(", genre='").append(genre).append('\'');
        sb.append(", is_active=").append(is_active);
        sb.append('}');
        return sb.toString();
    }
}
