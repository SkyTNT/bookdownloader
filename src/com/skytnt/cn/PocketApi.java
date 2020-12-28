package com.skytnt.cn;

import com.skytnt.cn.ApiAnnotation.*;
import java.util.Map;

public interface PocketApi {
    @POST("pockets/{pocketId}/albums")
   ApiResult addAlbum2Pocket(@PATH("pocketId") long j, @BODY Map<String, Object> map);

    @POST("pockets/{pocketId}/comics")
   ApiResult addComic2Pocket(@PATH("pocketId") long j, @BODY Map<String, Object> map);

    @POST("pockets/{pocketId}/novels")
   ApiResult addNovel2Pocket(@PATH("pocketId") long j, @BODY Map<String, Object> map);

    @POST("user/Pockets")
   ApiResult createPocket(@BODY Map<String, Object> map);

    @DELETE("pockets/{pid}/albums/{id}")
   ApiResult deleteAlbumsInPocket(@PATH("pid") long j, @PATH("id") long j2);

    @DELETE("user/pockets/comic/{id}")
   ApiResult deleteComicsInUserPockets(@PATH("id") long j);

    @DELETE("pockets/{pid}/novels/{nid}")
   ApiResult deleteNovelInPocket(@PATH("pid") long j, @PATH("nid") long j2);

    @DELETE("user/pockets/novels/{nid}")
   ApiResult deleteNovelsInUserPockets(@PATH("nid") long j);

    @DELETE("user/Pockets/{pid}")
   ApiResult deletePocket(@PATH("pid") long j);

    //@HTTP(hasBody = true, method = "DELETE", path = "user/pockets/entities")
    @DELETE("user/pockets/entities")
   ApiResult deletePocketsEntities(@BODY Map<String, Object> map);

    @DELETE("pockets/{pid}/comics/{id}")
   ApiResult deletecComicInPocket(@PATH("pid") long j, @PATH("id") long j2);

    @GET("user/Pockets")
   ApiResult getUserPockets(@QUERY("expand") String str);

    @PUT("user/pockets/entities/{pid}")
   ApiResult movePocketsEntities(@PATH("pid") long j, @BODY Map<String, Object> map);

    @PUT("user/Pockets/{pocketId}")
   ApiResult updatePocket(@PATH("pocketId") long j, @BODY Map<String, Object> map);
}
