package lib.aria2;

import lib.base64.Base64ls;
import okhttp3.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by LESA on 2018/1/14.
 */
public class Arai2_Addurl implements Runnable {
    private String host;
    private String token;
    private String fileurl;
    private static OkHttpClient client = new OkHttpClient();
    private boolean isSSl=false;

    public Arai2_Addurl(String host,String token,String fileurl,boolean isSSL){
        this.host=host;
        this.token=token;
        this.fileurl=fileurl;
        this.isSSl=isSSL;
    }
    @Override
    public void run() {
        String token = this.token;
        String fileurl = this.fileurl;

        String host = this.host;
        String method = "aria2.addUri";


        String params = null;

        JSONArray list_out = new JSONArray();
        list_out.add("token:"+token);
        JSONArray list_tmp = new JSONArray();
        list_tmp.add(fileurl);
        list_out.add(list_tmp);
        list_out.add(new JSONObject());
        String data = JSON.toJSONString(list_out);
        data = Base64ls.getBase64(data);

        try {
            data=java.net.URLEncoder.encode(data,"utf-8");
            params=data;
            //System.out.println(data);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String urls = "http://"+host+"/jsonrpc?method="+method+"&id=qwer&params="+params;
        if (this.isSSl){
            urls="https://"+host+"/jsonrpc?method="+method+"&id=qwer&params="+params;
        }


        //创建一个Request
        Request request = new Request.Builder()
                .get()
                .url(urls)
                .build();
        //通过client发起请求
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    // String str = response.body().string();
                }
                response.close();

            }
        });

    }
}
