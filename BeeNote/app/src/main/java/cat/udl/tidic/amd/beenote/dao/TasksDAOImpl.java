package cat.udl.tidic.amd.beenote.dao;

import java.util.List;

import cat.udl.tidic.amd.beenote.models.TaskModel2;
import cat.udl.tidic.amd.beenote.models.TasksModel;
import cat.udl.tidic.amd.beenote.network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Retrofit;

public class TasksDAOImpl implements TasksDAOI {

    private Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();

    @Override
    public Call<List<TasksModel>> getStudentTasks(String token) {
        return retrofit.create(TasksDAOI.class).getStudentTasks(token);
    }

    @Override
    public Call<Void> addTask(String token, String course_id, TaskModel2 model) {
        return null;
    }

}
