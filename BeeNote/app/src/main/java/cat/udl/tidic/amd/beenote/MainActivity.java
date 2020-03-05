package cat.udl.tidic.amd.beenote;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

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
        JSONObject object = new JSONObject();
        String diccionari ="";
        try {

            object.put("username", "Joan");
            object.put("password", "1234");
            object.put("email", "Joan@gmail.com");
            object.put("name", "Joan");
            object.put("surname", "Rialp");
            object.put("genere", "M");

            diccionari = object.toString();
        }
        catch(JSONException ex) {

        }

        salida = findViewById(R.id.Salida);


        UserService userService = RetrofitClientInstance.
                getRetrofitInstance().create(UserService.class);

        UserModel model = new UserModel("joan","1234","joanrialp@gmail.com","Joan","Rialp","M");
        Call<UserModel> call = userService.postUserProfile(diccionari);

        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {

                salida.setText("OK");
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {

                salida.setText("Error");
            }
        });
    }
}
