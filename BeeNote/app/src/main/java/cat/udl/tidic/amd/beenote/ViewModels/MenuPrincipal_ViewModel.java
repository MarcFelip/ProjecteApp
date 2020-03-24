package cat.udl.tidic.amd.beenote.ViewModels;

import androidx.lifecycle.ViewModel;

import cat.udl.tidic.amd.beenote.Repository.UserRepository;

public class MenuPrincipal_ViewModel extends ViewModel {

    private UserRepository userRepository;

    public MenuPrincipal_ViewModel() {
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
    public void setRegistrado(String token)
    {
        userRepository.setRegistrado(token);
    }
}
