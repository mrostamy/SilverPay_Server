package com.mydomomain.silverpay.helper;

import com.mydomomain.silverpay.model.Token;
import com.mydomomain.silverpay.model.User;
import com.mydomomain.silverpay.repository.mainRepository.ITokenRepository;
import com.mydomomain.silverpay.repository.mainRepository.IUserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

//www.codeJava.net

@Component
public class JwtUtil {

    final IUserRepository userRepository;

    final ITokenRepository tokenRepository;


    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.expire_time}")
    private String expireTime;

    @Value("${token.clint_id}")
    private String client_id;

    public String generatedToken = "";

    public JwtUtil(IUserRepository userRepository, ITokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }

    public String createAccessToken(User user, String refreshToken, boolean remember) {

        Date expire = remember ? Date.from(Instant.now().plus(2, ChronoUnit.DAYS)) :
                Date.from(Instant.now().plus(2, ChronoUnit.HOURS));
        generatedToken = Jwts.builder()
                .setSubject(String.format("%s,%s", user.getId(), user.getUsername()))
                .setIssuer("mohammad")
                .claim("roles", user.getRoles().toString())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(expire)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();

        return generatedToken;
    }


    public boolean validateToken(String token) {

        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            System.out.println("JWT error: " + e.getMessage());
            return false;
        }

    }

    public String getSubject(String token) {
        return parseClaims(token).getSubject();
    }

    public Claims parseClaims(String token) {

        return Jwts.parser().
                setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

    }

//    public GenerateTokenDto generateNewToken(TokenRequestDto requestDto) {
//
//        User user = userRepository.findByUsername(requestDto.getUsername()).orElse(null);
//
//        if (user != null && PasswordHash.verifyPassword(user.getPassword(), requestDto.getPassword())) {
//
//            Token newRefreshToken = createRefreshToken(requestDto.getClient_id(), user.getId());
//
//            List<Token> oldRTokens = tokenRepository.findAll().stream()
//                    .filter(p -> Objects.equals(p.getId(), user.getId())).collect(Collectors.toList());
//
//            tokenRepository.deleteAll(oldRTokens);
//
//            tokenRepository.save(newRefreshToken);
//
//            var accessToken= CreateAccessToken(user,newRefreshToken.getValue(), requestDto.isRemember());
//
//            GenerateTokenDto generateTokenDto=new GenerateTokenDto();
//            generateTokenDto.setAccessToken(accessToken);
//            generateTokenDto.setStatus(false);
//            return generateTokenDto;
//
//        }else{
//            GenerateTokenDto generateTokenDto=new GenerateTokenDto();
//            generateTokenDto.setStatus(false);
//            return generateTokenDto;
//        }
//    }

    public Token createRefreshToken(String client_id, String user_id) {

        Token token = new Token();
        token.setClient_id(client_id);
        token.setUser_id(user_id);
        token.setValue(UUID.randomUUID().toString());
        token.setExpireTime(Date.from(Instant.now().plus(7, ChronoUnit.DAYS)));

        return token;

    }


}
