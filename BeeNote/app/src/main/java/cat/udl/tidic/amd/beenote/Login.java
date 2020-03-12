package cat.udl.tidic.amd.beenote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

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

    private String autoritzacio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.Button_Login);

        Username = findViewById(R.id.Login_Username);
        Password = findViewById(R.id.Login_password);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserService userService = RetrofitClientInstance.
                        getRetrofitInstance().create(UserService.class);

                autoritzacio = Username.getText().toString() + ":" + Password.getText().toString();
                byte[] data = null;

                data = autoritzacio.getBytes(StandardCharsets.UTF_8);

                // .trim() --> perque no hi hagin caracaters raros
                String encoding = "Authorization " + Base64.encodeToString(data,Base64.DEFAULT);
                Call<ResponseBody> call = userService.postCreateToken(encoding.trim());


                call.enqueue(new Callback<ResponseBody>() {
                   @Override
                   public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                       try {
                           System.out.println(response.body().string().split(":")[1]);
                       } catch (IOException e) {
                           e.printStackTrace();
                       }
                   }

                   @Override
                   public void onFailure(Call<ResponseBody> call, Throwable t) {
                       System.out.println("Error");
                       System.out.println(t.getMessage());
                   }
               });



               Intent intent = new Intent(Login.this, Perfil_User.class);
                startActivity(intent);


            }
        });
    }
}
