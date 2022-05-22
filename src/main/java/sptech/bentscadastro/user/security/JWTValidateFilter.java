package sptech.bentscadastro.user.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JWTValidateFilter extends BasicAuthenticationFilter {

    public static final String HEADER = "Authorization";
    public static final String PREFIX = "Bearer ";

    public JWTValidateFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String atribute = request.getHeader(HEADER);

        if (atribute == null) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!atribute.startsWith(PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = atribute.replace(PREFIX, "");

        UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(token);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(String token) {
        String user = JWT.require(Algorithm.HMAC512(JWTAuthenticateFilter.TOKEN_PASSWORD))
                .build()
                .verify(token)
                .getSubject();

        if (user == null) {
            return null;
        }

        return new UsernamePasswordAuthenticationToken(user, null,new ArrayList<>());
    }
}
