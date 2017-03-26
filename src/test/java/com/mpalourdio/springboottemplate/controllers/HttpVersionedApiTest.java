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
import com.mpalourdio.springboottemplate.mediatype.MediaType;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

@WebMvcTest(HttpVersionedApiController.class)
public class HttpVersionedApiTest extends AbstractTestRunner {

    private static final String HEADER_V1 = MediaType.APPLICATION_VND_API_V1_VALUE;
    private static final String HEADER_V2 = MediaType.APPLICATION_VND_API_V2_VALUE;

    private static final String QUALITY_09 = ";q=0.9";
    private static final String QUALITY_1 = ";q=1";

    private static final String HEADER_V1_LOW_QUALITY = HEADER_V1 + QUALITY_09;
    private static final String HEADER_V1_HIGH_QUALITY = HEADER_V1 + QUALITY_1;
    private static final String HEADER_V2_LOW_QUALITY = HEADER_V2 + QUALITY_09;

    @Autowired
    private MockMvc mvc;

    @Test
    public void testGetAndAcceptHeaderV1ReturnsV1Content() throws Exception {
        mvc.perform(get("/http/test")
                .header("Accept", HEADER_V1))
                .andExpect(content().string("v1"));
    }

    @Test
    public void testGetAndAcceptHeaderV2ReturnsV2Content() throws Exception {
        mvc.perform(get("/http/test")
                .header("Accept", HEADER_V2))
                .andExpect(content().string("v2"));
    }

    @Test
    public void testGetAndNoAcceptHeaderReturnsV2ContentAsAcceptHeaderIsImplicitlyApplicationJson() throws Exception {
        mvc.perform(get("/http/test"))
                .andExpect(content().string("v2"))
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE));
    }

    @Test
    public void testGetAndLowQualityAcceptHeaderV2ReturnsV2Content() throws Exception {
        mvc.perform(get("/http/test")
                .header("Accept", HEADER_V2_LOW_QUALITY + ", " + MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().string("v2"));
    }

    @Test
    public void testGetAndLowQualityAcceptHeaderV1ReturnsV2ContentBecauseThePriorityIsLow() throws Exception {
        mvc.perform(get("/http/test")
                .header("Accept", HEADER_V1_LOW_QUALITY + ", " + MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().string("v2"))
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE));
    }

    @Test
    public void testGetAndHighQualityAcceptHeaderV1ReturnsV1ContentBecauseThePriorityIsHigh() throws Exception {
        mvc.perform(get("/http/test")
                .header("Accept", HEADER_V1_HIGH_QUALITY + ", " + MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().string("v1"));
    }


    @Test
    public void testPostAndContentTypeHeaderV1ReturnsV1Content() throws Exception {
        mvc.perform(post("/http/test")
                .header("Content-Type", HEADER_V1))
                .andExpect(content().string("v1-post"));
    }

    @Test
    public void testPostAndContentTypeHeaderV2ReturnsV2Content() throws Exception {
        mvc.perform(post("/http/test")
                .header("Content-Type", HEADER_V2))
                .andExpect(content().string("v2-post"));
    }

    @Test
    public void testPostAndLowQualityAcceptHeaderV2ReturnsV2ContentBecauseThePriorityIsLow() throws Exception {
        mvc.perform(post("/http/test")
                .header("Content-Type", HEADER_V2)
                .header("Accept", HEADER_V1_LOW_QUALITY + ", " + MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().string("v2-post"))
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE));
    }

    @Test
    public void testPostAndNoAcceptHeaderReturnsV2ContentAsAcceptHeaderIsImplicitlyApplicationJson() throws Exception {
        mvc.perform(post("/http/test"))
                .andExpect(content().string("v2-post-consumes-all"))
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE));
    }

    @Test
    public void testPostAndLowQualityAcceptHeaderV2ReturnsV2Content() throws Exception {
        mvc.perform(post("/http/test")
                .header("Content-Type", MediaType.ALL_VALUE)
                .header("Accept", HEADER_V2_LOW_QUALITY + ", " + MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().string("v2-post-consumes-all"));
    }

    @Test
    public void testPostAndLowQualityAcceptHeaderV1ReturnsV2ContentBecauseThePriorityIsLow() throws Exception {
        mvc.perform(post("/http/test")
                .header("Content-Type", MediaType.ALL_VALUE)
                .header("Accept", HEADER_V1_LOW_QUALITY + ", " + MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().string("v2-post-consumes-all"))
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE));
    }

    @Test
    public void testPostAndHighQualityAcceptHeaderV1ReturnsV1ContentBecauseThePriorityIsHigh() throws Exception {
        mvc.perform(post("/http/test")
                .header("Content-Type", MediaType.ALL_VALUE)
                .header("Accept", HEADER_V1_HIGH_QUALITY + ", " + MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().string("v1-post-consumes-all"));
    }

    @Test
    public void testPostContenTypeOfResponseIfTheOneThatHasTheHigherPriorityInTheAcceptRequestHeader() throws Exception {
        mvc.perform(post("/http/test")
                .header("Content-Type", MediaType.ALL_VALUE)
                .header("Accept", HEADER_V1_HIGH_QUALITY + ", " + MediaType.APPLICATION_JSON_VALUE))
                .andExpect(header().string("Content-Type", "application/vnd.api.v1+json;charset=UTF-8"));
    }

    @Test
    public void testPostContenTypeOfResponseIfTheOneThatHasTheHigherPriorityInTheAcceptRequestHeaderAgain() throws Exception {
        mvc.perform(post("/http/test")
                .header("Content-Type", MediaType.ALL_VALUE)
                .header("Accept", HEADER_V1_LOW_QUALITY + ", " + MediaType.APPLICATION_JSON_VALUE))
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE));
    }

    @Test
    public void testGetContenTypeOfResponseIfTheOneThatHasTheHigherPriorityInTheAcceptRequestHeader() throws Exception {
        mvc.perform(get("/http/test")
                .header("Content-Type", MediaType.ALL_VALUE)
                .header("Accept", HEADER_V1_HIGH_QUALITY + ", " + MediaType.APPLICATION_JSON_VALUE))
                .andExpect(header().string("Content-Type", "application/vnd.api.v1+json;charset=UTF-8"));
    }

    @Test
    public void testGetContenTypeOfResponseIfTheOneThatHasTheHigherPriorityInTheAcceptRequestHeaderAgain() throws Exception {
        mvc.perform(get("/http/test")
                .header("Content-Type", MediaType.ALL_VALUE)
                .header("Accept", HEADER_V1_LOW_QUALITY + ", " + MediaType.APPLICATION_JSON_VALUE))
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE));
    }
}

