package cat.udl.tidic.amd.beenote.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import cat.udl.tidic.amd.beenote.Repository.CourseRepo;
import cat.udl.tidic.amd.beenote.models.CourseModel;

public class AssiganturesViewModel extends AndroidViewModel {

    private CourseRepo repository;
    private LiveData<List<CourseModel>> mCourses;

    public AssiganturesViewModel(@NonNull Application application) {
        super(application);
        repository = new CourseRepo();
        mCourses = repository.getmResponseStudentCoursesEnrolled();
    }

    public LiveData<List<CourseModel>> getStudentCourses(){
        return mCourses;
    }

    public void obtainStudentCourses(){
        repository.getStudentCourses();
    }

}
