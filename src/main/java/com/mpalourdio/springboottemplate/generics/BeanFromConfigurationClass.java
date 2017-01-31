/*
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mpalourdio.springboottemplate.generics;

import com.mpalourdio.springboottemplate.model.Task;

public class BeanFromConfigurationClass<T extends Task> implements GenericInterface<T> {

    private final Class<T> tClass;

    public BeanFromConfigurationClass(final Class<T> tClass) {
        this.tClass = tClass;
    }

    @Override
    public T getParametrizedClass() {
        final ParametrizedClass<T> parametrizedClass = new ParametrizedClass<>();

        return parametrizedClass.getProperty();
    }
}
