package com.skytnt.cn;

import com.alibaba.fastjson.JSONObject;

public class UserInfo {
    public int accountId,vipLevel,money;
    public String nickName,avatar;
    public UserInfo (JSONObject jsonObject)
    {

        accountId=jsonObject.getInteger("accountId");
        vipLevel=jsonObject.getJSONObject("expand").getJSONObject("vipInfo").getInteger("level");
        nickName=jsonObject.getString("nickName");
        avatar=jsonObject.getString("avatar");
    }
    public UserInfo()
    {

    }

    public void setMoney(JSONObject jsonObject) {
        money=jsonObject.getInteger("fireMoneyRemain");
    }
}
