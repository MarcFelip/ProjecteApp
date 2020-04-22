package cat.udl.tidic.amd.beenote.ViewModels;

import androidx.lifecycle.ViewModel;

import cat.udl.tidic.amd.beenote.Repository.UserRepository;

public class menuPrincipal_ViewModel extends ViewModel {

    private UserRepository userRepository;
    private Long Date = getDate();

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

    public Long getDate(){
        return Date;
    }

    public void setDate(long date)
    {
           Date = date;
    }

    public String getCalendarID(){
        return userRepository.getCalendarID();
    }

    public void setCalendarID(long Calendarid)
    {
        userRepository.setCalendarID(Long.toString(Calendarid));
    }

    public void setDateToRepository(long date){
        userRepository.setDateCalendar(date);
    }
}
