/*
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mpalourdio.springboottemplate.service;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Slf4j
public class RSAUtils {

    private static final String TRANSFORMATION = "RSA/ECB/PKCS1Padding";
    private static final String ALGORITHM = "RSA";
    private static final String PRIVATE_KEY_PATH = "-----";
    private static final String PUBLIC_KEY_PATH = "----";
    private static final int KEY_SIZE = 4096;

    static String getBase64EncodedEncryptedString(String data) throws BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IOException, InvalidKeySpecException {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, getPublicKey());

        return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes(StandardCharsets.UTF_8)));
    }

    static String decryptBase64EncodedString(String base64Encoded) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IOException, InvalidKeySpecException {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, getPrivateKey());

        return new String(cipher.doFinal(Base64.getDecoder().decode(base64Encoded.getBytes(StandardCharsets.UTF_8))));
    }

    private static PrivateKey getPrivateKey() throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        byte[] privateKeyBytes = Files.readAllBytes(Paths.get(PRIVATE_KEY_PATH));
        KeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);

        return KeyFactory.getInstance(ALGORITHM).generatePrivate(pkcs8EncodedKeySpec);
    }

    private static PublicKey getPublicKey() throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        byte[] pubKeyBytes = Files.readAllBytes(Paths.get(PUBLIC_KEY_PATH));
        KeySpec x509EncodedKeySpec = new X509EncodedKeySpec(pubKeyBytes);

        return KeyFactory.getInstance(ALGORITHM).generatePublic(x509EncodedKeySpec);
    }

    static void generateKeyPair() throws NoSuchAlgorithmException, IOException {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance(ALGORITHM);
        kpg.initialize(KEY_SIZE);
        KeyPair kp = kpg.generateKeyPair();

        try (FileOutputStream out = new FileOutputStream(PRIVATE_KEY_PATH)) {
            log.info("Private key: {}", Base64.getEncoder().encodeToString(kp.getPrivate().getEncoded()));
            out.write(kp.getPrivate().getEncoded());
        }

        try (FileOutputStream out = new FileOutputStream(PUBLIC_KEY_PATH)) {
            log.info("Public key: {}", Base64.getEncoder().encodeToString(kp.getPublic().getEncoded()));
            out.write(kp.getPublic().getEncoded());
        }
    }
}
