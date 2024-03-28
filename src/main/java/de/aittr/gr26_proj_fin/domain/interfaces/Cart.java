package de.aittr.gr26_proj_fin.domain.interfaces;

public interface Cart {
    int getId();
    void setId(int id);
    void setCustomerId(int customerId);
    //List<Book> getBooks();          //Возвращает список книг
    //void addBook(Book book);        //Добавляем книгу в корзину
    //void deleteBookById(int bookId);//Удаляем книгу по ее id из корзины
    //void clear();                   //Очищаем корзину
    //double getTotalPrice();         //Возвращаем полную стоимость книг
}
