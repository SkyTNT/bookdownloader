package com.skytnt.cn;

import com.alibaba.fastjson.*;

public class Settings extends JSONObject {
    private String path;

    public Settings(String path)
    {
        super();
        this.path=path;
        String settings_str=Util.readFile(path);
        if (settings_str!=null&&!settings_str.equals(""))
        {
            JSONObject jsonObject=JSONObject.parseObject(settings_str);
            if (jsonObject!=null)putAll(jsonObject.getInnerMap());
        }

    }

    public void saveSettings()
    {
        Util.writeFile(path,toJSONString());
    }
}
