package de.aittr.gr26_proj_fin.repositories.interfaces;

import de.aittr.gr26_proj_fin.domain.CommonBook;
import de.aittr.gr26_proj_fin.domain.CommonOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

public interface BookRepository extends JpaRepository<CommonBook, Integer> {

    @Transactional
    void deleteByName(String name);

    CommonBook findByName(String name);

    @Transactional
    @Modifying
    @Query(value = "UPDATE book SET title = :title, price = :price, pathimg = :pathimg, is_active = :is_active, author = :author WHERE id = :id", nativeQuery = true)
    void updateBook(@Param("id") Integer id, @Param("title") String title, @Param("price") double price, @Param("pathimg") String pathimg, @Param("is_active") Integer is_active, @Param("author") String author);

}
