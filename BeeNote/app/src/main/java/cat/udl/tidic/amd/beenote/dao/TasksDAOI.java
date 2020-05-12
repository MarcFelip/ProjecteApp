package cat.udl.tidic.amd.beenote.dao;

import java.util.List;

import cat.udl.tidic.amd.beenote.models.TasksModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface TasksDAOI {

    // Account -> Courses
    @GET("/courses/task/list")
    Call<List<TasksModel>> getStudentTasks(@Header("Authorization") String token);

}
