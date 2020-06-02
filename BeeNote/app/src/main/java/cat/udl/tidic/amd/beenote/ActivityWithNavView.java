package cat.udl.tidic.amd.beenote;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;
import java.util.Map;

import RecyclerView_Tasks.Add_Tasks_List;
import cat.udl.tidic.amd.beenote.ViewModels.menuPrincipal_ViewModel;
import cat.udl.tidic.amd.beenote.models.TokenModel;
import cat.udl.tidic.amd.beenote.models.UserModel;
import cat.udl.tidic.amd.beenote.network.RetrofitClientInstance;
import cat.udl.tidic.amd.beenote.services.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityWithNavView extends AppCompatActivity {

    private Button menu;
    private Button ajustes;
    private DrawerLayout drawerLayout;

    private menuPrincipal_ViewModel menuPrincipal_viewModel = new menuPrincipal_ViewModel();
    private final UserService userService = RetrofitClientInstance.getRetrofitInstance().create(UserService.class);
    private Menu SubMenu;

    private Map<String, String> map = new HashMap<>();
    private UserModel userModel = new UserModel();
    private TextView username;
    private TextView name;
    private TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    protected void initView(int layout, int drawer, int nav_view, int pantalla){
        setContentView(layout);
        menu = findViewById(R.id.Toolbar_Menu);
        ajustes = findViewById(R.id.Toolbar_Ajustes);
        drawerLayout = findViewById(drawer);
        final NavigationView navigationView = findViewById(nav_view);

        View headerView = navigationView.getHeaderView(0);
        username = headerView.findViewById(R.id.toolbar_username);
        name = headerView.findViewById(R.id.toolbar_Nombre);
        email = headerView.findViewById(R.id.toolbar_Correo);

        my_header();

        System.out.println("Menu: " + layout);

        if(pantalla==1)
        {
            navigationView.getMenu().getItem(0).setChecked(true);
        }
        if(pantalla == 2)
        {
            navigationView.getMenu().getItem(2).setChecked(true);
        }
        if(pantalla == 3)
        {
            navigationView.getMenu().getItem(2).setChecked(true);
        }
        if(pantalla == 4)
        {
            navigationView.getMenu().getItem(1).setChecked(true);
        }
        if(pantalla == 5)
        {
            navigationView.getMenu().getItem(4).setChecked(true);
        }


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                //drawerLayout.closeDrawers();

                int id = menuItem.getItemId();

                if (id == R.id.nav_account) {
                    drawerLayout.closeDrawers();
                    navigateToAccount();
                }
                else if(id == R.id.nav_menu){
                    drawerLayout.closeDrawers();
                    navigateToMenuPrincipal();
                }
                else if (id == R.id.nav_notas) {
                    drawerLayout.closeDrawers();
                    navigateToAssignatures();
                }
                else if (id == R.id.nav_tareas) {
                    drawerLayout.closeDrawers();
                    navigateToTask();
                }
                else if (id == R.id.nav_grupos) {

                    SubMenu = navigationView.getMenu();
                    MenuItem submenu= SubMenu.findItem(R.id.nav_añadirgrupo);
                    submenu.setVisible(true);

                }
                else if (id == R.id.nav_añadirgrupo) {
                    drawerLayout.closeDrawers();
                }

                return true;
            }

        });

        ajustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popupMenu = popup_menu();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();

                        if (id == R.id.nav_cerrar_sesion) {
                            cerrarSesion();
                        }
                        return true;
                    }
                });
                popupMenu.show();
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

    // Funcion de poner la informacion del usuario el Header del NavegationView
    private void my_header()
    {
        String token = menuPrincipal_viewModel.getToken();
        map.put("Authorization", token);

        Call<UserModel> call = userService.getUserProfile(map);

        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                userModel = response.body();
                //System.out.println("Login - Toke " + response.body());
                try {
                    assert userModel != null;
                    System.out.println("Username " + userModel.getUsername());
                    username.setText(userModel.getUsername());
                    System.out.println("Get username " + username.getText());
                    name.setText(userModel.getName());
                    email.setText(userModel.getEmail());
                }catch (Exception e){
                    Log.e("My_Header OK", response.message());
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Log.d("My_Header Error",t.toString());
            }
        });
    }


    // Funció del cerrar sesion del menú d'ajustes
    private void cerrarSesion(){
        String token = menuPrincipal_viewModel.getToken();
        //System.out.println(token);
        TokenModel tokenModel = new TokenModel(token);

        Map<String, String> map = new HashMap<>();
        map.put("Authorization", token);

        Call<Void> call = userService.deleteToken(map,tokenModel);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                //System.out.println("MENU - Token eliminat bee "+ response.toString());
                menuPrincipal_viewModel.setToken("");
                menuPrincipal_viewModel.setMail("");
                navigateToLogin();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                //System.out.println("MENU - ERROR" + t.toString());
            }
        });
    }

    public PopupMenu popup_menu()
    {
        PopupMenu popupMenu = new PopupMenu(this, ajustes);
        popupMenu.getMenuInflater().inflate(R.menu.menu_ajustes, popupMenu.getMenu());
        return popupMenu;
    }
    public void navigateToLogin(){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
    public void navigateToMenuPrincipal(){
        Intent intent = new Intent(this, MenuPrincipal.class);
        startActivity(intent);
    }
    public void navigateToAssignatures(){
        Intent intent = new Intent(this, Assignatures.class);
        startActivity(intent);
    }
    public void navigateToAccount(){
        Intent intent = new Intent(this, Perfil_User.class);
        startActivity(intent);
    }

    private void navigateToTask(){
        Intent intent = new Intent(this, Tasks.class);
        startActivity(intent);

    }

}
