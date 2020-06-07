package cat.udl.tidic.amd.beenote.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import cat.udl.tidic.amd.beenote.Repository.CourseRepo;
import cat.udl.tidic.amd.beenote.Repository.TasksRepository;
import cat.udl.tidic.amd.beenote.models.CourseModel;
import cat.udl.tidic.amd.beenote.models.TasksModel;

public class TasksViewModel extends AndroidViewModel {

    private TasksRepository repository;
    private MutableLiveData<List<TasksModel>> mTasks;

    public TasksViewModel(@NonNull Application application) {
        super(application);
        repository = new TasksRepository();
        mTasks = repository.getmResponseStudentTasksEnrolled();
    }

    public LiveData<List<TasksModel>> getStudentTasks(){
        return mTasks;
    }

    public void obtainStudentTasks(){
        repository.getStudentTasks();
    }

    public void obtainTasksExamns(){
        repository.getTasksExamns();
    }

}
