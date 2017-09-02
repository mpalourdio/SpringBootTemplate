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
import com.mpalourdio.springboottemplate.json.Account;
import com.mpalourdio.springboottemplate.json.AccountDecorator;
import com.mpalourdio.springboottemplate.json.Context;
import com.mpalourdio.springboottemplate.mediatype.MediaType;
import com.mpalourdio.springboottemplate.service.ToSerialize;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class MiscControllerTest extends AbstractTestRunner {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testSerializationForMvcTests() throws Exception {
        ToSerialize toSerialize = new ToSerialize();
        String input = serializeToJson(toSerialize);

        ToSerialize output = toSerialize.clone();
        output.prop1 = "prop1updated";

        mockMvc.perform(post("/misc/serialization")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(input))
                .andExpect(content().json(serializeToJson(output), true));
    }

    @Test
    public void testJsonUnwrappingInAClassDecorator() throws Exception {

        Account account = new Account();
        account.lastName = "lastName";
        account.firstName = "firstName";

        String input = serializeToJson(account);

        AccountDecorator accountDecorator = new AccountDecorator(account);
        Context context = new Context();
        accountDecorator.context = context;
        context.ref = "ref";

        mockMvc.perform(post("/misc/jsonunwrapped")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(input))
                .andExpect(content().json(serializeToJson(accountDecorator), true));
    }
}
