/*
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mpalourdio.springboottemplate.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("marvel")
public class MarvelProperties {

    private String privatekey;
    private String publickey;

    public String getPrivatekey() {
        return privatekey;
    }

    public void setPrivatekey(final String privatekey) {
        this.privatekey = privatekey;
    }

    public String getPublickey() {
        return publickey;
    }

    public void setPublickey(final String publickey) {
        this.publickey = publickey;
    }
}
