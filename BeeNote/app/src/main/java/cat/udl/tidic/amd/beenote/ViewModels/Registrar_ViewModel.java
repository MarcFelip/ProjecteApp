package cat.udl.tidic.amd.beenote.ViewModels;

import androidx.lifecycle.ViewModel;

import cat.udl.tidic.amd.beenote.Repository.UserRepository;

public class Registrar_ViewModel extends ViewModel {

    private UserRepository userRepository;

    public Registrar_ViewModel() {
        this.userRepository = new UserRepository();
    }

    public void setRegistrado(String token)
    {
        userRepository.setRegistrado(token);
    }

    public String getResgistrado()
    {
        return userRepository.getRegistrado();
    }
}
