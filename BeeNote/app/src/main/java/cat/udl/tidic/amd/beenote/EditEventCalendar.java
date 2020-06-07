package cat.udl.tidic.amd.beenote;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
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

public class EditEventCalendar extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private EditText titulo;
    private TextView data;
    private Spinner assignatura;
    private Spinner tasca;
    private Button boto_hora;
    private TextView tv_hora;
    private EditText aula;
    private EditText descripcio;
    private Button grup_si;
    private Button grup_no;
    private EditText nota;

    private menuPrincipal_ViewModel menuPrincipal_viewModel = new menuPrincipal_ViewModel();


    @SuppressLint("WrongViewCast")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editeventcalendar);

        titulo = findViewById(R.id.dialog_Event_Titulo);
        data = findViewById(R.id.tw_data);
        assignatura = findViewById(R.id.spinner_assignatura);
        tasca = findViewById(R.id.spinner_tasca);
        boto_hora = findViewById(R.id.btn_hora);
        aula = findViewById(R.id.et_aula);
        descripcio = findViewById(R.id.et_descripción);
        grup_no = findViewById(R.id.btn_no_grup);
        grup_si = findViewById(R.id.btn_si_grup);
        nota = findViewById(R.id.et_nota);


        //Guardem la descripcció, la aula, la nota i el títol
        String titulo_string = titulo.getText().toString();
        String aula_string = aula.getText().toString();
        String descricpio_string = descripcio.getText().toString();
        String nota_string = nota.getText().toString();

        titulo.setText(titulo_string);
        aula.setText(aula_string);
        descripcio.setText(descricpio_string);
        nota.setText(nota_string);

        //Afegim la data previament seleccionada
        Bundle extras = getIntent().getExtras();
        assert extras != null;
        String dataCalendar = extras.getString("Data");
        data.setText(dataCalendar);

        //Listener botó hora
        boto_hora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(),"time picker");
            }
        });



        //Spinner assignatures
        ArrayAdapter<CharSequence> adapter_asignatures = ArrayAdapter.createFromResource(this,
                R.array.Asignaturas, android.R.layout.simple_spinner_item);
        adapter_asignatures.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assignatura.setAdapter(adapter_asignatures);


        //Spinner tasques
        ArrayAdapter<CharSequence> adapter_tipus = ArrayAdapter.createFromResource(this,
                R.array.Tipo, android.R.layout.simple_spinner_item);
        adapter_tipus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tasca.setAdapter(adapter_tipus);


    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        tv_hora = findViewById(R.id.tw_hora);
        tv_hora.setText(hourOfDay + ":" + minute);
    }
}

