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

    List<CommonBook> getBooks();          //Возвращает список книг
    void addBook(CommonBook book);       //Добавляем книгу в корзину
    double getTotalPrice();         //Возвращаем полную стоимость книг

//    void setCustomer(CommonUser customer);
//    List<CommonBook> getBooks();          //Возвращает список книг
//    void addBook(CommonBook book);       //Добавляем книгу в корзину
//    double getTotalPrice();         //Возвращаем полную стоимость книг
}
