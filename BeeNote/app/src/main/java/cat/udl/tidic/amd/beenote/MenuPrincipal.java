package cat.udl.tidic.amd.beenote;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;

import cat.udl.tidic.amd.beenote.Dialog.NewEventCalendar;
import cat.udl.tidic.amd.beenote.ViewModels.menuPrincipal_ViewModel;
import cat.udl.tidic.amd.beenote.models.TokenModel;
import cat.udl.tidic.amd.beenote.network.RetrofitClientInstance;
import cat.udl.tidic.amd.beenote.services.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.utils.DateUtils;

public class MenuPrincipal extends AppCompatActivity {

    private Button perfil_usuario;
    private Button menu;
    private Button ajustes;
    private FloatingActionButton editarCalendar;
    private List<EventDay> events = new ArrayList<>();
    private TextView error;

    private String TAG="MenuPrincipal";

    private menuPrincipal_ViewModel menuPrincipal_viewModel = new menuPrincipal_ViewModel();
    private final UserService userService = RetrofitClientInstance.getRetrofitInstance().create(UserService.class);

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        perfil_usuario = findViewById(R.id.MenuPrincipal_PerfilUsuario);
        menu = findViewById(R.id.Toolbar_Menu);
        ajustes = findViewById(R.id.Toolbar_Ajustes);
        editarCalendar = findViewById(R.id.menu_editar_button);
        error = findViewById(R.id.menu_scrolling_text_error);

        enableForm(true);

        checkCalendarPermission();



        query_calendar();

        //Popup editarCalendar amb les 3 opcions
        editarCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(MenuPrincipal.this,editarCalendar);
                popupMenu.getMenuInflater().inflate(R.menu.menu_calendar_editar, popupMenu.getMenu());

                //menuPrincipal_viewModel.setDateToRepository(menuPrincipal_viewModel.getDate());
                error.setText("");

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();

