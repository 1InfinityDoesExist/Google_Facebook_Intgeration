package com.fb.demo.utility;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class EncryptDecryptUtils {
    public static String encrypt(String plainText, String cipherTransformation,
                    String characterEncoding, String aesEncryptionAlgorithm, String encryptionKey) {
        String encryptedText = "";
        try {
            Cipher cipher = Cipher.getInstance(cipherTransformation);
            byte[] key = encryptionKey.getBytes(characterEncoding);
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, aesEncryptionAlgorithm);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(key);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] cipherText = cipher.doFinal(plainText.getBytes(characterEncoding));
            Base64.Encoder encoder = Base64.getEncoder();
            encryptedText = encoder.encodeToString(cipherText);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException
                        | UnsupportedEncodingException | InvalidKeyException
                        | InvalidAlgorithmParameterException | IllegalBlockSizeException
                        | BadPaddingException e) {
            e.printStackTrace();
        }
        log.info(":::::EncryptedcharsetText {}", encryptedText);
        return encryptedText;
    }

    public static String decrypt(String encryptedText, String cipherTransformation,
                    String characterEncoding, String aesEncryptionAlgorithm, String encryptionKey) {
        String plainText = "";
        try {
            Cipher cipher = Cipher.getInstance(cipherTransformation);
            byte[] key = encryptionKey.getBytes(characterEncoding);
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, aesEncryptionAlgorithm);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(key);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] cipherText = decoder.decode(encryptedText.getBytes(characterEncoding));
            plainText = new String(cipher.doFinal(cipherText), characterEncoding);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException
                        | UnsupportedEncodingException | InvalidKeyException
                        | InvalidAlgorithmParameterException | IllegalBlockSizeException
                        | BadPaddingException e) {
            e.printStackTrace();
        }
        log.info(":::::PlainText {}", plainText);
        return plainText;
    }

}
