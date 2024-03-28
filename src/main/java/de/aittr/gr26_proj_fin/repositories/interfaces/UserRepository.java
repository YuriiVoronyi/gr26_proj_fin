package de.aittr.gr26_proj_fin.repositories.interfaces;

import de.aittr.gr26_proj_fin.domain.CommonUser;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<CommonUser, Integer>{

    CommonUser findByname(String username);

    @Transactional
    @Modifying
    @Query(value = "UPDATE customer SET username = :username, email = :email WHERE id = :id", nativeQuery = true)
    void updateUser(@Param("id") Integer id, @Param("username") String username, @Param("email") String email);
}
