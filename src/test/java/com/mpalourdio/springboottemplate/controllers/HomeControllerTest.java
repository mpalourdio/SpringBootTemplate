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
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class HomeControllerTest extends AbstractTestRunner {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testValueInjectedInConstructor() throws Exception {
        mvc.perform(get("/valueinconstructor"))
                .andExpect(content().string("admin"));
    }

    @Test
    public void testPatchWithRestTemplate() throws Exception {
        MvcResult mvcResult = mvc.perform(get("/patchwithrestemplate")).andReturn();
        Gson gson = new Gson();
        HomeController.JsonPlaceHolder jsonPlaceHolder = gson.fromJson(mvcResult.getResponse().getContentAsString(), HomeController.JsonPlaceHolder.class);

        Assert.assertTrue(jsonPlaceHolder.body.equals("new body"));
    }

    @Test
    public void testPatchWithRestTemplateBuilder() throws Exception {
        MvcResult mvcResult = mvc.perform(get("/patchwithrestemplatebuilder")).andReturn();
        Gson gson = new Gson();
        HomeController.JsonPlaceHolder jsonPlaceHolder = gson.fromJson(mvcResult.getResponse().getContentAsString(), HomeController.JsonPlaceHolder.class);

        Assert.assertTrue(jsonPlaceHolder.body.equals("new body"));
    }
}
