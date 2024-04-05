package de.aittr.gr26_proj_fin.security.sec_service;

import de.aittr.gr26_proj_fin.domain.CommonUser;
import de.aittr.gr26_proj_fin.domain.Role;
import de.aittr.gr26_proj_fin.repositories.interfaces.RoleRepository;
import de.aittr.gr26_proj_fin.security.sec_dto.AuthInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class TokenService {
//
    private SecretKey accessKey;//используется для генерации и подписывания токенов доступа, и валидации их
    private SecretKey refreshKey;//используется для генерации и валидации рефреш токенов
    private RoleRepository roleRepository;

    public TokenService(
            @Value("${access.key}") String accessKey,
            @Value("${refresh.key}") String refreshKey,
            RoleRepository roleRepository
    ) {
        //с заранее подготовленной строки в application propeties преобразуем в объект SecretKey,
        //который мы можем использовать для JSON WEB токенов
        this.accessKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessKey));
        this.refreshKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshKey));
        this.roleRepository = roleRepository;
    }
//
    public String generateAccessToken(@Nonnull CommonUser user) {//Метод для генерации AccessToken
        LocalDateTime currentDate = LocalDateTime.now();//Текущий момент времени
        Instant expirationInstant = currentDate.plusDays(1).atZone(ZoneId.systemDefault()).toInstant();//временной отрезок в одни сутки, с учетом часового пояса
        Date expirationDate = Date.from(expirationInstant);//Время истечения действия аксесс токена

        return Jwts.builder()//Каласс токена JSON
                .subject(user.getUsername())//логин юзера, закладываем в токен инфу про владельца токена
                .expiration(expirationDate)//закладываем инфу про мемент времени истечения токена
                .signWith(accessKey)//подписываем токен аксесс ключом
                .claim("roles",user.getAuthorities())//закладываем список ролей пользователя
                .claim("name", user.getUsername())//закладываем в токен настоящее имя юзера
                .compact();//завершаем и аккумулируем инфу токена в строку
    }

    public String generateRefreshToken(@Nonnull CommonUser user) {//Метод для генерации RefreshToken
        LocalDateTime currentDate = LocalDateTime.now();//Текущий момент времени
        Instant expirationInstant = currentDate.plusDays(7).atZone(ZoneId.systemDefault()).toInstant();//временной отрезок в семь суток, с учетом часового пояса
        Date expirationDate = Date.from(expirationInstant);//Время истечения действия аксесс токена

        return Jwts.builder()
                .subject(user.getUsername())//логин юзера, закладываем в токен инфу про владельца токена
                .expiration(expirationDate)//закладываем инфу про мемент времени истечения токена
                .signWith(refreshKey)//подписываем токен рефреш ключом
                .compact();//завершаем и аккумулируем инфу токена в строку
    }

    public boolean validateAccessToken(@Nonnull String accessToken) {//метод валидации токена AccessToken
        return validateToken(accessToken, accessKey);
    }

    public boolean validateRefreshToken(@Nonnull String refreshToken) {//метод валидации токена RefreshToken
        return validateToken(refreshToken, refreshKey);
    }

    public boolean validateToken(@Nonnull String token, @Nonnull SecretKey key) {
        try {
            Jwts.parser()
                    .verifyWith(key)//валидация токена при помощи ключа
                    .build()
                    .parseSignedClaims(token);//здесь проверка: на повреждение, неверная структура, токен протух
            return true;//если токен полностью валидный, то возвращаем true
        } catch (Exception e) {
            return false;//если есть какая либо проблема с токеном, то возвращаем false
        }
    }

    public Claims getAccessClaims(@Nonnull String accessToken) {//Получение данных о пользователе из токен accessToken
        return getClaims(accessToken, accessKey);
    }

    public Claims getRefreshClaims(@Nonnull String refreshToken) {//Получение данных о пользователе из токен refreshToken
        return getClaims(refreshToken, refreshKey);
    }

    private Claims getClaims(@Nonnull String token, @Nonnull SecretKey key) {//из токена используя ключ извекли данные о юзере в виде объекта Claims
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();//метод возвращает из токена содержимое: Claims
    }

    public AuthInfo generateAuthInfo(Claims claims) {//Преобразуем объект Claims в AuthInfo
        String username = claims.getSubject();
        List<LinkedHashMap<String, String>> rolesList = (List<LinkedHashMap<String, String>>) claims.get("roles");
        Set<Role> roles = new HashSet<>();

        for (LinkedHashMap<String, String> roleEntry : rolesList) {
            String roleName = roleEntry.get("authority");
            Role role = roleRepository.findByName(roleName);
            roles.add(role);
        }

        return new AuthInfo(username, roles);
    }
}
