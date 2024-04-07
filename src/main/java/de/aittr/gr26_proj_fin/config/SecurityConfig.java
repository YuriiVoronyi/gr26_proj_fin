package de.aittr.gr26_proj_fin.config;

import de.aittr.gr26_proj_fin.security.sec_filter.TokenFilter;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private TokenFilter filter;

    public SecurityConfig(TokenFilter filter) {
        this.filter = filter;
    }

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
                                .requestMatchers(HttpMethod.GET, "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/auth/login", "/api/auth/access").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/auth/logout").permitAll()

                                .requestMatchers(HttpMethod.POST, "/api/cart/users/{userId}/cart/items").hasRole("USER")//Добавление книги в корзину
                                .requestMatchers(HttpMethod.DELETE, "/api/cart/del/users/{userId}/cart/items").hasRole("USER")//Удаление книги из корзины
                                .requestMatchers(HttpMethod.DELETE, "/api/cart/clear/users/{userId}").hasRole("USER")//Очистка корзины
                                .requestMatchers(HttpMethod.GET, "/api/cart/books/{userId}").hasRole("USER")//Вызов списка книг из корзины

                                .requestMatchers(HttpMethod.GET, "/api/book").hasRole("ADMIN")//Вывод всех книг
                                .requestMatchers(HttpMethod.POST, "/api/book/save").hasRole("ADMIN")//Сохранение книги
                                .requestMatchers(HttpMethod.POST, "/api/book/update").hasRole("ADMIN")//Обновление данных книги

                                .requestMatchers(HttpMethod.DELETE, "/api/book/{name}").hasRole("ADMIN")//Удаление книги по названию

//                                .requestMatchers(HttpMethod.GET, "/api/book/{name}").hasRole("ADMIN")//Вызов книги по названию
//                                .requestMatchers(HttpMethod.GET, "/api/book/author/{author}").hasRole("ADMIN")//Вызов список книг по автору
//                                .requestMatchers(HttpMethod.GET, "/api/book/genre/{genre}").hasRole("ADMIN")//Вызов список книг по жанру
//                                .requestMatchers(HttpMethod.GET, "/api/book/year/{year}").hasRole("ADMIN")//Вызов список книг по году издания
//                                .requestMatchers(HttpMethod.GET, "/api/book/isbn/{isbn}").hasRole("ADMIN")//Вызов список книг isbn

                                .requestMatchers(HttpMethod.GET, "/api/book/forusername/active").permitAll()//Вывод всех активных книг
                                .requestMatchers(HttpMethod.GET, "/api/book/forusername/{name}").permitAll()//Вывод активной книги по названию
//                                .requestMatchers(HttpMethod.GET, "/api/book/foruserauthor/{author}").permitAll()//Вывод активной книги по автору
//                                .requestMatchers(HttpMethod.GET, "/api/book/forusergenre/{genre}").permitAll()//Вывод активных книг по жанру
//                                .requestMatchers(HttpMethod.GET, "/api/book/foruseryear/{year}").permitAll()//Вывод активных книг по году издания
//                                .requestMatchers(HttpMethod.GET, "/api/book/foruserisbn/{isbn}").permitAll()//Вывод активной книги по isbn

//                                .requestMatchers(HttpMethod.GET, "/api/user").hasRole("ADMIN")//Вывод всех юзеров
//                                .requestMatchers(HttpMethod.GET, "/api/user/email/{email}").hasRole("ADMIN")//Вывод юзера по его почте
//                                .requestMatchers(HttpMethod.GET, "/api/user/{name}").hasRole("ADMIN")//Вывод одного юзера
//                                .requestMatchers(HttpMethod.DELETE, "/api/user/{name}").hasRole("ADMIN")//Удаление юзера по логину
//                                .requestMatchers(HttpMethod.POST, "/api/user/update").hasRole("ADMIN")//Обновление данных по юзеру, логин и почта
//                                .requestMatchers(HttpMethod.DELETE, "/api/user/delete/{id}").hasRole("ADMIN")//Удаление покупателя из базы данных

                                .requestMatchers(HttpMethod.POST, "/api/user/reg/items").permitAll()//Регистрация юзера

                                .requestMatchers(HttpMethod.POST, "/api/order/add/{userId}").hasRole("USER")//Добавление заказа юзером
                                .requestMatchers(HttpMethod.GET, "/api/order/getall").hasRole("ADMIN")//Вызов всех заказов из базы
                                .requestMatchers(HttpMethod.GET, "/api/order/getbynum/{number}").hasRole("ADMIN")//Вызов заказа по номеру
                                .requestMatchers(HttpMethod.POST, "/api/order/payorder/{number}").hasRole("ADMIN")//Оплата заказа
                                .requestMatchers(HttpMethod.DELETE, "/api/order/delorder/{number}").hasRole("ADMIN")//Удаление заказа

                                .anyRequest().authenticated())
                                .addFilterAfter(filter, UsernamePasswordAuthenticationFilter.class)
                                .build();
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().addSecurityItem(new SecurityRequirement().
                        addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes
                        ("Bearer Authentication", createAPIKeyScheme()))
                .info(new Info().title("Online Book Store app")
                        .description("APIs to shop books online")
                        .version("1.0.0").contact(new Contact().name("Yurii Voronyi")
                                .email(" ").url(" "))
                        .license(new License().name("@YuriiVoronyi")
                                .url(" ")));
    }

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }
}
