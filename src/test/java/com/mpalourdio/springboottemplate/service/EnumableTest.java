/*
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mpalourdio.springboottemplate.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mpalourdio.springboottemplate.model.MyEnum;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class EnumableTest {

    @Test
    public void testCanSerializeWithEnums() throws JsonProcessingException {
        Enumable enumable = new Enumable();
        enumable.myEnum = MyEnum.TOTO;

        Assert.assertEquals(
                "{\"myEnum\":\"tata\"}",
                new ObjectMapper().writeValueAsString(enumable)
        );
    }

    @Test
    public void testCanUnserializeWithEnums() throws IOException {
        Enumable enumable = new ObjectMapper().readValue("{\"myEnum\":\"tata\"}", Enumable.class);

        Assert.assertEquals(MyEnum.TOTO, enumable.myEnum);
    }
}
