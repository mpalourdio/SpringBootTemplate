/*
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mpalourdio.springboottemplate.controllers;

import com.google.gson.Gson;
import com.mpalourdio.springboottemplate.AbstractTestRunner;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class HomeControllerTest extends AbstractTestRunner {

    @Autowired
    private MockMvc mvc;

    @Test
    void testValueInjectedInConstructor() throws Exception {
        mvc.perform(get("/valueinconstructor"))
                .andExpect(content().string("admin"));
    }

    @Test
    void testPatchWithRestTemplate() throws Exception {
        var mvcResult = mvc.perform(get("/patchwithrestemplate")).andReturn();
        var gson = new Gson();
        var jsonPlaceHolder = gson.fromJson(mvcResult.getResponse().getContentAsString(), HomeController.JsonPlaceHolder.class);

        assertThat("new body").isEqualTo(jsonPlaceHolder.body);
    }

    @Test
    void testPatchWithRestTemplateBuilder() throws Exception {
        var mvcResult = mvc.perform(get("/patchwithrestemplatebuilder")).andReturn();
        var gson = new Gson();
        var jsonPlaceHolder = gson.fromJson(mvcResult.getResponse().getContentAsString(), HomeController.JsonPlaceHolder.class);

        assertThat("new body").isEqualTo(jsonPlaceHolder.body);
    }

    @Test
    void testCanPutWithCsrf() throws Exception {
        mvc.perform(put("/putput").with(csrf())).andExpect(status().isOk());
    }
}
