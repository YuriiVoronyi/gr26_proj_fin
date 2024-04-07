package de.aittr.gr26_proj_fin.repositories.interfaces;

import de.aittr.gr26_proj_fin.domain.CommonUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<CommonUser, Integer>{

    CommonUser findByname(String username);

}
