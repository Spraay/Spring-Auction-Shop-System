package krystianrytel.sklepaukcyjny.config;

import krystianrytel.sklepaukcyjny.config.ProfileNames;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    @Profile(ProfileNames.INMEMORY)
    public UserDetailsService userDetailsService() {

        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        User.UserBuilder userBuilder = User.withDefaultPasswordEncoder();

        UserDetails user = userBuilder
                .username("user")
                .password("user")
                .roles("USER")
                .build();

        UserDetails admin = userBuilder
                .username("admin")
                .password("admin")
                .roles("ADMIN")
                .build();

        UserDetails test = userBuilder
                .username("testowanie")
                .password("testowanie")
                .roles("USER", "ADMIN")
                .build();

        manager.createUser(user);
        manager.createUser(admin);
        manager.createUser(test);

        return manager;
    }

    @Bean
    @Profile(ProfileNames.DATABASE)
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public AccessDeniedHandler createAccessDeniedHandler(){
        AccessDeniedHandlerImpl impl = new AccessDeniedHandlerImpl();
        impl.setErrorPage("/error403");//url
        return impl;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .antMatchers("/statics/**", "/webjars/**", "/", "/item/list", "/user/register", "/error.html").permitAll()
                .antMatchers( "/item/**").hasRole("ADMIN")
                .anyRequest().authenticated();//każde żądanie ma być uwierzytelnione
        http
                .formLogin()//pozwól użytkownikom uwierzytelniać się poprzez formularz
                .loginPage("/user/login")//formularz logowania dostępny jest pod URL
                .successForwardUrl("/user/login/success")
                .permitAll();//przyznaj dostęp wszystkim użytkownikom dla wszystkich URL powiązanych z logowaniem opartym na formularzu.
        http
                .logout()//pozwól wszystkim użykownikom się wylogować
                .logoutSuccessUrl("/user/logout")
                .permitAll();//

        http.exceptionHandling().accessDeniedHandler(createAccessDeniedHandler());

    }

}


