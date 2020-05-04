package cat.udl.tidic.amd.beenote.Repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import cat.udl.tidic.amd.beenote.dao.CourseDAOI;
import cat.udl.tidic.amd.beenote.dao.CourseDAOImpl;
import cat.udl.tidic.amd.beenote.models.CourseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseRepo {

    private CourseDAOI courseDAO;
    private final String TAG = "CourseRepo";
    private UserRepository userRepo;

    private MutableLiveData<List<CourseModel>> mResponseStudentCoursesEnrolled;

    public CourseRepo() {
        courseDAO = new CourseDAOImpl();
        userRepo = new UserRepository();
        this.mResponseStudentCoursesEnrolled = new MutableLiveData<>();
    }

    public MutableLiveData<List<CourseModel>> getmResponseStudentCoursesEnrolled() {
        return mResponseStudentCoursesEnrolled;
    }

    public void setmResponseStudentCoursesEnrolled(MutableLiveData<List<CourseModel>> mResponseStudentCoursesEnrolled) {
        this.mResponseStudentCoursesEnrolled = mResponseStudentCoursesEnrolled;
    }

    public void getStudentCourses(){
        courseDAO.getStudentCourses(userRepo.getToken()).enqueue(new Callback<List<CourseModel>>() {
            @Override
            public void onResponse(Call<List<CourseModel>> call, Response<List<CourseModel>> response) {
                Log.d(TAG, "Url: " + call.request().url());
                Log.d(TAG, "req: " + call.request().toString());
                if(response.code() == 200) {
                    Log.d(TAG, "Se ha obtenido correctamente la lista de assignaturas.");
                    mResponseStudentCoursesEnrolled.setValue(response.body());
                }else{
                    Log.d(TAG, "No se ha obtenido correctamente la lista de assignaturas.");
                    Log.d(TAG, "API code | message: " + response.code()+ " | " +response.message());
                    mResponseStudentCoursesEnrolled.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<CourseModel>> call, Throwable t) {
                Log.d(TAG, "Error en la comunicación con API: " + t.getMessage());
            }
    });
    }

}
