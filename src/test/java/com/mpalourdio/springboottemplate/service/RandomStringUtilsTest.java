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

import static org.assertj.core.api.Assertions.assertThat;

class RandomStringUtilsTest {

    private static final int LENGTH = 100;

    @Test
    void randomAlphaNumeric() {
        var generated = RandomStringUtils.randomAlphaNumeric(LENGTH);
        assertThat(generated).matches("^[a-z\\d]{" + LENGTH + "}$");
    }

    @Test
    void randomAlphabetic() {
        var generated = RandomStringUtils.randomAlphabetic(LENGTH);
        assertThat(generated).matches("^[a-z]{" + LENGTH + "}$");
    }

    @Test
    void randomNumeric() {
        var generated = RandomStringUtils.randomNumeric(LENGTH);
        assertThat(generated).matches("^\\d{" + LENGTH + "}$");
    }
}
