package com.car.dealership.security;

import com.car.dealership.security.filter.JwtAuthenticationFilter;
import com.car.dealership.security.filter.JwtValidationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;


    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(requests -> requests
                        .antMatchers(HttpMethod.GET, "/**").permitAll()/////////////////
                        .antMatchers(HttpMethod.POST, "/**").permitAll()/////////////////
                        .antMatchers(HttpMethod.PUT, "/**").permitAll()/////////////////
                        .antMatchers(HttpMethod.DELETE, "/**").permitAll()/////////////////
                        .antMatchers(HttpMethod.GET, "/user", "/user/{id}").hasAnyRole("USER", "ADMIN")
                        .antMatchers(HttpMethod.POST, "/user/create").hasAnyRole("USER", "ADMIN")
                        .antMatchers(HttpMethod.POST, "/user/create/admin").hasRole("ADMIN")
                        .antMatchers(HttpMethod.PUT, "/user/update/{id}").hasAnyRole("USER", "ADMIN")
                        .antMatchers(HttpMethod.DELETE, "/user/delete/{id}").hasRole("ADMIN")
                        //
                        .antMatchers(HttpMethod.GET, "/role", "/role/{id}").hasAnyRole("ADMIN")
                        .antMatchers(HttpMethod.POST, "/role/create", "/role/search").hasRole("ADMIN")
                        .antMatchers(HttpMethod.PUT, "/role/update/{id}").hasRole("ADMIN")
                        .antMatchers(HttpMethod.DELETE, "/role/delete/{id}").hasRole("ADMIN")
                        //
                        .antMatchers(HttpMethod.GET, "/vehicle", "/vehicle/{id}", "/vehicle/search/url/byPlate/{plate}").hasAnyRole("USER", "ADMIN")
                        .antMatchers(HttpMethod.POST, "/vehicle/create").hasRole("ADMIN")
                        .antMatchers(HttpMethod.PUT, "/vehicle/update/{id}").hasRole("ADMIN")
                        .antMatchers(HttpMethod.DELETE, "/vehicle/delete/{id}").hasRole("ADMIN")
                        //
                        .antMatchers(HttpMethod.GET, "/client", "/client/{id}").hasAnyRole("USER","ADMIN")
                        .antMatchers(HttpMethod.POST, "/client/create").hasAnyRole("USER","ADMIN")
                        .antMatchers(HttpMethod.PUT, "/client/update/{id}").hasAnyRole("USER","ADMIN")
                        .antMatchers(HttpMethod.DELETE, "/client/delete/{id}").hasRole("ADMIN")
                        //
                        .antMatchers(HttpMethod.GET, "/workOrder", "/workOrder/{id}").hasAnyRole("USER", "ADMIN")
                        .antMatchers(HttpMethod.POST, "/workOrder/create").hasAnyRole("USER", "ADMIN")
                        .antMatchers(HttpMethod.PUT, "/workOrder/update/{id}").hasAnyRole("USER", "ADMIN")
                        .antMatchers(HttpMethod.DELETE, "/workOrder/delete/{id}").hasRole("ADMIN")
                        //
                        .antMatchers(HttpMethod.GET, "/workOrderItem", "/workOrderItem/{id}").hasAnyRole("USER", "ADMIN")
                        .antMatchers(HttpMethod.POST, "/workOrderItem/create").hasAnyRole("USER", "ADMIN")
                        .antMatchers(HttpMethod.PUT, "/workOrderItem/update/{id}").hasAnyRole("USER", "ADMIN")
                        .antMatchers(HttpMethod.DELETE, "/workOrderItem/delete/{id}").hasRole("ADMIN")
                        //
                        .antMatchers(HttpMethod.GET, "/orderType", "/orderType/{id}").hasRole("ADMIN")
                        .antMatchers(HttpMethod.POST, "/orderType/create").hasRole("ADMIN")
                        .antMatchers(HttpMethod.PUT, "/orderType/update/{id}").hasRole("ADMIN")
                        .antMatchers(HttpMethod.DELETE, "/orderType/delete/{id}").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtValidationFilter(authenticationManager()))
                .csrf(csrf -> {
                    try {
                        csrf.disable()
                                .headers(headers -> headers.frameOptions().disable());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                })
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(handling -> handling.accessDeniedHandler(new CustomAccessDeniedHandler()));
    }

    //

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("*"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    FilterRegistrationBean<CorsFilter> corsFilter() {
        FilterRegistrationBean<CorsFilter> corsBean = new FilterRegistrationBean<>(new CorsFilter(corsConfigurationSource()));
        corsBean.setOrder(Ordered.HIGHEST_PRECEDENCE);

        return corsBean;
    }
}
