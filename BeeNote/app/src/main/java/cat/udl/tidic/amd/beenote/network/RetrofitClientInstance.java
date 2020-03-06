package cat.udl.tidic.amd.beenote.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {

    private String Joan  = "http://192.168.43.8:8000";
    private String Emulador = "http://10.0.2.2:8000";
    private String Felip = "";
    private String Laura = "";
    private String Ion = "";

    private static Retrofit retrofit;
    //IMPORTANT --> Canviar la IP tambe el arxiu res/xml/network_security_config.xml
    private static final String BASE_URL = "http://192.168.43.8:8000";

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
