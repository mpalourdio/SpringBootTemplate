/*
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package app.config;

import com.mpalourdio.springboottemplate.events.AsyncEvent;
import com.mpalourdio.springboottemplate.events.AsyncLogger;
import com.mpalourdio.springboottemplate.events.MyEventListener;
import com.mpalourdio.springboottemplate.generics.BeanFromConfigurationClass;
import com.mpalourdio.springboottemplate.model.entities.Task;
import com.mpalourdio.springboottemplate.properties.MyPropertyConfigHolder;
import com.mpalourdio.springboottemplate.service.ABeanIWantToMock;
import com.mpalourdio.springboottemplate.service.ServiceWithConfigurationProperties;
import com.mpalourdio.springboottemplate.service.ServiceWithProperties;
import com.mpalourdio.springboottemplate.service.UselessBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MyPropertyConfigHolder.class)
public class BeansFactory {

    @Bean
    public BeanFromConfigurationClass<Task> beanFromConfigurationClass() {
        Task task = new Task();
        task.setTaskName("fromBeanConfiguration");
        return new BeanFromConfigurationClass<>(Task.class, task);
    }

    @Bean
    public ServiceWithConfigurationProperties serviceWithConfigurationProperties(MyPropertyConfigHolder myProperty) {
        return new ServiceWithConfigurationProperties(myProperty);
    }

    @Bean
    public ServiceWithProperties serviceWithProperties(@Value("${admin.username}") String valueFromConfig) {
        return new ServiceWithProperties(valueFromConfig);
    }

    @Bean
    public AsyncEvent asyncEvent() {
        return new AsyncEvent();
    }

    @Bean
    public AsyncLogger asyncLogger(ApplicationEventPublisher eventPublisher) {
        return new AsyncLogger(eventPublisher);
    }

    @Bean
    public MyEventListener myEventListener() {
        return new MyEventListener();
    }

    @Bean
    public ABeanIWantToMock aBeanIWantToMock() {
        return new ABeanIWantToMock();
    }

    @Bean
    public UselessBean uselessBean(ABeanIWantToMock aBeanIWantToMock) {
        return new UselessBean(aBeanIWantToMock);
    }
}

