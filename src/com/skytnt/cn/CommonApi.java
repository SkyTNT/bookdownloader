package com.skytnt.cn;

import com.skytnt.cn.ApiAnnotation.*;
import java.util.List;
import java.util.Map;
import okhttp3.RequestBody;

public interface CommonApi {
    @Deprecated
    @POST("FeedBacks")
   ApiResult feedback(@BODY Map<String, Object> map);

    @POST("feedbackuser")
   ApiResult feedbackuser(@BODY Map<String, Object> map);

    @POST("feedbackimages")
    @MULTI_PART
   ApiResult feedbackuser(@PART("jsonObj") RequestBody requestBody);

    @POST("feedbackimages")
    @MULTI_PART
   ApiResult feedbackuserByPic(@PART("jsonObj") RequestBody requestBody, @PART List<PART> list);

    @GET("actpush")
   ApiResult getActPush(@QUERY("filter") String str, @QUERY("page") int i, @QUERY("size") int i2);

    @GET("androidcfg")
   ApiResult getAndroidcfg();

    @GET("feedbackuser")
   ApiResult getFeedbackuser(@QUERY("userIdentifer") String str, @QUERY("channel") String str2);

    @GET("static/images")
   ApiResult getImages(@QUERY("fields") String str);

    @GET("newfeedbacks")
   ApiResult getNewfeedbacks(@QUERY("feedbackUserId") long j, @QUERY("page") int i, @QUERY("size") int i2);

    @GET("/position")
   ApiResult getPosition();

    @GET("androidpushsetting/{package}/{deviceId}")
   ApiResult getPushSetting(@PATH("package") String str, @PATH("deviceId") String str2);

    @GET("/reportOptions")
   ApiResult getReportOptions();

    @GET
   ApiResult getRewardFireMoney(@URL String str, @QUERY("op") String str2, @QUERY("deviceId") String str3);

    @GET("search/all/result")
   ApiResult getSearchAll(@QUERY("q") String str, @QUERY("expand") String str2, @QUERY("page") int i, @QUERY("size") int i2);

    @GET("search/all/result")
   ApiResult getSearchAll(@QUERY("q") String str, @QUERY("expand") String str2, @QUERY("sort") String str3, @QUERY("page") int i, @QUERY("size") int i2);

    @GET("/search/plusrefers")
   ApiResult getSearchAllReffers(@QUERY("q") String str);

    @GET("search/hotwords")
   ApiResult getSearchHotwords(@QUERY("page") int i, @QUERY("size") int i2);

    @GET("search/novels/result")
   ApiResult getSearchNovel(@QUERY("q") String str);

    @GET("search/novels/result")
   ApiResult getSearchNovel(@QUERY("q") String str, @QUERY("expand") String str2);

    @GET("search/{searchtype}/result")
   ApiResult getSearchNovelBySearchType(@PATH("searchtype") String str, @QUERY("q") String str2, @QUERY("expand") String str3, @QUERY("sort") String str4, @QUERY("page") int i, @QUERY("size") int i2);

    @GET("search/novels/reffers")
   ApiResult getSearchNovelReffers(@QUERY("q") String str);

    @GET("welfare/cfg")
   ApiResult getWelfarecfg();

    @GET("static/zips")
   ApiResult getZips(@QUERY("fields") String str);

    @GET("/user/readingtime")
   ApiResult loadReadingTime(@QUERY("entityType") int i, @QUERY("date") String str);

    @POST("newfeedbacks")
   ApiResult newfeedbacks(@QUERY("feedbackUserId") long j, @BODY Map<String, Object> map);

    @POST("user/androiddeviceinfos")
   ApiResult postAndroidDeviceInfos(@BODY Map<String, Object> map);

    @POST("static/images")
    @MULTI_PART
   ApiResult postImages(@QUERY("type") String str, @PART PART part);

    @POST("debuglog")
   ApiResult submitDebugLog(@BODY Map<String, Object> map);

    @POST("/user/reports")
   ApiResult submitReport(@BODY Map<String, Object> map);

    @PUT("feedbackuser")
   ApiResult updateFeedbackuser(@QUERY("feedbackUserId") long j);

    @PUT("androidpushsetting/{package}/{deviceId}")
   ApiResult updatePushSetting(@PATH("package") String str, @PATH("deviceId") String str2, @BODY Map<String, Object> map);

    @PUT("/user/readingtime")
   ApiResult updateReadingTime(@BODY Map<String, Object> map);
}
