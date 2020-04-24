package cat.udl.tidic.amd.beenote.Repository;

import android.content.SharedPreferences;

import cat.udl.tidic.amd.beenote.preferences.PreferencesProvider;

public class UserRepository implements UserRepository_Interface {

    private SharedPreferences mPreferences;

    public UserRepository() {
        this.mPreferences = PreferencesProvider.providePreferences();
    }

    @Override
    public void setToken(String i) {
        this.mPreferences.edit().putString("Token",i).apply();
    }

    @Override
    public String getToken() {
        return this.mPreferences.getString("Token", "");
    }

    @Override
    public void setSalt(String i) {
        this.mPreferences.edit().putString("salt",i).apply();
    }

    @Override
    public String getSalt() {
        return this.mPreferences.getString("salt", "");
    }

    @Override
    public void setNumero(int i) {
        this.mPreferences.edit().putInt("numero",i).apply();
    }

    @Override
    public int getNumero() {
        return this.mPreferences.getInt("numero", 0);
    }

    @Override
    public void setCalendarID(String i) {
        this.mPreferences.edit().putString("CalendarID",i).apply();
    }

    @Override
    public String getCalendarID() {
        return this.mPreferences.getString("CalendarID", "");
    }

}
