package cat.udl.tidic.amd.beenote;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import cat.udl.tidic.amd.beenote.models.UserModel;
import cat.udl.tidic.amd.beenote.network.RetrofitClientInstance;
import cat.udl.tidic.amd.beenote.services.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Perfil_User extends AppCompatActivity {

    UserModel u = new UserModel();
    private TextView username;
    private TextView name;
    private TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil__user);

        String token = "";

        username = findViewById(R.id.Perfil_username);
        name = findViewById(R.id.Perfil_name);
        email = findViewById(R.id.Perfil_email);

        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "656e50e154865a5dc469b80437ed2f963b8f58c8857b66c9bf");

        final UserService userService = RetrofitClientInstance.
                getRetrofitInstance().create(UserService.class);

        Call<UserModel> call = userService.getUserProfile(map);

        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                u = response.body();
                try {
                    username.setText(u.getUsername());
                    name.setText(u.getName());
                    email.setText(u.getEmail());
                }catch (Exception e){
                    Log.e("Perfil user OK", response.message());
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {

                Log.d("Perfil User Error",t.toString());
            }
        });


    }
}
