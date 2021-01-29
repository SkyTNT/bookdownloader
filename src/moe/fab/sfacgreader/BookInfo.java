package moe.fab.sfacgreader;

import com.alibaba.fastjson.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

public class BookInfo {
    public int charCount,authorId,novelId,markCount,lastViewChap,lastDownloadChap;
    public BigDecimal point;
    public String authorName,novelName,signStatus,bgBanner,novelCover,intro,lastUpdateTime,tags,sysTags,lastChap,lastViewTime;
    public boolean isFinish,isDownloaded;
    public HashMap<Integer,Integer> volumesOrder,chaptersOrder;
    public ArrayList<Volume> volumes;
    public ArrayList<ChapterInfo> chapters;

    public BookInfo(JSONObject jsonObject)
    {
        for (String k:jsonObject.keySet())
        {
            try {
                getClass().getField(k).set(this,jsonObject.get(k));
            }catch (Exception e)
            {
            }
        }

        lastViewChap=-1;
        lastDownloadChap=-1;
        isDownloaded=false;

    }
    public void setDetail(JSONObject jsonObject)
    {
        JSONObject expand=jsonObject.getJSONObject("expand");
        intro=expand.getString("intro");
        JSONArray tagArray=expand.getJSONArray("tags");
        tags="";
        sysTags="";
        for (Object tag:tagArray)
        {
            tags+=tag.toString()+"/";
        }
        JSONArray sysTagArray=expand.getJSONArray("sysTags");
        for (Object sysTag:sysTagArray)
        {
            sysTags+=((JSONObject)sysTag).getString("tagName")+"/";
        }
        JSONObject j=expand.getJSONObject("latestChapter");
        if(j!=null)lastChap=j.getString("title");
    }

    public void setChapters(JSONObject jsonObject)
    {
        volumes=new ArrayList<>();
        chapters=new ArrayList<>();
        volumesOrder=new HashMap<>();
        chaptersOrder=new HashMap<>();
        int order=0;
        for (Object object:jsonObject.getJSONArray("volumeList"))
        {
            JSONObject jsonObj=(JSONObject)object;
            order++;
            Volume volume=new Volume(jsonObj);
            volume.volumeOrder=order;
            volumes.add(volume);
            volumesOrder.put(volume.volumeId,order);

            for (Object object2:jsonObj.getJSONArray("chapterList"))
                chapters.add(new ChapterInfo((JSONObject)object2));

        }
        int co=0;
        for (ChapterInfo c:chapters)
        {
            c.chapOrder=++co;
            chaptersOrder.put(c.chapId,co);
        }
        if (lastViewChap==-1)
        {
            lastViewChap=chapters.get(0).chapId;
        }

    }

    public void saveReadInfo(int lastChap)
    {
        Settings settings=new Settings("books.txt");
        JSONObject jsonObject=settings.getJSONObject(novelId+"");
        if (jsonObject==null)jsonObject=new JSONObject();
        jsonObject.put("lastReadChap",lastChap);
        jsonObject.put("lastReadTime",Util.getDate());
        settings.put(novelId+"",jsonObject);
        settings.saveSettings();
        lastViewChap=lastChap;
    }
    public void saveDownloadInfo(int lastChap)
    {
        Settings settings=new Settings("books.txt");
        JSONObject jsonObject=settings.getJSONObject(novelId+"");
        if (jsonObject==null)jsonObject=new JSONObject();
        jsonObject.put("lastDownloadChap",lastChap);
        settings.put(novelId+"",jsonObject);
        settings.saveSettings();
        lastDownloadChap=lastChap;
    }

    public  void  setIsDownloaded(boolean val)
    {
        isDownloaded=val;
        Settings settings=new Settings("books.txt");
        JSONObject jsonObject=settings.getJSONObject(novelId+"");
        if (jsonObject==null)jsonObject=new JSONObject();
        jsonObject.put("isDownloaded",isDownloaded);
        settings.put(novelId+"",jsonObject);
        settings.saveSettings();
        settings.saveSettings();
    }


    public void writeChapToFile(int chapId)
    {
        int co=chaptersOrder.get(chapId);
        ChapterInfo chapterInfo=chapters.get(co-1);
        int vo=volumesOrder.get(chapterInfo.volumeId);
        Volume volume=volumes.get(vo-1);
        Util.writeFile(novelName+"/tmp/"+String.format("%03d",vo)+" "+volume.title+"/"+String.format("%04d",co)+" "+chapterInfo.title+".txt",chapterInfo.content);
        saveDownloadInfo(chapId);
    }

