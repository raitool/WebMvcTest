package ee.ooloros.hibernate.demo1321.shared;

import java.io.IOException;
import java.util.List;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AuthRequestFilter  extends OncePerRequestFilter {
//    private AdminContextService adminContextService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("CashAtCageApi::Login");
        var authentication = new UsernamePasswordAuthenticationToken("test@test.com", "test", authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        System.out.println("dsasdsd");
        try {
            filterChain.doFilter(request, response);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
