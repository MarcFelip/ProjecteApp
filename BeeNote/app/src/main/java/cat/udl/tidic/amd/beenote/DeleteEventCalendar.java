package cat.udl.tidic.amd.beenote;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import cat.udl.tidic.amd.beenote.Dialog.TimePickerFragment;
import cat.udl.tidic.amd.beenote.ViewModels.menuPrincipal_ViewModel;

public class DeleteEventCalendar extends AppCompatActivity {


    private TextView data;
    private Button boto_si;
    private Button boto_no;

    private menuPrincipal_ViewModel menuPrincipal_viewModel = new menuPrincipal_ViewModel();


    @SuppressLint("WrongViewCast")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deleteeventcalendar);


        data = findViewById(R.id.tw_data);

        boto_no = findViewById(R.id.btn_no);
        boto_si = findViewById(R.id.btn_si);


        //Afegim la data previament seleccionada
        String menu_data;
        menu_data = String.valueOf(menuPrincipal_viewModel.getDate());
        data.setText(menu_data);

        /*
        boto_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeleteEventCalendar.this, MenuPrincipal.class);
                startActivity(intent);
            }
        });


        boto_si.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        */
    }

}

