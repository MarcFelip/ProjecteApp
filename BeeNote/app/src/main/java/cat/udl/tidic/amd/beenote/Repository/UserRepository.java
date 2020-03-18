package cat.udl.tidic.amd.beenote.Repository;

import android.content.SharedPreferences;

import com.google.gson.Gson;

import cat.udl.tidic.amd.beenote.Repository.TokenRepository;
import cat.udl.tidic.amd.beenote.models.UserModel;
import cat.udl.tidic.amd.beenote.preferences.PreferencesProvider;

public class UserRepository implements TokenRepository {

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
