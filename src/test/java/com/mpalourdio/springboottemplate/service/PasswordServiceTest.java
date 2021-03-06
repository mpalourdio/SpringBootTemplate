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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PasswordServiceTest {

    @Test
    void testCanMakeMatchAGeneratedPassword() {

        var passwordService = new PasswordService();
        var myawesomepassword = "myawesomepassword";
        var generatedPassword = passwordService.generateEncryptedPasword(myawesomepassword);

        assertTrue(passwordService.isPasswordValid(myawesomepassword, generatedPassword));
    }

    @Test
    void testCanEncodeToBase64() {
        var passwordService = new PasswordService();
        var base64EncodedString = "dG9FbmNvZGU=";
        assertEquals(base64EncodedString, passwordService.encodeStringToBase64("toEncode"));
    }

    @Test
    void testCanDecodeBase64() {
        var passwordService = new PasswordService();
        var base64EncodedString = "dG9FbmNvZGU=";
        assertEquals("toEncode", passwordService.decodeBase64String(base64EncodedString));
    }

    @Test
    void testCanGeneratedAPasswordOfPredefinedLength() {
        var passwordService = new PasswordService();
        var passwordLength = 20;
        var generatedPassword = passwordService.generatedRandomPassword(passwordLength);

        assertEquals(generatedPassword.length(), passwordLength);
    }
}
