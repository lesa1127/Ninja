package lib.base64;

/**
 * Created by LESA on 2018/1/14.
 */



import android.util.Base64;

import java.io.UnsupportedEncodingException;


public class Base64ls {
    // 加密
    public static String getBase64(String str) {
        byte[] b = null;
        String s = null;
        try {
            b = str.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (b != null) {
             s = Base64.encodeToString(b, Base64.DEFAULT);
        }
        return s;
    }

}