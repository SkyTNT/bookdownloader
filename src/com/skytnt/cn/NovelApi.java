package com.skytnt.cn;

import com.skytnt.cn.ApiAnnotation.*;
import java.util.List;
import java.util.Map;

public interface NovelApi {
    @POST("Chaps")
   ApiResult addChaps(@BODY Map<String, Object> map);

    @POST("volumes")
   ApiResult addNewVolumes(@BODY Map<String, Object> map);

    @POST("novels/{novelId}/bonus")
   ApiResult addNovelBouns(@PATH("novelId") long j, @BODY Map<String, Object> map);

    @POST("novels/{novelId}/Cmts")
   ApiResult addNovelCmt(@PATH("novelId") long j, @BODY Map<String, Object> map);

    @POST("novels/{novelId}/favs")
   ApiResult addNovelFav(@PATH("novelId") long j);

    @POST("novels/{novelId}/lcmts")
   ApiResult addNovelLongCmt(@PATH("novelId") long j, @BODY Map<String, Object> map);

    @POST("novels/{novelId}/points")
   ApiResult addNovelPoints(@PATH("novelId") long j, @BODY Map<String, Object> map);

    @POST("novels/{novelId}/tickets")
   ApiResult addNovelTickets(@PATH("novelId") long j, @BODY Map<String, Object> map);

    @POST("novels/{id}/applys")
   ApiResult applyNovels(@PATH("id") long j);

    @PUT("/novels/{id}/chaporders")
   ApiResult chapOrders(@PATH("id") String str, @BODY Map<String, List> map);

    @POST("/novels/{id}/chatlines")
   ApiResult chatlines(@PATH("id") String str, @BODY Map<String, List> map);

    @POST("/novels/{id}/characters")
   ApiResult createCharacters(@PATH("id") String str, @BODY Map<String, String> map);

    @DELETE("/characters/{id}")
   ApiResult deleteCharacter(@PATH("id") String str);

    @PUT("Chaps/{id}")
   ApiResult fixChaps(@PATH("id") long j, @BODY Map<String, Object> map);

    @PUT("volumes/{id}")
   ApiResult fixVolumes(@PATH("id") long j, @BODY Map<String, Object> map);

    @GET("adpworks/{workType}/{workId}")
   ApiResult getAdaptData(@PATH("workType") String str, @PATH("workId") long j, @QUERY("expand") String str2);

    @GET("novels/{id}/applys")
   ApiResult getApplysResult(@PATH("id") long j);

    @GET("Chaps/{id}")
   ApiResult getChaps(@PATH("id") long j, @QUERY("expand") String str, @QUERY("autoOrder") boolean z);

    @GET("/novels/{id}/characters")
   ApiResult getCharacters(@PATH("id") long j);

    @GET("novels/{id}/dirs")
   ApiResult getFullFovelDir(@PATH("id") long j, @QUERY("nofilter") boolean z);

    @GET("/novels/{novelId}/actpushes")
   ApiResult getNovelActPushes(@PATH("novelId") long j, @QUERY("filter") String str);

    @GET("novels/{id}/chaps")
   ApiResult getNovelChaps(@PATH("id") long j);

    @GET("novels/{id}/chaps")
   ApiResult getNovelChaps(@PATH("id") long j, @QUERY("expand") String str);

    @GET("novels/{id}")
   ApiResult getNovelDetails(@PATH("id") long j, @QUERY("expand") String str);

    @GET("novels/{id}")
   ApiResult getNovelDetails(@PATH("id") long j, @QUERY("fields") String str, @QUERY("expand") String str2);

    @GET("novels/{id}")
   ApiResult getNovelDetails(@PATH("id") String str);

    @GET("novels/{id}/dirs")
   ApiResult getNovelDir(@PATH("id") long j, @QUERY("expand") String str);

    @GET("novels/{novelId}/fans")
   ApiResult getNovelFans(@PATH("novelId") long j);

    @GET("novels/{novelId}/fans")
   ApiResult getNovelFans(@PATH("novelId") long j, @QUERY("page") int i, @QUERY("size") int i2);

    @GET("novels/{novelId}/fans")
   ApiResult getNovelFans(@PATH("novelId") long j, @QUERY("page") int i, @QUERY("size") int i2, @QUERY("field") String str);

    @GET("user/novels/{novelId}/favs")
   ApiResult getNovelFavInfo(@PATH("novelId") long j);

    @GET("/novels/{id}")
   ApiResult getNovelOnline(@PATH("id") long j);

    @GET("novels/{id}/volumes")
   ApiResult getNovelVolumes(@PATH("id") long j);

