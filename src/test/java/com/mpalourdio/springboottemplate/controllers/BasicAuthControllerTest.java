/*
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mpalourdio.springboottemplate.controllers;

import com.mpalourdio.springboottemplate.AbstractTestRunner;
import com.mpalourdio.springboottemplate.properties.CredentialsProperties;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BasicAuthController.class)
public class BasicAuthControllerTest extends AbstractTestRunner {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CredentialsProperties credentialsProperties;

    @Test
    public void testBasicAuthWhenCredentialsAreOk() throws Exception {
        String basicDigestHeaderValue =
                "Basic " + new String(Base64.encodeBase64((credentialsProperties.getUsername()+ ":" + credentialsProperties.getPassword()).getBytes()));
        mvc.perform(get("/basicauth")
                .header("Authorization", basicDigestHeaderValue))
                .andExpect(status().isOk());
    }

    @Test
    public void testWrongCredentialsReturnUnauthorized() throws Exception {
        String basicDigestHeaderValue =
                "Basic " + new String(Base64.encodeBase64(("fuck:you").getBytes()));

        mvc.perform(get("/basicauth")
                .header("Authorization", basicDigestHeaderValue))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testNoCredentialsReturnUnauthorized() throws Exception {
        mvc.perform(get("/basicauth"))
                .andExpect(status().isUnauthorized());
    }
}
