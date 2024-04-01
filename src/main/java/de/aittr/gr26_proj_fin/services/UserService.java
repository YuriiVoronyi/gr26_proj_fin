package de.aittr.gr26_proj_fin.services;

import de.aittr.gr26_proj_fin.domain.CommonBook;
import de.aittr.gr26_proj_fin.domain.CommonCart;
import de.aittr.gr26_proj_fin.domain.CommonUser;
import de.aittr.gr26_proj_fin.domain.Role;
import de.aittr.gr26_proj_fin.exception_handlink.UserAlreadyExistsException;
import de.aittr.gr26_proj_fin.repositories.interfaces.BookRepository;
import de.aittr.gr26_proj_fin.repositories.interfaces.CartRepository;
import de.aittr.gr26_proj_fin.repositories.interfaces.RoleRepository;
import de.aittr.gr26_proj_fin.repositories.interfaces.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private BookRepository bookRepository;

    private BCryptPasswordEncoder encoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CommonUser user = userRepository.findByname(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found!");
        }
        return user;
    }

    public CommonUser register(String name, String psw, String email) {
        CommonUser user = userRepository.findByname(name);
        if (user != null) {
            throw new UserAlreadyExistsException("A user with the same name already exists!");
        }
        CommonUser testUser = new CommonUser();
        testUser.setName(name);
        testUser.setEmail(email);

        testUser.setId(0);
        testUser.clearRoles();
        Role role = new Role(1, "ROLE_USER");
        testUser.addRole(role);

        String encodePassword = encoder.encode(psw);
        testUser.setPassword(encodePassword);

        CommonUser newUser = userRepository.save(testUser);

        CommonCart newCart = new CommonCart();
        newCart.setCustomer(newUser);
        cartRepository.save(newCart);

        return newUser;
    }

    public List<CommonUser> getAll() {
        return userRepository.findAll();
    }

    public List<CommonUser> getUsersByEmail(String email) {
        return userRepository.findAll()
                .stream()
                .filter(x -> x.getEmail().equals(email))
                .toList();
    }

public void updateOfUser(Integer id, String name, String email) {
    userRepository.updateUser(id, name, email);
}

    @Transactional
    public void addBookToCart(Integer userId, Integer bookId) {
        //Получаем юзера из базы данных
        CommonUser user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found"));

        CommonBook bookToAdd = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book with id " + bookId + " not found"));
        // Добавляем книгу в корзину пользователя
        user.addToCart(bookToAdd);
        userRepository.save(user);

    }

    @Transactional
    public List<CommonBook> deleteBookFromCart(Integer userId, Integer bookId) {
        //Получаем юзера из базы данных
        CommonUser user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found"));

        CommonBook bookForDel = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book with id " + bookId + " not found"));
        // Удаляем книгу из корзины пользователя
        user.delFromCart(bookForDel);
        userRepository.save(user);
        return user.getCart().getBooks();

    }

    @Transactional
    public List<CommonBook> clearCart(Integer userId) {
        //Получаем юзера из базы данных
        CommonUser user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found"));
        // Чистим корзину пользователя
        user.getCart().clear();
        userRepository.save(user);
        return user.getCart().getBooks();

    }

    @Transactional
    public void deleteUserAndRelatedEntities(Integer userId) {
        // Получение пользователя по идентификатору
        CommonUser user = userRepository.findById(userId).orElse(null);

        if (user != null) {
            // Удаление связанных ролей
            userRepository.deleteByUserId(userId);
            // Удаление корзины пользователя
            CommonCart cart = user.getCart();
            if (cart != null) {
                // Удаление книг из корзины
                cart.getBooks().clear();
                cartRepository.save(cart);

                // Удаление самой корзины
                cartRepository.delete(cart);
            }

            // Удаление пользователя
            userRepository.delete(user);
        }
    }
}
