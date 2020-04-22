package cat.udl.tidic.amd.beenote;

import android.os.Parcel;
import android.os.Parcelable;

import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.utils.DateUtils;

import java.util.Calendar;
import java.util.Objects;

public class MyEventDay extends EventDay implements Parcelable {
    private String mNote;
    public MyEventDay(Calendar day, int imageResource, String note) {
        super(day, imageResource);
        mNote = note;
    }
    public  String getNote() {
        return mNote;
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
        parcel.writeInt(getImageResource());
        parcel.writeString(mNote);
    }
    @Override
    public int describeContents() {
        return 0;
    }
}