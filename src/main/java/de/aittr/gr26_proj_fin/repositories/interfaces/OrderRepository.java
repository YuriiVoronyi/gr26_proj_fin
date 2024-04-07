package de.aittr.gr26_proj_fin.repositories.interfaces;

import de.aittr.gr26_proj_fin.domain.CommonOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface OrderRepository extends JpaRepository<CommonOrder, Integer> {

//    @Query(value = "SELECT id FROM orderofuser ORDER BY id DESC LIMIT 1", nativeQuery = true)
//    int findMaxId();

    Set<CommonOrder> findAllByUser_Id(Integer user_id);

    CommonOrder findByNumber(Integer number);
}
