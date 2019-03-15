/*
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mpalourdio.springboottemplate.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RandomStringUtilsTest {

    public static final int LENGTH = 100;

    @Test
    public void randomAlphaNumeric() {
        String generated = RandomStringUtils.randomAlphaNumeric(LENGTH);
        assertTrue(generated.matches("^[a-z\\d]{" + LENGTH + "}$"));
    }

    @Test
    public void randomAlphabetic() {
        String generated = RandomStringUtils.randomAlphabetic(LENGTH);
        assertTrue(generated.matches("^[a-z]{" + LENGTH + "}$"));
    }

    @Test
    public void randomNumeric() {
        String generated = RandomStringUtils.randomNumeric(LENGTH);
        assertTrue(generated.matches("^\\d{" + LENGTH + "}$"));
    }
}
