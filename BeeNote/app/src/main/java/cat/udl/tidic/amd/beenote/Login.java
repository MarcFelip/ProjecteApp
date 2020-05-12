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
import androidx.lifecycle.ViewModelProvider;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


import cat.udl.tidic.amd.beenote.ViewModels.loginViewModel;
import cat.udl.tidic.amd.beenote.network.RetrofitClientInstance;
import cat.udl.tidic.amd.beenote.services.UserService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private Button login;
    private TextView username;
    private TextView password;
    private  TextView missatgeError;
    private Button registrar;
    private ProgressBar login_progressBar;
    private TextView login_registrado;
    private String output_name = "";

    private String autoritzacio;
    private UserService userService;

    @NonNull
    private loginViewModel loginviewmodel = new loginViewModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        if (loginviewmodel.getToken().equals("")) {

            login = findViewById(R.id.Button_Login);
            registrar = findViewById(R.id.Button_Register);
            username = findViewById(R.id.Login_Username);
            password = findViewById(R.id.Login_password);
            missatgeError = findViewById(R.id.Login_Error);
            login_progressBar = findViewById(R.id.Login_ProgressBar);
            login_registrado = findViewById(R.id.Login_Registrado);

            enableForm(true);

            userService = RetrofitClientInstance.getRetrofitInstance().create(UserService.class);

            //Agafem el string que ens pasa per Intent la classe registrar
            Bundle extras = getIntent().getExtras();

            if(extras != null)
            {
                output_name = extras.getString("registrar");
            }

            login_registrado.setText(output_name);

            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    login_progressBar.setVisibility(View.VISIBLE);
                    //System.out.println("Login Username "+ Username.getText().toString());

                    autoritzacio = username.getText().toString() + ":" + password.getText().toString();
                    byte[] data = null;

                    data = autoritzacio.getBytes(StandardCharsets.UTF_8);

                    // .trim() --> perque no hi hagin caracaters raros
                    String encoding = "Authentication: " + Base64.encodeToString(data, Base64.DEFAULT);
                    final Call<ResponseBody> call = userService.postCreateToken(encoding.trim());

                    System.out.println("Entrar " + encoding.trim());

                    // ----> Para que l'app se espere 0 segundos con el progressbar mientras espera la peticion a la BD
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
                                            missatgeError.setText("Invalido nombre de usuario o contraseña");
                                            //System.out.println("Null "+response.errorBody().string());
                                        } else {
                                            //System.out.println(response.body().string().split(":")[1]);

                                            String token = response.body().string().split(":")[1];

                                            // -----> SuperString de java per treure el primer i ultim caracter
                                            token = token.substring(2, token.length() - 2);

                                            loginviewmodel.Token(token);
                                            loginviewmodel.setMail(username.getText().toString());
                                            Log.e("User Token", token);

                                            login_progressBar.setVisibility(View.INVISIBLE);

                                            Call<String> call2 = userService.getUserID(loginviewmodel.getToken());
                                            call2.enqueue(new Callback<String>() {
                                                @Override
                                                public void onResponse(Call<String> call, Response<String> response) {

                                                    String id = response.body();
                                                    loginviewmodel.setUserID(id);
                                                    Log.e("User ID", id);
                                                    Intent intent = new Intent(Login.this, MenuPrincipal.class);
                                                    startActivity(intent);
                                                }

                                                @Override
                                                public void onFailure(Call<String> call, Throwable t) {

                                                }
                                            });
                                                                                    }

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    Log.d("Login ", t.getMessage());
                                    login_progressBar.setVisibility(View.INVISIBLE);
                                    missatgeError.setText("Conexion fallida");
                                }
                            });

                        }
                    }, 0);   //0 seconds

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

    private void enableForm(boolean enable){
        login.setEnabled(enable);
        username.setEnabled(enable);
        password.setEnabled(enable);
        missatgeError.setEnabled(enable);
        registrar.setEnabled(enable);
        login_progressBar.setEnabled(enable);
        login_registrado.setEnabled(enable);
    }
}
