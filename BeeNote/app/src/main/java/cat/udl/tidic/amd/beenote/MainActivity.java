package cat.udl.tidic.amd.beenote;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        salida = findViewById(R.id.Salida);

        UserService userService = RetrofitClientInstance.
                getRetrofitInstance().create(UserService.class);

        //Aqui creem i omplim el Head de la peticio (Postman)
        Map<String, String> map = new HashMap<>();
        map.put("Content-Type", "application/json");

        //Creem un Usermodel amb les variables que demana la API
        UserModel model = new UserModel("joan14345435234","1234","joanrialp@gmail.com","Joan","Rialp","M");

        //Fem la crida a la API amb el Head i Body
        Call<Void> call = userService.postUserProfile(map,model);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                Log.d("MainActivity",response.toString());
                Log.d("MainActiviti ok",response.message().toString());

                salida.setText("OK");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

                System.out.println(t.getMessage());
                Log.d("MainActivity333",t.getMessage());

                salida.setText("Error");
            }
        });
    }
}
