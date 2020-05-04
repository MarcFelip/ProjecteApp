package cat.udl.tidic.amd.beenote.dao;

import java.util.List;

import cat.udl.tidic.amd.beenote.models.CourseModel;
import cat.udl.tidic.amd.beenote.network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Retrofit;

public class CourseDAOImpl implements CourseDAOI {

    private Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();

    @Override
    public Call<List<CourseModel>> getStudentCourses(String token) {
        return retrofit.create(CourseDAOI.class).getStudentCourses(token);
    }
}
