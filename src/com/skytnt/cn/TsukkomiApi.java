package com.skytnt.cn;

import com.skytnt.cn.ApiAnnotation.*;

import java.util.Map;

public interface TsukkomiApi {
    @POST("/chaps/{entityType}/{chapId}/tsukkomis")
    ApiResult addTsukkomiByChap(@PATH("entityType") long j, @PATH("chapId") long j2, @BODY Map<String, Object> map);

    @POST("/tsukkomis/{tskId}/favs")
    ApiResult addTsukkomiFav(@PATH("tskId") long j);

    @POST("/tsukkomis/{id}/replys")
    ApiResult addTsukkomiReply(@PATH("id") long j, @BODY Map<String, Object> map);

    @GET("/chaps/{entityType}/{chapId}/tsukkomis")
    ApiResult getTsukkomiListByChap(@PATH("entityType") long j, @PATH("chapId") long j2, @QUERY("expand") String str, @QUERY("sort") String str2, @QUERY("page") int i, @QUERY("size") int i2, @QUERY("row") int i3);

    @GET("/tsukkomis/{tskId}/replys")
    ApiResult getTsukkomiReplyList(@PATH("tskId") long j, @QUERY("page") int i, @QUERY("size") int i2, @QUERY("expand") String str);
}
