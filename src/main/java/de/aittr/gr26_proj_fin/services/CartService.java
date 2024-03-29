package de.aittr.gr26_proj_fin.services;

import de.aittr.gr26_proj_fin.repositories.interfaces.CartRepository;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

}
