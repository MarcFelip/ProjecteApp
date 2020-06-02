package cat.udl.tidic.amd.beenote;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cat.udl.tidic.amd.beenote.Dialog.TimePickerFragment;
import cat.udl.tidic.amd.beenote.ViewModels.menuPrincipal_ViewModel;

public class NewEventCalendar extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

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
    private Button guardar;
    private Button calcelar;

    private int houreFinal = 000;
    private int minuteFinale;

    private boolean grup = false;

    private menuPrincipal_ViewModel menuPrincipal_viewModel = new menuPrincipal_ViewModel();
    private MenuPrincipal menuPrincipal = new MenuPrincipal();


    @SuppressLint("WrongViewCast")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neweventcalendar);

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
        guardar = findViewById(R.id.boto_guardar);
        calcelar = findViewById(R.id.evento_boto_cancelar);


        //Afegim la data previament seleccionada
        Bundle extras = getIntent().getExtras();
        String dataCalendar = extras.getString("Data");
        long datamilli = extras.getLong(("DataMilli"));
        System.out.println("data: " + dataCalendar);
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


        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Guardem la descripcció, la aula, la nota i el títol
                String titulo_string = titulo.getText().toString();
                String aula_string = aula.getText().toString();
                String descricpio_string = descripcio.getText().toString();
                TextView textView = (TextView)tasca.getSelectedView();
                String tipo_tasca = textView.getText().toString();
                TextView textView2 = (TextView)assignatura.getSelectedView();
                String assignatura = textView2.getText().toString();
                boolean grupFinal = grup;
                String notaFinal = nota.getText().toString();

                if (titulo_string.equals(""))
                {
                    titulo.setError("Campo Obligatorio");
                }
                if (descricpio_string.equals(""))
                {
                    descripcio.setError("Campo Obligatorio");
                }
                if(houreFinal == 000)
                {
                    boto_hora.setError("Campo Obligatorio");
                }
                else
                {
                    //menuPrincipal.popUp_InsertEvent(titulo_string,descricpio_string);
                    Intent intent = new Intent(NewEventCalendar.this, MenuPrincipal.class);
                    intent.putExtra("titulo",titulo_string);
                    intent.putExtra("descripcion",descricpio_string);
                    intent.putExtra("hora",houreFinal);
                    intent.putExtra("minutos",minuteFinale);
                    intent.putExtra("datamilli",datamilli);
                    intent.putExtra("assignatura",assignatura);
                    intent.putExtra("tipotasca",tipo_tasca);
                    intent.putExtra("grup",grupFinal);
                    intent.putExtra("aula",aula_string);
                    intent.putExtra("nota",notaFinal);
                    startActivity(intent);
                    //menuPrincipal.popUp_InsertEvent(titulo_string,descricpio_string,houreFinal,minuteFinale);
                }

            }
        });

        calcelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewEventCalendar.this, MenuPrincipal.class);
                startActivity(intent);
            }
        });

        grup_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grup = false;
            }
        });

        grup_si.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grup = true;
            }
        });


    }



    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        tv_hora = findViewById(R.id.tw_hora);
        tv_hora.setText(hourOfDay + ":" + minute);
        houreFinal = hourOfDay;
        minuteFinale = minute;
    }
}

