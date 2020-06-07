package cat.udl.tidic.amd.beenote;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import RecyclerView_Tasks.Add_Tasks_List;
import RecyclerView_Tasks.TasksDiffCallback;
import RecyclerView_Tasks.Tasks_Adapter;
import cat.udl.tidic.amd.beenote.ViewModels.TasksViewModel;
import cat.udl.tidic.amd.beenote.models.Task;
import cat.udl.tidic.amd.beenote.models.TasksModel;
import cat.udl.tidic.amd.beenote.preferences.PreferencesProvider;

public class Tasks extends ActivityWithNavView {

    private Button addTask;
    public static final int INSERT_TASK = 1;
    public static final int EDIT_TASK = 2;
    public static final String TAG = "Tasks";
    private SharedPreferences mPreferences;
    private Button filtrar;
    private TextView nombreFiltro;
    private ProgressBar tascas_progressbar;

    //private Button addCourse;
    private TasksViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        this.mPreferences = PreferencesProvider.providePreferences();
        this.initViews();
    }


    private void initViews() {
        // Creem la part del menu (Pare)
        super.initView(R.layout.activity_tasks,
                R.id.drawer_tasks,
                R.id.nav__tasks,5);

        addTask = findViewById(R.id.btn_new_task);
        filtrar = findViewById(R.id.tasks_filter);
        nombreFiltro = findViewById(R.id.filter_tittle);
        tascas_progressbar = findViewById(R.id.Tascas_progressBar);

        tascas_progressbar.setVisibility(View.VISIBLE);
        layoutFocus(false);

        PopupMenu popupMenu = new PopupMenu(Tasks.this,filtrar);
        popupMenu.getMenuInflater().inflate(R.menu.menu_task_filter, popupMenu.getMenu());

        filtrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        item.setChecked(true);

                        if (id == R.id.nav_examenes) {
                            viewModel.obtainTasksExamns();
                            nombreFiltro.setText("Examenes");
                        }
                        else if (id == R.id.nav_tascas) {
                            viewModel.obtainStudentTasks();
                            nombreFiltro.setText("Tareas Generales");
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

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
                tascas_progressbar.setVisibility(View.INVISIBLE);
                layoutFocus(true);
                adapter.submitList(tasksModels);
            }
        });
    }

    public void layoutFocus(Boolean enable){
        filtrar.setEnabled(enable);
        addTask.setEnabled(enable);
    }
}
