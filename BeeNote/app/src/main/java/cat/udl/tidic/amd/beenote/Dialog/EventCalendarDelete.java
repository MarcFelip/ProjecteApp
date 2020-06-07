package cat.udl.tidic.amd.beenote.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import cat.udl.tidic.amd.beenote.MenuPrincipal;
import cat.udl.tidic.amd.beenote.R;
import cat.udl.tidic.amd.beenote.Registrar;

public class EventCalendarDelete extends DialogFragment {

    private View rootView;
    private MenuPrincipal activity;
    private String mdata;

    public static EventCalendarDelete newInstance(MenuPrincipal activity) {
        EventCalendarDelete dialog = new EventCalendarDelete();
        dialog.activity = activity;
        return dialog;
    }

    public void adddata(String data){
        mdata = data;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        initViews();
        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                .setView(rootView)
                .setCancelable(true)
                .setPositiveButton("Si",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        activity.popupEliminarEvent_();
                    }
                })
                .setNegativeButton("No",null)
                .create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.setCancelable(true);
        return alertDialog;
    }

    private void initViews() {
        rootView = LayoutInflater.from(getContext())
                .inflate(R.layout.activity_delet_event_calendar, null, false);
        TextView dataText = rootView.findViewById(R.id.tw_data2);
        dataText.setText(mdata);
    }
}
