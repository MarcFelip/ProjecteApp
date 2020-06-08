package cat.udl.tidic.amd.beenote;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.google.api.client.util.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import cat.udl.tidic.amd.beenote.Dialog.TimePickerFragment;
import cat.udl.tidic.amd.beenote.ViewModels.menuPrincipal_ViewModel;

public class EditEventCalendar extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private static menuPrincipal_ViewModel menuPrincipal_viewModel;
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
    private  long millis1;
    private long millis2;

    private MenuPrincipal menuPrincipal = new MenuPrincipal();
    private ContentResolver contentResolver;

    public static void setViewModelMenu(menuPrincipal_ViewModel menu) {
        menuPrincipal_viewModel = menu;
    }

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
        guardar = findViewById(R.id.eventcalendar_guardar);

        contentResolver = menuPrincipal_viewModel.getContentResolver();

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
        String dateInString = "10:20";
        Date date = null;
        try {
            date = sdf.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(date);

        //Guardem la descripcció, la aula, la nota i el títol
        Bundle extras = getIntent().getExtras();
        assert extras != null;
        String titulo_string  = extras.getString("titulo");
        String descricpio_string = extras.getString("descripcion");
        String tascas_string =  extras.getString("tasca");
        String assigantura_string =  extras.getString("asigantura");
        String nota_string =  extras.getString("porcentage");
        long dataLong =  extras.getLong("dataLong");
        String eventID =  extras.getString("eventID");

        System.out.println("targetCalendarId " + eventID);
        System.out.println("tascas_string " + tascas_string);
        System.out.println("assigantura_string " + assigantura_string);

        titulo.setText(titulo_string);
        //aula.setText(aula_string);
        descripcio.setText(descricpio_string);
        nota.setText(nota_string);

        //Afegim la data previament seleccionada
        String dataCalendar = extras.getString("Data");
        data.setText(dataCalendar);

        DialogFragment timePicker = new TimePickerFragment();
        //Listener botó hora
        boto_hora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timePicker.show(getSupportFragmentManager(),"time picker");
            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_event(eventID,titulo.getText().toString());
                Intent intent = new Intent(EditEventCalendar.this, MenuPrincipal.class);
                startActivity(intent);
            }
        });

        //Spinner assignatures
        ArrayAdapter<CharSequence> adapter_asignatures = ArrayAdapter.createFromResource(this,
                R.array.Asignaturas, android.R.layout.simple_spinner_item);
        adapter_asignatures.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assignatura.setAdapter(adapter_asignatures);

        assert assigantura_string != null;
        if(assigantura_string.equals("Lengua castellana"))
        {
            tasca.setSelection(1);
        }
        else if(assigantura_string.equals("Lengua inglesa"))
        {
            tasca.setSelection(2);
        }
        else if(assigantura_string.equals("Matemáticas"))
        {
            tasca.setSelection(3);
        }
        else if(assigantura_string.equals("Biología"))
        {
            tasca.setSelection(4);
        }
        else if(assigantura_string.equals("Física"))
        {
            tasca.setSelection(5);
        }
        else if(assigantura_string.equals("Química"))
        {
            tasca.setSelection(6);
        }
        else if(assigantura_string.equals("Tecnología"))
        {
            tasca.setSelection(7);
        }
        else if(assigantura_string.equals("Diseño"))
        {
            tasca.setSelection(8);
        }

        //Spinner tasques
        ArrayAdapter<CharSequence> adapter_tipus = ArrayAdapter.createFromResource(this,
                R.array.Tipo, android.R.layout.simple_spinner_item);
        adapter_tipus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tasca.setAdapter(adapter_tipus);

        assert tascas_string != null;
        if(tascas_string.equals("Examen"))
        {
            tasca.setSelection(1);
        }
        else if(tascas_string.equals("Trabajo"))
        {
            tasca.setSelection(2);
        }
        else if(tascas_string.equals("Deberes"))
        {
            tasca.setSelection(3);
        }
        else if(tascas_string.equals("Reunión"))
        {
            tasca.setSelection(4);
        }

    }

    public void update_event(String targetEventId,String targetTitle) {
        // Obteniu l'ID d'esdeveniment a EditText
        long eventId = Long.parseLong(targetEventId);
        // Actualitzar l’activitat
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.TITLE, targetTitle);
        // Com que targetSDK = 25, comproveu els permisos quan s'executen aplicacions
        int permissionCheck = ContextCompat.checkSelfPermission(EditEventCalendar.this,
                Manifest.permission.WRITE_CALENDAR);
        // Si l’usuari té permís per començar a actualitzar el calendari
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Uri uri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventId);
            contentResolver.update(uri, values, null, null);
            Toast toast = Toast.makeText(this, "Evento actualizado con exito", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        tv_hora = findViewById(R.id.tw_hora);
        tv_hora.setText(hourOfDay + ":" + minute);
        millis1 = TimeUnit.HOURS.toMillis(hourOfDay);
        millis2 = TimeUnit.MINUTES.toMillis(minute);
    }
}

