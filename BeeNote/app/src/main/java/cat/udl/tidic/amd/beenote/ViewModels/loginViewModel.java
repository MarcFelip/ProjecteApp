package cat.udl.tidic.amd.beenote.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;

import cat.udl.tidic.amd.beenote.Repository.UserRepository;
import cat.udl.tidic.amd.beenote.models.TokenModel;

public class loginViewModel extends ViewModel {

    private UserRepository userRepository;
    private LiveData<List<TokenModel>> allNotes;

    public loginViewModel() {
        this.userRepository = new UserRepository();
    }

    public void Token(String token)
    {
        userRepository.setToken(token);
    }

    public String getToken()
    {
        return userRepository.getToken();
    }

    public LiveData<List<TokenModel>> prova(){
        return allNotes;
    }

    public void setMail(String mail)
    {
        userRepository.setMail(mail);
    }

    public void setUserID(String id)
    {
        userRepository.setUserID(id);
    }

}
