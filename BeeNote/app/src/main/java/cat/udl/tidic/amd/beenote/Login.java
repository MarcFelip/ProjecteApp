package cat.udl.tidic.amd.beenote;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cat.udl.tidic.amd.beenote.network.RetrofitClientInstance;
import cat.udl.tidic.amd.beenote.services.UserService;

public class Login extends AppCompatActivity {

    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.Button_Login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserService userService = RetrofitClientInstance.
                        getRetrofitInstance().create(UserService.class);


            }
        });
    }
}
