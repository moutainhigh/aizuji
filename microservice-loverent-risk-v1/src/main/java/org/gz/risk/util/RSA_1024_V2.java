package org.gz.risk.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Decoder;


public class RSA_1024_V2 {

    private static final Logger logger = LoggerFactory.getLogger(RSA_1024_V2.class);
    private static final String RSA = "RSA";
    private static final String UTF8 = "UTF-8";

    public static final RSAPrivateKey getPrivateKey(final String modulus, final String exponent) {
        try {
            final RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(new BigInteger(modulus), new BigInteger(exponent));
            return (RSAPrivateKey) KeyFactory.getInstance(RSA).generatePrivate(keySpec);
        } catch (Throwable t) {
            logger.error("", t);
            return null;
        }
    }

    public static final String encodeByPrivateKey(final RSAPrivateKey privateKey, final String plainText) {
        try {
            final Cipher cipher = Cipher.getInstance(RSA);
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            return new String(Base64.encodeBase64(cipher.doFinal(plainText.getBytes(UTF8))));
        } catch (Throwable t) {
            logger.error("", t);
            return null;
        }
    }

    public static final String decodeByPrivateKey(final RSAPrivateKey privateKey, final String encodedText) {
        try {
            final Cipher cipher = Cipher.getInstance(RSA);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return new String(cipher.doFinal(Base64.decodeBase64(encodedText)), UTF8);

        } catch (Throwable t) {
            logger.error("", t);
            return null;
        }
    }

    public static final String encodeByPublicKey(final RSAPublicKey publicKey, final String plainText) {
        try {
            final Cipher cipher = Cipher.getInstance(RSA);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return new String(Base64.encodeBase64(cipher.doFinal(plainText.getBytes(UTF8))));
        } catch (Throwable t) {
            logger.error("", t);
            return null;
        }
    }

    public static final String decodeByPublicKey(final RSAPublicKey publicKey, final String encodedText) {
        try {
            final Cipher cipher = Cipher.getInstance(RSA);
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            return new String(cipher.doFinal(Base64.decodeBase64(encodedText)), UTF8);
        } catch (Throwable t) {
            logger.error("", t);
            return null;
        }
    }

    public static final RSAPublicKey gainRSAPublicKeyFromCrtFile(final InputStream crtFileInputStream) {
        RSAPublicKey publicKey = null;
        ObjectInputStream ois = null;

        try {
            ois = new ObjectInputStream(crtFileInputStream);
            publicKey = (RSAPublicKey) ois.readObject();
            ois.close();
        } catch (Throwable t) {
            logger.error("", t);
            ois = null;
        }
        return publicKey;
    }

    public static final RSAPublicKey gainRSAPublicKeyFromCrtFile(final String crtFilePath) {
        RSAPublicKey publicKey = null;
        ObjectInputStream ois = null;

        try {
            final File crtFile = new File(crtFilePath);
            ois = new ObjectInputStream(new FileInputStream(crtFile));
            publicKey = (RSAPublicKey) ois.readObject();
            ois.close();
        } catch (Throwable t) {
            logger.error("", t);
            ois = null;
        }
        return publicKey;
    }

    public static final RSAPublicKey gainRSAPublicKeyFromPemFile(final String pemFilePath) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(pemFilePath));
            String s = br.readLine();
            String str = "";
            s = br.readLine();
            while (s.charAt(0) != '-') {
                str += s + "\r";
                s = br.readLine();
            }
            br.close();

            KeyFactory kf = KeyFactory.getInstance(RSA);
            return (RSAPublicKey) (kf.generatePublic(new X509EncodedKeySpec(new BASE64Decoder().decodeBuffer(str))));
        } catch (Throwable t) {
            logger.error("", t);
            br = null;
            return null;
        }
    }

}
