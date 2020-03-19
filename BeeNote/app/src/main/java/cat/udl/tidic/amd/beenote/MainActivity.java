package cat.udl.tidic.amd.beenote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.GnssMeasurementsEvent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.PBEKeySpec;

import cat.udl.tidic.amd.beenote.models.UserModel;
import cat.udl.tidic.amd.beenote.network.RetrofitClientInstance;
import cat.udl.tidic.amd.beenote.services.UserService;
import cat.udl.tidic.amd.beenote.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private TextView salida;
    private Button post;
    private Button Login;
    private TextView Username;
    private TextView Password;
    private TextView Email;

    private UserService userService = RetrofitClientInstance.getRetrofitInstance().create(UserService.class);

    //Aqui creem el Head de la peticio (Postman)
    private Map<String, String> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        salida = findViewById(R.id.Salida);
        post = findViewById(R.id.Registrar);
        Login = findViewById(R.id.Button_Login);
        Password = findViewById(R.id.Registrar_password);
        Email = findViewById(R.id.Registarr_email);

        //Aqui omplim el Head de la peticio (Postman)
        map.put("Content-Type", "application/json");


        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String password = Password.getText().toString();

                // ------> Per encriptar la contrasenya
                // Course API requires passwords in sha-256 in passlib format so:
                String salt = "16";
                String encode_hash = Utils.encode(password,salt,29000);
                System.out.println("PASSWORD_ENCRYPTED " + encode_hash);

                // ------> Per agafar la apart del davant del correu, i ficarla com a username
                String email = Email.getText().toString();

                boolean trobat = false;
                int posicio =  email.indexOf("@");
                String username = "";
                for (int i = 0; i < posicio; i++)
                {
                    username = username + email.charAt(i);
                }
                //System.out.println("username " + username);

                // ------> Creem un Usermodel amb les variables que demana la API
                // UserModel model = new UserModel("joan5234","1234","joanrialp@gmail.com","Joan","Rialp","M");
                UserModel model = new UserModel(username,encode_hash,email);

                // Fem la crida a la API amb el Head i Body
                Call<Void> call = userService.postUserProfile(map,model);

                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {

                        Log.d("MainActivity",response.toString());
                        Log.d("MainActiviti ok",response.message());

                        System.out.println(response.message());

                        if (response.message().equals("Bad Request"))
                        {
                            salida.setText("Error el registrarse: Usuari ya existente o campo invalido");
                        }
                       else if (response.message().equals("Internal Server Error"))
                        {
                            salida.setText("Error del Servidor");
                        }
                        else
                        {
                            salida.setText("OK");
                            Intent intent = new Intent(MainActivity.this, Login.class);
                            startActivity(intent);
                        }

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                        Log.d("MainActivity333",t.getMessage());
                        Log.d("MainActivity233",t.toString());

                        salida.setText("Error Conexion");
                    }
                });
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });


    }

}
