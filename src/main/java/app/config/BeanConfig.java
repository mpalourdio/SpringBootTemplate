/*
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package app.config;

import com.mpalourdio.springboottemplate.generics.BeanFromConfigurationClass;
import com.mpalourdio.springboottemplate.model.Task;
import com.mpalourdio.springboottemplate.properties.MyPropertyConfigHolder;
import com.mpalourdio.springboottemplate.service.ServiceWithConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MyPropertyConfigHolder.class)
public class BeanConfig {

    @Bean
    public BeanFromConfigurationClass<Task> beanFromConfigurationClass() {
        final Task task = new Task();
        task.setTaskName("fromBeanConfiguration");
        return new BeanFromConfigurationClass<>(Task.class, task);
    }

    @Bean
    public ServiceWithConfigurationProperties serviceWithConfigurationProperties(final MyPropertyConfigHolder myProperty) {
        return new ServiceWithConfigurationProperties(myProperty);
    }
}

