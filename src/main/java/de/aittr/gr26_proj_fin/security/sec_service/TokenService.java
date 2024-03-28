//package de.aittr.gr26_proj_fin.security.sec_service;
//
//import de.aittr.gr26_proj_fin.domain.CommonUser;
//import de.aittr.gr26_proj_fin.repositories.interfaces.RoleRepository;
//import jakarta.annotation.Nonnull;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import javax.crypto.SecretKey;
//import java.time.Instant;
//import java.time.LocalDateTime;
//import java.time.ZoneId;
//import java.util.Base64;
//import java.util.Date;
//
//@Service
//public class TokenService {
//
//    private SecretKey accessKey;
//    private SecretKey refreshKey;
//    private RoleRepository roleRepository;
//
//    public TokenService(
//            @Value("${access.key}") String accessKey,
//            @Value("${refresh.key}") String refreshKey,
//            RoleRepository roleRepository
//    ) {
//        this.accessKey = Keys.hmacShaKeyFor(Decoder.BASE64.decode(accessKey));
//        this.refreshKey = Keys.hmacShaKeyFor(Decoder.BASE64.decode(refreshKey));
//        this.roleRepository = roleRepository;
//    }
//
//    public String generateAccessToken(@Nonnull CommonUser user) {
//        LocalDateTime currentDate = LocalDateTime.now();
//        Instant expirationInstant = currentDate.plusDays(1).atZone(ZoneId.systemDefault()).toInstant();
//        Date expirationDate = Date.from(expirationInstant);
//
//        return Jwts.builder()
//                .subject(user.getUsername())//логин юзера
//                .expiration(expirationDate)
//                .signWith(accessKey)
//                .claim("roles",user.getAuthorities())
//                .claim("name", user.getUsername())//настоящее имя юзера
//                .compact();
//    }
//
//    public String generateRefreshToken(@Nonnull CommonUser user) {
//        LocalDateTime currentDate = LocalDateTime.now();
//        Instant expirationInstant = currentDate.plusDays(7).atZone(ZoneId.systemDefault()).toInstant();
//        Date expirationDate = Date.from(expirationInstant);
//
//        return Jwts.builder()
//                .subject(user.getUsername())//логин юзера
//                .expiration(expirationDate)
//                .signWith(refreshKey)
//                .compact();
//    }
//
//    public boolean validateAccessToken(@Nonnull String accessToken) {
//        return validateToken(accessToken, accessKey);
//    }
//
//    public boolean validateRefreshToken(@Nonnull String refreshToken) {
//        return validateToken(refreshToken, refreshKey);
//    }
//
//    public boolean validateToken(@Nonnull String token, @Nonnull SecretKey key) {
//        try {
//            Jwts.parser()
//                    .verifyWith(key)
//                    .build()
//                    .parseSignedClaims(token);
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//
//    }
//}
