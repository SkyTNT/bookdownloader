package com.skytnt.cn;

import com.skytnt.cn.ApiAnnotation.*;

import java.util.Map;

public interface AccountApi {
    @PUT("user/socialAccounts")
    ApiResult bindSocialAccounts(@BODY Map<String, Object> map);

    @POST("/user/blocks")
    ApiResult blocks(@BODY Map<String, Object> map);

    @PUT("email")
    ApiResult checkValiteEmail(@QUERY("type") String str, @BODY Map<String, Object> map);

    @PUT("email")
    ApiResult checkValiteEmail(@BODY Map<String, Object> map);

    @PUT("sms/{phoneNum}/{countryCode}")
    ApiResult checkValiteSms(@PATH("phoneNum") String str, @PATH("countryCode") String str2, @QUERY("type") String str3, @BODY Map<String, Object> map);

    @PUT("sms/{phoneNum}/{countryCode}")
    ApiResult checkValiteSms(@PATH("phoneNum") String str, @PATH("countryCode") String str2, @BODY Map<String, Object> map);

    @POST("user")
    ApiResult createUser(@BODY Map<String, Object> map);

    @POST("user/novels")
    ApiResult createUserNovels(@BODY Map<String, Object> map);

    @DELETE("/user/blocks/{id}")
    ApiResult deleteBlocks(@PATH("id") long j);

    @DELETE("/user/messages/{relateAccountId}")
    ApiResult deleteMessageDialog(@PATH("relateAccountId") long j);

    @DELETE("/user/socialAccounts/{id}")
    ApiResult deleteSocialAccounts(@PATH("id") int i);

    @GET("/users/{accountId}/novels")
    ApiResult getAccountNovels(@PATH("accountId") long j);

    @GET("/users/{accountId}/novels")
    ApiResult getAccountNovels(@PATH("accountId") long j, @QUERY("expand") String str);

    @GET("/users/{accountId}/pocketEntities")
    ApiResult getAccountPocketEntities(@PATH("accountId") long j, @QUERY("page") int i, @QUERY("size") int i2, @QUERY("expand") String str);

    @GET("/users")
    ApiResult getBatchUserOpenInfo(@QUERY("expand") String str, @QUERY("uids") String str2);

    @GET("/user/blocks")
    ApiResult getBlocks(@QUERY("expand") String str);

    @GET("/countrycodes")
    ApiResult getCountryCodeList();

    @GET("/user/messages/{relateAccountId}")
    ApiResult getFromAccountIdMessageList(@PATH("relateAccountId") long j, @QUERY("page") int i, @QUERY("size") int i2, @QUERY("expand") String str);

    @GET("/user/tasks")
    ApiResult getGreenHandTaskList(@QUERY("taskCategory") int i);

    @GET("user/messages")
    ApiResult getMessageList(@QUERY("page") int i, @QUERY("size") int i2, @QUERY("type") String str);

    @GET("/user/risks/{id}")
    ApiResult getRisks(@PATH("id") long j);

    @GET("user")
    ApiResult getUser(@QUERY("expand") String str);

    @GET("/users/nickname/{nickName}")
    ApiResult getUserByNickName(@PATH("nickName") String str);

    @GET("/users/{accountId}/gameCards")
    ApiResult getUserCardList(@PATH("accountId") long j, @QUERY("page") int i, @QUERY("size") int i2, @QUERY("expand") String str);

    @GET("/users/{accountId}/cmts")
    ApiResult getUserCmtList(@PATH("accountId") long j, @QUERY("expand") String str, @QUERY("page") int i, @QUERY("size") int i2);

    @GET("/user/consumeitems")
    ApiResult getUserConsumeItemList(@QUERY("type") String str, @QUERY("page") int i, @QUERY("size") int i2);

    @GET("user/consumes")
    ApiResult getUserConsumes(@QUERY("type") String str, @QUERY("page") int i, @QUERY("size") int i2);

    @GET("user/consumes")
    ApiResult getUserConsumesDetail(@QUERY("type") String str, @QUERY("entityId") long j, @QUERY("page") int i, @QUERY("size") int i2);

    @GET("/user/coupons")
    ApiResult getUserCouponsRecord(@QUERY("page") int i, @QUERY("size") int i2);

    @GET("/users/{accountId}/lcmts")
    ApiResult getUserLcmtList(@PATH("accountId") long j, @QUERY("expand") String str, @QUERY("page") int i, @QUERY("size") int i2, @QUERY("charlen") int i3, @QUERY("sort") String str2, @QUERY("order") String str3);

    @GET("user/money")
    ApiResult getUserMoney();

    @GET("user/novels")
    ApiResult getUserNovels();

