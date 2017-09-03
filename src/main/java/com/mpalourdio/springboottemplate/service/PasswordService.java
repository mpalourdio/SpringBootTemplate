/*
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mpalourdio.springboottemplate.service;

import org.apache.commons.text.RandomStringGenerator;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.Base64;

import static org.apache.commons.text.CharacterPredicates.DIGITS;
import static org.apache.commons.text.CharacterPredicates.LETTERS;

public class PasswordService {

    public String generateEncryptedPasword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    public Boolean isPasswordValid(String plainTextPassword, String hash) {
        return BCrypt.checkpw(plainTextPassword, hash);
    }

    public String encodeStringToBase64(String toEncode) {
        return new String(Base64.getEncoder().encode(toEncode.getBytes()));
    }

    public String decodeBase64String(String base64EncodedString) {
        return new String(Base64.getDecoder().decode(base64EncodedString.getBytes()));
    }

    public String generatedRandomPassword(int length) {
        return new RandomStringGenerator.Builder()
                .withinRange('0', 'z')
                .filteredBy(LETTERS, DIGITS)
                .build()
                .generate(length);
    }
}
