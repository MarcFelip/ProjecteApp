package cat.udl.tidic.amd.beenote.Repository;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import cat.udl.tidic.amd.beenote.dao.TasksDAOI;
import cat.udl.tidic.amd.beenote.dao.TasksDAOImpl;
import cat.udl.tidic.amd.beenote.models.TasksModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TasksRepository {

    private TasksDAOI tasksDAO;
    private final String TAG = "TasksRepo";
    private UserRepository userRepo;

    private MutableLiveData<List<TasksModel>> mResponseStudentTasksEnrolled;

    public TasksRepository() {
        tasksDAO = new TasksDAOImpl();
        userRepo = new UserRepository();
        this.mResponseStudentTasksEnrolled = new MutableLiveData<>();
    }

    public MutableLiveData<List<TasksModel>> getmResponseStudentTasksEnrolled() {
        return mResponseStudentTasksEnrolled;
    }

    public void setmResponseStudentTasksEnrolled(MutableLiveData<List<TasksModel>> mResponseStudentTasksEnrolled) {
        this.mResponseStudentTasksEnrolled = mResponseStudentTasksEnrolled;
    }

    public void getStudentTasks() {
        System.out.println(userRepo.getToken());
        tasksDAO.getStudentTasks(userRepo.getToken()).enqueue(new Callback<List<TasksModel>>() {
            @Override
            public void onResponse(Call<List<TasksModel>> call, Response<List<TasksModel>> response) {
                Log.d(TAG, "Url: " + call.request().url());
                Log.d(TAG, "req: " + call.request().toString());
                if (response.code() == 200) {
                    Log.d(TAG, "Se ha obtenido correctamente la lista de assignaturas.");
                    mResponseStudentTasksEnrolled.setValue(response.body());
                } else {
                    Log.d(TAG, "No se ha obtenido correctamente la lista de assignaturas.");
                    Log.d(TAG, "API code | message: " + response.code() + " | " + response.message());
                    mResponseStudentTasksEnrolled.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<TasksModel>> call, Throwable t) {
                Log.d(TAG, "Error en la comunicación con API: " + t.getMessage());
            }
        });
    }

    public void getTasksExamns() {
        System.out.println(userRepo.getToken());
        tasksDAO.getStudentTasksExams(userRepo.getToken()).enqueue(new Callback<List<TasksModel>>() {
            @Override
            public void onResponse(Call<List<TasksModel>> call, Response<List<TasksModel>> response) {
                Log.d(TAG, "Url: " + call.request().url());
                Log.d(TAG, "req: " + call.request().toString());
                if (response.code() == 200) {
                    Log.d(TAG, "Se ha obtenido correctamente la lista de assignaturas.");
                    mResponseStudentTasksEnrolled.setValue(response.body());
                } else {
                    Log.d(TAG, "No se ha obtenido correctamente la lista de assignaturas.");
                    Log.d(TAG, "API code | message: " + response.code() + " | " + response.message());
                    mResponseStudentTasksEnrolled.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<TasksModel>> call, Throwable t) {
                Log.d(TAG, "Error en la comunicación con API: " + t.getMessage());
            }
        });
    }

}