    public void writeChapToFile(ChapterInfo chapterInfo)
    {
        int vo=volumesOrder.get(chapterInfo.volumeId);
        Volume volume=volumes.get(vo-1);
        Util.writeFile(novelName+"/tmp/"+String.format("%03d",vo)+" "+volume.title+"/"+String.format("%04d",chapterInfo.chapOrder)+" "+chapterInfo.title+".txt",chapterInfo.content);
        saveDownloadInfo(chapterInfo.chapId);
    }


    public void downloadBook(Api api,int startChap,DownloadListener listener)
    {
        if (chapters==null)api.updateBookChapters(this);
        if (startChap==-1)startChap=chapters.get(0).chapId;
        ChapterInfo chapterInfo=api.getChapterDetail(startChap);
        int total=0,progress=0;
        total=chapters.size()-chapterInfo.chapOrder+1;

        while (chapterInfo!=null)
        {
            api.getChapterDetail(chapterInfo.chapId);
            writeChapToFile(chapterInfo);
            progress++;
            listener.onProgressChanged(progress,total);
            chapterInfo=getNextChap(chapterInfo);
        }

        /*File tmp=new File(novelName+"/tmp/");
        for (File vf:tmp.listFiles())
        {
            if (vf.isDirectory())
            {
                String str="";
                for (File cf: vf.listFiles())
                    if (cf.isFile())
                    {
                        str+=cf.getName()+"\n"+Util.readFile(cf.getPath());
                    }
                Util.writeFile(vf.getParent()+"/"+vf.getName()+".txt",str);
            }

        }*/
    }

    public ChapterInfo getNextChap(ChapterInfo chapterInfo)
    {
        if (chapterInfo.chapOrder<chapters.size())
            return chapters.get(chapterInfo.chapOrder);
        return null;
    }


    public static class Volume
    {
        public int volumeId,volumeOrder;
        public String title;
        public Volume(JSONObject jsonObject)
        {
            volumeId=jsonObject.getInteger("volumeId");
            title=jsonObject.getString("title");
        }
    }

    public static class ChapterInfo
    {
        public int chapId,novelId,volumeId,chapOrder,needFireMoney;
        public String title,content;
        public boolean isVip;
        public HashMap<Integer,Integer>tsukkomiCount;
        public ChapterInfo(JSONObject jsonObject)
        {
            tsukkomiCount=new HashMap<>();
            for (String k:jsonObject.keySet())
            {
                try {
                    getClass().getField(k).set(this,jsonObject.get(k));
                }catch (Exception e)
                {
                }
            }
            JSONObject expand=jsonObject.getJSONObject("expand");
            if (expand!=null){
                content=expand.getString("content");
                JSONArray tsukkomiArray=expand.getJSONArray("tsukkomi");
                for (Object tsukkomi:tsukkomiArray)
                    if (tsukkomi instanceof JSONObject)
                    {
                        int count=((JSONObject)tsukkomi).getIntValue("count");
                        int row=((JSONObject)tsukkomi).getIntValue("row");
                        tsukkomiCount.put(row,count);
                    }
            }

        }


    }

    public static class Tsukkomi
    {
        int count;
        ArrayList<UserInfo>users;
        ArrayList<String>dates,contents;
        ArrayList<Integer>favNums,replyNums;
        public Tsukkomi(JSONArray jsonArray)
        {
            count=jsonArray.size();
            users=new ArrayList<>();
            dates=new ArrayList<>();
            contents=new ArrayList<>();
            favNums=new ArrayList<>();
            replyNums=new ArrayList<>();
            for (Object obj1:jsonArray)
                if (obj1 instanceof JSONObject)
                {
                    JSONObject jsonObject=((JSONObject) obj1);

                    favNums.add(jsonObject.getInteger("favNum"));
                    replyNums.add(jsonObject.getInteger("replyNum"));
                    dates.add(jsonObject.getString("postDate"));
                    contents.add(jsonObject.getString("content"));
                    JSONObject userJson=jsonObject.getJSONObject("postUser");
                    JSONObject expand=userJson.getJSONObject("expand");
                    UserInfo userInfo=new UserInfo();
                    userInfo.accountId=userJson.getInteger("accountId");
                    userInfo.vipLevel=expand.getInteger("vipLevel");
                    userInfo.avatar=expand.getString("avatar");
                    userInfo.nickName=userJson.getString("nickName");
                    users.add(userInfo);
                }

        }

    }



}
