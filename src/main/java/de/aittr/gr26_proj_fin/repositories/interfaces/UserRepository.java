package de.aittr.gr26_proj_fin.repositories.interfaces;

import de.aittr.gr26_proj_fin.domain.CommonUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<CommonUser, Integer>{

    CommonUser findByname(String username);

//    @Transactional
//    @Modifying
//    @Query(value = "UPDATE customer SET username = :username, email = :email WHERE id = :id", nativeQuery = true)
//    void updateUser(@Param("id") Integer id, @Param("username") String username, @Param("email") String email);
//
//    @Transactional
//    @Modifying
//    @Query(value = "DELETE FROM user_role WHERE user_id = :id", nativeQuery = true)
//    void deleteByUserId(@Param("id") Integer id);

}
