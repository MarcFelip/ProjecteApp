package cat.udl.tidic.amd.beenote.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

import cat.udl.tidic.amd.beenote.MenuPrincipal;
import cat.udl.tidic.amd.beenote.R;
import cat.udl.tidic.amd.beenote.Repository.UserRepository;
import cat.udl.tidic.amd.beenote.ViewModels.menuPrincipal_ViewModel;


public class NewEventCalendar extends DialogFragment {

    private View rootView;
    private MenuPrincipal activity;
    private EditText titulo;
    private MenuPrincipal menuPrincipal;

    public static NewEventCalendar newInstance(MenuPrincipal activity) {
        NewEventCalendar dialog = new NewEventCalendar();
        dialog.activity = activity;
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        initViews();
        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_neweventcalendar, null, false);
        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                .setView(rootView)
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String titulo2 = titulo.getText().toString();
                        dismiss();
                        System.out.println("Titulo popup"+titulo2);
                        //menuPrincipal.popUp_InsertEvent(titulo2);
                    }
                })
                .setTitle("Carcateristicas del nuevo Evento")
                .setCancelable(false)
                .create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        titulo = view.findViewById(R.id.dialog_Event_Titulo);

        return alertDialog;
    }

    private void initViews() {
        rootView = LayoutInflater.from(getContext())
                .inflate(R.layout.activity_neweventcalendar, null, false);
    }
}
