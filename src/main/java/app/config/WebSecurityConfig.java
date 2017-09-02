/*
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package app.config;

import com.mpalourdio.springboottemplate.properties.CredentialsProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
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
@EnableConfigurationProperties(CredentialsProperties.class)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String PROTECTED_ENDPOINT = "/basicauth";
    private static final String ADMIN_ROLE = "ADMIN";
    private static final String ACTUATOR_ROLE = "ACTUATOR";

    private final CredentialsProperties credentialsProperties;

    public WebSecurityConfig(CredentialsProperties credentialsProperties) {
        this.credentialsProperties = credentialsProperties;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
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

        http.authorizeRequests()
                .antMatchers(PROTECTED_ENDPOINT, "/actuator")
                .hasRole(ACTUATOR_ROLE).and()
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser(credentialsProperties.getUsername())
                .password(credentialsProperties.getPassword())
                .roles(ADMIN_ROLE, ACTUATOR_ROLE);
    }

    private LogoutSuccessHandler logoutHandler() {
        SimpleUrlLogoutSuccessHandler logoutSuccessHandler = new SimpleUrlLogoutSuccessHandler();
        DefaultRedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

        redirectStrategy.setContextRelative(false);
        logoutSuccessHandler.setRedirectStrategy(redirectStrategy);

        return logoutSuccessHandler;
    }
}

