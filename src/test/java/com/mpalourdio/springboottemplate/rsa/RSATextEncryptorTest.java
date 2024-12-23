/*
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mpalourdio.springboottemplate.rsa;

import com.mpalourdio.springboottemplate.rsa.exceptions.DecryptException;
import com.mpalourdio.springboottemplate.rsa.exceptions.EncryptException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RSATextEncryptorTest {

    private TextEncryptor encryptor;

    @BeforeEach
    void setUp() {
        encryptor = new RSATextEncryptor();
    }

    @Test
    void testCanEncrypt() {
        var toEncrypt = "luohkufiuijlkkjfhdgfjlkmkjhgfdghoiukgfhgfdsgfhhliyjhfgdsxbcnvb,;njythdxb nbnjo_iètrgfxbcv bhyèu"
                + "-tyhdfcbvluohkutfiuijlkjfhdgfjlkmkjhgfdghoiukgfhgfdsgfhhliyjhfgdsxbcnvb,;njythdxb nbnjo_iètrgfxbcv bhyèu-"
                + "tyhdfcbvluohkutfiuifgyksryusryufdsgfhhliyjhfgdsxbcnvb,;njythdxb nbnjo_iètrgfxbcv bhyèu-"
                + "tyhdfcbvluohkutfiuijlkjfhdgfjlkmkjhgfdghoiukgfhgfdsgfhhliyjhfgdsxbcnvb,;njythdxb nbnjo_iètrgfxbcv bhyèu-"
                + "tyhdfcbvluohkutfiuijlkjfhdgfjlkmkjhgfdghoiukgfhgfdsgfhhliyjhfgdsxbcnggggggggggggggggg";
        var encrypted = encryptor.encrypt(toEncrypt);
        var decrypted = encryptor.decrypt(encrypted);

        assertThat(decrypted).isEqualTo(toEncrypt);
    }

    @Test
    void testDecryptThrowsException() {
        assertThatThrownBy(() -> encryptor.decrypt("toto")).isInstanceOf(DecryptException.class);
    }

    /**
     * The RSA algorithm can only encrypt data that has a maximum byte length of the RSA key length in bits
     * divided with eight, minus eleven padding bytes, i.e. number of maximum bytes = (key length in bits / 8) - 11.
     * (4096/8) - 11 = 501 bytes max;
     */
    @Test
    void testEncryptThrowsException() {
        assertThatThrownBy(() -> encryptor.encrypt(
                "luohkufiuidfjlkkjfhdgfjlkmkjhgfdghoiukgfhgfdsgfhhliyjhfgdsxbcnvb,;njythdxb nbnjo_iètrgfxbcv bhyèu"
                        + "-tyhdfcbvluohkutfiuijlkjfhdgfjlkmkjhgfdghoiukgfhgfdsgfhhldfiyjhfgdsxbcnvb,;njythdxb nbnjo_iètrgfxbcv bhyèu-"
                        + "tyhdfcbvluohkutfiuifgyksryusryufdsgfhhliyjhfgdsxbdfcnvb,;njythdxb nbnjo_iètrgfxbcvnbnjo_iètrgfxbcvnbnjo_iètrgfxbcvnbn"
                        + "jo_iètrgfxbcvnbnjo_iètrgfxbcv bhyèu-tyhdfcbvluohkutfiuijlkjfhdgfjlkmkjhgfdghoiukgfhdfgfdsgfhhliyjhfgdsxbcnvb,;"
                        + "njythdxb nbnjo_iètrgfxbcv bhyèu-tyhdfcbvluohkutfiuijlkjfhdgfjlkmkjhgfdghoiukgfhdfgfdsgfhhliyjhfgdsxbcnggggggggggggggggg"
        )).isInstanceOf(EncryptException.class);
    }
}
