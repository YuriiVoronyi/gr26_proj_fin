package de.aittr.gr26_proj_fin.repositories.interfaces;

import de.aittr.gr26_proj_fin.domain.CommonBook;
import de.aittr.gr26_proj_fin.domain.CommonCart;
import de.aittr.gr26_proj_fin.domain.CommonOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface CartRepository extends JpaRepository<CommonCart, Integer> {

}
