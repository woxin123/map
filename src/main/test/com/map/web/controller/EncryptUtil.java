package com.map.web.controller;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EncryptUtil {
    private static final Log logger = LogFactory.getLog(EncryptUtil.class);

    private static final String SHA = "SHA";
    private static final String SHA1 = "SHA1";
    private static final String MD5 = "MD5";
    private static final String HMAC_SHA1 = "HmacSHA1";

    public static String Encrypt(String algorithm, String source) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance(algorithm);
        } catch (Exception e) {
            logger.debug(e.getMessage());
            return "";
        }
        char[] charArray = source.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];

        byte[] md5Bytes = md5.digest(byteArray);

        StringBuffer hexValue = new StringBuffer();

        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }

        return hexValue.toString();
    }

    /**
     * SHA加密 并转换为16进制大写字符串
     * @param source
     * @return
     */
    public static String encryptSHA(String source)
    {
        try {
            MessageDigest sha = MessageDigest.getInstance(SHA);
            sha.update(source.getBytes());
            byte[] bytes = sha.digest();

            StringBuilder stringBuilder = new StringBuilder("");
            if (bytes == null || bytes.length <= 0) {
                return null;
            }
            for (int i = 0; i < bytes.length; i++) {
                int v = bytes[i] & 0xFF;
                String hv = Integer.toHexString(v);
                if (hv.length() < 2) {
                    stringBuilder.append(0);
                }
                stringBuilder.append(hv);
            }
            return stringBuilder.toString().toUpperCase();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * SHA加密 并转换为16进制大写字符串
     * @param source
     * @return
     */
    public static String encryptSHA1(String source)
    {
        try {
            MessageDigest sha = MessageDigest.getInstance(SHA1);
            sha.update(source.getBytes());
            byte[] bytes = sha.digest();

            StringBuilder stringBuilder = new StringBuilder("");
            if (bytes == null || bytes.length <= 0) {
                return null;
            }
            for (int i = 0; i < bytes.length; i++) {
                int v = bytes[i] & 0xFF;
                String hv = Integer.toHexString(v);
                if (hv.length() < 2) {
                    stringBuilder.append(0);
                }
                stringBuilder.append(hv);
            }
            return stringBuilder.toString().toUpperCase();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * BASE64加密
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(byte[] key) {
        return filter((new BASE64Encoder()).encodeBuffer(key));
    }

    /**
     * BASE64解密
     * @param key
     * @return
     * @throws IOException
     */
    public static byte[] decryptBASE64(String key) throws IOException {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    /**
     * 删除BASE64加密时出现的换行符
     * <功能详细描述>
     * @param str
     * @return
     * @see [类、类#方法、类#成员]
     */
    private static String filter(String str) {
        String output = null;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            int asc = str.charAt(i);
            if (asc != 10 && asc != 13) {
                sb.append(str.subSequence(i, i + 1));
            }
        }
        output = new String(sb);
        return output;
    }

    /**
     * MD5 加密
     */
    public static String getMD5Str(String str) {
        MessageDigest messageDigest = null;

        try {
            messageDigest = MessageDigest.getInstance(MD5);

            messageDigest.reset();

            messageDigest.update(str.getBytes("UTF-8"));

        } catch (NoSuchAlgorithmException e) {
            System.out.println("NoSuchAlgorithmException caught!");
            System.exit(-1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        byte[] byteArray = messageDigest.digest();

        StringBuffer md5StrBuff = new StringBuffer();

        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(
                        Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }

        return md5StrBuff.toString();
    }

    /**
     * 加密
     * @param encData 要加密的数据
     * @param secretKey 密钥 ,16位的数字和字母
     * @param vector 初始化向量,16位的数字和字母
     * @return
     * @throws Exception
     */
    public static String Encrypt(String encData ,String secretKey,String vector) throws Exception {

        if(secretKey == null) {
            return null;
        }
        if(secretKey.length() != 16) {
            return null;
        }
        byte[] raw = secretKey.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// "算法/模式/补码方式"
        IvParameterSpec iv = new IvParameterSpec(vector.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(encData.getBytes());
        return String.valueOf(encrypted);
    }

    /**
     * 生成签名数据
     * @param data 待加密的数据
     * @param key  加密使用的key
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     */
    public static  byte[] getSignature(String data,String key) throws NoSuchAlgorithmException, InvalidKeyException {
        byte[] keyBytes=key.getBytes();
        SecretKeySpec signingKey = new SecretKeySpec(keyBytes, HMAC_SHA1);
        Mac mac = Mac.getInstance(HMAC_SHA1);
        mac.init(signingKey);
        //byte[] rawHmac = mac.doFinal(("GET&"+data).getBytes());
        byte[] rawHmac = mac.doFinal((data).getBytes());
/*        StringBuilder sb=new StringBuilder();
        for(byte b:rawHmac){
         sb.append(byteToHexString(b));
        }  */
        return rawHmac;
    }

    private static String byteToHexString(byte ib){
        char[] Digit={'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
        char[] ob=new char[2];
        ob[0]=Digit[(ib>>>4)& 0X0f];
        ob[1]=Digit[ib & 0X0F];
        String s=new String(ob);
        return s;
    }

    /**
     * 创富md5加密方法
     */
    public static String encode(String encodestr)
    {
        try
        {
            char[] hexDigits = { '9', '0', '1', '4', 'g', '2', 'a', '5', 'p', '6', 'l', 'u', '7', '8', '3', 'e' };
            byte[] strTemp = encodestr.getBytes();
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[(k++)] = hexDigits[(byte0 >>> 4 & 0xF)];
                str[(k++)] = hexDigits[(byte0 & 0xF)];
            }
            return new String(str);
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws UnsupportedEncodingException
    {
        System.out.println(encode("yituke" + "abc"));
        System.out.println(getMD5Str("yituke" + "abc"));

    }
}