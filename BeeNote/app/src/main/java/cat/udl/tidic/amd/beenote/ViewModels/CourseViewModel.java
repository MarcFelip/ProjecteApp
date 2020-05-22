package cat.udl.tidic.amd.beenote.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import cat.udl.tidic.amd.beenote.Repository.CourseRepo;
import cat.udl.tidic.amd.beenote.Repository.UserRepository;
import cat.udl.tidic.amd.beenote.models.Course;
import cat.udl.tidic.amd.beenote.models.CourseModel;

public class CourseViewModel extends ViewModel {

    private UserRepository userRepository;
    private CourseRepo courseRepo;

    public CourseViewModel() {
        this.courseRepo = new CourseRepo();
    }

    public void getCourse(String course_id){
        this.courseRepo.getCourse(course_id);
    }

    public MutableLiveData<Course> getCourseLiveData(){
        return this.courseRepo.getmResponseCourseInfo();
    }
}
