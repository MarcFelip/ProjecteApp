package cat.udl.tidic.amd.beenote;

import android.os.Parcel;
import android.os.Parcelable;

import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.utils.DateUtils;

import java.util.Calendar;
import java.util.Objects;

public class MyEventDay extends EventDay implements Parcelable {
    private String mNote;
    private String mId;
    private String mDescription;
    private String fecha;
    private String horario;
    private String mtipo_tarea;
    private String masignatura;
    private boolean mgrup;
    private String mAula;
    private String mpercentatge_nota;
    private int mposition;

    public MyEventDay(Calendar day, int imageResource, int position,String note,String description, String id,int year, int month, int date, int hourOfDay, int minute, int seconds, String tipo_tarea, String asignatura, boolean grup, String Aula, String percentatge_nota)  {
        super(day, imageResource);
        mposition = position;
        mNote = note;
        mId = id;
        mDescription = description;
        fecha = String.valueOf(date);
        fecha = fecha + "/" + String.valueOf(month);
        fecha = fecha + "/" + String.valueOf(year);
        horario = String.valueOf(hourOfDay);
        if(minute == 0)
        {
            horario = horario + ":0" + String.valueOf(minute);
        }
        else
        {
            horario = horario + ":" + String.valueOf(minute);
        }
       if(seconds == 0)
       {
           horario = horario + ":0" + String.valueOf(seconds);
       }
       else
       {
           horario = horario + ":" + String.valueOf(seconds);
       }

        mtipo_tarea = tipo_tarea;
        masignatura = asignatura;
        mgrup = grup;
        mAula = Aula;
        mpercentatge_nota = percentatge_nota;
    }

    public int getMposition() {
        return mposition;
    }

    public  String getNote() {
        return mNote;
    }
    public  String getID() {
        return mId;
    }
    public String getDescription(){
        return mDescription;
    }
    public String getFecha(){
        return fecha;
    }
    public String getHorario(){
        return horario;
    }
    public String getMtipo_tarea() {
        return mtipo_tarea;
    }

    public String getMasignatura() {
        return masignatura;
    }

    public boolean isMgrup() {
        return mgrup;
    }

    public String getmAula() {
        return mAula;
    }

    public String getMpercentatge_nota() {
        return mpercentatge_nota;
    }

    private MyEventDay(Parcel in) {
        super((Calendar) Objects.requireNonNull(in.readSerializable()), in.readInt());
        mNote = in.readString();
    }
    public static final Creator<MyEventDay> CREATOR = new Creator<MyEventDay>() {
        @Override
        public MyEventDay createFromParcel(Parcel in) {
            return new MyEventDay(in);
        }
        @Override
        public MyEventDay[] newArray(int size) {
            return new MyEventDay[size];
        }
    };
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeSerializable(getCalendar());
        //parcel.writeInt(getImageResource());
        parcel.writeInt(i);
        parcel.writeString(mNote);
    }
    @Override
    public int describeContents() {
        return 0;
    }
}