package fiap.gestao.hospitalar.security;

import fiap.gestao.hospitalar.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;
    @Autowired
    UserRepository userRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String path = request.getRequestURI();
        final String method = request.getMethod();

        // 1) Preflight CORS deve passar direto
        if ("OPTIONS".equalsIgnoreCase(method)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2) Endpoints públicos devem passar sem exigir token
        if (path.startsWith("/auth/")
                || path.startsWith("/playground")
                || "/graphql".equals(path)
                || "/graphiql".equals(path)
                || "/error".equals(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3) Demais rotas: validar token se houver
        String token = recoverToken(request);
        if (token != null && !token.isBlank()) {
            try {
                String login = tokenService.validateToken(token);
                if (login != null) {
                    UserDetails user = userRepository.findByLogin(login);
                    var auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    // opcional: setar detalhes da requisição
                    // auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            } catch (Exception e) {
                SecurityContextHolder.clearContext(); // token inválido: segue sem auth
            }
        }

        filterChain.doFilter(request, response);
    }



    private String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}
