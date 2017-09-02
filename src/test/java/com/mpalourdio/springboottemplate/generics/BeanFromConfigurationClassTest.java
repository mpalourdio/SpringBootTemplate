/*
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mpalourdio.springboottemplate.generics;

import com.mpalourdio.springboottemplate.AbstractTestRunner;
import com.mpalourdio.springboottemplate.model.entities.Task;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class BeanFromConfigurationClassTest extends AbstractTestRunner {

    @Autowired
    public BeanFromConfigurationClass<Task> beanFromConfigurationClass;

    @Test
    public void testImportBeanConfigurationFile() {
        Task task = beanFromConfigurationClass.getInstance();
        Assert.assertEquals("fromBeanConfiguration", task.getTaskName());
    }
}




