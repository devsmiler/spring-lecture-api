package member.week1.configuration;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter { // Generic타입하고 OncePer타입이있는데 차이는 나중에 정리가 필요할듯
    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal( // generic쓰면 그냥 doFilter로 하게됨
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain ) throws ServletException, IOException {

        String token = jwtTokenProvider.resolveToken(request); // 토큰 값 헤더에서 추출

        if ( token != null && jwtTokenProvider.validateToken(token)){ // 토큰 값 유효한지 검증
            Authentication authentication = jwtTokenProvider.getAuthentication(token); // 유효하다면 토큰에서 페이로드 추출 후, UsernameAndPasswordAuth 클래스에 담아서 가지고옴 이래야지 Security context에 담을수있음
            SecurityContextHolder.getContext().setAuthentication(authentication);//auth 컨텍스트에 담아줌
        }

        filterChain.doFilter(request, response);
    }
}
