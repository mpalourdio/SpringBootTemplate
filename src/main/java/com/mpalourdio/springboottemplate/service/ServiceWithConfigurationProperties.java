/*
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mpalourdio.springboottemplate.service;

import com.mpalourdio.springboottemplate.properties.MyPropertyConfigHolder;

public class ServiceWithConfigurationProperties {

    private final MyPropertyConfigHolder myProperty;

    public ServiceWithConfigurationProperties(MyPropertyConfigHolder myProperty) {
        this.myProperty = myProperty;
    }

    public String getFirstProperty() {
        return myProperty.first;
    }
}
