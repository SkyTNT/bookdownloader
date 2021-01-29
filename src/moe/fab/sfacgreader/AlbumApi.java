package moe.fab.sfacgreader;

import moe.fab.sfacgreader.ApiAnnotation.*;
import moe.fab.sfacgreader.ApiAnnotation.*;

import java.util.Map;

public interface AlbumApi {
    @POST("albums/{albumId}/albumcmts")
   ApiResult addAlbumCmt(@PATH("albumId") long j, @BODY Map<String, Object> map);

    @POST("albums/{albumId}/favs")
   ApiResult addAlbumsFav(@PATH("albumId") long j);

    @GET("albums/{albumId}/chaps")
   ApiResult getAlbumChapsWithAlbumId(@PATH("albumId") long j, @QUERY("expand") String str);

    @GET("albumchaps/{id}")
   ApiResult getAlbumChapsWithChapId(@PATH("id") long j, @QUERY("expand") String str, @QUERY("autoOrder") boolean z);

    @GET("albumcmts/{commentId}/replys")
   ApiResult getAlbumCommentReplys(@PATH("commentId") long j, @QUERY("page") int i, @QUERY("size") int i2);

    @GET("user/albumvisits")
   ApiResult getAlbumVisits();

    @GET("user/albumvisits/{id}")
   ApiResult getAlbumVisits(@PATH("id") long j);

    @GET("albums/{albumId}/volumes")
   ApiResult getAlbumVolumes(@PATH("albumId") long j);

    @GET("albums/{albumId}")
   ApiResult getAlbumWithId(@PATH("albumId") long j, @QUERY("expand") String str);

    @GET("albums")
   ApiResult getAlbums(@QUERY("page") int i, @QUERY("size") int i2, @QUERY("expand") String str);

    @GET("albums")
   ApiResult getAlbums(@QUERY("page") int i, @QUERY("size") int i2, @QUERY("filter") String str, @QUERY("expand") String str2);

    @GET("albumcmts/{commentId}")
   ApiResult getAlbumsCmtDetail(@PATH("commentId") long j);

    @GET("albums/{albumId}/albumcmts")
   ApiResult getAlbumsCmts(@PATH("albumId") long j, @QUERY("page") int i, @QUERY("size") int i2);

    @GET("albums/{albumId}/albumcmts")
   ApiResult getAlbumsCmts(@PATH("albumId") long j, @QUERY("page") int i, @QUERY("size") int i2, @QUERY("type") String str);

    @GET("albumauthors/{authorId}/albums")
   ApiResult getAlbumsWithAuthorId( @PATH("authorId") long j);

    @GET("albums/novelid/{novelId}")
   ApiResult getNovelAlbumsWithNovelId(@PATH("novelId") long j, @QUERY("expand") String str);

    @GET("user/orderedalbums")
   ApiResult getOrderedAlbums(@QUERY("page") int i, @QUERY("size") int i2);

    @HEAD("user/orderedalbums")
    void getOrderedAlbumsCount();

    @HEAD("albums/{albumId}/albumcmts")
    void loadAlbumCmtsCount(@PATH("albumId") long j);

    @POST("albums/{id}/orderedchaps")
   ApiResult orderAlubmChaps(@PATH("id") long j, @BODY Map<String, Object> map);

    @POST("albumcmts/{commentId}/replys")
   ApiResult replyAlbumCmt(@PATH("commentId") long j, @BODY Map<String, Object> map);

    @PUT("user/orderedalbums/{albumId}")
   ApiResult updateAlbumAutoOrder(@PATH("albumId") long j, @BODY Map<String, Object> map);

    @PUT("user/albumvisits/{albumId}")
   ApiResult updateAlbumVisits(@PATH("albumId") long j, @BODY Map<String, Object> map);
}
