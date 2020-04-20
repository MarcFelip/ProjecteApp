package cat.udl.tidic.amd.beenote;

import android.app.Application;

import cat.udl.tidic.amd.beenote.Repository.UserRepository;
import cat.udl.tidic.amd.beenote.preferences.PreferencesProvider;


public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PreferencesProvider.init(this);

        UserRepository repository = new UserRepository();
        repository.setSalt("16");
        repository.setNumero(2900);
    }
}
