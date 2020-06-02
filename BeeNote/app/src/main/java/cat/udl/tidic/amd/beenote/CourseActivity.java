package cat.udl.tidic.amd.beenote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.app.progresviews.ProgressWheel;

import cat.udl.tidic.amd.beenote.ViewModels.CourseViewModel;
import cat.udl.tidic.amd.beenote.adapters.TaskAdapter;
import cat.udl.tidic.amd.beenote.adapters.TaskDiffCallback;
import cat.udl.tidic.amd.beenote.models.Course;
import cat.udl.tidic.amd.beenote.preferences.PreferencesProvider;
import params.com.stepprogressview.StepProgressView;

public class CourseActivity extends ActivityWithNavView {


    private CourseViewModel courseViewModel;
    private TextView textViewTaskSubscribed;
    private TextView textViewCourseSchedules;
    private TextView textViewCourseTasks;
    private TextView textViewCourseExams;
    private ProgressWheel progressWheelMark;
    private StepProgressView courseProgress;
    private TextView courseName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignatures);
        initViews();
    }


    private void initViews() {
        super.initView(R.layout.course_activity,
                R.id.drawer_assignatures,
                R.id.nav__assignatures,3);

        textViewTaskSubscribed = findViewById(R.id.textViewTaskSubscribed);
        textViewCourseSchedules = findViewById(R.id.textViewCourseSchedules);
        textViewCourseTasks = findViewById(R.id.textViewCourseTasks);
        textViewCourseExams = findViewById(R.id.textViewCourseExams);
        progressWheelMark = findViewById(R.id.current_mark);
        courseProgress = findViewById(R.id.current_progress);
        courseName = findViewById(R.id.textViewCourseName);


        progressWheelMark.setDefText("Nota");

        RecyclerView recyclerView = findViewById(R.id.courseTaskRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        TaskAdapter taskAdapter = new TaskAdapter(new TaskDiffCallback());
        recyclerView.setAdapter(taskAdapter);



        courseViewModel = new CourseViewModel();
        courseViewModel.getCourse("1");
        courseViewModel.getCourseLiveData().observe(this, new Observer<Course>() {
            @Override
            public void onChanged(Course course) {
                courseName.setText(course.getName());
                textViewTaskSubscribed.setText(String.valueOf(course.getSubscribed_tasks()));
                textViewCourseSchedules.setText(String.valueOf(course.getSchedules().size()));
                textViewCourseTasks.setText(String.valueOf(course.getTasks().size()));
                textViewCourseExams.setText(String.valueOf(course.getExams().size()));
                int progress = (int) ((course.getMark()*100)/10);
                progressWheelMark.setPercentage(progress);
                courseProgress.setTotalProgress(course.getTasks().size());
                courseProgress.setCurrentProgress(course.getCompleted_tasks());
                progressWheelMark.setStepCountText(progress + "%");
                taskAdapter.submitList(course.getTasks());
            }
        });
    }
}
