package RecyclerView_Tasks;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.LifecycleOwner;

import com.google.android.material.navigation.NavigationView;

import cat.udl.tidic.amd.beenote.ActivityWithNavView;
import cat.udl.tidic.amd.beenote.Assignatures;
import cat.udl.tidic.amd.beenote.MenuPrincipal;
import cat.udl.tidic.amd.beenote.Perfil_User;
import cat.udl.tidic.amd.beenote.R;

public class Add_Tasks_List extends ActivityWithNavView implements LifecycleOwner {

    public static final String EXTRA_ID =
            "cat.udl.tidic.amd.beenote.EXTRA_ID";
    public static final String EXTRA_TITLE =
            "cat.udl.tidic.amd.beenote.EXTRA_TITLE";

    private final static String TAG = "AddEditForm";


    private DrawerLayout drawerLayout;
    private EditText editTextTitle;
    private Button menu;
    private Button crear_tasca;
    private Button cancelar_asignatura;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__assignatures__list);

        menu = findViewById(R.id.Toolbar_Menu);
        cancelar_asignatura = findViewById(R.id.cancelar_asignatura);
        crear_tasca = findViewById(R.id.btn_grupos_crear);
        editTextTitle = findViewById(R.id.asignaturas_title);

        Intent intent = getIntent();


        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Event");
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));

        } else {
            setTitle("Add Event");
        }

        crear_tasca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveEvent();
            }
        });

        cancelar_asignatura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Add_Tasks_List.this, Assignatures.class);
                startActivity(intent);
            }
        });

        // El menu deslizante
        drawerLayout = findViewById(R.id.drawer_add_notes);
        final NavigationView navigationView = findViewById(R.id.nav__add_notes);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();

                int id = menuItem.getItemId();

                if (id == R.id.nav_account) {
                    Intent intent = new Intent(Add_Tasks_List.this, Perfil_User.class);
                    startActivity(intent);
                }
                else if(id == R.id.nav_menu){
                    Intent intent = new Intent(Add_Tasks_List.this, MenuPrincipal.class);
                    startActivity(intent);
                }
                else if(id == R.id.nav_notas){
                    Intent intent = new Intent(Add_Tasks_List.this, Assignatures.class);
                    startActivity(intent);
                }
                return true;
            }
        });

        // El icono del toolbar per anar el menu
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });


    }



    private void saveEvent() {
        String title = editTextTitle.getText().toString();


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
    }
}
