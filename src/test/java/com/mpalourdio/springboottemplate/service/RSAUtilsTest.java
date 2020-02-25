package com.mpalourdio.springboottemplate.service;

import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RSAUtilsTest {

    @Test
    void testCanEncrypt() throws IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IOException, InvalidKeySpecException {
       // RSAUtils.generateKeyPair();

        /*
        The RSA algorithm can only encrypt data that has a maximum byte length of the RSA key length in bits
        divided with eight minus eleven padding bytes, i.e. number of maximum bytes = key length in bits / 8 - 11.
        (4096/8) - 11 = 501 bytes max;
         */

        String toEncrypt = "luohkufiuijlkkjfhdgfjlkmkjhgfdghoiukgfhgfdsgfhhliyjhfgdsxbcnvb,;njythdxb nbnjo_iètrgfxbcv bhyèu-tyhdfcbvluohkutfiuijlkjfhdgfjlkmkjhgfdghoiukgfhgfdsgfhhliyjhfgdsxbcnvb,;njythdxb nbnjo_iètrgfxbcv bhyèu-tyhdfcbvluohkutfiuijlkjfhdgfjlkmkjhgfdghoiukgfhgfdsgfhhliyjhfgdsxbcnvb,;njythdxb nbnjo_iètrgfxbcv bhyèu-tyhdfcbvluohkutfiuijlkjfhdgfjlkmkjhgfdghoiukgfhgfdsgfhhliyjhfgdsxbcnvb,;njythdxb nbnjo_iètrgfxbcv bhyèu-tyhdfcbvluohkutfiuijlkjfhdgfjlkmkjhgfdghoiukgfhgfdsgfhhliyjhfgdsxbcnggggggggggggggggg";
        String encrypted = RSAUtils.getBase64EncodedEncryptedString(toEncrypt);
        String decrypted = RSAUtils.decryptBase64EncodedString(encrypted);

        assertEquals(toEncrypt, decrypted);
    }
}
