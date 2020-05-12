package cat.udl.tidic.amd.beenote;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cat.udl.tidic.amd.beenote.RecyclerView.Add_Assignatures_List;
import cat.udl.tidic.amd.beenote.RecyclerView.Assignatura_Adapter;
import cat.udl.tidic.amd.beenote.RecyclerView.AssignaturesDiffCallback;
import cat.udl.tidic.amd.beenote.ViewModels.AssiganturesViewModel;
import cat.udl.tidic.amd.beenote.ViewModels.CourseViewModel;
import cat.udl.tidic.amd.beenote.dao.CourseDAOI;
import cat.udl.tidic.amd.beenote.models.CourseModel;
import cat.udl.tidic.amd.beenote.models.CourseModel2;
import cat.udl.tidic.amd.beenote.network.RetrofitClientInstance;
import cat.udl.tidic.amd.beenote.preferences.PreferencesProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Assignatures extends ActivityWithNavView {

    public static final int INSERT_ASSIGNATURA = 1;
    public static final int EDIT_ASSIGNATURA = 2;
    public static final String TAG = "Assignatures";
    private SharedPreferences mPreferences;

    private Button addCourse;
    private CourseDAOI courseDAOI = RetrofitClientInstance.getRetrofitInstance().create(CourseDAOI.class);
    @NonNull
    private CourseViewModel courseViewModel = new CourseViewModel();

    private AssiganturesViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignatures);

        this.mPreferences = PreferencesProvider.providePreferences();
        this.initViews();
    }

    private void initViews() {
        // Creem la part del menu (Pare)
        super.initView(R.layout.activity_assignatures,
                R.id.drawer_assignatures,
                R.id.nav__assignatures);

        addCourse = findViewById(R.id.addCourse);

        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO ADD COURSE 2 FORMATS
                Intent intent = new Intent(Assignatures.this, Add_Assignatures_List.class);
                startActivity(intent);
            }
        });

        // TODO RecyclerView Course
        RecyclerView recyclerView = findViewById(R.id.activityMainRcyMain);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        final Assignatura_Adapter adapter = new Assignatura_Adapter(new AssignaturesDiffCallback());
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new Assignatura_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(CourseModel event) {
                Intent intent = new Intent(Assignatures.this, Assignatures.class);
                startActivity(intent);
            }
        });

        viewModel = new AssiganturesViewModel(getApplication());
        viewModel.obtainStudentCourses();

        viewModel.getStudentCourses().observe(this, new Observer<List<CourseModel>>() {
            @Override
            public void onChanged(List<CourseModel> courseModels) {
                adapter.submitList(courseModels);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                viewModel.removeCourse(adapter.getEventAt(viewHolder.getAdapterPosition()));

                //TODO falla eliminar
                final Call<Void> call = courseDAOI.deleteCourse(viewModel.getToken(), "1", viewModel.getUserID());
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Log.e("Course eliminado", response.message());
                        Toast.makeText(getApplicationContext(), "Curso eliminado", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.e("Course eliminado ERROR", t.toString());
                    }
                });

            }
        }).attachToRecyclerView(recyclerView);
    }
}
