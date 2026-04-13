/*package com.tlr.teloreparto.authentication.security;

import com.tlr.teloreparto.authentication.service.UserCompanyService;
import com.tlr.teloreparto.authentication.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfigurationOld {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;          // Usuarios normales
    private final JwtAuthenticationCompanyFilter jwtAuthenticationCompanyFilter;  // Empresas
    private final UserService userService;
    private final UserCompanyService userCompanyService;

    private static final String[] PUBLIC_URLS = {
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/api-docs/**",
            "/swagger-resources/**",
            "/webjars/**",
            "/favicon.ico",
            "/api/v1/auth/**",
            "/api/v1/auth-company/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 🔥 MUY IMPORTANTE PARA CORS (preflight request)
                .authorizeHttpRequests(auth -> auth
                        // .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // NECESARIO PARA CORS
                        .requestMatchers(HttpMethod.GET, "/api/v1/tipo-vehiculos/list").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/categorias-empresas/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/conductor/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/conductor/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/productos-page/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/documentos-conductor/**").permitAll()
                        .requestMatchers("/api/v1/solicitudes-api/**").permitAll()

                        .requestMatchers(PUBLIC_URLS).permitAll()
                        .anyRequest().authenticated()
                )
                // Filtros JWT
                .addFilterBefore(jwtAuthenticationCompanyFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    // ===============================
    //  AUTH MANAGER (UNIFICADO)
    // ===============================
    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(List.of(
                userAuthenticationProvider(),
                companyAuthenticationProvider()
        ));
    }
    // Providers
    @Bean
    public AuthenticationProvider userAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService.userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationProvider companyAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userCompanyService.userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    // Password encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // CORS
    private UrlBasedCorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:5173",
                "http://localhost:5174",
                "http://localhost:5175",
                "http://localhost:3000",
                "http://127.0.0.1:*",
                "https://*",
                "http://*",
                "http://127.0.0.1:5173",
                "http://127.0.0.1:5174",
                "http://127.0.0.1:5175",
                "http://127.0.0.1:3000",
                "https://panelclient.telareparto.com",
                "https://panelbo.telareparto.com",
                "https://telareparto.com"
        ));

        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}*/