package cat.udl.tidic.amd.beenote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import cat.udl.tidic.amd.beenote.ViewModels.MenuPrincipal_ViewModel;

public class MenuPrincipal extends AppCompatActivity {

    private Button cerrar_sesion;
    private Button perfil_usuario;
    private MenuPrincipal_ViewModel menuPrincipal_viewModel = new MenuPrincipal_ViewModel();

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
