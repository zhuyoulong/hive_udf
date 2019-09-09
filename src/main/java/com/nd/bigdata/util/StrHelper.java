package com.nd.bigdata.util;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Description:  配合解密字符串工具类</p>
 * <p>Copyright: Copyright (c) 2017     </p>
 * <p>Company: ND Co., Ltd.       </p>
 *
 * @author zhuyoulong
 * @date 2019/8/28
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
public class StrHelper {
    private StrHelper(){}

    /**
     * 将字符串转成十六进制字节数组
     * 
     * @param str String 字符串
     * @return
     */
    public static byte[] convertHexString(String str) {

        byte digest[] = new byte[str.length() / 2];

        for (int i = 0; i < digest.length; i++) {
            String byteString = str.substring(2 * i, 2 * i + 2);
            int byteValue = Integer.parseInt(byteString, 16);
            digest[i] = (byte) byteValue;
        }

        return digest;
    }

    /**
     * 将十六进制字节数组转成字符串
     * 
     * @param b byte[] 字节数组
     * @return
     */
    public static String toHexString(byte b[]) {

        StringBuffer hexString = new StringBuffer();

        for (int i = 0; i < b.length; i++) {
            String plainText = Integer.toHexString(0xff & b[i]);

            if (plainText.length() < 2)
                plainText = "0" + plainText;

            hexString.append(plainText);
        }

        return hexString.toString();
    }

    /**
     * 获取汉字的拼音首字母
     * @param CNStr String 汉字
     * @return
     */
    // public static String getPYHeader(String CNStr) {
    //
    // String pyHeader = "";
    //
    // for (int i=0; i<CNStr.length(); i++) {
    //
    // char word = CNStr.charAt(i);
    // String py = PinyinHelper.toPinyin(word);
    //
    // if (py != null)
    // pyHeader += py.charAt(0);
    // else
    // pyHeader += word;
    // }
    //
    // return pyHeader;
    // }

    /**
     * 将请求参数是name1=value1&name2=value2的形式的字符串转为键值对
     * 
     * @param paramsString name1=value1&name2=value2形式的请求参数
     * @return
     */
    public static List<NameValuePair> toNameValuePairList(String paramsString) {
        List<NameValuePair> NameValuePairList = new ArrayList<NameValuePair>();
        if (paramsString != null && !paramsString.equals("")) {
            String[] paramsArray = paramsString.split("\\&");
            for (String param : paramsArray) {
                String[] keyValue = param.split("\\=");
                NameValuePairList.add(new BasicNameValuePair(keyValue[0], keyValue.length > 1 ? keyValue[1] : ""));
            }
        }
        return NameValuePairList;
    }

    /**
     * 将键值对转化为name1=value1&name2=value2形式的字符串
     * 
     * @param params
     * @return
     */
    public static String fromNameValuePairList(List<NameValuePair> params) {
        String result = "";

        for (NameValuePair nameValuePair : params) {
            result += nameValuePair.getName() + "=" + nameValuePair.getValue() + "&";
        }
        if (result.endsWith("&"))
            result = result.substring(0, result.length() - 1);
        return result;
    }

    /**
     * 将HTML转义，转换为正确符号
     * 
     * @param htmlStr {@link String} HTML字符串
     * @return
     */
    public static String htmlDecode(String htmlStr) {
        String str = htmlStr;

        str = str.replaceAll("&amp;", "&");
        str = str.replaceAll("&apos;", "'");
        str = str.replaceAll("&apos;", "'");
        str = str.replaceAll("&quot;", "\"");
        str = str.replaceAll("&lt;", "<");
        str = str.replaceAll("&gt;", ">");

        return str;
    }

    public static String HtmlEncode(String str) {
        return StringEscapeUtils.escapeHtml4(str);
    }
}
