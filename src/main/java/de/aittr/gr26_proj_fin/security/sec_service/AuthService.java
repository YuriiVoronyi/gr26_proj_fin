package de.aittr.gr26_proj_fin.security.sec_service;

import de.aittr.gr26_proj_fin.domain.CommonUser;
import de.aittr.gr26_proj_fin.security.sec_dto.AuthInfo;
import de.aittr.gr26_proj_fin.security.sec_dto.TokenResponseDto;
import de.aittr.gr26_proj_fin.services.UserService;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Nonnull;
import jakarta.security.auth.message.AuthException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private UserService userService;
    private TokenService tokenService;
    private Map<String, String> refreshStorage;//Хранилище рефрештокенов
    private BCryptPasswordEncoder encoder;

    public AuthService(UserService userService, TokenService tokenService, BCryptPasswordEncoder encoder) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.encoder = encoder;
        this.refreshStorage = new HashMap<>();
    }

    public TokenResponseDto login(@Nonnull CommonUser inboundUser) throws AuthException {//inboundUser-входящий юзер с сырым паролем
        String username = inboundUser.getUsername();
        CommonUser foundUser = (CommonUser) userService.loadUserByUsername(username);//foundUser-найденный юзер с зашифрованным паролем
        //Сопоставим пароли этих двух юзеров
        if (encoder.matches(inboundUser.getPassword(), foundUser.getPassword())) {//сопоставляем пароли того, что ввел юзер и из БД
            String accessToken = tokenService.generateAccessToken(foundUser);//генерируем accessToken
            String refreshToken = tokenService.generateRefreshToken(foundUser);//генерируем refreshToken
            refreshStorage.put(username, refreshToken);
            return new TokenResponseDto(accessToken, refreshToken);
        } else {
            throw new AuthException("Password is incorrect");
        }
    }

    //фронт будет получать аксес токен по рефреш токену когда истек срок действия старого аксес токена
    //В TokenResponseDto будет находиться только аксес токен, а вместо рефреш токена будет null
    public TokenResponseDto getAccessToken(@Nonnull String refreshToken) {
        if (tokenService.validateRefreshToken(refreshToken)) {//проверяем (валидация) рефреш токена
            Claims refreshClaims = tokenService.getRefreshClaims(refreshToken);//из refreshToken извлекаем инфу о юзере в виде объекта Claims
            String username = refreshClaims.getSubject();//из объекта Claims извлекаем username
            //теперь надо удостовериться в том, что refreshToken предьявляется действительно его владельцем
            String savedRefreshToken = refreshStorage.get(username);//извлекаем из хранилища сохраненный refreshToken по имени юзера

            if (savedRefreshToken != null && savedRefreshToken.equals(refreshToken)) {//проверка refreshToken-а
                CommonUser user = (CommonUser) userService.loadUserByUsername(username);//извлекаем, после проверки, из БД юзера
                String accessToken = tokenService.generateAccessToken(user);//для юзера генерируем новый accessToken с новым сроком действия
                return new TokenResponseDto(accessToken, null);//возвращаем новый accessToken без refreshToken-а
            }
        }
        return new TokenResponseDto(null, null);
    }

    //как обратиться из любого места проекта к секьюрити-контексту, и получить инфу о юзере?
    //SecurityContextHolder - здесь содержится инфа об авторизованных юзерах.
    //наш класс AuthInfo имплементирует (реализует) Authentication
    public AuthInfo getAuthInfo() {
            return (AuthInfo) SecurityContextHolder.getContext().getAuthentication();
    }
}
