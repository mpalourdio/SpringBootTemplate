/*
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mpalourdio.springboottemplate.service;

import com.mpalourdio.springboottemplate.exception.AnotherCustomException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ServiceWithPropertiesTest {

    @Test
    public void testClassPropertyIsRead() {
        ServiceWithProperties serviceWithProperties = new ServiceWithProperties("hey");
        Assert.assertEquals("hey", serviceWithProperties.getValueFromConfig());
    }

    @Test(expected = AnotherCustomException.class)
    public void throwExceptionTest() throws AnotherCustomException {
        ServiceWithProperties serviceWithProperties = new ServiceWithProperties("hey");
        serviceWithProperties.throwException();
    }
}
