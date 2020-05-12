package cat.udl.tidic.amd.beenote.dao;

import androidx.room.Delete;

import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;

import cat.udl.tidic.amd.beenote.models.CourseModel;
import cat.udl.tidic.amd.beenote.models.CourseModel2;
import cat.udl.tidic.amd.beenote.models.UserModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CourseDAOI {

    @Headers("Content-Type:application/json; charset=UTF-8")
    // Account -> Courses

    @GET("/courses/list")
    Call<List<CourseModel>> getStudentCourses(@Header("Authorization") String token);

    @POST("/courses/add")
    Call<Void> postCourseWithoutId(@Header("Authorization") String base64_encoding, @Body CourseModel2 model);

    @POST("/courses/{course_id}/add")
    Call<Void> postCourseWithId(@HeaderMap Map<String, String> headers, @Path("course_id")String course_id);

    @DELETE("/courses/{course_id}/{user_id}/delete")
    Call<Void> deleteCourse(@Header("Authorization") String base64_encoding, @Path("course_id")String course_id, @Path("user_id")String user_id);


    @Delete
    void delete(CourseModel event);
}
