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
public final class RSAEncryptor implements Encryptor {

    private static final String TRANSFORMATION = "RSA/ECB/PKCS1Padding";
    private static final String ALIAS = "mpalourdio";
    private static final String KEY_STORE = "keystore/" + ALIAS + ".p12";
    private static final String KEY_STORE_PASSWORD = ALIAS;
    public static final String KEYSTORE_TYPE = "PKCS12";
    private final Cipher cipher;
    private final KeyStore keyStore;

    public RSAEncryptor() {
        try {
            cipher = Cipher.getInstance(TRANSFORMATION);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new CipherInitException(e.getMessage());
        }
        keyStore = loadKeyStore();
    }

    /**
     * @param text the string to encrypt
     *
     * @return the encoded string
     */
    @Override
    public String encrypt(String text) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, getPublicKey());
            return Base64.getEncoder().encodeToString(cipher.doFinal(text.getBytes(StandardCharsets.UTF_8)));
        } catch (BadPaddingException | IllegalBlockSizeException | InvalidKeyException e) {
            log.error("Unable to encrypt '{}'", text);
            throw new EncryptException(e.getMessage());
        }
    }

    /**
     * @param base64EncodedText the input string, base64 encoded, to decrypt
     *
     * @return the decrypted string
     */
    @Override
    public String decrypt(String base64EncodedText) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, getPrivateKey());
            return new String(cipher.doFinal(Base64.getDecoder().decode(base64EncodedText.getBytes(StandardCharsets.UTF_8))));
        } catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            log.error("Unable to decrypt");
            throw new DecryptException(e.getMessage());
        }
    }

    private KeyStore loadKeyStore() {
        try {
            KeyStore keyStore = KeyStore.getInstance(KEYSTORE_TYPE);
            FileInputStream keystoreFile = new FileInputStream(new File(KEY_STORE));
            keyStore.load(keystoreFile, KEY_STORE_PASSWORD.toCharArray());
            return keyStore;
        } catch (CertificateException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
            log.error("Unable to load the Keystore");
            throw new KeyStoreLoadException(e.getMessage());
        }
    }

    private PrivateKey getPrivateKey() {
        try {
            KeyStore.PrivateKeyEntry privateK = (KeyStore.PrivateKeyEntry) keyStore.getEntry(
                    ALIAS,
                    new KeyStore.PasswordProtection(KEY_STORE_PASSWORD.toCharArray())
            );
            return privateK.getPrivateKey();
        } catch (NoSuchAlgorithmException | KeyStoreException | UnrecoverableEntryException e) {
            log.error("Unable to get the private key");
            throw new PrivateKeyException(e.getMessage());
        }
    }

    private PublicKey getPublicKey() {
        try {
            Certificate cert = keyStore.getCertificate(ALIAS);
            return cert.getPublicKey();
        } catch (KeyStoreException e) {
            log.error("Unable to get the public key");
            throw new PublicKeyException(e.getMessage());
        }
    }
}
