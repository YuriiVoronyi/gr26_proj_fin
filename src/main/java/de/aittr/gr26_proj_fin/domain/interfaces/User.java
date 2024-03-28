package de.aittr.gr26_proj_fin.domain.interfaces;

import de.aittr.gr26_proj_fin.domain.CommonCart;

public interface User {

    int getId();
    void setId(int id);

    String getUsername();
    void setName(String name);

    String getPassword();
    void setPassword(String password);

    String getEmail();
    void setEmail();

//    Cart getCart();
//    void setCart(CommonCart cart);
}
