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
    private UserDetailsService userService; // UserDetailsService in userServiceIF implemented

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

    // TODO: Registration of accessible sites without being logged in
    // TODO: --> imagefolder muss auch eingebunden werden!!
    private static final String[] ALLOW_ACCESS_WITHOUT_AUTHENTICATION = {
        "/", "/login", "/register"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers(ALLOW_ACCESS_WITHOUT_AUTHENTICATION)
            .permitAll()
            .anyRequest()
            .authenticated();

        // TODO: successUrls...
        http.formLogin()
            .loginPage("/login")
            .permitAll()
            .defaultSuccessUrl("/account")
            .failureUrl("/login?error")
            .and()
            .logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .logoutSuccessUrl("/login?logout")
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
