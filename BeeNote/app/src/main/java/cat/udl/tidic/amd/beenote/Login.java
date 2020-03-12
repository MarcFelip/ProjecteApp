package cat.udl.tidic.amd.beenote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
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

                autoritzacio = Username.toString() + ":" + Password.toString();

                byte[] data = null;

                try {
                    data = autoritzacio.getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }


                // .trim() --> perque no hi hagin caracaters raros

                String prova1 = Base64.encodeToString(data,Base64.DEFAULT);
                System.out.println("P1"+prova1);

                String prova2 = "Authentication:" + prova1;
                System.out.println("P2"+prova2);

                String prova2Trim = prova2.trim();
                System.out.println("P3"+prova2Trim);


                String encoding = ("Authentication:" + Base64.encodeToString(data,Base64.DEFAULT)).trim();


                Call<ResponseBody> call = userService.postCreateToken(prova2Trim);

                System.out.println("Encoding"+encoding);


                call.enqueue(new Callback<ResponseBody>() {
                   @Override
                   public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                       System.out.println(response.body().toString());
                   }

                   @Override
                   public void onFailure(Call<ResponseBody> call, Throwable t) {
                       System.out.println(t.getMessage());
                   }
               });



               Intent intent = new Intent(Login.this, Perfil_User.class);
                startActivity(intent);


            }
        });
    }
}
