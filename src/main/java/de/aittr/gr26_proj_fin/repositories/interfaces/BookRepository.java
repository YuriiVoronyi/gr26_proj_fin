package de.aittr.gr26_proj_fin.repositories.interfaces;

import de.aittr.gr26_proj_fin.domain.CommonBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface BookRepository extends JpaRepository<CommonBook, Integer> {

    @Transactional
    void deleteByName(String name);

    CommonBook findByName(String name);

    @Transactional
    @Modifying
    @Query(value = "UPDATE book SET title = :title, price = :price, year = :year, pathimg = :pathimg, is_active = :is_active, isbn = :isbn, author = :author, genre = :genre WHERE id = :id", nativeQuery = true)
    void updateBook(@Param("id") Integer id, @Param("title") String title, @Param("price") double price, @Param("year") String year, @Param("pathimg") String pathimg, @Param("is_active") Integer is_active, @Param("isbn") String isbn, @Param("author") String author, @Param("genre") String genre);


}
