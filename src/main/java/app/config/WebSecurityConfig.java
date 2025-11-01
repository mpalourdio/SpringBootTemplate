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
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.security.autoconfigure.actuate.web.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;

@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
@EnableConfigurationProperties(CredentialsProperties.class)
@ConditionalOnWebApplication
public class WebSecurityConfig {

    private static final String BASIC_AUTH_ENDPOINT = "/basicauth";
    private static final String ADMIN_ROLE = "ADMIN";
    private static final String ACTUATOR_ROLE = "ACTUATOR";

    private final CredentialsProperties credentialsProperties;

    public WebSecurityConfig(CredentialsProperties credentialsProperties) {
        this.credentialsProperties = credentialsProperties;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) {
        http.csrf(csrf -> csrf.ignoringRequestMatchers(EndpointRequest.toAnyEndpoint()))
                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .logoutSuccessHandler(logoutHandler())
                        .logoutRequestMatcher(PathPatternRequestMatcher.withDefaults().matcher("/logout"))
                )
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(BASIC_AUTH_ENDPOINT)
                        .hasRole(ADMIN_ROLE)
                        .requestMatchers(EndpointRequest.toAnyEndpoint())
                        .hasRole(ACTUATOR_ROLE)
                        .anyRequest().permitAll()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
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
        var logoutSuccessHandler = new SimpleUrlLogoutSuccessHandler();
        var redirectStrategy = new DefaultRedirectStrategy();

        redirectStrategy.setContextRelative(false);
        logoutSuccessHandler.setRedirectStrategy(redirectStrategy);

        return logoutSuccessHandler;
    }
}

