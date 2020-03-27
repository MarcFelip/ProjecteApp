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

}
