package RecyclerView_Tasks;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.LifecycleOwner;

import java.util.Objects;

import cat.udl.tidic.amd.beenote.ActivityWithNavView;
import cat.udl.tidic.amd.beenote.R;
import cat.udl.tidic.amd.beenote.Tasks;
import cat.udl.tidic.amd.beenote.models.CourseModel2;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Add_Tasks_List extends  ActivityWithNavView implements LifecycleOwner {

    public static final String EXTRA_ID =
            "cat.udl.tidic.amd.beenote.EXTRA_ID";
    public static final String EXTRA_TITLE =
            "cat.udl.tidic.amd.beenote.EXTRA_TITLE";

    private final static String TAG = "AddEditForm";


    private DrawerLayout drawerLayout;
    private EditText editTextTitle;
    private EditText textDesciptionTask;
    private EditText textAddTaskstart;
    private EditText textAddTaskend;
    private TextView TextTitle;
    private EditText textAddTask;
    private Button menu;
    private Button crear_tasca;
    private Button cancelar_tasca;
    private TextView text_error;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__tasks__list);

        // Creem la part del menu (Pare)
        super.initView(R.layout.activity_add__tasks__list,
                R.id.drawer_add_task,
                R.id.nav__add_task);

        menu = findViewById(R.id.Toolbar_Menu);
        cancelar_tasca = findViewById(R.id.cancelar_task);
        crear_tasca = findViewById(R.id.crear_task);
        TextTitle = findViewById(R.id.add_task_title);
        textDesciptionTask = findViewById(R.id.textDesciptionTask);
        textAddTaskstart = findViewById(R.id.textAddTaskstart);
        textAddTaskend = findViewById(R.id.textAddTaskend);

        text_error = findViewById(R.id.task_text_error);
        textAddTask = findViewById(R.id.textAddTask);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Task");
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));

        } else {
            setTitle("Add Task");
        }


        cancelar_tasca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Add_Tasks_List.this, Tasks.class);
                startActivity(intent);
            }
        });

        crear_tasca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (textAddTask.getText().toString().equals("")) {
                    text_error.setText("Pon un nombre la tarea a crear");
                } else {
                    //saveTaskAPI();
                    saveEvent();
                }
            }
        });

        /*private void saveTaskAPI() {
            String description = "";
            if (textDesciptionTask.getText().toString().equals("")) {
                description = "No description";

            } else {
                description = textDesciptionTask.getText().toString();
            }
            CourseModel2 model = new CourseModel2(textAddTask.getText().toString(), description);
            final Call<Void> call = tasksDAOI.postCourseWithoutId("656e50e154865a5dc469b80437ed2f963b8f58c8857b66c9bf", model);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Log.d("AddTask", "OK" + response.toString());
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.d("AddTask", Objects.requireNonNull(t.getMessage()));
                }
            });*/
        }

        private void saveEvent() {
            String title = textAddTask.getText().toString();


            if (title.trim().isEmpty()) {
                Toast.makeText(this,
                        "Porfavor, inserte un nombre ", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent data = new Intent();
            data.putExtra(EXTRA_TITLE, title);

            int id = getIntent().getIntExtra(EXTRA_ID, -1);
            if (id != -1) {
                data.putExtra(EXTRA_ID, id);
            }

            setResult(RESULT_OK, data);

            finish();
            Intent intent = new Intent(Add_Tasks_List.this, Tasks.class);
            startActivity(intent);
        }

}
