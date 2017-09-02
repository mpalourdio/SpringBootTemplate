/*
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mpalourdio.springboottemplate.service;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UselessBean {

    private String testPro = "inclass";

    private final ABeanIWantToMock aBeanIWantToMock;

    public UselessBean(ABeanIWantToMock aBeanIWantToMock) {
        this.aBeanIWantToMock = aBeanIWantToMock;
    }

    public Boolean iWantToMockThisMethod() {
        return aBeanIWantToMock.iAlwaysReturnFalse();
    }

    public String getTestPro() {
        return testPro;
    }

    public void setTestPro(String testPro) {
        this.testPro = testPro;
    }

    public void testSerialization(ToSerialize toSerialize) {
        toSerialize.prop1 = "prop1updated";
    }
}
