package de.aittr.gr26_proj_fin.security.sec_dto;

import de.aittr.gr26_proj_fin.domain.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

//Класс, объекты которого будут хранить инфу об авторизованном пользоателе. СекьюритиКонтекстХолдер - ключевая сущность фреймворка
//СпрингСекьюрити. Фреймворк сам работает с этих холдерем, мы так же как и он можем получать инфу об авторизованных пользователях.
//Объекты этого класса мы сможем помещать в секьюритиконтекст, и наоборот получать оттуда если нам это понадобится.

public class AuthInfo implements Authentication {//implements Authentication для того, чтобы спрингсек. мог работать с этим классом

    private boolean authenticated;//Если юзер авторизован, то здесь будет true, иначе - false
    private String username;
    private Set<Role> roles;

    public AuthInfo(String username, Set<Role> roles) {
        this.username = username;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public Object getCredentials() {
        return null;
    }//Этот и следующий нам не нужны

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return username;
    }//

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }//Мы сможем определить аутентифицирован ли юзер

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return username;
    }//Возвращает реальное имя, но у нас его нет-возвращаем логин

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthInfo authInfo = (AuthInfo) o;
        return authenticated == authInfo.authenticated && Objects.equals(username, authInfo.username) && Objects.equals(roles, authInfo.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authenticated, username, roles);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AuthInfo{");
        sb.append("authenticated=").append(authenticated);
        sb.append(", username='").append(username).append('\'');
        sb.append(", roles=").append(roles);
        sb.append('}');
        return sb.toString();
    }
}
