package cat.udl.tidic.amd.beenote.ViewModels;

import androidx.lifecycle.ViewModel;

import cat.udl.tidic.amd.beenote.Repository.UserRepository;

public class menuPrincipal_ViewModel extends ViewModel {

    private UserRepository userRepository;

    public menuPrincipal_ViewModel() {
        this.userRepository = new UserRepository();
    }

    public void setToken(String token)
    {
        userRepository.setToken(token);
    }

    public String getToken()
    {
        return userRepository.getToken();
    }
}
