package cat.udl.tidic.amd.beenote.Repository;

import cat.udl.tidic.amd.beenote.models.UserModel;

public interface UserRepository_Interface {

     void setToken(String i);
     String getToken();
     void setSalt(String i);
     String getSalt();
     void setNumero(int i);
     int getNumero();
     void setCalendarID(String i);
     String getCalendarID();
}
