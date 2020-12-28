package com.skytnt.cn;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;

public class ApiResult {
    public Headers headers;
    public int httpCode,errorCode,msgType;
    public String msg;
    public JSONArray data;

    public ApiResult(Response rsp)
    {

        headers=rsp.headers();
        try {
            String resStr=rsp.body().string();
            JSONObject res=JSONObject.parseObject(resStr);

            JSONObject status=res.getJSONObject("status");

            Object data_p=res.get("data");

            if (data_p instanceof JSONArray)
                data=(JSONArray) data_p;
            else
            {
                data=new JSONArray();
                data.add(data_p);
            }

            //data=res.getJSONObject("data");



            httpCode=status.getInteger("httpCode");
            errorCode=status.getInteger("errorCode");
            msgType=status.getInteger("msgType");
            msg=status.getString("msg");
            if(errorCode!=200)System.out.println("error response:\n"+resStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
