package de.othr.sw.mos.artistAgency.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("userService")
    private UserDetailsService userService;

    @Autowired
    private SecurityUtils securityUtils;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userService)
               .passwordEncoder(passwordEncoder());
    }

    private BCryptPasswordEncoder passwordEncoder() {
        return securityUtils.passwordEncoder();
    }

    // accessible sites without being logged in
    private static final String[] ALLOW_ACCESS_WITHOUT_AUTHENTICATION = {
            "/",
            "/home",
            "/error",
            "/errorPage",
            "/artist/list",
            "/artist/details",
            "/event/list",
            "/event/details",
            "/user/login",
            "/user/register",
            // interface
            "/api/**",
            // img & css folder
            "/img/**",
            "/style/**"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers(ALLOW_ACCESS_WITHOUT_AUTHENTICATION)
            .permitAll()
            .anyRequest()
            .authenticated();

        http.formLogin()
            .loginPage("/user/login")
            .permitAll()
            .defaultSuccessUrl("/user/login?code=success")
            .failureUrl("/user/login?code=error")
            .and()
            .logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
            .logoutSuccessUrl("/user/login?code=logout")
            .deleteCookies("remember-me")
            .permitAll()
            .and()
            .rememberMe();

        http.cors()
            .and()
            .csrf()
            .disable();
    }
}
