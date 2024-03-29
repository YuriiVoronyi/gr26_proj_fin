package de.aittr.gr26_proj_fin.domain.interfaces;

import de.aittr.gr26_proj_fin.domain.CommonBook;
import de.aittr.gr26_proj_fin.domain.CommonUser;

import java.util.List;

public interface Cart {
    int getId();
    void setId(int id);
    void setCustomer(CommonUser customer);
    List<CommonBook> getBooks();          //Возвращает список книг
    void addBook(CommonBook book);       //Добавляем книгу в корзину
    void deleteBookById(int bookId);    //Удаляем книгу по ее id из корзины
    void clear();                      //Очищаем корзину
    double getTotalPrice();         //Возвращаем полную стоимость книг
}
