package com.skytnt.cn;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import javax.net.ssl.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Api {
    private OkHttpClient client;
    private Settings settings;
    private AccountApi accountApi=null;
    private PocketApi pocketApi=null;
    private NovelApi novelApi=null;
    private TsukkomiApi tsukkomiApi=null;
    private String cookies;

    private UserInfo userInfo;
    private ArrayList<BookInfo> bookInfos;
    private HashMap<Integer,Integer> bookInfoIndex;
    private Random random;

     public Api(Settings settings)
    {
        this.settings=settings;
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        client= builder
                .sslSocketFactory(RxUtils.createSSLSocketFactory())
                .hostnameVerifier(new RxUtils.TrustAllHostnameVerifier())
                .retryOnConnectionFailure(true)
                .readTimeout(10,TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS).build();
        cookies=settings.getString("cookies");
        if (cookies==null)cookies="";
        random=new Random();
        
    }

    private ApiResult invokeApiMethod(ApiMethod method)
    {
        return new ApiResult(sendData(method.httpMethod,method.path,method.requestBody));
    }

    private  <T> T getApi(Class<T>cls)
    {
        return (T)Proxy.newProxyInstance(cls.getClassLoader(), new Class[]{cls}, (proxy, method, args) -> invokeApiMethod(new ApiMethod(method,args)));
    }
    public AccountApi getAccountApi()
    {
        if (accountApi==null)
            accountApi=getApi(AccountApi.class);
        return accountApi;
    }
    public PocketApi getPocketApi()
    {
        if (pocketApi==null)
            pocketApi=getApi(PocketApi.class);
        return pocketApi;
    }
    public NovelApi getNovelApi()
    {
        if (novelApi==null)
            novelApi=getApi(NovelApi.class);
        return novelApi;
    }

    public TsukkomiApi getTsukkomiApi() {
        if (tsukkomiApi==null)
            tsukkomiApi=getApi(TsukkomiApi.class);
        return tsukkomiApi;
    }

    public Response sendData(String method, String path, String requestBodyStr)
    {
        Request.Builder requestBuilder =new Request.Builder()

                .url("https://api.sfacg.com/"+path)
                .addHeader("Accept-Charset","UTF-8")
                .addHeader("Authorization"," Basic YW5kcm9pZHVzZXI6MWEjJDUxLXl0Njk7KkFjdkBxeHE=")
                .addHeader("Accept"," application/vnd.sfacg.api+json;version=1")
                .addHeader("User-Agent"," boluobao/4.3.20(android;22)/HomePage")
                .addHeader("SFSecurity",getSFSecurity());

        if (!cookies.equals(""))
            requestBuilder=requestBuilder.addHeader("Cookie",cookies);

        RequestBody requestBody=RequestBody.create(MediaType.parse("application/json; charset=utf-8"),requestBodyStr);


        requestBuilder.method(method,method.matches("GET|HEAD")?null:requestBody);
        try {
            Request request=requestBuilder.build();
            //System.out.println(request.headers());
            return client.newCall(request).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("send data error");
        return null;
    }

    private static String getSFSecurity() {
        String toUpperCase = UUID.randomUUID().toString().toUpperCase();
        String toUpperCase2 = "AAC3B586-D131-32DE-942C-F5CCED55B45E";
        String valueOf =String.valueOf(new Date().getTime());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(toUpperCase);
        stringBuilder.append(valueOf);
        stringBuilder.append(toUpperCase2);
        stringBuilder.append("xw3#a12-x");
        String toUpperCase3 = Util.md5(String.valueOf(stringBuilder.toString())).toUpperCase();
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("nonce=");
        stringBuilder2.append(toUpperCase);
        stringBuilder2.append("&timestamp=");
        stringBuilder2.append(valueOf);
        stringBuilder2.append("&devicetoken=");
        stringBuilder2.append(toUpperCase2);
        stringBuilder2.append("&sign=");
        stringBuilder2.append(toUpperCase3);
        return stringBuilder2.toString();
    }


    public boolean isLogin()
    {
        return !(cookies==null||cookies.equals("")||!updateUserInfo());
    }

    public ApiResult login(String user,String pwd)
    {
        HashMap<String,Object> hashMap= new HashMap<>();
        hashMap.put("username",user);
        hashMap.put("password",pwd);
        ApiResult result=getAccountApi().login(hashMap);
        List<Cookie> cs=Cookie.parseAll(HttpUrl.parse("https://api.sfacg.com/sessions"),result.headers);
        cookies="";
        for (int i=0;i<cs.size();i++)
        {
            cookies+=cs.get(i).name()+"="+cs.get(i).value();
            if (i<cs.size()-1)cookies+=";";
        }
        System.out.println(cookies);
        settings.put("cookies",cookies);
        settings.saveSettings();

        return result;
    }

    public UserInfo getUserInfo()
    {
        if(userInfo==null)
            updateUserInfo();
        return userInfo;
    }
    public boolean updateUserInfo()
    {
        ApiResult result=getAccountApi().getUser("vipInfo,welfareCoin,welfareMoney,redpacketCode,useWelfaresys,usedRedpacketCode");
        if (result.httpCode!=200)
            return false;
        userInfo=new UserInfo(result.data.getJSONObject(0));

        return true;
    }
    public void updateMoney()
    {
        userInfo.setMoney(getAccountApi().getUserMoney().data.getJSONObject(0));
    }

    public ArrayList<BookInfo> getBooks()
    {
        if (bookInfos==null)
            updateBooks();

        return bookInfos;
    }

    public ArrayList<BookInfo> getDownloadedBooks()
    {
        ArrayList<BookInfo> result=new ArrayList<>();
        for (BookInfo bookInfo:getBooks())
            if (bookInfo.isDownloaded)result.add(bookInfo);
        return result;
    }

    public ArrayList<BookInfo> getRecommendBooks()
    {
        if (bookInfos.size()==0)return new ArrayList<>();

        ArrayList<BookInfo> result=new ArrayList<>();
        for (Object o:getNovelApi().getPersonalizedRecommendNovel(bookInfos.get(random.nextInt(bookInfos.size())).novelId,false,0,0,8,"intro,tags,sysTags","").data)
        {
            if (o instanceof JSONObject)
            {
                BookInfo bookInfo=new BookInfo((JSONObject) o);
                bookInfo.setDetail((JSONObject) o);
                result.add(bookInfo);
            }

        }
        return result;
    }

    public void updateBooks()
    {
        bookInfoIndex=new HashMap<>();
        bookInfos=new ArrayList<>();
        HashMap<Integer,Integer> chapViews=new HashMap<>();
        HashMap<Integer,String> viewTimes=new HashMap<>();


        JSONArray jsonArray1=null;
        try {
            jsonArray1=getNovelApi().getUserNovelView().data;
        }catch (Exception e)
        {
            jsonArray1=getNovelApi().getUserNovelView().data;
        }

        for (Object o:jsonArray1)
            if (o instanceof JSONObject)
            {
                JSONObject jsonObject=(JSONObject) o;
                int novelId=jsonObject.getInteger("novelId");
                chapViews.put(novelId,jsonObject.getInteger("chapterId"));
                viewTimes.put(novelId,jsonObject.getString("lastViewTime"));
            }
        JSONArray ja1=getPocketApi().getUserPockets("novels,albums,comics,discount,discountExpireDate").data;

        for (Object o1:ja1)
            if ((o1 instanceof JSONObject)){
                if (((JSONObject)o1).getJSONObject("expand").getJSONArray("novels")==null)
                    continue;
                for (Object o:((JSONObject)o1).getJSONObject("expand").getJSONArray("novels"))
                    if (o instanceof JSONObject)
                    {
                        BookInfo bookInfo=new BookInfo((JSONObject) o);
                        Integer lastViewChap=chapViews.get(bookInfo.novelId);
                        if (lastViewChap!=null)
                        {
                            if (lastViewChap==0)lastViewChap=-1;
                            bookInfo.lastViewChap=lastViewChap;
                            bookInfo.lastViewTime=viewTimes.get(bookInfo.novelId);
                        }else
                        {
                            bookInfo.lastViewTime="0000-00-00T00:00:00";
                        }

                        bookInfos.add(bookInfo);
                    }
            }

        for (int i=0;i<bookInfos.size();i++)
            bookInfoIndex.put(bookInfos.get(i).novelId,i);

        if(Util.fileExists("books.txt"))
        {
            Settings bs=new Settings("books.txt");
            for (String key:bs.getInnerMap().keySet())
            {
                Integer id=bookInfoIndex.get(Integer.valueOf(key));
                if (id==null)
                continue;
                BookInfo bookInfo=bookInfos.get(id);

                JSONObject bo=bs.getJSONObject(key);
                Integer c=bo.getInteger("lastReadChap");
                String t=bo.getString("lastReadTime");
                Boolean isDownloaded=bo.getBoolean("isDownloaded");
                if (isDownloaded!=null)bookInfo.isDownloaded=true;
                if (t.compareTo(bookInfo.lastViewTime)>0)
                {
                    bookInfo.lastViewChap=c;
                    bookInfo.lastViewTime=t;
                }
                Integer lsd=bo.getInteger("lastDownloadChap");
                if (lsd!=null)bookInfo.lastDownloadChap=lsd;
            }

        }


        bookInfos.sort(new Comparator<BookInfo>() {
            @Override
            public int compare(BookInfo o1, BookInfo o2) {
                return o2.lastViewTime.compareTo(o1.lastViewTime);
            }
        });

        bookInfoIndex.clear();
        for (int i=0;i<bookInfos.size();i++)
            bookInfoIndex.put(bookInfos.get(i).novelId,i);


    }

    public void updateBookDetail(BookInfo bookInfo)
    {
        bookInfo.setDetail(getNovelApi().getNovelDetails(bookInfo.novelId,"chapterCount,bigBgBanner,bigNovelCover,typeName,intro,fav,ticket,pointCount,tags,sysTags,signlevel,discount,discountExpireDate,totalNeedFireMoney,originTotalNeedFireMoney,latestchapter,latestcommentdate").data.getJSONObject(0));
    }

    public void updateBookChapters(BookInfo bookInfo)
    {
        bookInfo.setChapters(getNovelApi().getNovelDir(bookInfo.novelId,"originNeedFireMoney").data.getJSONObject(0));
    }


    public BookInfo.ChapterInfo getChapterDetail(int chapId)
    {
        ApiResult apiResult= getNovelApi().getChaps(chapId,"content,needFireMoney,originNeedFireMoney,tsukkomi,chatlines",true);
        BookInfo.ChapterInfo chapterInfo=new BookInfo.ChapterInfo(apiResult.data.getJSONObject(0));
        if (apiResult.errorCode!=200)
        {
            System.out.println("needOrder:"+chapterInfo.title);
            HashMap<String,Object> data=new HashMap<>();
            data.put("orderType","readOrder");
            data.put("chapIds",new int[]{chapterInfo.chapId});
            data.put("autoOrder",true);
            data.put("orderAll",false);
            getNovelApi().orderNovelChaps(chapterInfo.novelId,data);
            chapterInfo=new BookInfo.ChapterInfo(getNovelApi().getChaps(chapId,"content,needFireMoney,originNeedFireMoney,tsukkomi,chatlines",true).data.getJSONObject(0));
        }
        try{
            Integer index=bookInfoIndex.get(chapterInfo.novelId);
            if (index!=null)
            {
                BookInfo bookInfo=bookInfos.get(index);
                BookInfo.ChapterInfo chap=bookInfo.chapters.get(bookInfo.chaptersOrder.get(chapId)-1);
                chap.content=chapterInfo.content;
                chap.tsukkomiCount=chapterInfo.tsukkomiCount;
                chapterInfo.chapOrder=chap.chapOrder;

            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        return chapterInfo;
    }

    public BookInfo.Tsukkomi getTsukkomi(int chapId,int row,int num)
    {
        return new BookInfo.Tsukkomi(getTsukkomiApi().getTsukkomiListByChap(0,chapId,"vipLevel,avatar,roleName","hot",0,num,row).data);
    }

    public void updateNovelVisits(int novelId,int chapId)
    {
        HashMap<String,Object> data=new HashMap<>();
        data.put("triggerCardPieceDrop",true);
        data.put("chapterId",chapId);
        getNovelApi().updateNovelVisits(novelId,data);
    }

    public void deleteBookFromPocket(int novelId)
    {
        Map<String, Object> map=new HashMap<>();
        map.put("eids",String.valueOf(novelId));
        map.put("tid",2);
        getPocketApi().deletePocketsEntities(map);

    }

    public void addBookToPocket(int novelId)
    {
        Map<String, Object> map=new HashMap<>();
        map.put("novelId",novelId);
        map.put("categoryId",0);
        getPocketApi().addNovel2Pocket(-1,map);
    }

}

class RxUtils {

    public static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory sSLSocketFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllManager()},
                    new SecureRandom());
            sSLSocketFactory = sc.getSocketFactory();
            //System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2,SSLv3");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sSLSocketFactory;
    }

    public static class TrustAllManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    public static class TrustAllHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }
}
