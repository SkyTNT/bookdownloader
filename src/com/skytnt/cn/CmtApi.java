package com.skytnt.cn;

import com.skytnt.cn.ApiAnnotation.*;
import java.util.Map;

public interface CmtApi {
    @PUT("albumcmts/{id}/action/fav")
   ApiResult addAlbumcmtsFav(@PATH("id") long j);

    @PUT("/cmts/{commentId}/action/fav")
   ApiResult addCmtFav(@PATH("commentId") long j);

    @PUT("comiccmts/{id}/action/fav")
   ApiResult addComiccmtsFav(@PATH("id") long j);

    @PUT("lcmts/{commentId}/action/fav")
   ApiResult addLcmtFav(@PATH("commentId") long j);

    @POST("cmts/{commentId}/replys")
   ApiResult addNovelCmtReply(@PATH("commentId") long j, @BODY Map<String, Object> map);

    @POST("lcmts/{commentId}/replys")
   ApiResult addNovelLcmtReply(@PATH("commentId") long j, @BODY Map<String, Object> map);

    @DELETE("/cmts/{id}")
   ApiResult deleteCmt(@PATH("id") long j);

    @DELETE("/cmts/{id}/action/stick")
   ApiResult deleteStick(@PATH("id") long j);

    @GET("cmts/{commentId}")
   ApiResult getCmtDetail(@PATH("commentId") long j);

    @GET("cmts/{commentId}/replys")
   ApiResult getCmtsReplys(@PATH("commentId") long j, @QUERY("page") int i, @QUERY("size") int i2);

    @GET("lcmts/{commentId}")
   ApiResult getLongCmtDetail(@PATH("commentId") long j);

    @GET("lcmts/{commentId}/replys")
   ApiResult getLongCmtsReplys(@PATH("commentId") long j, @QUERY("page") int i, @QUERY("size") int i2);

    @HEAD("novels/{novelId}/Cmts")
    void getNovelCommentCount(@PATH("novelId") long j);

    @HEAD("novels/{novelId}/lcmts")
    void getNovelLongCmtsCount(@PATH("novelId") long j);

    @PUT("/cmts/{id}/action/stick")
   ApiResult putStick(@PATH("id") long j);
}
