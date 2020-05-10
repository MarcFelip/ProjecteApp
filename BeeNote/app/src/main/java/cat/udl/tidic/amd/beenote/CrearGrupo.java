package cat.udl.tidic.amd.beenote;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class CrearGrupo extends ActivityWithNavView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_grupo);

        this.initViews();

    }

    private void initViews() {

        // Creem la part del menu
        super.initView(R.layout.activity_crear_grupo,
                R.id.drawer_creargrupo,
                R.id.nav__creargrupo);
    }


}

