package cat.udl.tidic.amd.beenote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import cat.udl.tidic.amd.beenote.Repository.UserRepository;
import cat.udl.tidic.amd.beenote.ViewModels.Perfil_UserViewModel;
import cat.udl.tidic.amd.beenote.models.UserModel;
import cat.udl.tidic.amd.beenote.network.RetrofitClientInstance;
import cat.udl.tidic.amd.beenote.services.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.transition.Fade.IN;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class Perfil_User extends AppCompatActivity {

    private UserModel u = new UserModel();
    private TextView username;
    private TextView name;
    private TextView email;
    private EditText estudios;
    private EditText telefono;
    private DatePicker calendari;
    private Button editar;
    private Button guardar;
    private Button menu;

    private Perfil_UserViewModel perfil_userViewModel = new Perfil_UserViewModel();

    private final UserService userService = RetrofitClientInstance.getRetrofitInstance().create(UserService.class);

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil__user);

        estudios = findViewById(R.id.Perfil_estudios);
        telefono = findViewById(R.id.Perfil_telefono);
        calendari = findViewById(R.id.Perfil_calendario);
        username = findViewById(R.id.Perfil_username);
        name = findViewById(R.id.Perfil_name);
        email = findViewById(R.id.Perfil_email);
        editar = findViewById(R.id.Perfil_editar);
        guardar = findViewById(R.id.Perfil_guardar);
        menu = findViewById(R.id.Toolbar_Menu);

        // Desabilitar tots els edittext del layout
        disableform();

        // El menu deslizante
        drawerLayout = findViewById(R.id.drawer_perfil_usuari);
        final NavigationView navigationView = findViewById(R.id.nav__perfil_usuari);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();

                int id = menuItem.getItemId();

                if (id == R.id.nav_account) {

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

        //-----------------------------------
        String token = perfil_userViewModel.getToken();
        //System.out.println("Login - Toke " + token);

        Map<String, String> map = new HashMap<>();
        map.put("Authorization", token);

        final Call<UserModel> call = userService.getUserProfile(map);

        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                u = response.body();
                try {
                    assert u != null;
                    username.setText(u.getUsername());
                    name.setText(u.getName());
                    email.setText(u.getEmail());
                }catch (Exception e){
                    Log.e("Perfil user OK", response.message());
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Log.d("Perfil User Error",t.toString());
            }
        });

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               enableform();
            }
        });



        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String estudios_string = estudios.getText().toString();
                String telefono_string = telefono.getText().toString();
                Calendar c = Calendar.getInstance();
                c.set(calendari.getYear(), calendari.getMonth(), calendari.getDayOfMonth());

                disableform();

                estudios.setText(estudios_string);
                telefono.setText(telefono_string);



            }
        });

    }
    private void enableform(){
        estudios.setEnabled(true);
        telefono.setEnabled(true);
        calendari.setEnabled(true);
    }

    private void disableform(){
        estudios.setEnabled(false);
        telefono.setEnabled(false);
        calendari.setEnabled(false);
    }

}
