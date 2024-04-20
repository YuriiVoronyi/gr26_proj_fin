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

                                .requestMatchers(HttpMethod.POST, "/api/carts/{userName}").hasRole("USER")//Добавление книги в корзину
                                //.requestMatchers(HttpMethod.DELETE, "/api/carts/del/users/{userId}/cart/items").hasRole("USER")//Удаление книги из корзины
                                .requestMatchers(HttpMethod.DELETE, "/api/carts/{userName}").hasRole("USER")//Удаление книги из корзины
                                .requestMatchers(HttpMethod.DELETE, "/api/carts/clear/users/{userId}").hasRole("USER")//Очистка корзины
                                .requestMatchers(HttpMethod.GET, "/api/carts/books/{userName}").hasRole("USER")//Вызов списка книг из корзины
                                .requestMatchers(HttpMethod.GET, "/api/carts/{userName}").hasRole("USER")//Вызов количества и общей стоимости книг корзины

                                .requestMatchers(HttpMethod.GET, "/api/books").hasRole("ADMIN")//Вывод всех книг
                                .requestMatchers(HttpMethod.POST, "/api/books/save").hasRole("ADMIN")//Сохранение книги
                                .requestMatchers(HttpMethod.POST, "/api/books/update").hasRole("ADMIN")//Обновление данных книги

                                .requestMatchers(HttpMethod.DELETE, "/api/books/{name}").hasRole("ADMIN")//Удаление книги по названию

                                .requestMatchers(HttpMethod.GET, "/api/books/active").permitAll()//Вывод всех активных книг
                                .requestMatchers(HttpMethod.GET, "/api/books/{name}").permitAll()//Вывод активной книги по названию

                                .requestMatchers(HttpMethod.POST, "/api/users/reg/items").permitAll()//Регистрация юзера

                                .requestMatchers(HttpMethod.POST, "/api/img/load/{id}").permitAll()//Загрузка картинки на бэк и в таблицу
                                .requestMatchers(HttpMethod.GET, "/api/img/{imageName}").permitAll()//Выгрузка картинки с бэка
                                .requestMatchers(HttpMethod.POST, "/api/img/{id}/items").hasRole("ADMIN")//Замена пути у картинки

                                .requestMatchers(HttpMethod.POST, "/api/orders/add/{userId}").hasRole("USER")//Добавление заказа юзером
                                .requestMatchers(HttpMethod.GET, "/api/orders/getall").hasRole("ADMIN")//Вызов всех заказов из базы
                                .requestMatchers(HttpMethod.GET, "/api/orders/getbynum/{number}").hasRole("ADMIN")//Вызов заказа по номеру
                                .requestMatchers(HttpMethod.POST, "/api/orders/payorder/{number}").hasRole("ADMIN")//Оплата заказа
                                .requestMatchers(HttpMethod.DELETE, "/api/orders/delorder/{number}").hasRole("ADMIN")//Удаление заказа

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
