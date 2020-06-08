package cat.udl.tidic.amd.beenote.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

import cat.udl.tidic.amd.beenote.Assignatures;
import cat.udl.tidic.amd.beenote.MenuPrincipal;
import cat.udl.tidic.amd.beenote.R;
import cat.udl.tidic.amd.beenote.dao.CourseDAOI;
import cat.udl.tidic.amd.beenote.models.CourseModel2;
import cat.udl.tidic.amd.beenote.network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAsignatures_Dialog extends DialogFragment {

    private View rootView;
    private Assignatures activity;
    private DrawerLayout drawerLayout;
    private EditText editTextTitle;
    private EditText textDesciptionCourse;
    private EditText textAddCourse;
    private Button menu;
    private Button crear_asignatura;
    private Button cancelar_asignatura;
    private TextView text_error;

    private CourseDAOI courseDAOI = RetrofitClientInstance.getRetrofitInstance().create(CourseDAOI.class);

    public static final String EXTRA_ID =
            "cat.udl.tidic.amd.beenote.EXTRA_ID";
    public static final String EXTRA_TITLE =
            "cat.udl.tidic.amd.beenote.EXTRA_TITLE";


    public static AddAsignatures_Dialog newInstance(Assignatures activity) {
        AddAsignatures_Dialog dialog = new AddAsignatures_Dialog();
        dialog.activity = activity;
        dialog.setCancelable(false);
        return dialog;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        initViews();
        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                .setView(rootView)
                .create();
        alertDialog.setCanceledOnTouchOutside(false);
        return alertDialog;
    }


    private void initViews() {
        rootView = LayoutInflater.from(getContext())
                .inflate(R.layout.activity_add__assignatures__list, null, false);

        menu = rootView.findViewById(R.id.Toolbar_Menu);
        cancelar_asignatura = rootView.findViewById(R.id.cancelar_asignatura);
        crear_asignatura = rootView.findViewById(R.id.crear_asignatura);
        editTextTitle = rootView.findViewById(R.id.asignaturas_title);
        textAddCourse = rootView.findViewById(R.id.textAddCourse);
        textDesciptionCourse = rootView.findViewById(R.id.textDesciptionCourse);


        crear_asignatura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (textAddCourse.getText().toString().equals("")) {
                    text_error.setText("Pon un nombre a la asignatura a crear");
                } else {
                    saveEventAPI();
                    getDialog().dismiss();
                }
            }
        });

        cancelar_asignatura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

    }


    private void saveEventAPI() {
        String description = "";
        if (textDesciptionCourse.getText().toString().equals("")) {
            description = "No description";

        } else {
            description = textDesciptionCourse.getText().toString();
        }
        CourseModel2 model = new CourseModel2(textAddCourse.getText().toString(), description);
        final Call<Void> call = courseDAOI.postCourseWithoutId("656e50e154865a5dc469b80437ed2f963b8f58c8857b66c9bf", model);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("AddCourse", "OK" + response.toString());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("AddCure", Objects.requireNonNull(t.getMessage()));
            }
        });
    }
}
