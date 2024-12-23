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
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ServiceWithPropertiesTest {

    @Test
    void testClassPropertyIsRead() {
        var serviceWithProperties = new ServiceWithProperties("hey");
        assertThat(serviceWithProperties.getValueFromConfig()).isEqualTo("hey");
    }

    @Test()
    void throwExceptionTest() {
        var serviceWithProperties = new ServiceWithProperties("hey");
        assertThatThrownBy(serviceWithProperties::throwException).isInstanceOf(AnotherCustomException.class);
    }
}
