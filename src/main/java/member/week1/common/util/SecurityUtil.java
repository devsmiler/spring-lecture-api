package member.week1.common.util;

import member.week1.member.domain.Member;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class SecurityUtil {

    private SecurityUtil() {}
/**
 *  2023.05.29
 *  created by Edgar Kim
     인증 API를 제외하고는 loadUserByUsername를 호출하지 않기에 별도로 Account를 디비에서 조회해주어야 한다.
     security context의 Authentication 객체를 이용해 username을 리턴해준다.
     security context에 authentication 객체가 저장되는 시점은 JwtFilter의 doFilter 영역
 */
    public static Optional<String> getCurrentUsername() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            return Optional.empty();
        }

        String username = null;
        if (authentication.getPrincipal() instanceof Member) {
            Member springSecurityUser = (Member) authentication.getPrincipal();
            username = springSecurityUser.getEmail();
        } else if (authentication.getPrincipal() instanceof String) {
            username = (String) authentication.getPrincipal();
        }

        return Optional.ofNullable(username);
    }
}
