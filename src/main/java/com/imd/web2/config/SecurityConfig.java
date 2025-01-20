package com.imd.web2.config;

import com.imd.web2.usuarios.Enums.RoleEnum;
import com.imd.web2.usuarios.UserEntity;
import com.imd.web2.usuarios.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Autowired
    private SecurityFilter securityFilter;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // Desabilita CSRF
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Política de sessão stateless
                .authorizeHttpRequests(authorize ->
                        authorize
                                // Permitir acesso sem autenticação aos endpoints de login e registro
                                .requestMatchers("/auth/login", "/auth/register").permitAll()
                                // Permitir acesso ao método GET para produtos, pedidos e clientes para qualquer usuário autenticado
                                .requestMatchers(HttpMethod.GET, "/produtos", "/pedidos", "/clientes").authenticated()
                                // Permitir apenas para ADMIN métodos POST, DELETE, PUT para produtos, pedidos, clientes e usuários
                                .requestMatchers(HttpMethod.POST, "/produtos", "/pedidos", "/clientes", "/usuarios").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/produtos", "/pedidos", "/clientes", "/usuarios").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/produtos", "/pedidos", "/clientes", "/usuarios").hasRole("ADMIN")
                                // Permitir acesso ao método GET para usuários logados em pedidos e clientes
                                .requestMatchers(HttpMethod.GET, "/produtos").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/pedidos", "/clientes").hasAnyRole("USER", "ADMIN")
                                // Qualquer outra requisição precisa ser autenticada
                                .anyRequest().authenticated())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }



    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager(); // Configura o gerenciador de autenticação
    }

    @PostConstruct
    public void createInitialAdminUser() {
        if (userRepository.count() == 0) {
            UserEntity adminUser = new UserEntity();
            adminUser.setLogin("admin@example.com");

            adminUser.setPassword(passwordEncoder.encode("admin123"));
            adminUser.setRole(RoleEnum.ADMIN);

            userRepository.save(adminUser);
            System.out.println("Usuário ADMIN inicial criado com sucesso!");
        }
    }
}
