package web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import web.config.handler.LoginSuccessHandler;

/**
 * 07.12.2020
 *
 * @author MescheRGen
 */
@Configuration // притянестя по цепочке, через EnableWebSecurity, необязательно
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private  UserDetailsService userDetailsService;
    private  LoginSuccessHandler loginSuccessHandler;

    //public SecurityConfig(@Qualifier("userDetailsServiceImp") UserDetailsService userDetailsService, LoginSuccessHandler loginSuccessHandler) {
    //    this.userDetailsService = userDetailsService;
    //    this.loginSuccessHandler = loginSuccessHandler;
    //}

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
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("ADMIN").password("1234").roles("ROLE_ADMIN") // TODO без ROLE_ !
                .and()
                .withUser("Johny").password("test").roles("ROLE_USER");
    }*/

    @Autowired
    protected void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //даём доступ к форме login всем
        http.formLogin()
                .loginPage("/login")
                .successHandler(loginSuccessHandler)
                .loginProcessingUrl("/login") // /login - адрес по умолчанию, можно не указывать
                .usernameParameter("j_username")
                .passwordParameter("j_password")
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
                .antMatchers("/hello").hasAnyRole("ROLE_ADMIN").anyRequest().authenticated()
                .antMatchers("/users/info").hasAnyRole("ROLE_USER") // TODO - role без Role_ !
                .antMatchers("/users/edit").hasAnyRole("ROLE_ADMIN")
                .antMatchers("/users/list").hasAnyRole("ROLE_ADMIN") // можно переработать через "/users/**"
                .antMatchers("/").permitAll();

        //TODO: ДОСТУП к crud только админу. Доступ к инфо- И админу И юзеру (частично сделано)
    }

    @Bean
    public static BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
