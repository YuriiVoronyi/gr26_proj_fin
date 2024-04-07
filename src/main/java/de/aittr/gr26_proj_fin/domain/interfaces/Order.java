package de.aittr.gr26_proj_fin.domain.interfaces;

import de.aittr.gr26_proj_fin.domain.CommonBook;

import java.util.List;

public interface Order {
    int getId();
    void setId(int id);
    void setNumber(int number);
    int getNumber();
    boolean isIs_active();
    void setIs_active(boolean active);

    List<CommonBook> getBooks();
    void addBook(CommonBook book);
    double getTotalPrice();
}
