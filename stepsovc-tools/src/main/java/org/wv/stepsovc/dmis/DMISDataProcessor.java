package org.wv.stepsovc.dmis;

import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.generators.PKCS5S1ParametersGenerator;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Component;
import org.wv.stepsovc.crypto.PasswordDeriveBytes;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;

@Component
public class DMISDataProcessor {

    public static final String PASSWORD = "STEPSOVC";
    public static final String PADDING = "AES/CBC/PKCS7Padding";
    public static final String ALGORITHM = "AES";
    public static final String UTF_8 = "UTF-8";
    private String SALT_STR = "Kosher";
    private String INITIAL_VECTOR_STR = "#FZ/Pv%mXYB[mQq<";

    public String decrypt(String cipherStr) {
        String decryptedString;
        try {
            byte[] password = processKey(PASSWORD).getBytes(UTF_8);

            final byte[] salt = SALT_STR.getBytes();
            final byte[] iv = INITIAL_VECTOR_STR.getBytes();
            Security.addProvider(new BouncyCastleProvider());
            final SHA1Digest sha1Digest = new SHA1Digest();
            PKCS5S1ParametersGenerator generator = new PasswordDeriveBytes(sha1Digest);
            generator.init(password, salt, 2);

            byte[] key = ((KeyParameter)
                    generator.generateDerivedParameters(32 * 8)).getKey();

            //"MyBzxx8aq4u9AxqKEmE69nWq+0g2TAfroRQGuLd6QWY="
            final BASE64Decoder base64Decoder = new BASE64Decoder();
            Cipher cipher = Cipher.getInstance(PADDING);
            SecretKey secret = new SecretKeySpec(key, ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(iv));
            byte[] bytes = cipher.doFinal(base64Decoder.decodeBuffer(cipherStr));
            decryptedString = new String(bytes, UTF_8);
        } catch (Exception e) {
            decryptedString = cipherStr;
            e.printStackTrace();
        }
        return decryptedString;
    }

    public String encrypt(String plainStr) {
        String encryptedString;
        try {
            final byte[] password = processKey(PASSWORD).getBytes(UTF_8);
            final byte[] salt = SALT_STR.getBytes();
            final byte[] iv = INITIAL_VECTOR_STR.getBytes();
            Security.addProvider(new BouncyCastleProvider());
            final SHA1Digest sha1Digest = new SHA1Digest();
            PKCS5S1ParametersGenerator generator = new PasswordDeriveBytes(sha1Digest);
            generator.init(password, salt, 2);

            byte[] key = ((KeyParameter)
                    generator.generateDerivedParameters(32 * 8)).getKey();

            //"MyBzxx8aq4u9AxqKEmE69nWq+0g2TAfroRQGuLd6QWY="
            final BASE64Decoder base64Decoder = new BASE64Decoder();
            final BASE64Encoder base64Encoder = new BASE64Encoder();
            Cipher cipher = Cipher.getInstance(PADDING);
            SecretKey secret = new SecretKeySpec(key, ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secret, new IvParameterSpec(iv));
            final byte[] bytes = cipher.doFinal(plainStr.getBytes(UTF_8));
            encryptedString = base64Encoder.encode(bytes);
        } catch (Exception e) {
            encryptedString = plainStr;
            e.printStackTrace();
        }
        return encryptedString;
    }

    private String processKey(String strKey) {
        return strKey.replaceAll("o", "0").replaceAll("O", "0").replaceAll("l", "1").replaceAll("L", "1").replaceAll("e", "3").replaceAll("E", "3").replaceAll("a", "4").replaceAll("A", "4").replaceAll("s", "5").replaceAll("S", "5");
    }

}

