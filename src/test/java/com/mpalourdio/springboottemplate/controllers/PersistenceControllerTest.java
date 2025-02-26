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
import com.google.gson.reflect.TypeToken;
import com.mpalourdio.springboottemplate.AbstractTestRunner;
import com.mpalourdio.springboottemplate.model.Dummy;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class PersistenceControllerTest extends AbstractTestRunner {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        initializeData();
    }

    @Test
    @Transactional
    void testHydrationWithJsonIgnoreProperty() throws Exception {

        entityManager.persist(task);
        entityManager.persist(people);

        var mvcResult = mvc.perform(get("/hydrate")).andReturn();

        var gson = new Gson();
        var unserializationType = new TypeToken<List<Dummy>>() {
        }.getType();
        List<Dummy> dummyList = gson.fromJson(mvcResult.getResponse().getContentAsString(), unserializationType);

        assertThat(dummyList.get(0).desc).isNull();
    }
}
