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

public class ServiceWithProperties {

    private final String valueFromConfig;

    public ServiceWithProperties(String valueFromConfig) {
        this.valueFromConfig = valueFromConfig;
    }

    public String getValueFromConfig() {
        return valueFromConfig;
    }

    public void throwException() throws AnotherCustomException {
        throw new AnotherCustomException("gosh");
    }
}
