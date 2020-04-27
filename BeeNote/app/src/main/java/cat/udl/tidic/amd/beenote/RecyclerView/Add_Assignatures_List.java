package cat.udl.tidic.amd.beenote.RecyclerView;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import cat.udl.tidic.amd.beenote.R;

public class Add_Assignatures_List extends AppCompatActivity {

    public static final String EXTRA_ID =
            "cat.udl.tidic.amd.dam_recyclerview.EXTRA_ID";
    public static final String EXTRA_DESCRIPTION =
            "cat.udl.tidic.amd.dam_recyclerview.EXTRA_DESCRIPTION";
    public static final String EXTRA_TITLE =
            "cat.udl.tidic.amd.dam_recyclerview.EXTRA_TITLE";
    public static final String EXTRA_TEMA =
            "cat.udl.tidic.amd.dam_recyclerview.EXTRA_TEMA";
    public static final String EXTRA_AVALUATION=
            "cat.udl.tidic.amd.dam_recyclerview.EXTRA_AVALUATION";
    public static final String EXTRA_USERID =
            "cat.udl.tidic.amd.dam_recyclerview.EXTRA_USERID";
    public static final String EXTRA_START =
            "cat.udl.tidic.amd.dam_recyclerview.EXTRA_START";
    public static final String EXTRA_END =
            "cat.udl.tidic.amd.dam_recyclerview.EXTRA_END";

    private final static String TAG = "AddEditForm";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__assignatures__list);
    }
}
