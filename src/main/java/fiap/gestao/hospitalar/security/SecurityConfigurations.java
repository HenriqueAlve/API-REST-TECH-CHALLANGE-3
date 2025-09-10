package fiap.gestao.hospitalar.security;

import fiap.gestao.hospitalar.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    SecurityFilter securityFilter;

    @Autowired
    private UserRepository userRepository;

    /*@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/users").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/consultas/**").hasAnyRole("MEDICO" , "ENFERMEIRO" , "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/consultas").hasAnyRole("MEDICO" , "ENFERMEIRO" , "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/consultas/**").hasAnyRole("MEDICO" , "ENFERMEIRO" , "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/consultas/**").hasAnyRole("MEDICO" , "ENFERMEIRO" , "ADMIN")

                        .requestMatchers(HttpMethod.GET, "/medico/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/medico").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/medico/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/medico/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/enfermeiro/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/enfermeiro").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/enfermeiro/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/enfermeiro/**").hasRole("ADMIN")


                        .requestMatchers(HttpMethod.GET, "/paciente/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/paciente").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/paciente/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/paciente/**").hasRole("ADMIN")

                        // liberar GraphQL e Playground
                        .requestMatchers("/graphql/**").permitAll()
                        .requestMatchers("/playground/**").permitAll()


                        .anyRequest().authenticated())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }*/
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // desabilita CSRF (não usamos cookies de sessão)
                .csrf(csrf -> csrf.disable())

                // stateless, pois usamos JWT
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // regras de autorização
                .authorizeHttpRequests(authorize -> authorize
                        // endpoints públicos de autenticação
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()

                        // endpoints REST para ADMIN
                        .requestMatchers(HttpMethod.POST, "/users").hasRole("ADMIN")
                        .requestMatchers("/medico/**").hasRole("ADMIN")
                        .requestMatchers("/enfermeiro/**").hasRole("ADMIN")
                        .requestMatchers("/paciente/**").hasRole("ADMIN")

                        // endpoints REST para MEDICO/ENFERMEIRO/ADMIN
                        .requestMatchers("/consultas/**").hasAnyRole("MEDICO", "ENFERMEIRO", "ADMIN")

                        // libera GraphQL e Playground para todos
                        .requestMatchers("/playground/**").permitAll()
                        .requestMatchers("/graphql").permitAll() // Permite acesso ao GraphQL
                        .requestMatchers("/graphiql").permitAll() // Permite acesso ao GraphiQL

                        // qualquer outro endpoint precisa estar autenticado
                        .anyRequest().authenticated()
                )

                // adiciona seu filtro JWT se tiver
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)

                .build();
    }


    @Bean
    public AuthenticationManager authenticationManager(PasswordEncoder passwordEncoder) {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(username -> userRepository.findByLogin(username));
        authProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
