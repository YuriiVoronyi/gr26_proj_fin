package de.aittr.gr26_proj_fin;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Encodpsw {
    public static void main(String[] args) {

        // Получение зашифрованного пароля
        String password = "qwe";
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodePassword = encoder.encode(password);
        System.out.println(encodePassword);
    }
}
