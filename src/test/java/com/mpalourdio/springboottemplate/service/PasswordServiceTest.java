/*
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mpalourdio.springboottemplate.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class PasswordServiceTest {

    @Test
    public void testCanMakeMatchAGeneratedPassword() {

        PasswordService passwordService = new PasswordService();
        String myawesomepassword = "myawesomepassword";
        String generatedPassword = passwordService.generateEncryptedPasword(myawesomepassword);

        Assert.assertTrue(passwordService.isPasswordValid(myawesomepassword, generatedPassword));
    }

    @Test
    public void testCanEncodeToBase64() {
        PasswordService passwordService = new PasswordService();
        String base64EncodedString = "dG9FbmNvZGU=";
        Assert.assertEquals(base64EncodedString, passwordService.encodeStringToBase64("toEncode"));
    }

    @Test
    public void testCanDecodeBase64() {
        PasswordService passwordService = new PasswordService();
        String base64EncodedString = "dG9FbmNvZGU=";
        Assert.assertEquals("toEncode", passwordService.decodeBase64String(base64EncodedString));
    }

    @Test
    public void testCanGeneratedAPasswordOfPredefinedLength() {
        PasswordService passwordService = new PasswordService();
        int passwordLength = 20;
        String generatedPassword = passwordService.generatedRandomPassword(passwordLength);

        Assert.assertTrue(generatedPassword.length() == passwordLength);
    }
}
