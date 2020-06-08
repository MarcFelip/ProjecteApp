package cat.udl.tidic.amd.beenote.ViewModels;

import android.content.ContentResolver;

import androidx.lifecycle.ViewModel;

import cat.udl.tidic.amd.beenote.Repository.UserRepository;

public class menuPrincipal_ViewModel extends ViewModel {

    private UserRepository userRepository;
    private String date = "0";
    private Long data2 = 0L;
    private String eventID = "0";
    private int positionEvent = 0;
    private String titulo = "";
    private String descripcion = "";
    private String tasca = "";
    private String asigantura = "";
    private String aula = "";
    private String porcentage = "";
    private ContentResolver contentResolver;

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


    public String getDate(){ return date; }

    public void setDate(String date) { this.date = date; }

    public Long getData2() {
        return data2;
    }
    public void setData2(Long data2) {
        this.data2 = data2;
    }


    public String getEventID(){
        return eventID;
    }

    public void setEventID(long eventid)
    {
        this.eventID = Long.toString(eventid);
    }

    public String getCalendarID(){
        return userRepository.getCalendarID();
    }

    public void setCalendarID(long Calendarid) { userRepository.setCalendarID(Long.toString(Calendarid)); }


    public String getMail (){
        return userRepository.getMail();
    }

    public void setMail(String mail)
    {
        userRepository.setMail(mail);
    }

    public int getPositionEvent() {
        return positionEvent;
    }

    public void setPositionEvent(int positionEvent) {
        this.positionEvent = positionEvent;
    }


    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTasca() {
        return tasca;
    }

    public void setTasca(String tasca) {
        this.tasca = tasca;
    }

    public String getAsigantura() {
        return asigantura;
    }

    public void setAsigantura(String asigantura) {
        this.asigantura = asigantura;
    }

    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public String getPorcentage() {
        return porcentage;
    }

    public void setPorcentage(String porcentage) {
        this.porcentage = porcentage;
    }


    public ContentResolver getContentResolver() {
        return contentResolver;
    }
    public void setContentResolver(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

}
