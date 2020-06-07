package cat.udl.tidic.amd.beenote.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import org.jetbrains.annotations.NotNull;

import cat.udl.tidic.amd.beenote.MenuPrincipal;
import cat.udl.tidic.amd.beenote.R;

public class Calendar_Dialog extends DialogFragment {

    private View rootView;
    private MenuPrincipal activity;
    private String mtitulo = "Titulo";
    private String mdescripcion;
    private String mdia;
    private String mhorari;
    private String mtipo_tarea;
    private String masignatura;
    private boolean mgrup;
    private String mAula;
    private String mpercentatge_nota;

    public static Calendar_Dialog newInstance(MenuPrincipal activity) {
        Calendar_Dialog dialog = new Calendar_Dialog();
        dialog.activity = activity;
        return dialog;
    }

    public void add_Info(String titulo, String descripcion, String dia,String horari,String tipo_tarea, String asignatura, boolean grup, String Aula, String percentatge_nota)
    {
        mtitulo = titulo;
        mdescripcion = descripcion;
        mdia = dia;
        mhorari = horari;
        mtipo_tarea = tipo_tarea;
        masignatura = asignatura;
        mgrup = grup;
        mAula = Aula;
        mpercentatge_nota = percentatge_nota;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        initViews();
        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                .setView(rootView)
                .setTitle("Evento")
                .create();
        alertDialog.setCanceledOnTouchOutside(true);
        return alertDialog;
    }


    private void initViews() {
        rootView = LayoutInflater.from(getContext())
                .inflate(R.layout.activity_calendario_dialog, null, false);
        TextView titulo = rootView.findViewById(R.id.dialog_calendar_titulo);
        TextView descripcion = rootView.findViewById(R.id.dialog_calendar_descripcion);
        TextView dia = rootView.findViewById(R.id.dialog_calendar_fecha);
        TextView horario = rootView.findViewById(R.id.dialog_calendar_horario);
        TextView tarea = rootView.findViewById(R.id.dialog_calendar_tarea2);
        TextView asignatura = rootView.findViewById(R.id.dialog_calendar_asignatura2);
        TextView grupo = rootView.findViewById(R.id.dialog_calendar_grupo2);
        TextView aula = rootView.findViewById(R.id.dialog_calendar_aula2);
        TextView nota = rootView.findViewById(R.id.dialog_calendar_nota2);

        TextView tarea2 = rootView.findViewById(R.id.dialog_calendar_tarea);
        TextView asignatura2 = rootView.findViewById(R.id.dialog_calendar_asignatura);
        TextView grupo2 = rootView.findViewById(R.id.dialog_calendar_grupo);
        TextView aula2 = rootView.findViewById(R.id.dialog_calendar_aula);
        TextView nota2 = rootView.findViewById(R.id.dialog_calendar_nota);


        titulo.setText(mtitulo);
        descripcion.setText(mdescripcion);
        dia.setText(mdia);
        horario.setText(mhorari);

        if (!mtipo_tarea.equals("") && !masignatura.equals("") && !mAula.equals(""))
        {
            tarea2.setVisibility(View.VISIBLE);
            asignatura2.setVisibility(View.VISIBLE);
            grupo2.setVisibility(View.VISIBLE);
            aula2.setVisibility(View.VISIBLE);
            nota2.setVisibility(View.VISIBLE);

            tarea.setText(mtipo_tarea);
            asignatura.setText(masignatura);
            if (mgrup)
            {
                grupo.setText("Con grupo");
            }
            else
            {
                grupo.setText("Sin grupo");
            }
            aula.setText(mAula);
            nota.setText(mpercentatge_nota);

            System.out.println("Dintre visible");
        }
        else{
           /*
            tarea2.setVisibility(View.INVISIBLE);
            asignatura2.setVisibility(View.INVISIBLE);
            grupo2.setVisibility(View.INVISIBLE);
            aula2.setVisibility(View.INVISIBLE);
            nota2.setVisibility(View.INVISIBLE);
            */
            tarea.setText("");
            asignatura.setText("");
            grupo.setText("");
            aula.setText("");
            nota.setText("");

            System.out.println("Fora invisible");
        }
    }
}
