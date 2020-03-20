package cat.udl.tidic.amd.beenote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import java.util.HashMap;
import java.util.Map;

import cat.udl.tidic.amd.beenote.ViewModels.MenuPrincipal_ViewModel;
import cat.udl.tidic.amd.beenote.ViewModels.Perfil_UserViewModel;
import cat.udl.tidic.amd.beenote.models.UserModel;
import cat.udl.tidic.amd.beenote.network.RetrofitClientInstance;
import cat.udl.tidic.amd.beenote.services.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuPrincipal extends AppCompatActivity {

    private Button cerrar_sesion;
    private Button perfil_usuario;
    private MenuPrincipal_ViewModel menuPrincipal_viewModel = new MenuPrincipal_ViewModel();

    private MenuPrincipal_ViewModel perfil_userViewModel = new MenuPrincipal_ViewModel();
    private final UserService userService = RetrofitClientInstance.getRetrofitInstance().create(UserService.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        cerrar_sesion =  findViewById(R.id.MenuPrincipal_CerrarSesion);
        perfil_usuario = findViewById(R.id.MenuPrincipal_PerfilUsuario);

        cerrar_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuPrincipal_viewModel.setToken("");
                menuPrincipal_viewModel.setRegistrado("");

                String token = perfil_userViewModel.getToken();

                Map<String, String> map = new HashMap<>();
                map.put("Authorization", token);

                Call<Void> call = userService.deleteToken(map);

                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        System.out.println("MENU - Token eleiminat bee "+ response.toString());
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        System.out.println("MENU - ERROR" + t.toString());
                    }
                });

                Intent intent = new Intent(MenuPrincipal.this, Login.class);
                startActivity(intent);
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
}
