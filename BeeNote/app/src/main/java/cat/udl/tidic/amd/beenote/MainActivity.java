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

import java.util.HashMap;
import java.util.Map;

import cat.udl.tidic.amd.beenote.models.UserModel;
import cat.udl.tidic.amd.beenote.network.RetrofitClientInstance;
import cat.udl.tidic.amd.beenote.services.UserService;
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
    private TextView Name;
    private TextView Surname;
    private TextView Genere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        salida = findViewById(R.id.Salida);
        post = findViewById(R.id.Registrar);
        Login = findViewById(R.id.Button_Login);

        Username = findViewById(R.id.Registrar_Username);
        Password = findViewById(R.id.Registrar_password);
        Email = findViewById(R.id.Registarr_email);
        Name = findViewById(R.id.Registrar_name);
        Surname = findViewById(R.id.Registar_surname);
        Genere = findViewById(R.id.Registrar_genere);



        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String username = Username.getText().toString();
                final String password = Password.getText().toString();
                final String email = Email.getText().toString();
                final String name = Name.getText().toString();
                final String surname = Surname.getText().toString();
                final String genere = Genere.getText().toString();

                UserService userService = RetrofitClientInstance.
                        getRetrofitInstance().create(UserService.class);

                //Aqui creem i omplim el Head de la peticio (Postman)
                Map<String, String> map = new HashMap<>();
                map.put("Content-Type", "application/json");

                //Creem un Usermodel amb les variables que demana la API
                //UserModel model = new UserModel("joan5234","1234","joanrialp@gmail.com","Joan","Rialp","M");
                UserModel model = new UserModel(username,password,email,name,surname,genere);

                //Fem la crida a la API amb el Head i Body
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
