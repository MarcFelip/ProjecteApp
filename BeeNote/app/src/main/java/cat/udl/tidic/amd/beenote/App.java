package cat.udl.tidic.amd.beenote;

import android.app.Application;

import cat.udl.tidic.amd.beenote.preferences.PreferencesProvider;


public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PreferencesProvider.init(this);
    }
}
