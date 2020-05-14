package cat.udl.tidic.amd.beenote.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import cat.udl.tidic.amd.beenote.Repository.CourseRepo;
import cat.udl.tidic.amd.beenote.Repository.UserRepository;
import cat.udl.tidic.amd.beenote.models.CourseModel;

public class AssiganturesViewModel extends AndroidViewModel {

    private CourseRepo repository;
    private LiveData<List<CourseModel>> mCourses;
    private UserRepository userRepository;

    public AssiganturesViewModel(@NonNull Application application) {
        super(application);
        repository = new CourseRepo();
        mCourses = repository.getmResponseStudentCoursesEnrolled();
        this.userRepository = new UserRepository();
    }

    public LiveData<List<CourseModel>> getStudentCourses(){
        return mCourses;
    }

    public void obtainStudentCourses(){
        repository.getStudentCourses();
    }

    public void removeCourse(CourseModel course){
        this.repository.delete(course);
    }

    public String getToken(){
        return userRepository.getToken();
    }

    public String getPositionID(){
        return userRepository.getPositionID();
    }

    public String getUserID()
    {
        return userRepository.getUserID();
    }

}
