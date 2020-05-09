package cat.udl.tidic.amd.beenote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

import cat.udl.tidic.amd.beenote.RecyclerView.Assignatura_Adapter;
import cat.udl.tidic.amd.beenote.RecyclerView.AssignaturesDiffCallback;
import cat.udl.tidic.amd.beenote.models.CourseModel;

public class Grups extends ActivityWithNavView  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grups);

        this.initViews();

    }

    private void initViews() {

        // Creem la part del menu
        super.initView(R.layout.activity_grups,
                R.id.drawer_grups,
                R.id.nav__grups);
    }

}
