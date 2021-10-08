package de.tom.ref.webshop.security.config;

import de.tom.ref.webshop.entities.customers.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomerService customerService;
    private final BCryptPasswordEncoder  passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/static/**", "/css/**", "/img/**", "/js/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // track user by givim him a cockie (here a JSON web token JWT)
        http
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/", "/index",
                            "/shop", "/info",
                            "/signup", "/register", "/register_confirm",
                            "/filter",
                            "/api/registration/**").permitAll()
                    .antMatchers("/cart",
                            "/cartContent",
                            "/order", "/order_confirm", "/order_cancel").hasAnyRole("ADMIN","USER")
                    .antMatchers("/api/**").hasRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                    .loginPage("/login").permitAll()
                    //.loginProcessingUrl("/signin_form")
                    .defaultSuccessUrl("/shop", true)
                    //.failureUrl("/signin_form.html?error=true")
                    //.failureHandler(authenticationFailureHandler()
                .and()
                .logout()
                .logoutSuccessUrl("/");
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(customerService);
        return provider;
    }
}
