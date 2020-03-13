package cat.udl.tidic.amd.beenote.models;

import androidx.annotation.NonNull;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import cat.udl.tidic.amd.beenote.services.UserService;

public class UserModel {

    @SerializedName("created_at")
    private String created_at;
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;
    @SerializedName("email")
    private String email;
    @SerializedName("name")
    private String name;
    @SerializedName("surname")
    private String surname;
    @SerializedName("birthday")
    private String birthday;
    @SerializedName("genere")
    private String genere;
    @SerializedName("phone")
    private String phone;
    @SerializedName("photo")
    private String photo;


    public UserModel() {
    }


    public UserModel(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    @NonNull
    @Override
    public String toString(){
        return this.name + " " + this.surname;
    }

    private void JSON(){
        JSONObject object = new JSONObject();
        String diccionari ="";
        try {

            object.put("username", "Joan");
            object.put("password", "1234");
            object.put("email", "Joan@gmail.com");
            object.put("name", "Joan");
            object.put("surname", "Rialp");
            object.put("genere", "M");

            diccionari = object.toString();
        }
        catch(JSONException ex) {

        }

        JsonObject userJson = new JsonObject();
        userJson.addProperty("username","");
        userJson.addProperty("password", "1234");
        userJson.addProperty("email", "Joan@gmail.com");
        userJson.addProperty("name", "Joan");
        userJson.addProperty("surname", "Rialp");
        userJson.addProperty("genere", "M");

        //UserService.
    }
}
