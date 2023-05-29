package member.week1.configuration;

import com.jwt.study.domain.Member;
import com.jwt.study.service.MemberDetailService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;

/**
 * created by Edgar Kim
 * # TokenProvider 설명
 * init() 시크릿값을 base64로 인코딩
 * createToken()  유저 아이디(pk)와 권한(role)을 넣어서 토큰 생성
 * getAuthentication() 유저가 있는지 체크 및 권한 확인해서 Authentication을 반환해줌 -> 이게있어야지 나중에 권한별로 체크해주기 편해짐
 * getUserIdFromToken() 유저 아이디(pk) 반환
 * resolveToken() 헤더에서 토큰을 가지고 옵니다.
 * validateToken() 토큰 만료시간을 페이로드에서 빼서 체크해줌
 * */
@Component @Slf4j
@RequiredArgsConstructor
public class JwtTokenProvider{
    private final MemberDetailService memberDetailService;
    @Value("${jwt.token.secret}")
    private String secret;
    @PostConstruct
    void init(){
        log.info("before encode " + secret);

        secret = Base64.getEncoder().encodeToString(secret.getBytes(StandardCharsets.UTF_8));
        log.info("after encode "+secret);
    }

    public String createToken(String userId, List<String> roles) { // 토큰 생성하는 과정
        Claims claims = Jwts.claims().setSubject(userId);
        claims.put("roles", roles);

        return Jwts.builder().setClaims(claims) // 페이로드값 설정
                .setIssuedAt(new Date(System.currentTimeMillis())) // 발급시간
                .setExpiration(new Date(System.currentTimeMillis() + 360000L)) // 만료 시간
                .signWith(SignatureAlgorithm.HS256, secret) // 서명을 어떤식으로 할것인지 알고리즘은 HS256을 쓰고 시크릿키로 한번더 요걸로 토큰 위변조 값을 체크하는것이지유
                .compact();
    }
    public String getUserIdFromToken(String token) { // 토큰 페이로드에서 값을 추출하는 과정
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }
    public String resolveToken(HttpServletRequest req) { // Header에서 토큰 값 추출
        return req.getHeader("Authorization");
    }

    public boolean validateToken(String token) { // 토큰 유효성 체크
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);// 토큰 페이로드 추출
            return !claimsJws.getBody().getExpiration().before(new Date()); // 만료시간 검사 만료시간은 바디값에 있어요
        } catch (Exception e) { return false;} //에러처리 세분화 필요
    }
    public Authentication getAuthentication(String token) {
        log.info("Token : " + token);
        Member member = memberDetailService.getMemberId(this.getUserIdFromToken(token));
        return new UsernamePasswordAuthenticationToken(member, "", member.getAuthorities());
    }
}
