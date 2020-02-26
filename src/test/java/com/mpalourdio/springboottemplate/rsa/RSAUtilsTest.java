package com.mpalourdio.springboottemplate.rsa;

import com.mpalourdio.springboottemplate.rsa.exceptions.DecryptException;
import com.mpalourdio.springboottemplate.rsa.exceptions.EncryptException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RSAUtilsTest {

    /**
     * The RSA algorithm can only encrypt data that has a maximum byte length of the RSA key length in bits
     * divided with eight minus eleven padding bytes, i.e. number of maximum bytes = key length in bits / 8 - 11.
     * (4096/8) - 11 = 501 bytes max;
     */
    @Test
    void testCanEncrypt() {
        String toEncrypt = "luohkufiuijlkkjfhdgfjlkmkjhgfdghoiukgfhgfdsgfhhliyjhfgdsxbcnvb,;njythdxb nbnjo_iètrgfxbcv bhyèu"
                + "-tyhdfcbvluohkutfiuijlkjfhdgfjlkmkjhgfdghoiukgfhgfdsgfhhliyjhfgdsxbcnvb,;njythdxb nbnjo_iètrgfxbcv bhyèu-"
                + "tyhdfcbvluohkutfiuifgyksryusryufdsgfhhliyjhfgdsxbcnvb,;njythdxb nbnjo_iètrgfxbcv bhyèu-"
                + "tyhdfcbvluohkutfiuijlkjfhdgfjlkmkjhgfdghoiukgfhgfdsgfhhliyjhfgdsxbcnvb,;njythdxb nbnjo_iètrgfxbcv bhyèu-"
                + "tyhdfcbvluohkutfiuijlkjfhdgfjlkmkjhgfdghoiukgfhgfdsgfhhliyjhfgdsxbcnggggggggggggggggg";
        String encrypted = RSAUtils.getBase64EncodedEncryptedString(toEncrypt);
        String decrypted = RSAUtils.decryptBase64EncodedString(encrypted);

        assertEquals(toEncrypt, decrypted);
    }

    @Test
    void testDecryptThrowsException() {
        assertThrows(DecryptException.class, () -> RSAUtils.decryptBase64EncodedString("toto"));
    }

    @Test
    void testEncryptThrowsException() {
        assertThrows(EncryptException.class, () -> RSAUtils.getBase64EncodedEncryptedString(
                "luohkufiuidfjlkkjfhdgfjlkmkjhgfdghoiukgfhgfdsgfhhliyjhfgdsxbcnvb,;njythdxb nbnjo_iètrgfxbcv bhyèu"
                        + "-tyhdfcbvluohkutfiuijlkjfhdgfjlkmkjhgfdghoiukgfhgfdsgfhhldfiyjhfgdsxbcnvb,;njythdxb nbnjo_iètrgfxbcv bhyèu-"
                        + "tyhdfcbvluohkutfiuifgyksryusryufdsgfhhliyjhfgdsxbdfcnvb,;njythdxb nbnjo_iètrgfxbcvnbnjo_iètrgfxbcvnbnjo_iètrgfxbcvnbn"
                        + "jo_iètrgfxbcvnbnjo_iètrgfxbcv bhyèu-tyhdfcbvluohkutfiuijlkjfhdgfjlkmkjhgfdghoiukgfhdfgfdsgfhhliyjhfgdsxbcnvb,;"
                        + "njythdxb nbnjo_iètrgfxbcv bhyèu-tyhdfcbvluohkutfiuijlkjfhdgfjlkmkjhgfdghoiukgfhdfgfdsgfhhliyjhfgdsxbcnggggggggggggggggg"
        ));
    }
}
