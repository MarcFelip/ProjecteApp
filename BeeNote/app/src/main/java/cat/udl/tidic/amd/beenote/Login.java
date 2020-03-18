package cat.udl.tidic.amd.beenote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import cat.udl.tidic.amd.beenote.models.UserModel;
import cat.udl.tidic.amd.beenote.network.RetrofitClientInstance;
import cat.udl.tidic.amd.beenote.ViewModels.LoginViewModel;
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

    private String autoritzacio;
    private UserService userService;

    @NonNull
    private LoginViewModel loginviewmodel = new LoginViewModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.Button_Login);
        registrar = findViewById(R.id.Button_Register);
        Username = findViewById(R.id.Login_Username);
        Password = findViewById(R.id.Login_password);
        MissatgeError = findViewById(R.id.Login_Error);

        userService = RetrofitClientInstance.getRetrofitInstance().create(UserService.class);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println(Username.getText().toString());

                autoritzacio = Username.getText().toString() + ":" + Password.getText().toString();
                byte[] data = null;

                data = autoritzacio.getBytes(StandardCharsets.UTF_8);

                // .trim() --> perque no hi hagin caracaters raros
                String encoding = "Authentication: " + Base64.encodeToString(data,Base64.DEFAULT);
                Call<ResponseBody> call = userService.postCreateToken(encoding.trim());

                System.out.println("Entrar " + encoding.trim());

                call.enqueue(new Callback<ResponseBody>() {
                   @Override
                   public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                       try {
                           //assert response.body() != null;

                           if (response.body() == null)
                           {
                               MissatgeError.setText("Invalido nombre de usuario o contraseña");
                               //System.out.println("Null "+response.errorBody().string());
                           }
                           else
                           {
                               //System.out.println(response.body().string().split(":")[1]);

                               String token = response.body().string().split(":")[1];

                               // -----> SuperString de java per treure el primer i ultim caracter
                               token = token.substring(2,token.length()-2);

                               //System.out.println("Token"+token);

                               loginviewmodel.Token(token);

                               Intent intent = new Intent(Login.this, Perfil_User.class);
                               startActivity(intent);
                           }

                       } catch (IOException e) {
                           e.printStackTrace();
                       }
                   }

                   @Override
                   public void onFailure(Call<ResponseBody> call, Throwable t) {
                       Log.d("Login ",t.getMessage());
                       MissatgeError.setText("Conexion fallida");
                   }
               });

            }
        });

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
