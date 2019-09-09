package com.nd.bigdata.hive.udf;

import com.nd.bigdata.util.PDescHelper;
import org.apache.hadoop.hive.ql.exec.UDF;

/**
 * <p>Description:  解密UDF</p>
 * <p>Copyright: Copyright (c) 2019     </p>
 * <p>Company: ND Co., Ltd.       </p>
 *
 * @author zhuyoulong
 * @date 2019年-08月-28日
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
public class DecUdf extends UDF {

    public String evaluate(String input) throws Exception{
        return PDescHelper.decrypt(input);
    }

    /**
     * 解密
     * @param message
     * @return
     * @throws Exception
     */
    public String decrypt(String message) throws Exception{
        return PDescHelper.decrypt(message);
    }

    public static void main(String[] args) {
        DecUdf d = new DecUdf();
        try {
            System.out.println(d.evaluate("F9024819C0ABB029"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
