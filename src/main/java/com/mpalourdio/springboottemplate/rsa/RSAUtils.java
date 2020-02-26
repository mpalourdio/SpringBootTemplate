/*
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mpalourdio.springboottemplate.rsa;

import com.mpalourdio.springboottemplate.rsa.exceptions.*;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Base64;

@Slf4j
public class RSAUtils {

    private static final String TRANSFORMATION = "RSA/ECB/PKCS1Padding";
    private static final String ALIAS = "mpalourdio";
    private static final String JAVAX_NET_SSL_KEY_STORE_PASSWORD = ALIAS;
    private static final String JAVAX_NET_SSL_KEY_STORE = "keystore/" + ALIAS + ".p12";

    public static String getBase64EncodedEncryptedString(String toEncrypt) {
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, getPublicKey());

            return Base64.getEncoder().encodeToString(cipher.doFinal(toEncrypt.getBytes(StandardCharsets.UTF_8)));
        } catch (BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException e) {
            log.error("getBase64EncodedEncryptedString error: {}", e.getMessage());
            throw new EncryptException(toEncrypt);
        }
    }

    public static String decryptBase64EncodedString(String base64EncodedToDecrypt) {
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, getPrivateKey());

            return new String(cipher.doFinal(Base64.getDecoder().decode(base64EncodedToDecrypt.getBytes(StandardCharsets.UTF_8))));
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            log.error("decryptBase64EncodedString error: {}", e.getMessage());
            throw new DecryptException();
        }
    }

    private static KeyStore loadKeyStore() {
        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            FileInputStream keystoreFile = new FileInputStream(new File(JAVAX_NET_SSL_KEY_STORE));
            keyStore.load(keystoreFile, JAVAX_NET_SSL_KEY_STORE_PASSWORD.toCharArray());
            return keyStore;
        } catch (CertificateException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
            log.error(e.getMessage());
            throw new KeyStoreLoadException();
        }
    }

    private static PrivateKey getPrivateKey() {
        try {
            KeyStore keyStore = loadKeyStore(); //TODO execute once at class instance to avoid overhead at runtime
            KeyStore.PrivateKeyEntry privateK = (KeyStore.PrivateKeyEntry) keyStore.getEntry(ALIAS, new KeyStore.PasswordProtection(JAVAX_NET_SSL_KEY_STORE_PASSWORD.toCharArray()));
            return privateK.getPrivateKey();
        } catch (NoSuchAlgorithmException | KeyStoreException | UnrecoverableEntryException e) {
            log.error(e.getMessage());
            throw new PrivateKeyException();
        }
    }

    private static PublicKey getPublicKey() {
        try {
            KeyStore keyStore = loadKeyStore(); //TODO execute once at class instance to avoid overhead at runtime
            Certificate cert = keyStore.getCertificate(ALIAS);
            return cert.getPublicKey();
        } catch (KeyStoreException e) {
            log.error(e.getMessage());
            throw new PublicKeyException();
        }
    }
}
