package cat.udl.tidic.amd.beenote.Dialog;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import cat.udl.tidic.amd.beenote.R;
import cat.udl.tidic.amd.beenote.Registrar;

public class TerminosCondiciones extends DialogFragment {

    private View rootView;
    private Registrar activity;

    public static TerminosCondiciones newInstance(Registrar activity) {
        TerminosCondiciones dialog = new TerminosCondiciones();
        dialog.activity = activity;
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        initViews();
        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                .setView(rootView)
                .setTitle("Terminos y Condiciones")
                .setCancelable(false)
                .setPositiveButton("Estoy de acuerdo", null)
                .create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        return alertDialog;
    }

    private void initViews() {
        rootView = LayoutInflater.from(getContext())
                .inflate(R.layout.activity_terminos_condiciones, null, false);
    }
}
