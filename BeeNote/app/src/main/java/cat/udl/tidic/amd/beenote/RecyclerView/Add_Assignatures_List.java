package cat.udl.tidic.amd.beenote.RecyclerView;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.LifecycleOwner;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import cat.udl.tidic.amd.beenote.ActivityWithNavView;
import cat.udl.tidic.amd.beenote.Assignatures;
import cat.udl.tidic.amd.beenote.R;
import cat.udl.tidic.amd.beenote.dao.CourseDAOI;
import cat.udl.tidic.amd.beenote.models.CourseModel2;
import cat.udl.tidic.amd.beenote.network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Add_Assignatures_List extends ActivityWithNavView implements LifecycleOwner {

    public static final String EXTRA_ID =
            "cat.udl.tidic.amd.beenote.EXTRA_ID";
    public static final String EXTRA_TITLE =
            "cat.udl.tidic.amd.beenote.EXTRA_TITLE";

    private final static String TAG = "AddEditForm";


    private DrawerLayout drawerLayout;
    private EditText editTextTitle;
    private EditText textDesciptionCourse;
    private EditText textAddCourse;
    private Button menu;
    private Button crear_asignatura;
    private Button cancelar_asignatura;
    private TextView text_error;


    private CourseDAOI courseDAOI = RetrofitClientInstance.getRetrofitInstance().create(CourseDAOI.class);

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__assignatures__list);

        // Creem la part del menu (Pare)
        super.initView(R.layout.activity_add__assignatures__list,
                R.id.drawer_add_notes,
                R.id.nav__add_notes,0);

        menu = findViewById(R.id.Toolbar_Menu);
        cancelar_asignatura = findViewById(R.id.cancelar_asignatura);
        crear_asignatura = findViewById(R.id.crear_asignatura);
        editTextTitle = findViewById(R.id.asignaturas_title);
        text_error = findViewById(R.id.assignatures_text_error);
        textAddCourse = findViewById(R.id.textAddCourse);
        textDesciptionCourse = findViewById(R.id.textDesciptionCourse);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Event");
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));

        } else {
            setTitle("Add Event");
        }

        crear_asignatura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (textAddCourse.getText().toString().equals("")) {
                    text_error.setText("Pon un nombre el curso a crear");
                }
                else {
                    saveEventAPI();
                    saveEvent();
                }
            }
        });

        cancelar_asignatura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Add_Assignatures_List.this, Assignatures.class);
                startActivity(intent);
            }
        });

    }

    private void saveEventAPI() {
        String description = "";
        if (textDesciptionCourse.getText().toString().equals("")) {
            description = "No description";

        } else {
            description = textDesciptionCourse.getText().toString();
        }
        CourseModel2 model = new CourseModel2(textAddCourse.getText().toString(), description);
        final Call<Void> call = courseDAOI.postCourseWithoutId("656e50e154865a5dc469b80437ed2f963b8f58c8857b66c9bf", model);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("AddCourse", "OK" + response.toString());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("AddCure", Objects.requireNonNull(t.getMessage()));
            }
        });
     }


    private void saveEvent() {
        String title = textAddCourse.getText().toString();


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
        Intent intent = new Intent(Add_Assignatures_List.this, Assignatures.class);
        startActivity(intent);
    }
}
