package org.gz.risk.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RC4_128_V2 {

    private static final Logger logger = LoggerFactory.getLogger(RC4_128_V2.class);
    private static final String RC4 = "RC4";
    private static final String UTF8 = "UTF-8";

    public static final String encode(final String plainText, final String rc4Key) {
        try {
            final Cipher c1 = Cipher.getInstance(RC4);
            c1.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(rc4Key.getBytes(), RC4));
            return new String(Base64.encodeBase64(c1.doFinal(plainText.getBytes(UTF8))));
        } catch (Throwable t) {
            logger.error("", t);
            return null;
        }
    }

    public static final String decode(final String encodedText, final String rc4Key) {
        try {
            final Cipher c1 = Cipher.getInstance(RC4);
            c1.init(Cipher.DECRYPT_MODE, new SecretKeySpec(rc4Key.getBytes(), RC4));
            return new String(c1.doFinal(Base64.decodeBase64(encodedText.getBytes())), UTF8);
        } catch (Throwable t) {
            logger.error("Throwable", t);
            return null;
        }
    }

}
