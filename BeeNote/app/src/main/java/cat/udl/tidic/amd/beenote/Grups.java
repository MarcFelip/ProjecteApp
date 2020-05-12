package cat.udl.tidic.amd.beenote;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Grups extends ActivityWithNavView  {

     private ImageButton crear;
     private Button unir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grups);

        this.initViews();




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
