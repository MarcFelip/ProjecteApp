package cat.udl.tidic.amd.beenote.services;


import com.google.gson.JsonObject;

import org.json.JSONObject;

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
import retrofit2.http.PUT;

public interface UserService {

    @Headers("Content-Type:application/json; charset=UTF-8")
    @GET("/account/profile/show")
    Call<UserModel> getUserProfile(@HeaderMap Map<String, String> headers);

    @POST("/users/register")
    Call<Void> postUserProfile(@HeaderMap Map<String, String> headers, @Body UserModel model);

    @POST("/account/create_token")
    Call<ResponseBody> postCreateToken(@Header("Authorization") String base64_encoding);

    @POST("/account/delete_token")
    Call<Void> deleteToken(@HeaderMap Map<String, String> headers, @Body TokenModel token);

    @PUT("/account/profile/update")
        Call<Void> updateUserProfile(@HeaderMap Map<String, String> headers, @Body JsonObject json);

    @GET("/account/profile/showID")
    Call<String> getUserID(@Header("Authorization") String base64_encoding);

}
