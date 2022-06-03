package com.example.vzigo;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {

    @PATCH("devices")
    Call<LinkDevices1> linkdevices(@Body LinkDevices linkDevices);

    @POST("feedbacks")
    Call<ResponseBody> feedbacks(@Header("DeviceId") String deviceId, @Header("Authorization") String Authorization, @Body Feedbacks feedbacks);

    @PATCH("checkout")
    Call<ResponseBody> checkout(@Header("DeviceId") String deviceId, @Header("Authorization") String Authorization);

    @POST("visitors")
    Call<CheckInresponse> checkin(@Header("DeviceId") String deviceId, @Header("Authorization") String Authorization, @Body RegisterVisitors registerVisitors);

    @Multipart
    @POST("image")
    Call<ResponseBody> sendImage(@Header("DeviceId") String deviceId, @Header("Authorization") String Authorization, @Header("Content-Type") String content_type, @Part MultipartBody.Part image);


}
