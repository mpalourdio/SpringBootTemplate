package app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String PROTECTED_ENDPOINT = "/basicauth";
    private static final String ADMIN_ROLE = "ADMIN";

    private final Environment env;

    public WebSecurityConfig(final Environment env) {
        this.env = env;
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.headers().defaultsDisabled().cacheControl();

        http.logout()
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .logoutSuccessHandler(logoutHandler())
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"));

        http.authorizeRequests()
                .antMatchers(PROTECTED_ENDPOINT)
                .hasRole(ADMIN_ROLE).and()
                .httpBasic();
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser(env.getProperty("admin.username"))
                .password(env.getProperty("admin.password"))
                .roles(ADMIN_ROLE);
    }

    private LogoutSuccessHandler logoutHandler() {
        final SimpleUrlLogoutSuccessHandler logoutSuccessHandler = new SimpleUrlLogoutSuccessHandler();
        final DefaultRedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

        redirectStrategy.setContextRelative(false);
        logoutSuccessHandler.setRedirectStrategy(redirectStrategy);

        return logoutSuccessHandler;
    }
}

