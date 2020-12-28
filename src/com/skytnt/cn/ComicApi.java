package com.skytnt.cn;

import com.skytnt.cn.ApiAnnotation.*;
import java.util.Map;

public interface ComicApi {
    @GET("authors/{authorId}/comics")
   ApiResult getAuthorComics(@PATH("authorId") long j, @QUERY("page") int i, @QUERY("size") int i2);

    @GET("authors/{authorId}/comics")
   ApiResult getAuthorComics(@PATH("authorId") long j, @QUERY("page") int i, @QUERY("size") int i2, @QUERY("fields") String str, @QUERY("expand") String str2);

    @GET("comics/{id}")
   ApiResult getComic(@PATH("id") long j, @QUERY("expand") String str);

    @GET("comiccmts/{id}/replys")
   ApiResult getComicCmtsReplys(@PATH("id") long j);

    @GET("comiccmts/{id}/replys")
   ApiResult getComicCmtsReplys(@PATH("id") long j, @QUERY("page") int i, @QUERY("size") int i2);

    @GET("/lib/comics/{typeId}")
   ApiResult getComicTypeByFiltrate(@PATH("typeId") long j, @QUERY("page") int i, @QUERY("size") int i2, @QUERY("isfinish") String str, @QUERY("isfree") String str2, @QUERY("sort") String str3, @QUERY("expand") String str4);

    @GET("comictypes")
   ApiResult getComicTypes();

    @GET("comics")
   ApiResult getComics(@QUERY("page") int i, @QUERY("size") int i2);

    @GET("comics")
   ApiResult getComics(@QUERY("page") int i, @QUERY("size") int i2, @QUERY("tid") long j, @QUERY("filter") String str, @QUERY("expand") String str2);

    @GET("comics")
   ApiResult getComics(@QUERY("page") int i, @QUERY("size") int i2, @QUERY("expand") String str);

    @GET("comics")
   ApiResult getComics(@QUERY("page") int i, @QUERY("size") int i2, @QUERY("filter") String str, @QUERY("daybefore") int i3);

    @GET("comics")
   ApiResult getComics(@QUERY("page") int i, @QUERY("size") int i2, @QUERY("filter") String str, @QUERY("expand") String str2);

    @GET("comics")
   ApiResult getComics(@QUERY("page") int i, @QUERY("size") int i2, @QUERY("filter") String str, @QUERY("fields") String str2, @QUERY("expand") String str3);

    @GET("comics")
   ApiResult getComics(@QUERY("size") int i, @QUERY("filter") String str, @QUERY("expand") String str2);

    @GET("comics/{id}/chappics/{pathName}")
   ApiResult getComicsChappics(@PATH("id") long j, @PATH("pathName") String str, @QUERY("autoOrder") boolean z);

    @GET("comics/{id}/chaps")
   ApiResult getComicsChaps(@PATH("id") long j, @QUERY("expand") String str);

    @GET("comics/{id}/comiccmts")
   ApiResult getComicsCmts(@PATH("id") long j);

    @GET("comics/{id}/comiccmts")
   ApiResult getComicsCmts(@PATH("id") long j, @QUERY("page") int i, @QUERY("size") int i2);

    @GET("comics/{id}/comiccmts")
   ApiResult getComicsCmts(@PATH("id") long j, @QUERY("page") int i, @QUERY("size") int i2, @QUERY("type") String str);

    @GET("comiccmts/{id}")
   ApiResult getComicsCmtsInfo(@PATH("id") long j);

    @GET("/user/orderedcomics")
   ApiResult getOrderedComics(@QUERY("page") int i, @QUERY("size") int i2);

    @GET("ranks/{timeRange}/comics")
   ApiResult getRangeComics(@PATH("timeRange") String str, @QUERY("tid") long j, @QUERY("rtype") String str2);

    @GET("ranks/{timeRange}/comics")
   ApiResult getRangeComics(@PATH("timeRange") String str, @QUERY("rtype") String str2, @QUERY("expand") String str3);

    @GET("ranks/{timeRange}/comics")
   ApiResult getRangeComics(@PATH("timeRange") String str, @QUERY("rtype") String str2, @QUERY("expand") String str3, @QUERY("page") int i, @QUERY("size") int i2);

    @GET("serials")
   ApiResult getSerials();

    @GET("comicspecialpush")
   ApiResult getSpecialpush();

    @GET("user/comicvisits")
   ApiResult getUserComicVisits();

    @GET("user/comicvisits/{id} ")
   ApiResult getUserComicVisits(@PATH("id") long j);

    @HEAD("comics/{id}/comiccmts")
    void loadComicsCmtsCount(@PATH("id") long j);

    @POST("comics/{id}/orderedchaps")
   ApiResult orderComicChaps(@PATH("id") long j, @BODY Map<String, Object> map);

    @POST("comiccmts/{id}/replys")
   ApiResult postComicCmtsReplys(@PATH("id") long j, @BODY Map<String, Object> map);

    @POST("comics/{id}/comiccmts")
   ApiResult postComicsCmts(@PATH("id") long j, @BODY Map<String, Object> map);

    @POST("comics/{id}/favs")
   ApiResult postComicsFavs(@PATH("id") long j);

    @POST("comics/{id}/points")
   ApiResult postComicsPoints(@PATH("id") long j, @BODY Map<String, Object> map);

    @PUT("/user/orderedcomics/{id}")
   ApiResult updateComicAutoOrder(@PATH("id") long j, @BODY Map<String, Object> map);

    @PUT("user/comicvisits/{id}")
   ApiResult updateComicVisits(@PATH("id") long j, @BODY Map<String, Object> map);
}
