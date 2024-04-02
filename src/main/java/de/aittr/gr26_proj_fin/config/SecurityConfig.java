package de.aittr.gr26_proj_fin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(x -> x.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        x -> x
                                .requestMatchers(HttpMethod.POST, "api/cart/users/{userId}/cart/items").hasRole("USER")//Добавление книги в корзину
                                .requestMatchers(HttpMethod.DELETE, "api/cart/del/users/{userId}/cart/items").hasRole("USER")//Удаление книги из корзины
                                .requestMatchers(HttpMethod.DELETE, "api/cart/clear/users/{userId}").hasRole("USER")//Очистка корзины
                                .requestMatchers(HttpMethod.GET, "api/cart/books/{userId}").hasRole("USER")//Вызов списка книг из корзины

                                .requestMatchers(HttpMethod.GET, "api/book").hasRole("ADMIN")//Вывод всех книг
                                .requestMatchers(HttpMethod.POST, "api/book/save").hasRole("ADMIN")//Сохранение книги
                                .requestMatchers(HttpMethod.POST, "api/book/update").hasRole("ADMIN")//Обновление данных книги
                                .requestMatchers(HttpMethod.DELETE, "api/book/{name}").hasRole("ADMIN")//Удаление книги по названию
                                .requestMatchers(HttpMethod.GET, "api/book/{name}").hasRole("ADMIN")//Вызов книги по названию
                                .requestMatchers(HttpMethod.GET, "api/book/author/{author}").hasRole("ADMIN")//Вызов список книг по автору
                                .requestMatchers(HttpMethod.GET, "api/book/genre/{genre}").hasRole("ADMIN")//Вызов список книг по жанру
                                .requestMatchers(HttpMethod.GET, "api/book/year/{year}").hasRole("ADMIN")//Вызов список книг по году издания
                                .requestMatchers(HttpMethod.GET, "api/book/isbn/{isbn}").hasRole("ADMIN")//Вызов список книг isbn

                                .requestMatchers(HttpMethod.GET, "api/book/forusername/active").permitAll()//Вывод всех активных книг
                                .requestMatchers(HttpMethod.GET, "api/book/forusername/{name}").permitAll()//Вывод активной книги по названию
                                .requestMatchers(HttpMethod.GET, "api/book/foruserauthor/{author}").permitAll()//Вывод активной книги по автору
                                .requestMatchers(HttpMethod.GET, "api/book/forusergenre/{genre}").permitAll()//Вывод активных книг по жанру
                                .requestMatchers(HttpMethod.GET, "api/book/foruseryear/{year}").permitAll()//Вывод активных книг по году издания
                                .requestMatchers(HttpMethod.GET, "api/book/foruserisbn/{isbn}").permitAll()//Вывод активной книги по isbn

                                .requestMatchers(HttpMethod.GET, "api/user").hasRole("ADMIN")//Вывод всех юзеров
                                .requestMatchers(HttpMethod.GET, "api/user/email/{email}").hasRole("ADMIN")//Вывод юзера по его почте
                                .requestMatchers(HttpMethod.GET, "api/user/{name}").hasRole("ADMIN")//Вывод одного юзера
                                .requestMatchers(HttpMethod.DELETE, "api/user/{name}").hasRole("ADMIN")//Удаление юзера по логину
                                .requestMatchers(HttpMethod.POST, "api/user/update").hasRole("ADMIN")//Обновление данных по юзеру, логин и почта
                                .requestMatchers(HttpMethod.DELETE, "api/user/delete/{id}").hasRole("ADMIN")//Удаление покупателя из базы данных
                                .requestMatchers(HttpMethod.POST, "api/user/reg").permitAll()//Регистрация юзера

                                .requestMatchers("/v3/**","/swagger-ui/**","/swagger-ui.html","/**").permitAll()//разрешаем работу для SWAGGER

                                .anyRequest().authenticated()
                ).httpBasic(Customizer.withDefaults()).build();
        //return http.build();
    }

//    private static final String[] AUTH_WHITELIST = {
//            "/swagger-ui/**",
//            "/v3/api-docs/**",
//            "/swagger-ui.html",
//            "/**"
//    };

//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests().anyRequest().authenticated();
//    }
}
