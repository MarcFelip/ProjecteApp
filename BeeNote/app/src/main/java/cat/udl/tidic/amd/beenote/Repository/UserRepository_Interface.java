package cat.udl.tidic.amd.beenote.Repository;

import android.content.ContentResolver;

public interface UserRepository_Interface {
     void setToken(String i);
     String getToken();
     void setSalt(String i);
     String getSalt();
     void setNumero(int i);
     int getNumero();
     void setCalendarID(String i);
     String getCalendarID();
     void setMail(String i);
     String getMail();
     void setPositionID(String i);
     String getPositionID();
     void setUserID(String i);
     String getUserID();
}