    @GET("novels")
   ApiResult getNovels(@QUERY("page") int i, @QUERY("size") int i2, @QUERY("tid") int i3, @QUERY("categoryId") int i4, @QUERY("ntype") String str, @QUERY("filter") String str2, @QUERY("expand") String str3);

    @GET("novels")
   ApiResult getNovels(@QUERY("page") int i, @QUERY("size") int i2, @QUERY("tid") int i3, @QUERY("filter") String str, @QUERY("expand") String str2);

    @GET("novels")
   ApiResult getNovels(@QUERY("page") int i, @QUERY("size") int i2, @QUERY("tid") int i3, @QUERY("ntype") String str, @QUERY("filter") String str2, @QUERY("expand") String str3);

    @GET("novels")
   ApiResult getNovels(@QUERY("page") int i, @QUERY("size") int i2, @QUERY("filter") String str);

    @GET("novels")
   ApiResult getNovels(@QUERY("page") int i, @QUERY("size") int i2, @QUERY("filter") String str, @QUERY("categoryId") int i3, @QUERY("expand") String str2);

    @GET("novels")
   ApiResult getNovels(@QUERY("page") int i, @QUERY("size") int i2, @QUERY("filter") String str, @QUERY("expand") String str2);

    @GET("novels")
   ApiResult getNovels(@QUERY("page") int i, @QUERY("size") int i2, @QUERY("ntype") String str, @QUERY("filter") String str2, @QUERY("expand") String str3);

    @GET("novels")
   ApiResult getNovels(@QUERY("size") int i, @QUERY("filter") String str);

    @GET("novels")
   ApiResult getNovels(@QUERY("size") int i, @QUERY("filter") String str, @QUERY("expand") String str2);

    @GET("novels/{tid}/sysTags/{sysTagId}/novels")
   ApiResult getNovelsBySysTags(@PATH("tid") long j, @PATH("sysTagId") long j2, @QUERY("page") int i, @QUERY("size") int i2, @QUERY("categoryId") int i3, @QUERY("expand") String str, @QUERY("sort") String str2, @QUERY("filter") String str3);

    @GET("novels/{tid}/sysTags/{sysTagId}/novels")
   ApiResult getNovelsBySysTags(@PATH("tid") long j, @PATH("sysTagId") long j2, @QUERY("page") int i, @QUERY("size") int i2, @QUERY("expand") String str, @QUERY("sort") String str2, @QUERY("filter") String str3);

    @GET("novels/{tid}/sysTags/{sysTagId}/novels")
   ApiResult getNovelsBySysTags(@PATH("tid") long j, @PATH("sysTagId") long j2, @QUERY_MAP Map<String, Object> map);

    @GET("novels/{novelId}/Cmts")
   ApiResult getNovelsCmts(@PATH("novelId") long j, @QUERY("page") int i, @QUERY("size") int i2, @QUERY("type") String str, @QUERY("sort") String str2, @QUERY("replyUserId") long j2);

    @GET("novels/{novelId}/lcmts")
   ApiResult getNovelsLongCmts(@PATH("novelId") long j, @QUERY("page") int i, @QUERY("size") int i2, @QUERY("sort") String str, @QUERY("charlen") int i3);

    @GET("novels/{typeId}/sysTags")
   ApiResult getNovelsSysTags(@PATH("typeId") long j);

    @GET("novels/{typeId}/sysTags")
   ApiResult getNovelsSysTags(@PATH("typeId") long j, @QUERY("filter") String str);

    @GET("novels/{typeId}/sysTags/novels")
   ApiResult getNovelsSysTagsByTypeId(@PATH("typeId") long j, @QUERY("sort") String str, @QUERY("systagids") String str2, @QUERY("isfree") String str3, @QUERY("isfinish") String str4, @QUERY("categoryId") int i, @QUERY("page") int i2, @QUERY("size") int i3, @QUERY("expand") String str5);

    @GET("novels/{typeId}/sysTags/novels")
   ApiResult getNovelsSysTagsByTypeId(@PATH("typeId") long j, @QUERY("sort") String str, @QUERY("systagids") String str2, @QUERY("isfree") String str3, @QUERY("isfinish") String str4, @QUERY("updatedays") String str5, @QUERY("charcountbegin") long j2, @QUERY("charcountend") long j3, @QUERY("page") int i, @QUERY("size") int i2, @QUERY("expand") String str6);

    @GET("noveltypes")
   ApiResult getNoveltypes();

