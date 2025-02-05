package bakos.life_pm.auth;

import bakos.life_pm.service.JwtService;
import bakos.life_pm.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static bakos.life_pm.service.Utils.getTokenFromCookie;

@Slf4j
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    public static final String AUTH_COOKIE_NAME = "auth-token";
    public static final String REFRESH_COOKIE_NAME = "refresh-token";

    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;


    public JwtAuthFilter(JwtService jwtService, UserDetailsServiceImpl userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {

            String token = getTokenFromAuthHeader(request);

            if (token == null) {
                token = getTokenFromCookie(request, AUTH_COOKIE_NAME);
            }

            if (token != null && jwtService.validateJwtToken(token)) {
                String username = jwtService.extractUsername(token);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception e) {
            log.warn("Error in JwtAuthFilter: {}", e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    private static String getTokenFromAuthHeader(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

}
