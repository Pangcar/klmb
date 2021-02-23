package com.auxiliary;



import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class AesHelper {

    private final static String CIPHER_KEY = "AES";
    private final static String encoding = "UTF-8";
    private final static char[] chars={'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};

//    public static void main(String[] args) {
//        //”luolf123456” 加密后的结果为
//        //“N0NDRTZCNUU1NTgzRjYzRjM5ODE3RkU3QzY0OUFCODg=”
//        try {
//            String param = "DX_HW_gdtestly001_2";
//            String aesKey = "7A1396D5BC9A910E17F49DF2F756B570";
//            String result =encrypt(param, aesKey);
//            System.out.println(result);
//            String desRes = decrypt(result, aesKey);
//            System.out.println(desRes);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    /**
     * AES加密
     *
     * @param content
     * @param password
     * @return
     */
    public static String encrypt(String content, String password) {
        byte[] encryptResult = encryptAES(content, password);
        String encryptResultStr = parseByte2HexStr(encryptResult);
        // BASE64位加密
        encryptResultStr = ebotongEncrypto(encryptResultStr);
        return encryptResultStr;
    }

    /**
     * AES解密
     *
     * @param encryptResultStr
     * @param password
     * @return
     */
    public static String decrypt(String encryptResultStr, String password) {
        // BASE64位解密
        String decrpt = ebotongDecrypto(encryptResultStr);
        byte[] decryptFrom = parseHexStr2Byte(decrpt);
        byte[] decryptResult = decrypt(decryptFrom, password);
        return new String(decryptResult);
    }

    /**
     * 加密字符串
     */
    public static String ebotongEncrypto(String str) {
        String result = str;
        if (str != null && str.length() > 0) {
            try {
                byte[] encodeByte = str.getBytes(encoding);
                result = Base64.encodeBase64String(encodeByte);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //base64加密超过一定长度会自动换行 需要去除换行符
        return result.replaceAll("\r\n", "").replaceAll("\r", "").replaceAll("\n", "");
    }

    /**
     * 解密字符串
     */
    public static String ebotongDecrypto(String str) {
        try {
            byte[] encodeByte = Base64.decodeBase64(str);
            return new String(encodeByte);
        } catch (Exception e) {
            e.printStackTrace();
            return str;
        }
    }
    /**
     * 加密
     *
     * @param content 需要加密的内容
     * @param password  加密密码
     * @return
     */
    private static byte[] encryptAES(String content, String password) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            //防止linux下 随机生成key
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(password.getBytes());
            kgen.init(128, secureRandom);
            //kgen.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(byteContent);
            return result; // 加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**解密
     * @param content  待解密内容
     * @param password 解密密钥
     * @return
     */
    private static byte[] decrypt(byte[] content, String password) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            //防止linux下 随机生成key
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );
            secureRandom.setSeed(password.getBytes());
            kgen.init(128, secureRandom);
            //kgen.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(content);
            return result; // 加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**将二进制转换成16进制
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**将16进制转换为二进制
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length()/2];
        for (int i = 0;i< hexStr.length()/2; i++) {
            int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);
            int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    public static String getAESKey(String password) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(password.getBytes("UTF-8"));
        keyGenerator.init(128,random);//要生成多少位，只需要修改这里即可128, 192或256
        SecretKey sk = keyGenerator.generateKey();
        byte[] b = sk.getEncoded();
        return parseByte2HexStr(b);
    }

    private static String getPassword(){
        char[] saltchars=new char[16];
        Random RANDOM = new SecureRandom();
        for(int i=0;i<16;i++)
        {
            int n=RANDOM.nextInt(62);
            saltchars[i]=chars[n];
        }
        String passwd=new String(saltchars);
        return passwd;
    }

    private static String getPassword32(){
        char[] saltchars=new char[32];
        Random RANDOM = new SecureRandom();
        for(int i=0;i<32;i++)
        {
            int n=RANDOM.nextInt(62);
            saltchars[i]=chars[n];
        }
        String passwd=new String(saltchars);
        return passwd;
    }

}

