package cat.udl.tidic.amd.beenote;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;
import java.util.Map;

import cat.udl.tidic.amd.beenote.ViewModels.menuPrincipal_ViewModel;
import cat.udl.tidic.amd.beenote.models.TokenModel;
import cat.udl.tidic.amd.beenote.network.RetrofitClientInstance;
import cat.udl.tidic.amd.beenote.services.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuPrincipal extends AppCompatActivity {

    private Button cerrar_sesion;
    private Button perfil_usuario;
    private Button menu;

    private menuPrincipal_ViewModel menuPrincipal_viewModel = new menuPrincipal_ViewModel();
    private final UserService userService = RetrofitClientInstance.getRetrofitInstance().create(UserService.class);

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        cerrar_sesion =  findViewById(R.id.MenuPrincipal_CerrarSesion);
        perfil_usuario = findViewById(R.id.MenuPrincipal_PerfilUsuario);
        menu = findViewById(R.id.Toolbar_Menu);

        enableForm(true);

        // El menu deslizante
        drawerLayout = findViewById(R.id.drawer_menu_principal);
        final NavigationView navigationView = findViewById(R.id.nav_menu_principal);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();

                int id = menuItem.getItemId();

                if (id == R.id.nav_account) {
                    Intent intent = new Intent(MenuPrincipal.this, Perfil_User.class);
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

        // El boto de tancar sessio
        cerrar_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = menuPrincipal_viewModel.getToken();
                //System.out.println(token);
                TokenModel tokenModel = new TokenModel(token);

                Map<String, String> map = new HashMap<>();
                map.put("Authorization", token);

                Call<Void> call = userService.deleteToken(map,tokenModel);

                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        //System.out.println("MENU - Token eleiminat bee "+ response.toString());
                        menuPrincipal_viewModel.setToken("");
                        Intent intent = new Intent(MenuPrincipal.this, Login.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        //System.out.println("MENU - ERROR" + t.toString());
                    }
                });

            }
        });

        perfil_usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPrincipal.this, Perfil_User.class);
                startActivity(intent);
            }
        });
    }

    private void enableForm(boolean enable){
        cerrar_sesion.setEnabled(enable);
        perfil_usuario.setEnabled(enable);
        menu.setEnabled(enable);
    }

}
