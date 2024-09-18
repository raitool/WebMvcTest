package ee.ooloros.hibernate.demo1321.shared;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthRequestFilter extends OncePerRequestFilter {

    private final AdminContextService adminContextService;

    @Override
    protected void doFilterInternal(
            @Nonnull HttpServletRequest request,
            @Nonnull HttpServletResponse response,
            @Nonnull FilterChain filterChain
    ) throws ServletException, IOException {
        if (request.getCookies() == null) {
            filterChain.doFilter(request, response);
            return;
        }
        var sessionCookie = Arrays.stream(request.getCookies())
                .filter(cookie -> Objects.equals(cookie.getName(), "BOSESSIONID"))
                .findFirst();
        if (sessionCookie.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        Optional.ofNullable(adminContextService.resolveRole(sessionCookie.get().getValue(), false))
                .map(admin -> new RememberMeAuthenticationToken(admin.sessionToken(), admin, admin.roles().stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                        .toList()))
                .ifPresent(token -> SecurityContextHolder.getContextHolderStrategy()
                        .getContext().setAuthentication(token));

        filterChain.doFilter(request, response);
    }
}
