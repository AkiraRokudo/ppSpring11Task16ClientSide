package ppSpring11Task16.config;

import ppSpring11Task16.config.handler.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private UserDetailsService userService;

    @Autowired
    public SecurityConfig(UserDetailsService userService) {
        this.userService = userService;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //csrf, login and logout
        http
                .formLogin()
                    // указываем страницу с формой логина
                    .loginPage("/")
                    //указываем логику обработки при логине
                    .successHandler(new LoginSuccessHandler())
                    // указываем action с формы логина
                    .loginProcessingUrl("/")
                    // даем доступ к форме логина всем
                    .permitAll()
                    .and()
                .logout()
                    // разрешаем делать логаут всем
                    .permitAll()
                    // указываем URL логаута
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))//TODO:Проверить то что ловит
                    // указываем URL при удачном логауте
                    .logoutSuccessUrl("/?logout")
                    //выклчаем кроссдоменную секьюрность (на этапе обучения неважна)
                    .and()
                .csrf().disable();

        //access
        http
                // делаем страницу регистрации недоступной для авторизированных пользователей
                .authorizeRequests()
                //страницы аутентификаци доступна всем
                .antMatchers("/").anonymous()
                // защищенные URL
                .antMatchers("/admin/**").access("hasAnyRole('Admin')").anyRequest().authenticated();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

}