    @GET("user/novels")
    ApiResult getUserNovels(@QUERY("expand") String str);

    @GET("/users/{accountId}")
    ApiResult getUserOpenInfo(@PATH("accountId") long j, @QUERY("expand") String str);

    @GET("user/platforms/android/orders")
    ApiResult getUserOrders(@QUERY("page") int i, @QUERY("size") int i2);

    @GET("/user/signInfo")
    ApiResult getUserSignInfo();

    @GET("/user/signInfo")
    ApiResult getUserSignInfo(@QUERY("year") int i, @QUERY("month") int i2);

    @GET("/user/socialAccounts")
    ApiResult getUserSocialAccounts();

    @HEAD("user/tasks")
    ApiResult getUserTasksCount(@QUERY("taskCategory") int i, @QUERY("status") int i2);

    @GET("user/ticketlogs")
    ApiResult getUserTicketLogs(@QUERY("page") int i, @QUERY("size") int i2);

    @GET("user/tickets")
    ApiResult getUserTickets(@QUERY("page") int i, @QUERY("size") int i2, @QUERY("isexpired") String str);

    @GET("user/welfare/income")
    ApiResult getUserWelfareIncome();

    @GET("user/welfare/logs")
    ApiResult getUserWelfareLogs(@QUERY("wftype") int i, @QUERY("page") int i2, @QUERY("size") int i3);

    @GET("user")
    ApiResult getUserWithFields(@QUERY("fields") String str);

    @GET("/user/welfare/applycashitems")
    ApiResult getWelfareApplyCashItems();

    @GET("/user/welfare/payapply")
    ApiResult getWelfarePayapply(@QUERY("page") int i, @QUERY("size") int i2, @QUERY("status") int i3);

    @GET("/user/welfare/payinfo")
    ApiResult getWelfarePayinfo();

    @GET("user/welfare/redpacket")
    ApiResult getWelfareRedpacket(@QUERY("deviceId") String str);

    @PUT("/user/tasks/{id}")
    ApiResult getWelfareReward(@PATH("id") int i);

    @POST("/user/tasks/{id}")
    ApiResult getWelfareTask(@PATH("id") int i);

    @HEAD("user/tickets")
    ApiResult loadUserTickets();

    @POST("sessions")
    ApiResult login(@BODY Map<String, Object> map);

    @POST("sessions/3rd")
    ApiResult login3rd(@BODY Map<String, Object> map);

    @DELETE("sessions")
    ApiResult logout();

    @PUT("/user/password")
    ApiResult password(@BODY Map<String, Object> map);

    @POST("/user/welfare/payapply")
    ApiResult postWelfarePayapply(@BODY Map<String, Integer> map);

    @POST("/user/welfare/payinfo")
    ApiResult postWelfarePayinfo(@BODY Map<String, String> map);

    @POST("/user/welfare/redpacket")
    ApiResult postredpacketCode(@BODY Map<String, String> map);

    @PUT("/user/messages/{relateAccountId}")
    ApiResult putEditMessage(@PATH("relateAccountId") long j);

    @PUT("password")
    ApiResult resetPassword(@BODY Map<String, Object> map);

    @POST("user/messages")
    ApiResult sendShortMessage(@BODY Map<String, String> map);

    @POST("email")
    ApiResult sendValiteEmail(@BODY Map<String, Object> map);

    @POST("email")
    ApiResult sendValiteEmail(@BODY Map<String, Object> map, @QUERY("type") String str);

    @POST("sms/{phoneNum}/{countryCode}")
    ApiResult sendValiteSms(@PATH("phoneNum") String str, @PATH("countryCode") String str2);

    @POST("sms/{phoneNum}/{countryCode}")
    ApiResult sendValiteSms(@PATH("phoneNum") String str, @PATH("countryCode") String str2, @QUERY("type") String str3);

    @PUT("user/signInfo")
    ApiResult signInfo();

    @PUT("user/Avatar")
    @MULTI_PART
    ApiResult updateUserAvatar(@PART PART part);

    @PUT("/user/backgroundpic")
    @MULTI_PART
    ApiResult updateUserBackgroundpic(@PART PART part);

    @PUT("user/info")
    ApiResult updateUserInfo(@BODY Map<String, Object> map);

    @PUT("user")
    ApiResult updateUserPassword(@BODY Map<String, Object> map);

    @PUT("user/validate")
    ApiResult validateUser(@BODY Map<String, Object> map);

    @GET("users/availablename/{nickName}")
    ApiResult validateUserNickName(@PATH("nickName") String str);

    @POST("users/availablename")
    ApiResult validateUserNickName(@BODY Map<String, Object> map);
}
