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
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(HttpVersionedApiController.class)
public class HttpVersionedApiTest extends AbstractTestRunner {

    private static final String ACCEPT_HEADER_V1 = "application/vnd.api.v1+json";
    private static final String ACCEPT_HEADER_V2 = "application/vnd.api.v2+json";

    private static final String QUALITY_09 = ";q=0.9";
    private static final String QUALITY_1 = ";q=1";

    private static final String ACCEPT_HEADER_V1_LOW_QUALITY = ACCEPT_HEADER_V1 + QUALITY_09;
    private static final String ACCEPT_HEADER_V1_HIGH_QUALITY = ACCEPT_HEADER_V1 + QUALITY_1;
    private static final String ACCEPT_HEADER_V2_LOW_QUALITY = ACCEPT_HEADER_V2 + QUALITY_09;


    @Autowired
    private MockMvc mvc;

    @Test
    public void testAcceptHeaderV1ReturnsV1Content() throws Exception {
        mvc.perform(get("/http/test")
                .header("Accept", ACCEPT_HEADER_V1))
                .andExpect(content().string("v1"));
    }

    @Test
    public void testAcceptHeaderV2ReturnsV2Content() throws Exception {
        mvc.perform(get("/http/test")
                .header("Accept", ACCEPT_HEADER_V2))
                .andExpect(content().string("v2"));
    }

    @Test
    public void testNoAcceptHeaderV2ReturnsV2ContentAsAcceptHeaderIsImplicitlyApplicationJson() throws Exception {
        mvc.perform(get("/http/test"))
                .andExpect(content().string("v2"));
    }

    @Test
    public void testLowQualityAcceptHeaderV2ReturnsV2Content() throws Exception {
        mvc.perform(get("/http/test")
                .header("Accept", ACCEPT_HEADER_V2_LOW_QUALITY + ", " + MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().string("v2"));
    }

    @Test
    public void testLowQualityAcceptHeaderV1ReturnsV2ContentBecauseThePriorityIsLow() throws Exception {
        mvc.perform(get("/http/test")
                .header("Accept", ACCEPT_HEADER_V1_LOW_QUALITY + ", " + MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().string("v2"));
    }

    @Test
    public void testHighQualityAcceptHeaderV1ReturnsV1ContentBecauseThePriorityIsHigh() throws Exception {
        mvc.perform(get("/http/test")
                .header("Accept", ACCEPT_HEADER_V1_HIGH_QUALITY + ", " + MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().string("v1"));
    }
}

