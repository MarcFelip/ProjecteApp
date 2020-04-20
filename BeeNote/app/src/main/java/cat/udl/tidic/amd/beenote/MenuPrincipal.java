package cat.udl.tidic.amd.beenote;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import cat.udl.tidic.amd.beenote.ViewModels.menuPrincipal_ViewModel;
import cat.udl.tidic.amd.beenote.models.TokenModel;
import cat.udl.tidic.amd.beenote.network.RetrofitClientInstance;
import cat.udl.tidic.amd.beenote.services.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuPrincipal extends AppCompatActivity {

    private Button cerrar_sesion;
    private Button perfil_usuario;
    private Button menu;
    private Button ajustes;
    private CalendarView calendario;

    private menuPrincipal_ViewModel menuPrincipal_viewModel = new menuPrincipal_ViewModel();
    private final UserService userService = RetrofitClientInstance.getRetrofitInstance().create(UserService.class);

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        cerrar_sesion = findViewById(R.id.MenuPrincipal_CerrarSesion);
        perfil_usuario = findViewById(R.id.MenuPrincipal_PerfilUsuario);
        menu = findViewById(R.id.Toolbar_Menu);
        ajustes = findViewById(R.id.Toolbar_Ajustes);
        calendario = findViewById(R.id.calendarView);

        enableForm(true);

        query_calendar();

        long prova = calendario.getMaxDate();
        System.out.println(prova);

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

        //Anar al perfil d'usuari
        perfil_usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPrincipal.this, Perfil_User.class);
                startActivity(intent);
            }
        });
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
        cerrar_sesion.setEnabled(enable);
        perfil_usuario.setEnabled(enable);
        menu.setEnabled(enable);
    }


    // --------------- Google Calendar ------------------
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
        // Obtenga el nombre de la cuenta en EditText
        String targetAccount = ((TextView) findViewById(R.id.account)).getText().toString();
        // Calendario de consulta
        Cursor cur = null;
        ContentResolver cr = getApplicationContext().getContentResolver();
        Uri uri = CalendarContract.Calendars.CONTENT_URI;
        // Defina criterios de búsqueda para encontrar calendarios que pertenezcan a la cuenta de Google anterior y tengan control total
        String selection = "((" + CalendarContract.Calendars.ACCOUNT_NAME + " = ?) AND ("
                + CalendarContract.Calendars.ACCOUNT_TYPE + " = ?) AND ("
                + CalendarContract.Calendars.OWNER_ACCOUNT + " = ?))";
        String[] selectionArgs = new String[]{"beenote00@gmail.com",
                "com.google","beenote00@gmail.com"
        };
        //  Integer.toString(CalendarContract.Calendars.CAL_ACCESS_OWNER)
        // Debido a que el SDK de destino = 25, verifique los permisos cuando se ejecutan las aplicaciones
        int permissionCheck = ContextCompat.checkSelfPermission(MenuPrincipal.this, Manifest.permission.READ_CALENDAR);
        System.out.println("Permisos:"+ permissionCheck);
        System.out.println("Permisos2."+ PackageManager.PERMISSION_GRANTED);

        // Cree una lista para almacenar temporalmente los resultados de la consulta.
        final List<String> accountNameList = new ArrayList<>();
        final List<Integer> calendarIdList = new ArrayList<>();
        // Si el usuario tiene permiso para comenzar a consultar el calendario
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            cur = cr.query(uri, EVENT_PROJECTION, selection, selectionArgs, null);
            System.out.println("Cur: "+ cur);
            if (cur != null) {
                System.out.println("Cur No buit ");
                while (cur.moveToNext()) {
                    long calendarId = 0;
                    String accountName = null;
                    String displayName = null;
                    String ownerAccount = null;
                    int accessLevel = 0;
                    // Obtenga la información requerida
                    calendarId = cur.getLong(PROJECTION_ID_INDEX);
                    System.out.println("CalendarId "+ calendarId);

                    String prova = cur.getString(0);
                    System.out.println("Provaaa "+ prova);
                    if (cur.getColumnIndex(CalendarContract.Events.TITLE) > 0)
                    {
                        String eventTitle = cur.getString(cur.getColumnIndex(CalendarContract.Events.TITLE));
                        System.out.println("EveneTitle "+ eventTitle);
                    }

                    accountName = cur.getString(PROJECTION_ACCOUNT_NAME_INDEX);
                    displayName = cur.getString(PROJECTION_DISPLAY_NAME_INDEX);
                    ownerAccount = cur.getString(PROJECTION_OWNER_ACCOUNT_INDEX);
                    accessLevel = cur.getInt(PROJECTION_CALENDAR_ACCESS_LEVEL);
                    Log.i("query_calendar", String.format("calendarId=%s", calendarId));
                    Log.i("query_calendar", String.format("accountName=%s", accountName));
                    Log.i("query_calendar", String.format("displayName=%s", displayName));
                    Log.i("query_calendar", String.format("ownerAccount=%s", ownerAccount));
                    Log.i("query_calendar", String.format("accessLevel=%s", accessLevel));
                    // Datos temporales para que los usuarios elijan
                    accountNameList.add(displayName);
                    calendarIdList.add((int) calendarId);

                    //Part Jordi
                    //String eventTitle = cur.getString(cur.getColumnIndex(CalendarContract.Events.TITLE));
                    //String eventId = cur.getString(cur.getColumnIndex(CalendarContract.Events._ID));
                    //String startDate = cur.getString(cur.getColumnIndex(CalendarContract.Events.DTSTART));
                    //tring endDate = cur.getString(cur.getColumnIndex(CalendarContract.Events.DTEND));
                }
                System.out.println("While Tancat ");
                cur.close();
            }

            if (calendarIdList.size() != 0) {
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
        }
        else {
            if(shouldShowRequestPermissionRationale(Manifest.permission.READ_CALENDAR)) {
                Toast toast = Toast.makeText(this, "\n" +
                        "Se requieren permisos", Toast.LENGTH_LONG);
                toast.show();
            }
            requestPermissions(new String[]{Manifest.permission.READ_CALENDAR},4);
        }
    }

    public void insert_event(View view) {
        // Obteniu l'ID del calendari a EditText
        String targetCalendarId = "CalendarID";
        long calendarId = Long.parseLong(targetCalendarId);
        // Obteniu l’hora actual com l’hora d’inici de l’esdeveniment
        long currentTimeMillis = System.currentTimeMillis();
        // Definiu l'hora de finalització de l'esdeveniment a 15 minuts
        long endTimeMillis = currentTimeMillis + 900000;
        // Obteniu el títol a EditText
        String targetTitle = "Title";
        // Nova activitat
        ContentResolver cr = getContentResolver();
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.DTSTART, currentTimeMillis);
        values.put(CalendarContract.Events.DTEND, endTimeMillis);
        values.put(CalendarContract.Events.TITLE, targetTitle);
        values.put(CalendarContract.Events.DESCRIPTION, "Description");
        values.put(CalendarContract.Events.CALENDAR_ID, calendarId);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getDisplayName());
        // Com que targetSDK = 25, comproveu els permisos quan s'executen aplicacions
        int permissionCheck = ContextCompat.checkSelfPermission(MenuPrincipal.this,
                Manifest.permission.WRITE_CALENDAR);
        // Si l’usuari té permís per començar a afegir calendari
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
            // Retorna l'ID de l'esdeveniment de nova creació
            if (uri != null) {
                long eventID = Long.parseLong(uri.getLastPathSegment());
                //EditText targetEventId = (EditText) findViewById(R.id.event_id);
                System.out.println(String.format("%s", eventID));
            }
        }
    }

    public void update_event(View view) {
        // Obteniu l'ID d'esdeveniment a EditText
        String targetEventId = "EventID";
        long eventId = Long.parseLong(targetEventId);
        // Obteniu el títol a EditText
        String targetTitle = "Title";
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
        }
    }

    public void delete_event(View view) {
        // Obteniu l'ID d'esdeveniment a EditText
        String targetEventId = "EventID";
        long eventId = Long.parseLong(targetEventId);
        // Suprimeix l'esdeveniment
        ContentResolver cr = getContentResolver();
        // Com que targetSDK = 25, comproveu els permisos quan s'executen aplicacions
        int permissionCheck = ContextCompat.checkSelfPermission(MenuPrincipal.this,
                Manifest.permission.WRITE_CALENDAR);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Uri uri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventId);
            cr.delete(uri, null, null);
        }
    }


}
