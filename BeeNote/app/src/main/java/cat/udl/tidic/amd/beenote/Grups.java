package cat.udl.tidic.amd.beenote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.List;

import cat.udl.tidic.amd.beenote.RecyclerView.Assignatura_Adapter;
import cat.udl.tidic.amd.beenote.RecyclerView.AssignaturesDiffCallback;
import cat.udl.tidic.amd.beenote.models.CourseModel;

public class Grups extends ActivityWithNavView  {

     private ImageButton crear;
     private Button unir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grups);

        this.initViews();

        crear = findViewById(R.id.btn_grupos_crear);
        unir = findViewById(R.id.btn_grupos_unir);


        // Per anar al layout de crear un grup
        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Grups.this, CrearGrupo.class);
                startActivity(intent);
            }
        });

        /*unir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Grups.this, NewGroup.class);
                startActivity(intent);
            }
        });*/

    }

    private void initViews() {

        // Creem la part del menu
        super.initView(R.layout.activity_grups,
                R.id.drawer_grups,
                R.id.nav__grups);
    }

}
