package de.aittr.gr26_proj_fin.services;

import de.aittr.gr26_proj_fin.domain.CommonBook;
import de.aittr.gr26_proj_fin.domain.CommonUser;
import de.aittr.gr26_proj_fin.repositories.interfaces.CartRepository;
import de.aittr.gr26_proj_fin.repositories.interfaces.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;


    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public List<CommonBook> getBooksFromCart(Integer id) {
        CommonUser user = userRepository.findById(id).orElse(null);
        if (user != null) {
            return user.getCart().getBooks();
        }
        return null;
    }

}