                        // TODO Fer un pop amb per actulizar informacio
                        if (id == R.id.nav_editarEvento) {
                            String eventid = menuPrincipal_viewModel.getEventID();
                            if(eventid.equals("0"))
                            {
                                error.setText("Selecciona una fecha que tenga Evento");
                            }
                            else
                            {
                                update_event(eventid,"Canviat");
                                // TODO Que es canvii tambe el Title en el calendarView (MyEventDay)
                            }
                        }
                        else if (id == R.id.nav_ElimimarEvento) {
                            String targetEventId = menuPrincipal_viewModel.getEventID();
                            //System.out.println(targetEventId);
                            // Comprovem que ha seleccionar una data que te Events
                            if(targetEventId.equals("0"))
                            {
                                error.setText("Selecciona una fecha que tenga Evento");
                            }
                            else
                            {
                                delete_event(targetEventId);
                                elimateEventCalendar(targetEventId);
                            }
                        }
                        else if (id == R.id.nav_NuevoEvento) {
                            NewEventCalendar dialog = NewEventCalendar.newInstance(MenuPrincipal.this);
                            dialog.setCancelable(false);
                            dialog.show(getSupportFragmentManager(),"Insertar_Evento_Calendar");
                            //popUp_InsertEvent("prova 22/04/2020");
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        //Popup ajustes
        ajustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(MenuPrincipal.this, ajustes);
                popupMenu.getMenuInflater().inflate(R.menu.menu_ajustes, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();

                        if (id == R.id.nav_cerrar_sesion) {
                            cerrarSesion();
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        // El menu deslizante
        drawerLayout = findViewById(R.id.drawer_menu_principal);
        final NavigationView navigationView = findViewById(R.id.nav_menu_principal);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();

                int id = menuItem.getItemId();

                if (id == R.id.nav_account) {
                    Intent intent = new Intent(MenuPrincipal.this, Perfil_User.class);
                    startActivity(intent);
                }
                else if(id == R.id.nav_menu){
                    drawerLayout.closeDrawers();
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

        // Anar al perfil d'usuari
        perfil_usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPrincipal.this, Perfil_User.class);
                startActivity(intent);
            }
        });
    }

    private void checkCalendarPermission() {

        if(shouldShowRequestPermissionRationale(Manifest.permission.READ_CALENDAR)) {
            Toast toast = Toast.makeText(this, "\n" +
                    "Se requieren permisos", Toast.LENGTH_LONG);
            toast.show();
        }
        requestPermissions(new String[]{Manifest.permission.READ_CALENDAR},4);

        if(shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CALENDAR)) {
            Toast toast = Toast.makeText(this, "\n" +
                    "Se requieren permisos", Toast.LENGTH_LONG);
            toast.show();
        }

        requestPermissions(new String[]{Manifest.permission.WRITE_CALENDAR},4);
    }

    // Funció del cerrar sesion del menú d'ajustes
    private void cerrarSesion(){
        String token = menuPrincipal_viewModel.getToken();
        //System.out.println(token);
        TokenModel tokenModel = new TokenModel(token);

        Map<String, String> map = new HashMap<>();
        map.put("Authorization", token);

        Call<Void> call = userService.deleteToken(map,tokenModel);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                //System.out.println("MENU - Token eleiminat bee "+ response.toString());
                menuPrincipal_viewModel.setToken("");
                Intent intent = new Intent(MenuPrincipal.this, Login.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                //System.out.println("MENU - ERROR" + t.toString());
            }
        });
    }

    private void enableForm(boolean enable){
        perfil_usuario.setEnabled(enable);
        menu.setEnabled(enable);
        ajustes.setEnabled(enable);
        editarCalendar.setEnabled(enable);
    }

    // (TODO) Insertar Event a partir del popUp del NewEventCalendar.java
    public void popUp_InsertEvent(String title){
        // Agafem les variables a partir de la data selecionada el calendarView
        Log.d(TAG, "Date: " + menuPrincipal_viewModel.getDate());
        int year = Integer.parseInt(DateFormat.format("yyyy", Long.parseLong(String.valueOf(menuPrincipal_viewModel.getDate()))).toString());
        int month = Integer.parseInt(DateFormat.format("MM", Long.parseLong(String.valueOf(menuPrincipal_viewModel.getDate()))).toString());
        int data = Integer.parseInt(DateFormat.format("dd", Long.parseLong(String.valueOf(menuPrincipal_viewModel.getDate()))).toString());
        int hour = Integer.parseInt(DateFormat.format("hh", Long.parseLong(String.valueOf(menuPrincipal_viewModel.getDate()))).toString());
        int minute = Integer.parseInt(DateFormat.format("mm", Long.parseLong(String.valueOf(menuPrincipal_viewModel.getDate()))).toString());
        int seconds = Integer.parseInt(DateFormat.format("ss", Long.parseLong(String.valueOf(menuPrincipal_viewModel.getDate()))).toString());

        String eventid = menuPrincipal_viewModel.getEventID();
        String calendarid = menuPrincipal_viewModel.getCalendarID();
        insert_event(calendarid,menuPrincipal_viewModel.getDate(),menuPrincipal_viewModel.getDate()+100,title);
        addEventCalendar(year,month,data,hour,minute,seconds,title,eventid);

        //System.out.println("Guardar");
    }

        // ------------------- Material Calendar View --------------------------
    public void addEventCalendar(int year, int month, int date, int hourOfDay, int minute, int seconds, String title, String ID)
    {
        //events.add(new EventDay(calendar, DrawableUtils.getCircleDrawableWithText(this, "M")));
        //events.add(new EventDay(calendar, R.drawable.account,Color.parseColor("#228B22")));
        // Indiquem a quin dia volem posar el event
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month-1,date,hourOfDay,minute,seconds);

        // Omplim la llista de EventDay amb MyEventDay (custom class)
        events.add(new MyEventDay(calendar, R.drawable.boton_circulo,title,ID));

        //System.out.println(events);

        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);

        // Paramentres per definir quants mesos es veuen el calendari
        Calendar min = Calendar.getInstance();
        min.add(Calendar.MONTH, -12);
        Calendar max = Calendar.getInstance();
        max.add(Calendar.MONTH, 12);

        calendarView.setMinimumDate(min);
        calendarView.setMaximumDate(max);

        calendarView.setEvents(events);

        //calendarView.setDisabledDays(getDisabledDays());

        //  Aqui la inicialitzo perque si l'usuari vol crear un Event el mateix dia que estem
        if(menuPrincipal_viewModel.getDate() == null)
        {
            long date2 = calendar.getTimeInMillis();
            Log.d(TAG, "Date (init): " + date2);
            menuPrincipal_viewModel.setDate(date2);
        }

        // Listener per veure quina data i Event esta seleccionan del CalendarView
        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                long date = eventDay.getCalendar().getTimeInMillis();
                Log.d(TAG, "Date (onDayClick): " + date);
                // Guardem la data seleccionada el viewModel
                menuPrincipal_viewModel.setDate(date);

                // Try catch per comprovar que la data del calendari que selecciona el usuari hi ha o no Events creats
                try {
                    if(((MyEventDay) eventDay).getID() != null)
                    {
                        Log.e("Event",((MyEventDay) eventDay).getID()+" <--");
                        menuPrincipal_viewModel.setEventID(Long.parseLong(((MyEventDay) eventDay).getID()));
                    }
                    if(((MyEventDay) eventDay).getNote() != null)
                    {
                        Toast.makeText(getApplicationContext(), eventDay.getCalendar().getTime().toString() + " " + ((MyEventDay) eventDay).getNote(), Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e) {
                    Log.e("Event","Error no eventID <--");
                    menuPrincipal_viewModel.setEventID(0);
                    Toast.makeText(getApplicationContext(), eventDay.getCalendar().getTime().toString() + " " + "No Evento", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // (TODO) Per eliminar els Events del CalendarView
    public void elimateEventCalendar(String ID)
    {
        //events.remove(Integer.parseInt(ID));

    }

    // Desabilitas el poder seleccionar un dia en el CalendarView
    private List<Calendar> getDisabledDays() {
        Calendar firstDisabled = DateUtils.getCalendar();
        firstDisabled.add(Calendar.DAY_OF_MONTH, 2);

        Calendar secondDisabled = DateUtils.getCalendar();
        secondDisabled.add(Calendar.DAY_OF_MONTH, 1);

        Calendar thirdDisabled = DateUtils.getCalendar();
        thirdDisabled.add(Calendar.DAY_OF_MONTH, 18);

        List<Calendar> calendars = new ArrayList<>();
        calendars.add(firstDisabled);
        calendars.add(secondDisabled);
        calendars.add(thirdDisabled);
        return calendars;
    }


    // ----------------- Google Calendar --------------------
    public void query_calendar() {
        // Establecer los datos que se devolverán
        String[] EVENT_PROJECTION = new String[]{
                CalendarContract.Calendars._ID,                             // 0 ID del calendario
                CalendarContract.Calendars.ACCOUNT_NAME,                // 1 Nombre de la cuenta a la que pertenece el calendario
                CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,       // 2 Nombre del calendario
                CalendarContract.Calendars.OWNER_ACCOUNT,                  // 3 Propietario del calendario
                CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL,           // 4 Permisos para este calendario
        };
        // De acuerdo con la configuración anterior, defina el índice de cada dato para mejorar la legibilidad del código
        int PROJECTION_ID_INDEX = 0;
        int PROJECTION_ACCOUNT_NAME_INDEX = 1;
        int PROJECTION_DISPLAY_NAME_INDEX = 2;
        int PROJECTION_OWNER_ACCOUNT_INDEX = 3;
        int PROJECTION_CALENDAR_ACCESS_LEVEL = 4;

        //Datos de los eventos
        String[] eventmProjection =
                {CalendarContract.Calendars._ID,
                        CalendarContract.Events.DTSTART,
                        CalendarContract.Events.DTEND,
                        CalendarContract.Events.TITLE,
                        CalendarContract.Events.EVENT_LOCATION,
                        CalendarContract.Events.ORGANIZER,
                        CalendarContract.Events._ID,
                        CalendarContract.Events.DESCRIPTION,
                        CalendarContract.Events.DURATION,
                        CalendarContract.Events.SYNC_DATA1,
                        CalendarContract.Events.DIRTY,
                        CalendarContract.Events.UID_2445,
                        CalendarContract.Events.DELETED,
                        CalendarContract.Events.LAST_DATE,
                        CalendarContract.Events.SYNC_DATA2,
                        CalendarContract.Events.ALL_DAY,
                        CalendarContract.Events.RRULE,
                        CalendarContract.Events.STATUS,
                        CalendarContract.Events.RDATE
                };

        // Calendario de consulta
        Cursor cur = null;
        Cursor cur2 = null;
        ContentResolver cr = getApplicationContext().getContentResolver();
        Uri uri_cal = CalendarContract.Calendars.CONTENT_URI;
        Uri uri_events = CalendarContract.Events.CONTENT_URI;
        // Defina criterios de búsqueda para encontrar calendarios que pertenezcan a la cuenta de Google anterior y tengan control total
        String selection = "((" + CalendarContract.Calendars.ACCOUNT_NAME + " = ?) AND ("
                + CalendarContract.Calendars.ACCOUNT_TYPE + " = ?) AND ("
                + CalendarContract.Calendars.OWNER_ACCOUNT + " = ?))";
       // Posem el correu que volem controlar

        //@JordiMateoUdL: Això s'ha de llegir del account
        String[] selectionArgs = new String[]{"XXXXXX@gmail.com",
                "com.google","XXXXXXX@gmail.com"
        };
        // Debido a que el SDK de destino = 25, verifique los permisos cuando se ejecutan las aplicaciones
        int permissionCheck = ContextCompat.checkSelfPermission(MenuPrincipal.this, Manifest.permission.READ_CALENDAR);
        //System.out.println("Permisos:"+ permissionCheck);
        //System.out.println("Permisos2."+ PackageManager.PERMISSION_GRANTED);

        // Cree una lista para almacenar temporalmente los resultados de la consulta.
        //final List<String> accountNameList = new ArrayList<>();
        //final List<Integer> calendarIdList = new ArrayList<>();

        // Si el usuario tiene permiso para comenzar a consultar el calendario
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            cur = cr.query(uri_events, eventmProjection, selection, selectionArgs, null);
           // System.out.println("Cur: "+ cur);
            if (cur != null) {
                while (cur.moveToNext()) {
                    // Obtenga la información dels Events
                    String eventTitle = cur.getString(cur.getColumnIndex(CalendarContract.Events.TITLE));
                    String eventId = cur.getString(cur.getColumnIndex(CalendarContract.Events._ID));
                    String startDate = cur.getString(cur.getColumnIndex(CalendarContract.Events.DTSTART));
                    String endDate = cur.getString(cur.getColumnIndex(CalendarContract.Events.DTEND));
                    Log.i("query_calendar", String.format("eventTitle=%s", eventTitle));
                    Log.i("query_calendar", String.format("eventId=%s", eventId));
                    Log.i("query_calendar", String.format("startDate=%s", startDate));
                    Log.i("query_calendar", String.format("endDate=%s", endDate));

                    int year = Integer.parseInt(DateFormat.format("yyyy", Long.parseLong(startDate)).toString());
                    int month = Integer.parseInt(DateFormat.format("MM", Long.parseLong(startDate)).toString());
                    int date = Integer.parseInt(DateFormat.format("dd", Long.parseLong(startDate)).toString());
                    int hour = Integer.parseInt(DateFormat.format("hh", Long.parseLong(startDate)).toString());
                    int minute = Integer.parseInt(DateFormat.format("mm", Long.parseLong(startDate)).toString());
                    int seconds = Integer.parseInt(DateFormat.format("ss", Long.parseLong(startDate)).toString());

                    addEventCalendar(year,month,date,hour,minute,seconds,eventTitle,eventId);
                }
                cur.close();
            }
            // Per poder agafar la informacio del Calenadi (uri_cal)
            cur2 = cr.query(uri_cal, EVENT_PROJECTION, selection, selectionArgs, null);
            if (cur2 != null) {
                while (cur2.moveToNext()) {
                    long calendarId = 0;
                    //String accountName = null;
                    //String displayName = null;
                    //String ownerAccount = null;
                    //int accessLevel = 0;

                    // Obtenga la información del Calendari
                    calendarId = cur2.getLong(PROJECTION_ID_INDEX);
                    Log.i("CalendarId", String.format("CalendarId=%s", calendarId));
                    menuPrincipal_viewModel.setCalendarID(calendarId);
                    //accountName = cur.getString(PROJECTION_ACCOUNT_NAME_INDEX);
                    //accessLevel = cur.getInt(PROJECTION_CALENDAR_ACCESS_LEVEL);
                    //Log.i("query_calendar", String.format("calendarId=%s", calendarId));
                    //Log.i("query_calendar", String.format("accountName=%s", accountName));

                    // Datos temporales para que los usuarios elijan
                    //accountNameList.add(displayName);
                    //calendarIdList.add((int) calendarId);
                }
                cur2.close();
            }
            // Dialog perque el usuari pugui triar quin calendari utilizar
            /*if (calendarIdList.size() != 0) {
                // Cree un cuadro de diálogo para que los usuarios elijan un calendario
                AlertDialog.Builder adb = new AlertDialog.Builder(this);
                CharSequence items[] = accountNameList.toArray(new CharSequence[accountNameList.size()]);
                adb.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TextView targetCalendarId = (TextView) findViewById(R.id.calendar_id);
                        targetCalendarId.setText(String.format("%s", calendarIdList.get(which)));
                        dialog.dismiss();
                    }
                });
                adb.setNegativeButton("CANCEL", null);
                adb.show();
            }
            else {
                Toast toast = Toast.makeText(this, "No puedo encontrar el Google Calendar", Toast.LENGTH_LONG);
                toast.show();
            }
             */
        }
        else {
            Log.d(TAG,"No HAURIA d'entrar mai!");
            //Comprovar que tenim els permisos de read/write Calendar
//            if(shouldShowRequestPermissionRationale(Manifest.permission.READ_CALENDAR)) {
//                Toast toast = Toast.makeText(this, "\n" +
//                        "Se requieren permisos", Toast.LENGTH_LONG);
//                toast.show();
//            }
//            requestPermissions(new String[]{Manifest.permission.READ_CALENDAR},4);


        }
    }

    // Insertar els nous Events el google Calendar
    public void insert_event(String targetCalendarId, long StartTimeMillis,long EndTimeMillis, String Title) {
        // Obteniu l'ID del calendari a EditText
        long calendarId = Long.parseLong(targetCalendarId);
        // Nova activitat
        ContentResolver cr = getContentResolver();
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.DTSTART, StartTimeMillis);
        values.put(CalendarContract.Events.DTEND, EndTimeMillis);
        values.put(CalendarContract.Events.TITLE, Title);
        values.put(CalendarContract.Events.DESCRIPTION, "Description");
        values.put(CalendarContract.Events.CALENDAR_ID, calendarId);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getDisplayName());
        // Com que targetSDK = 25, comproveu els permisos quan s'executen aplicacions


        int permissionCheck = ContextCompat.checkSelfPermission(MenuPrincipal.this,
                Manifest.permission.WRITE_CALENDAR);
        // Si l’usuari té permís per començar a afegir calendari
        Log.d(TAG, "Permission: " + permissionCheck);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
            // Retorna l'ID de l'esdeveniment de nova creació
            Log.d(TAG, "Uri: " + uri);
            if (uri != null) {
                long eventID = Long.parseLong(Objects.requireNonNull(uri.getLastPathSegment()));
                //EditText targetEventId = (EditText) findViewById(R.id.event_id);
                Log.d(TAG, "InsertEVENT: " + String.format("InsertEvent %s ", eventID));
                Toast toast = Toast.makeText(this, "Evento creado con exito", Toast.LENGTH_LONG);
                toast.show();
                menuPrincipal_viewModel.setEventID(eventID);
            }
        }else{
            //requestPermissions(new String[]{Manifest.permission.WRITE_CALENDAR},4);
        }
    }

    // Actualizar els Events ja existents el google Calendar
    public void update_event(String targetEventId,String targetTitle) {
        // Obteniu l'ID d'esdeveniment a EditText
        long eventId = Long.parseLong(targetEventId);
        // Actualitzar l’activitat
        ContentResolver cr = getContentResolver();
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.TITLE, targetTitle);
        // Com que targetSDK = 25, comproveu els permisos quan s'executen aplicacions
        int permissionCheck = ContextCompat.checkSelfPermission(MenuPrincipal.this,
                Manifest.permission.WRITE_CALENDAR);
        // Si l’usuari té permís per començar a actualitzar el calendari
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Uri uri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventId);
            cr.update(uri, values, null, null);
            Toast toast = Toast.makeText(this, "Evento actualizado con exito", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    // Eliminar els Events ja existents el google Calendar
    public void delete_event(String targetEventId) {
        // Obteniu l'ID d'esdeveniment
        long eventId = Long.parseLong(targetEventId);
        // Suprimeix l'esdeveniment
        ContentResolver cr = getContentResolver();
        // Com que targetSDK = 25, comproveu els permisos quan s'executen aplicacions
        int permissionCheck = ContextCompat.checkSelfPermission(MenuPrincipal.this,
                Manifest.permission.WRITE_CALENDAR);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Uri uri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventId);
            cr.delete(uri, null, null);
            Toast toast = Toast.makeText(this, "Eliminado Correctamente", Toast.LENGTH_LONG);
            toast.show();

        }
    }


}
