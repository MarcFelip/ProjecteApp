package cat.udl.tidic.amd.beenote.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import cat.udl.tidic.amd.beenote.MenuPrincipal;
import cat.udl.tidic.amd.beenote.R;
import cat.udl.tidic.amd.beenote.Registrar;

public class EventCalendarDelete extends DialogFragment {

    private View rootView;
    private MenuPrincipal activity;
    private String mtitulo = "Titulo";
    private String mdata;

    public static EventCalendarDelete newInstance(MenuPrincipal activity) {
        EventCalendarDelete dialog = new EventCalendarDelete();
        dialog.activity = activity;
        return dialog;
    }

    public void add_Info(String titulo, String data )
    {
        mtitulo = titulo;
        mdata = data;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        initViews();
        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                .setView(rootView)
                .setCancelable(false)
                .setPositiveButton("Si",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        activity.popupEliminarEvent_();
                    }
                })
                .create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        return alertDialog;
    }

    private void initViews() {
        rootView = LayoutInflater.from(getContext())
                .inflate(R.layout.activity_delet_event_calendar, null, false);
    }
}
