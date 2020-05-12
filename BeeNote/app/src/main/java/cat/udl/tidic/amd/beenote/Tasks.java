package cat.udl.tidic.amd.beenote;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import RecyclerView_Tasks.Add_Tasks_List;
import RecyclerView_Tasks.TasksDiffCallback;
import RecyclerView_Tasks.Tasks_Adapter;
import cat.udl.tidic.amd.beenote.RecyclerView.Add_Assignatures_List;
import cat.udl.tidic.amd.beenote.ViewModels.TasksViewModel;
import cat.udl.tidic.amd.beenote.dao.CourseDAOI;
import cat.udl.tidic.amd.beenote.dao.TasksDAOI;
import cat.udl.tidic.amd.beenote.models.TasksModel;
import cat.udl.tidic.amd.beenote.network.RetrofitClientInstance;
import cat.udl.tidic.amd.beenote.preferences.PreferencesProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Tasks extends ActivityWithNavView {

    private Button addTask;
    public static final int INSERT_ASSIGNATURA = 1;
    public static final int EDIT_ASSIGNATURA = 2;
    public static final String TAG = "Assignatures";
    private SharedPreferences mPreferences;


    //private Button addCourse;
    private TasksViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grups);

        this.mPreferences = PreferencesProvider.providePreferences();
        this.initViews();
    }


    private void initViews() {
        // Creem la part del menu (Pare)
        super.initView(R.layout.activity_grups,
                R.id.drawer_grups,
                R.id.nav__grups);

        addTask = findViewById(R.id.btn_grupos_crear);

        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tasks.this, Add_Tasks_List.class);
                startActivity(intent);
            }
        });
        // TODO RecyclerView Course
        RecyclerView recyclerView = findViewById(R.id.activityRVTasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        final Tasks_Adapter adapter = new Tasks_Adapter(new TasksDiffCallback());
        recyclerView.setAdapter(adapter);

        /*
        adapter.setOnItemClickListener(new Tasks_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(TasksModel event) {
                Intent intent = new Intent(Tasks.this, Tasks.class);
                startActivity(intent);
            }
        });
        */
        viewModel = new TasksViewModel(getApplication());
        viewModel.obtainStudentTasks();

        viewModel.getStudentTasks().observe(this, new Observer<List<TasksModel>>() {
            @Override
            public void onChanged(List<TasksModel> tasksModels) {
                adapter.submitList(tasksModels);
            }
        });


    }
}
