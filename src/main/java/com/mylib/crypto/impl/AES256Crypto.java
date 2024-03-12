package com.mylib.crypto.impl;

import com.mylib.crypto.exception.CryptException;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class AES256Crypto {

    private final String aesKey;

    public AES256Crypto(String aesKey) {
        this.aesKey = aesKey;
    }

    public String encrypt(String raw) {
        assertTarget(raw);
        try {
            Cipher cipher = getCipher(Cipher.ENCRYPT_MODE);

            byte[] encrypted = cipher.doFinal(raw.getBytes());

            StringBuilder sb = new StringBuilder(encrypted.length * 2);
            String hexNumber;

            for (byte b : encrypted) {
                hexNumber = "0" + Integer.toHexString(0xff & b);
                sb.append(hexNumber.substring(hexNumber.length() - 2));
            }

            return sb.toString();
        } catch (DecoderException | NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | BadPaddingException | InvalidKeyException e) {
            throw new CryptException("AESHexEncrypt throws exception " + e.getMessage(), e);
        }
    }

    public String decrypt(String encodedText) {
        assertTarget(encodedText);
        try {
            Cipher cipher = getCipher(Cipher.DECRYPT_MODE);

            //16진수 문자열을 byte로 변환
            byte[] byteArray = new byte[encodedText.length() /2 ];

            for(int i=0; i<byteArray.length; i++){
                byteArray[i] = (byte) Integer.parseInt(encodedText.substring(2 * i, 2*i+2), 16);
            }

            byte[] original = cipher.doFinal(byteArray);

            return new String(original);
        } catch (DecoderException | NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException e) {
            throw new CryptException("AESHexDecrypt throws exception " + e.getMessage(), e);
        }
    }

    public void assertTarget(String target) {
        if (target == null || target.isEmpty() || target.isBlank()) {
            throw new CryptException("AESHexEncrypt input raw text is empty(blank) or null");
        }

    }

    private Cipher getCipher(int encryptMode) throws DecoderException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        SecretKeySpec secretKeySpec = createSecretKeySpec();

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(encryptMode, secretKeySpec);
        return cipher;
    }

    private SecretKeySpec createSecretKeySpec() throws DecoderException {
        byte[] keyBytes = Hex.decodeHex(aesKey.toCharArray());

        return new SecretKeySpec(keyBytes, "AES");
    }
}
