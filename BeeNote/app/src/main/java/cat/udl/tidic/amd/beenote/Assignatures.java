package cat.udl.tidic.amd.beenote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.List;

import cat.udl.tidic.amd.beenote.RecyclerView.Add_Assignatures_List;
import cat.udl.tidic.amd.beenote.RecyclerView.Assignatura_Adapter;
import cat.udl.tidic.amd.beenote.RecyclerView.AssignaturesDiffCallback;
import cat.udl.tidic.amd.beenote.ViewModels.AssiganturesViewModel;
import cat.udl.tidic.amd.beenote.models.CourseModel;
import cat.udl.tidic.amd.beenote.preferences.PreferencesProvider;

public class Assignatures extends ActivityWithNavView {

    public static final int INSERT_EVENT = 1;
    public static final int EDIT_EVENT = 2;
    public static final String TAG = "Assignatures";
    private SharedPreferences mPreferences;

    // private ImageButton searchButton;

    private DrawerLayout drawerLayout;
    private Button menu;
    private ImageButton addButton;

    private AssiganturesViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignatures);

        this.initViews();
    }

    private void initViews() {

        // @JordiMateoUdl: Creem la part del menu (Pare)
        super.initView(R.layout.activity_assignatures,
                R.id.drawer_assignatures,
                R.id.nav__assignatures);

        // @JordiMateoUdl: Creem la part no comuna de l'activity
        RecyclerView recyclerView = findViewById(R.id.activityMainRcyMain);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        final Assignatura_Adapter adapter = new Assignatura_Adapter(new AssignaturesDiffCallback());
        recyclerView.setAdapter(adapter);

        viewModel = new AssiganturesViewModel(getApplication());
        viewModel.obtainStudentCourses();

        viewModel.getStudentCourses().observe(this, new Observer<List<CourseModel>>() {
            @Override
            public void onChanged(List<CourseModel> courseModels) {
                adapter.submitList(courseModels);
            }
        });


    }
}