    @GET("/personalizedrecommend/user/novels")
   ApiResult getPersonalizedRecommendNovel(@QUERY("nid") long j, @QUERY("change") boolean z, @QUERY("categoryId") int i, @QUERY("page") int i2, @QUERY("size") int i3, @QUERY("expand") String str, @QUERY("filter") String str2);

    @GET("rank/{timeRange}/consumeusers")
   ApiResult getRankConsumeusers(@PATH("timeRange") String str, @QUERY("page") int i, @QUERY("size") int i2, @QUERY("expand") String str2, @QUERY("filter") String str3);

    @GET("ranks/{timeRange}/novels")
   ApiResult getRankNovels(@PATH("timeRange") String str, @QUERY("page") int i, @QUERY("size") int i2, @QUERY("categoryId") int i3, @QUERY("rtype") String str2, @QUERY("ntype") String str3, @QUERY("expand") String str4);

    @GET("ranks/{timeRange}/novels")
   ApiResult getRankNovels(@PATH("timeRange") String str, @QUERY("page") int i, @QUERY("size") int i2, @QUERY("rtype") String str2, @QUERY("ntype") String str3, @QUERY("expand") String str4);

    @GET("ranks/{timeRange}/novels")
   ApiResult getRankNovels(@PATH("timeRange") String str, @QUERY("tid") int i, @QUERY("categoryId") int i2, @QUERY("rtype") String str2, @QUERY("ntype") String str3, @QUERY("filter") String str4, @QUERY("expand") String str5);

    @GET("ranks/{timeRange}/novels")
   ApiResult getRankNovels(@PATH("timeRange") String str, @QUERY("tid") int i, @QUERY("rtype") String str2, @QUERY("ntype") String str3, @QUERY("filter") String str4, @QUERY("expand") String str5);

    @GET("ranks/{timeRange}/novels")
   ApiResult getRankNovels(@PATH("timeRange") String str, @QUERY("ntype") String str2, @QUERY("expand") String str3);

    @GET("specialpush")
   ApiResult getSpecialpush();

    @GET("novels/{tid}/sysTags/{sysTagId}")
   ApiResult getSysTagsInfo(@PATH("tid") long j, @PATH("sysTagId") long j2);

    @GET("user/orderednovels")
   ApiResult getUserAutoOrderNovels(@QUERY("page") int i, @QUERY("size") int i2, @QUERY("filter") String str, @QUERY("expand") String str2, @QUERY("orderType") String str3);

    @GET("/user/novelFansInfo/{novelId}")
   ApiResult getUserNovelFansInfo(@PATH("novelId") long j);

    @GET("user/NovelViews")
   ApiResult getUserNovelView();

    @GET("user/NovelViews/{id}")
   ApiResult getUserNovelView(@PATH("id") long j);

    @GET("/volumes/{id}/chaps")
   ApiResult getVolumesChapters(@PATH("id") long j, @QUERY("expand") String str);

    @HEAD("novels/{novelId}/Cmts")
    ApiResult loadNovelCmtsCount(@PATH("novelId") long j);

    @HEAD("novels/{novelId}/lcmts")
    ApiResult loadNovelLongCmtsCount(@PATH("novelId") long j);

    @GET("/novels/{id}/cover")
   ApiResult novelCoverStatus(@PATH("id") long j);

    @POST("novels/{id}/orderedchaps")
   ApiResult orderNovelChaps(@PATH("id") long j, @BODY Map<String, Object> map);

    @POST("cmts/{id}/replys")
   ApiResult postReplys(@PATH("id") long j, @BODY Map<String, Object> map);

    @PUT("/novels/{id}")
   ApiResult putNovels(@PATH("id") long j, @BODY Map<String, Object> map);

    @PUT("/characters/{id}")
   ApiResult updateCharacter(@PATH("id") String str, @BODY Map<String, Object> map);

    @PUT("/chaps/{id}/chatlines")
   ApiResult updateChatlines(@PATH("id") String str, @BODY Map<String, List> map);

    @PUT("user/orderednovels/{novelId}")
   ApiResult updateNovelAutoOrder(@PATH("novelId") long j, @BODY Map<String, Object> map);

    @PUT("/novels/{novelId}")
   ApiResult updateNovelDetail(@PATH("novelId") long j, @BODY Map<String, Object> map);

    @PUT("user/NovelViews/{novelId}")
   ApiResult updateNovelVisits(@PATH("novelId") long j, @BODY Map<String, Object> map);

    @PUT("/novels/{novelId}/cover")
    @MULTI_PART
   ApiResult uplaodNovelCover(@PATH("novelId") long j, @PART PART part);
}
