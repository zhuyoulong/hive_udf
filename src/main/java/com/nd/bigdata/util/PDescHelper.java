/* =============================================================
 * Created: [2016年3月22日] by wanghuan
 * =============================================================
 *
 * Copyright 2014-2015 NetDragon Websoft Inc. All Rights Reserved
 *
 * =============================================================
 */

package com.nd.bigdata.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.MessageDigest;

/**
 * <p>Description:  解密</p>
 * <p>Copyright: Copyright (c) 2017     </p>
 * <p>Company: ND Co., Ltd.       </p>
 *
 * @author zhuyoulong
 * @date 2019/8/25
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
public class PDescHelper {

    /**
     * ERP系统加密方法(专用)
     * 
     * @param message 明文
     * @return 密文
     */
    public static String encrypt(String message) throws Exception {

        String key = "$[f,3/fg";

        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

        byte[] bytesrc = cipher.doFinal(message.getBytes("UTF-8"));
        return StrHelper.toHexString(bytesrc);
    }

    /**
     * ERP系统解密方法(专用)
     * 
     * @param message 密文
     * @return 明文
     */
    public static String decrypt(String message) throws Exception {

        String key = "$[f,3/fg";

        byte[] bytesrc = StrHelper.convertHexString(message);
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);

        byte[] retByte = cipher.doFinal(bytesrc);
        return new String(retByte,"GBK");
    }

    public static void main(String[] args) throws Exception {
//        System.out.println(decrypt("80fba977d063a6f7262a8a9c95f61140"));
        System.out.println(decrypt("4FD7885DFA3CD0B9"));
        System.out.println(decrypt("6B941FAD63275C7F"));
    }

    /**
     * ERP系统登录密码MD5加密(专用)
     * 
     * @param message 明文
     * @return 密文
     */
    public static String ERP_MD5encrypt(String message) throws Exception {

        String pwd = message + "\uff0c\u3002fdjf,jkgfkl";
        MessageDigest digest = MessageDigest.getInstance("MD5");
        digest.update(pwd.getBytes("GB2312"));
        byte messageDigest[] = digest.digest();

        return StrHelper.toHexString(messageDigest);
    }

    /**
     * MD5加密
     * 
     * @param message 明文
     * @return 密文
     */
    public static String MD5encrypt(String message) throws Exception {

        MessageDigest digest = MessageDigest.getInstance("MD5");
        digest.update(message.getBytes());
        byte messageDigest[] = digest.digest();

        return StrHelper.toHexString(messageDigest);
    }

    private static int hexToInt(String str, int start, int end) {
        long res = 0;
        for (int i = start; i < end; i++) {
            res = res * 16 + Integer.parseInt(str.substring(i, i + 1), 16);
        }
        return (int) res;
    }

    public static String VCDecrypt(String sbuf) {
        int num;
        byte[] bytes = new byte[0x3e8];
        char[] chArray = "12ASD9ASDFJE3489JJee12".toCharArray();
        char[] chArray2 = new char[0x3e8];
        byte[] buffer2 = new byte[0x3e8];
        for (num = 0; num < 0x3e8; num++) {
            chArray2[num] = '\0';
            buffer2[num] = 0;
            bytes[num] = 0;
        }
        chArray2 = sbuf.toCharArray();
        int count = sbuf.length() / 2;
        for (num = 0; num < count; num++) {
            buffer2[0] = (byte) chArray2[num * 2];
            buffer2[1] = (byte) chArray2[(num * 2) + 1];
            buffer2[2] = 0;
            String s = new String(buffer2);
            bytes[num] = (byte) hexToInt(s, 0, 2);
            chArray2[num] = (char) bytes[num];
            bytes[num] = 0;
        }
        int index = 0;
        for (num = 0; num < count; num++) {
            bytes[num] = (byte) (chArray2[num] ^ chArray[index]);
            if (index >= chArray.length) {
                index = 0;
            }
        }
        byte[] result = new byte[num];
        for(int i=0;i<=num-1;i++){
            result[i]=bytes[i];
        }
        return new String(result);
    }

    public static String VCEncrypt(String sbuf) {
        int num;
        byte[] bytes = new byte[0x3e8];
        char[] chArray = "12ASD9ASDFJE3489JJee12".toCharArray();
        char[] chArray2 = new char[0x3e8];
        char[] chArray3 = new char[0x3e8];
        for (num = 0; num < 0x3e8; num++) {
            chArray2[num] = '\0';
            chArray3[num] = '\0';
            bytes[num] = 0;
        }
        chArray2 = sbuf.toCharArray();
        int index = 0;
        int length = sbuf.length();
        for (num = 0; num < length; num++) {
            chArray3[num] = (char) (chArray2[num] ^ chArray[index]);
            if (index >= chArray.length) {
                index = 0;
            }
        }
        for (num = 0; num < length; num++) {
            int num4 = chArray3[num];
            char[] chArray4 = Long.toHexString(num4).toCharArray();
            bytes[num * 2] = (byte) chArray4[0];
            bytes[(num * 2) + 1] = (byte) chArray4[1];
        }
        bytes[length * 2] = 0;
        bytes[(length * 2) + 1] = 0;


        byte[] result = new byte[num];
        for(int i=0;i<=num-1;i++){
            result[i]=bytes[i];
        }
        return new String(result);
    }

}
