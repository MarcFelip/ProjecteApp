package cat.udl.tidic.amd.beenote;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import cat.udl.tidic.amd.beenote.ViewModels.LoginViewModel;
import cat.udl.tidic.amd.beenote.network.RetrofitClientInstance;
import cat.udl.tidic.amd.beenote.services.UserService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private Button login;
    private TextView Username;
    private TextView Password;
    private  TextView MissatgeError;
    private Button registrar;
    private ProgressBar login_progressBar;
    private TextView login_registrado;

    private String autoritzacio;
    private UserService userService;

    @NonNull
    private LoginViewModel loginviewmodel = new LoginViewModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        if (loginviewmodel.getToken().equals("")) {

            login = findViewById(R.id.Button_Login);
            registrar = findViewById(R.id.Button_Register);
            Username = findViewById(R.id.Login_Username);
            Password = findViewById(R.id.Login_password);
            MissatgeError = findViewById(R.id.Login_Error);
            login_progressBar = findViewById(R.id.Login_ProgressBar);
            login_registrado = findViewById(R.id.Login_Registrado);

            userService = RetrofitClientInstance.getRetrofitInstance().create(UserService.class);

            login_registrado.setText(loginviewmodel.getResgistrado());

            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    login_progressBar.setVisibility(View.VISIBLE);
                    //System.out.println("Login Username "+ Username.getText().toString());

                    autoritzacio = Username.getText().toString() + ":" + Password.getText().toString();
                    byte[] data = null;

                    data = autoritzacio.getBytes(StandardCharsets.UTF_8);

                    // .trim() --> perque no hi hagin caracaters raros
                    String encoding = "Authentication: " + Base64.encodeToString(data, Base64.DEFAULT);
                    final Call<ResponseBody> call = userService.postCreateToken(encoding.trim());

                    System.out.println("Entrar " + encoding.trim());

                    // ----> Para que l'app se espere 3 segundos con el progressbar mientras espera la peticion a la BD
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            call.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    try {
                                        //assert response.body() != null;

                                        if (response.body() == null) {
                                            login_progressBar.setVisibility(View.INVISIBLE);
                                            MissatgeError.setText("Invalido nombre de usuario o contraseña");
                                            //System.out.println("Null "+response.errorBody().string());
                                        } else {
                                            //System.out.println(response.body().string().split(":")[1]);

                                            String token = response.body().string().split(":")[1];

                                            // -----> SuperString de java per treure el primer i ultim caracter
                                            token = token.substring(2, token.length() - 2);

                                            //System.out.println("Token"+token);

                                            loginviewmodel.Token(token);

                                            loginviewmodel.setRegistrado("");
                                            login_progressBar.setVisibility(View.INVISIBLE);
                                            Intent intent = new Intent(Login.this, MenuPrincipal.class);
                                            startActivity(intent);
                                        }

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    Log.d("Login ", t.getMessage());
                                    login_progressBar.setVisibility(View.INVISIBLE);
                                    MissatgeError.setText("Conexion fallida");
                                }
                            });

                        }
                    }, 500);   //0,5 seconds

                }
            });

            registrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Login.this, Registrar.class);
                    startActivity(intent);
                }
            });

        }
        else
        {
            Intent intent = new Intent(Login.this, MenuPrincipal.class);
            startActivity(intent);
        }
    }
}
