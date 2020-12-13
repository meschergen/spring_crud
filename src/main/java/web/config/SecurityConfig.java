package web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import web.config.handler.LoginSuccessHandler;

/**
 * 07.12.2020
 *
 * @author MescheRGen
 */
@Configuration // притянестя по цепочке, через EnableWebSecurity, необязательно
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsService;
    private LoginSuccessHandler loginSuccessHandler;

    @Autowired
    @Qualifier("userDetailsServiceImp")
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Autowired
    public void setLoginSuccessHandler(LoginSuccessHandler loginSuccessHandler) {
        this.loginSuccessHandler = loginSuccessHandler;
    }

    /*@Override
    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin").password("1234").roles("ADMIN") //
                .and()
                .withUser("Johny").password("test").roles("USER");
    }

    /*@Autowired
    protected void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //даём доступ к форме login всем
        http.formLogin()
                //.loginPage("/login")
                .successHandler(loginSuccessHandler)
                //.loginProcessingUrl("/login") // /login - адрес по умолчанию, можно не указывать
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll();

        //разрешаем logout всем
        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .permitAll()
                .and()
                .csrf().disable(); // выключение кроссдоменной секьюрности

        // страница регистрации недоступна авторизованным пользователям
        http.authorizeRequests()
                .antMatchers("/login").anonymous()
                //.antMatchers("/hello").hasAnyRole("ADMIN").anyRequest().authenticated()
                //.antMatchers("/users/**").hasRole("ADMIN")
                .antMatchers("/users").hasRole("ADMIN")
                .antMatchers("/users/info/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/users/edit").hasRole("ADMIN")
                .antMatchers("/users/list").hasRole("ADMIN")
                .antMatchers("users/new").hasRole("ADMIN") // можно переработать через "/users/**"
                .antMatchers("/").permitAll();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
        daoProvider.setPasswordEncoder(passwordEncoder());
        daoProvider.setUserDetailsService(userDetailsService);
        return daoProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }
}
