package cat.udl.tidic.amd.beenote.dao;

import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;

import cat.udl.tidic.amd.beenote.models.CourseModel;
import cat.udl.tidic.amd.beenote.models.CourseModel2;
import cat.udl.tidic.amd.beenote.network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Retrofit;

public class CourseDAOImpl implements CourseDAOI {

    private Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();

    @Override
    public Call<List<CourseModel>> getStudentCourses(String token) {
        return retrofit.create(CourseDAOI.class).getStudentCourses(token);
    }

    @Override
    public Call<Void> postCourseWithoutId(String base64_encoding, CourseModel2 model) {
        return null;
    }

    @Override
    public Call<Void> postCourseWithId(Map<String, String> headers, String course_id) {
        return null;
    }

    @Override
    public Call<Void> deleteCourse(String base64_encoding, String course_id, String user_id) {
        return null;
    }


    @Override
    public void delete(CourseModel event) {

    }
}
