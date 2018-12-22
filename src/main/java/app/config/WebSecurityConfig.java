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
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.actuate.autoconfigure.web.server.ManagementServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties({
        CredentialsProperties.class,
        ManagementServerProperties.class,
        WebEndpointProperties.class,
})
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String BASIC_AUTH_ENDPOINT = "/basicauth";
    private static final String ADMIN_ROLE = "ADMIN";
    private static final String ACTUATOR_ROLE = "ACTUATOR";

    private final CredentialsProperties credentialsProperties;

    public WebSecurityConfig(CredentialsProperties credentialsProperties) {
        this.credentialsProperties = credentialsProperties;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //We disable csrf protection for actuator endpoints so we can post with curl
        //ex : the refresh endpoint
        http.csrf().ignoringRequestMatchers(EndpointRequest.toAnyEndpoint());

        http.logout()
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutSuccessHandler(logoutHandler())
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"));

        http.authorizeRequests()
                .antMatchers(BASIC_AUTH_ENDPOINT)
                .hasRole(ADMIN_ROLE)
                .requestMatchers(EndpointRequest.toAnyEndpoint())
                .hasRole(ACTUATOR_ROLE)
                .and()
                .httpBasic();
    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        return new InMemoryUserDetailsManager(
                User.withDefaultPasswordEncoder()
                        .username(credentialsProperties.getUsername())
                        .password(credentialsProperties.getPassword())
                        .roles(ADMIN_ROLE, ACTUATOR_ROLE)
                        .build()
        );
    }

    private LogoutSuccessHandler logoutHandler() {
        SimpleUrlLogoutSuccessHandler logoutSuccessHandler = new SimpleUrlLogoutSuccessHandler();
        DefaultRedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

        redirectStrategy.setContextRelative(false);
        logoutSuccessHandler.setRedirectStrategy(redirectStrategy);

        return logoutSuccessHandler;
    }
}

