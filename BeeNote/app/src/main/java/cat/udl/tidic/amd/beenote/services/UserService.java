package cat.udl.tidic.amd.beenote.services;


import java.util.Map;

import cat.udl.tidic.amd.beenote.models.TokenModel;
import cat.udl.tidic.amd.beenote.models.UserModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserService {

    //@Headers("Authorization:656e50e154865a5dc469b80437ed2f963b8f58c8857b66c9bf")
    @Headers("Content-Type:application/json; charset=UTF-8")
    @GET("/account/profile")
    Call<UserModel> getUserProfile(@HeaderMap Map<String, String> headers);
    //Call<UserModel> getUserProfile();

    @POST("/users/register")
    Call<Void> postUserProfile(@HeaderMap Map<String, String> headers, @Body UserModel model);
    //Call<El que hi ha aqui dintre es el que retorna la peticio de la API> ...

    @POST("/account/create_token")
    Call<ResponseBody> postCreateToken(@Header("Authorization") String base64_encoding);

    @POST("/account/delete_token")
    Call<Void> deleteToken(@HeaderMap Map<String, String> headers, @Body TokenModel token);

    //@TODO: EFHJDSAHFKJAS

}
