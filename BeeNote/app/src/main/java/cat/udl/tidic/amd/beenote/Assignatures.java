package cat.udl.tidic.amd.beenote;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.List;

import cat.udl.tidic.amd.beenote.RecyclerView.Add_Assignatures_List;
import cat.udl.tidic.amd.beenote.RecyclerView.Assignatura_Adapter;
import cat.udl.tidic.amd.beenote.RecyclerView.AssignaturesDiffCallback;
import cat.udl.tidic.amd.beenote.ViewModels.AssiganturesViewModel;
import cat.udl.tidic.amd.beenote.models.Assignatures_Model;
import cat.udl.tidic.amd.beenote.preferences.PreferencesProvider;

public class Assignatures extends AppCompatActivity {

    public static final int INSERT_EVENT = 1;
    public static final int EDIT_EVENT = 2;
    public static final String TAG = "Assignatures";
    private SharedPreferences mPreferences;

   // private ImageButton searchButton;

    private DrawerLayout drawerLayout;
    private Button menu;
    private ImageButton addButton;

    private AssiganturesViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignatures);

        menu = findViewById(R.id.Toolbar_Menu);

        // El menu deslizante
        drawerLayout = findViewById(R.id.drawer_assignatures);
        final NavigationView navigationView = findViewById(R.id.nav__assignatures);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();

                int id = menuItem.getItemId();

                if (id == R.id.nav_account) {
                    drawerLayout.closeDrawers();
                }
                else if(id == R.id.nav_menu){
                    Intent intent = new Intent(Assignatures.this, MenuPrincipal.class);
                    startActivity(intent);
                }
                return true;
            }
        });

        // El icono del toolbar per anar el menu
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        this.mPreferences = PreferencesProvider.providePreferences();
        initViews();
    }
    private void initViews() {

        addButton = findViewById(R.id.createEvent);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Assignatures.this,
                        Add_Assignatures_List.class); // TODO Add_assigantures_List implementar
                startActivityForResult(intent, INSERT_EVENT);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.activityMainRcyMain);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        final Assignatura_Adapter adapter = new Assignatura_Adapter(new AssignaturesDiffCallback());
        recyclerView.setAdapter(adapter);

       /* adapter.setOnItemClickListener(new Assignatura_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(Assignatures_Model event) {
                Log.d(TAG, event.getTitle());
                Intent intent = new Intent(Assignatures.this, Add_Assignatures_List.class);
                intent.putExtra(Add_Assignatures_List.EXTRA_ID, event.getId());
                intent.putExtra(Add_Assignatures_List.EXTRA_TITLE, event.getTitle());
                startActivityForResult(intent, EDIT_EVENT);
            }
        });*/


       /* viewModel = new AssiganturesViewModel(this.getApplication());
        viewModel.setUserId("");
        viewModel.getEvents().observe(this, new Observer<List<Assignatures_Model>>() {
            @Override
            public void onChanged(@Nullable List<Assignatures_Model> events) {
                adapter.submitList(events);
            }
        });*/

        //searchButton = findViewById(R.id.searchButton);

        /* searchButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             TextView textView = findViewById(R.id.activityMainAtcEventUserId);
             viewModel.setUserId(textView.getText().toString());
         }
     });*/
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                viewModel.removeEvent(adapter.getEventAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getApplicationContext(), "Deleted event", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == INSERT_EVENT && resultCode == RESULT_OK) {
            String title = data.getStringExtra(Add_Assignatures_List.EXTRA_TITLE);


            String current_user = this.mPreferences.getString("current_user", "");
            Assignatures_Model event = new Assignatures_Model(Integer.parseInt(current_user),title);
            viewModel.insert(event);

            Toast.makeText(this, "Event saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_EVENT && resultCode == RESULT_OK) {
            int id = data.getIntExtra(Add_Assignatures_List.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Event can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String title = data.getStringExtra(Add_Assignatures_List.EXTRA_TITLE);



            String current_user = this.mPreferences.getString("current_user", "");
            Assignatures_Model event = new Assignatures_Model(Integer.parseInt(current_user),title);

            event.setId(id);
            viewModel.update(event);

            Toast.makeText(this, "Event updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Event not saved", Toast.LENGTH_SHORT).show();
        }
    }
}
