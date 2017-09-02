/*
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mpalourdio.springboottemplate;

import app.config.BeansFactory;
import app.config.PropertyPlaceholderConfig;
import app.config.WebSecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import java.util.Arrays;

@SpringBootApplication
@PropertySource(value = {
        "file:properties/global.properties",
        "file:properties/local.properties"
}, ignoreResourceNotFound = true)
@Import({
        WebSecurityConfig.class,
        BeansFactory.class,
        PropertyPlaceholderConfig.class
})
@EnableDiscoveryClient
public class SpringBootTemplateApplication {

    public static void main(String... args) {
        ApplicationContext ctx = SpringApplication.run(SpringBootTemplateApplication.class, args);
        String[] beanNames = ctx.getBeanDefinitionNames();

        Arrays.stream(beanNames)
                .sorted()
                .forEach(System.out::println);
    }
}
