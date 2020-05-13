package cat.udl.tidic.amd.beenote.dao;

import java.util.List;
import java.util.Map;

import cat.udl.tidic.amd.beenote.models.CourseModel2;
import cat.udl.tidic.amd.beenote.models.TaskModel2;
import cat.udl.tidic.amd.beenote.models.TasksModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TasksDAOI {

    // Account -> Courses
    @GET("/courses/task/list")
    Call<List<TasksModel>> getStudentTasks(@Header("Authorization") String token);

    @POST("/courses/{course_id}/task/add")
    Call<Void> addTask(@Header("Authorization") String token, @Path("course_id")String course_id, @Body TaskModel2 model);


}
