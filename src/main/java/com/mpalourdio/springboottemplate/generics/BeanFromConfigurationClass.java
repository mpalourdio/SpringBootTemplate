/*
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mpalourdio.springboottemplate.generics;

import com.mpalourdio.springboottemplate.model.entities.Task;

public class BeanFromConfigurationClass<T extends Task> implements GenericInterface<T> {

    private final Class<T> tClass;
    private final T taskInstance;

    public BeanFromConfigurationClass(Class<T> tClass, T taskInstance) {
        this.tClass = tClass;
        this.taskInstance = taskInstance;
    }

    @Override
    public T getInstance() {
        ParametrizedClass<T> taskParametrizedClass = new ParametrizedClass<>(taskInstance);

        return taskParametrizedClass.getProperty();
    }
}
