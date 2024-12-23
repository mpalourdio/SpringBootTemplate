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

class PasswordServiceTest {

    @Test
    void testCanMakeMatchAGeneratedPassword() {

        var passwordService = new PasswordService();
        var myawesomepassword = "myawesomepassword";
        var generatedPassword = passwordService.generateEncryptedPasword(myawesomepassword);

        assertThat(passwordService.isPasswordValid(myawesomepassword, generatedPassword)).isTrue();
    }

    @Test
    void testCanEncodeToBase64() {
        var passwordService = new PasswordService();
        var base64EncodedString = "dG9FbmNvZGU=";
        assertThat(passwordService.encodeStringToBase64("toEncode")).isEqualTo(base64EncodedString);
    }

    @Test
    void testCanDecodeBase64() {
        var passwordService = new PasswordService();
        var base64EncodedString = "dG9FbmNvZGU=";
        assertThat(passwordService.decodeBase64String(base64EncodedString)).isEqualTo("toEncode");
    }

    @Test
    void testCanGeneratedAPasswordOfPredefinedLength() {
        var passwordService = new PasswordService();
        var passwordLength = 20;
        var generatedPassword = passwordService.generatedRandomPassword(passwordLength);

        assertThat(generatedPassword.length()).isEqualTo(passwordLength);
    }
}
