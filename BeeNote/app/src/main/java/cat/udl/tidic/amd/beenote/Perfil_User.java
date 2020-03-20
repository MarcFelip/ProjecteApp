package cat.udl.tidic.amd.beenote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import cat.udl.tidic.amd.beenote.Repository.UserRepository;
import cat.udl.tidic.amd.beenote.ViewModels.Perfil_UserViewModel;
import cat.udl.tidic.amd.beenote.models.UserModel;
import cat.udl.tidic.amd.beenote.network.RetrofitClientInstance;
import cat.udl.tidic.amd.beenote.services.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Perfil_User extends AppCompatActivity {

    private UserModel u = new UserModel();
    private TextView username;
    private TextView name;
    private TextView email;

    private Perfil_UserViewModel perfil_userViewModel = new Perfil_UserViewModel();

    private final UserService userService = RetrofitClientInstance.getRetrofitInstance().create(UserService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil__user);

        username = findViewById(R.id.Perfil_username);
        name = findViewById(R.id.Perfil_name);
        email = findViewById(R.id.Perfil_email);

        String token = perfil_userViewModel.getToken();
        //System.out.println("Login - Toke " + token);

        Map<String, String> map = new HashMap<>();
        map.put("Authorization", token);

        Call<UserModel> call = userService.getUserProfile(map);

        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                u = response.body();
                try {
                    assert u != null;
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
