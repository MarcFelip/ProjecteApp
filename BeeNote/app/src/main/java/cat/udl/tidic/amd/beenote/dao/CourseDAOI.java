package cat.udl.tidic.amd.beenote.dao;

import java.util.List;

import cat.udl.tidic.amd.beenote.models.CourseModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface CourseDAOI {

    // Account -> Courses
    @GET("/courses/list")
    Call<List<CourseModel>> getStudentCourses(@Header("Authorization") String token);

}
