package cat.udl.tidic.amd.beenote;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import cat.udl.tidic.amd.beenote.Dialog.TerminosCondiciones;
import cat.udl.tidic.amd.beenote.ViewModels.Registrar_ViewModel;
import cat.udl.tidic.amd.beenote.models.UserModel;
import cat.udl.tidic.amd.beenote.network.RetrofitClientInstance;
import cat.udl.tidic.amd.beenote.services.UserService;
import cat.udl.tidic.amd.beenote.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Registrar extends AppCompatActivity {

    private TextView salida;
    private Button post;
    private Button login;
    private TextView password;
    private TextView passwordConfirmacio;
    private TextView email;
    private ProgressBar registrar_progressbar;
    private CheckBox aceptarTerminos;
    private TextView terminosCondiciones;

    private UserService userService = RetrofitClientInstance.getRetrofitInstance().create(UserService.class);
    private Registrar_ViewModel registrar_viewModel = new Registrar_ViewModel();
    //Aqui creem el Head de la peticio (Postman)
    private Map<String, String> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        salida = findViewById(R.id.Salida);
        post = findViewById(R.id.Registrar);
        login = findViewById(R.id.Button_Login);
        password = findViewById(R.id.Registrar_password);
        email = findViewById(R.id.Registarr_email);
        registrar_progressbar = findViewById(R.id.Registrar_progressBar);
        passwordConfirmacio = findViewById(R.id.registrar_password_2);
        aceptarTerminos = findViewById(R.id.Registrar_checkBox);
        terminosCondiciones = findViewById(R.id.Registrar_LeerMas);

        enableForm(true);

        //Aqui omplim el Head de la peticio (Postman)
        map.put("Content-Type", "application/json");

        terminosCondiciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TerminosCondiciones dialog = TerminosCondiciones.newInstance(Registrar.this);
                dialog.setCancelable(false);
                dialog.show(getSupportFragmentManager(),"TerminosCondicionesTag");
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                if (aceptarTerminos.isChecked()) {

                    registrar_progressbar.setVisibility(View.VISIBLE);
                    String password = Registrar.this.password.getText().toString();
                    String password2 = passwordConfirmacio.getText().toString();

                    if (password.equals(password2)) {
                        if(password.length()>=8 && !password.equals("12345678")) {
                            // ------> Per encriptar la contrasenya
                            // Course API requires passwords in sha-256 in passlib format so:
                            String salt = registrar_viewModel.getSalt();
                            int numero = registrar_viewModel.getNumero();
                            String encode_hash = Utils.encode(password, salt, numero);
                            System.out.println("PASSWORD_ENCRYPTED " + encode_hash);

                            // ------> Per agafar la apart del davant del correu, i ficarla com a username
                            String email = Registrar.this.email.getText().toString();

                            boolean trobat = false;
                            int posicio = email.indexOf("@");
                            String username = "";
                            for (int i = 0; i < posicio; i++) {
                                username = username + email.charAt(i);
                            }
                            //System.out.println("username " + username);

                            // ------> Creem un Usermodel amb les variables que demana la API
                            // UserModel model = new UserModel("joan5234","1234","joanrialp@gmail.com","Joan","Rialp","M");
                            UserModel model = new UserModel(username, encode_hash, email);

                            // Fem la crida a la API amb el Head i Body
                            final Call<Void> call = userService.postUserProfile(map, model);

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    call.enqueue(new Callback<Void>() {
                                        @SuppressLint("SetTextI18n")
                                        @Override
                                        public void onResponse(Call<Void> call, Response<Void> response) {

                                            Log.d("MainActivity", response.toString());
                                            Log.d("MainActiviti ok", response.message());

                                            System.out.println(response.message());

                                            if (response.message().equals("Bad Request")) {
                                                registrar_progressbar.setVisibility(View.INVISIBLE);
                                                salida.setText("Error el registrarse: Usuari ya existente o campo invalido");
                                            } else if (response.message().equals("Internal Server Error")) {
                                                registrar_progressbar.setVisibility(View.INVISIBLE);
                                                salida.setText("Error del Servidor");
                                            } else {
                                                registrar_progressbar.setVisibility(View.INVISIBLE);
                                                //salida.setText("OK");

                                                Intent intent = new Intent(Registrar.this, Login.class);
                                                intent.putExtra("registrar", "Registrado con exito");
                                                startActivity(intent);
                                            }

                                        }

                                        @SuppressLint("SetTextI18n")
                                        @Override
                                        public void onFailure(Call<Void> call, Throwable t) {

                                            Log.d("MainActivity333", t.getMessage());
                                            Log.d("MainActivity233", t.toString());

                                            registrar_progressbar.setVisibility(View.INVISIBLE);
                                            salida.setText("Error Conexion");
                                        }
                                    });
                                }
                            }, 0);   //0 seconds

                        } else{
                            salida.setText("Las contraseñas es poco segura");
                            registrar_progressbar.setVisibility(View.INVISIBLE);
                        }
                    } else {
                        salida.setText("Las contraseñas no coinciden");
                        registrar_progressbar.setVisibility(View.INVISIBLE);
                    }
                }
                else{
                    salida.setText("Acepta los terminos");
                    registrar_progressbar.setVisibility(View.INVISIBLE);
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registrar.this, Login.class);
                startActivity(intent);
            }
        });


    }

    private void enableForm(boolean enable){
        salida.setEnabled(enable);
        post.setEnabled(enable);
        login.setEnabled(enable);
        password.setEnabled(enable);
        passwordConfirmacio.setEnabled(enable);
        email.setEnabled(enable);
        registrar_progressbar.setEnabled(enable);
        aceptarTerminos.setEnabled(enable);
        terminosCondiciones.setEnabled(enable);
    }

}
